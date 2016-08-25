package net.iegreen.domain.application;

import net.iegreen.domain.AbstractDomain;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.user.User;
import net.iegreen.domain.user.UserRepository;
import net.iegreen.domain.user.WeixinUser;

import javax.persistence.*;
import java.util.List;

/**
 * A server instance that is need monitoring
 * 定义一个需要心跳监测的应用实例
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "application_instance")
public class ApplicationInstance extends AbstractDomain {

    private static final long serialVersionUID = 1826152029135090793L;


    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

    //实例名称
    @Column(name = "instance_name")
    private String instanceName;


    @Embedded
    private ApplicationInstanceURL instanceURL = new ApplicationInstanceURL();

    /**
     * 连接时超时的时间
     * 0,表示无超时
     */
    @Column(name = "max_connection_seconds")
    private int maxConnectionSeconds;

    //enabled or disabled
    //是否启用
    @Column(name = "enabled")
    private boolean enabled;

    /**
     * 心跳检测频率, 默认30秒
     */
    @Column(name = "frequency")
    @Enumerated(value = EnumType.STRING)
    private HeartBeatFrequency frequency = HeartBeatFrequency.THIRTY;

    /**
     * 若出现测试正常或不正常时提醒的邮件地址
     * 若有多个请用英文分号(;)分隔
     */
    @Column(name = "email")
    private String email;


    /**
     * Schedule中的任务名称,
     * 当启用该监听任务时, 将会有唯一对应的jobName
     */
    @Column(name = "job_name")
    private String jobName;

    /**
     * 备注信息
     */
    @Column(name = "remark")
    private String remark;

    /**
     * The instance creator (owner)
     */
    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;


    /**
     * 是否为私有应用, 私有应用只有自己登录后才能查看,
     * 别人不能看见, 默认为false,公开
     * <p/>
     * From Version 0.5
     */
    @Column(name = "private_instance")
    private boolean privateInstance;


    /**
     * 当连续连接失败 指定的次数后才发送提醒.
     * 这用于处理有时连接在检测链接状态的时候，不要发现一次链接故障的时候就马上发邮件通知，
     * 这个过程可能因为网络故障（如常见的：丢包、dns故障）而出现问题
     * <p/>
     * 默认为连续2次
     */
    @Column(name = "continue_failed_times")
    private int continueFailedTimes = 2;


    /**
     * Default
     */
    public ApplicationInstance() {
    }


    public int continueFailedTimes() {
        return continueFailedTimes;
    }

    public ApplicationInstance continueFailedTimes(int continueFailedTimes) {
        this.continueFailedTimes = continueFailedTimes;
        return this;
    }

    public boolean privateInstance() {
        return privateInstance;
    }

    public ApplicationInstance privateInstance(boolean privateInstance) {
        this.privateInstance = privateInstance;
        return this;
    }

    public List<WeixinUser> weixinUsers() {
        return userRepository.findWeixinUsersByInstanceGuid(this.guid);
    }

    public ApplicationInstanceURL instanceURL() {
        return instanceURL;
    }

    public ApplicationInstance addMonitorURLParameter(InstanceMonitorURLParameter urlParameter) {
        instanceURL.urlParameters().add(urlParameter);
        return this;
    }

    public User creator() {
        return creator;
    }

    public ApplicationInstance creator(User creator) {
        this.creator = creator;
        return this;
    }

    public MonitorUrlRequestMethod requestMethod() {
        return instanceURL.requestMethod();
    }

    public ApplicationInstance requestMethod(MonitorUrlRequestMethod requestMethod) {
        instanceURL.requestMethod(requestMethod);
        return this;
    }

    public String instanceName() {
        return instanceName;
    }

    public ApplicationInstance instanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public String monitorUrl() {
        return instanceURL.monitorUrl();
    }

    public ApplicationInstance monitorUrl(String monitorUrl) {
        instanceURL.monitorUrl(monitorUrl);
        return this;
    }

    public int maxConnectionSeconds() {
        return maxConnectionSeconds;
    }

    public ApplicationInstance maxConnectionSeconds(int maxConnectionSeconds) {
        this.maxConnectionSeconds = maxConnectionSeconds;
        return this;
    }

    public boolean enabled() {
        return enabled;
    }

    public ApplicationInstance enabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public HeartBeatFrequency frequency() {
        return frequency;
    }

    public ApplicationInstance frequency(HeartBeatFrequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public String email() {
        return email;
    }

    public String[] emailAsArray() {
        return email.indexOf(";") > 0 ? email.split(";") : new String[]{email};
    }

    public ApplicationInstance email(String email) {
        this.email = email;
        return this;
    }

    public String jobName() {
        return jobName;
    }

    public ApplicationInstance jobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String remark() {
        return remark;
    }

    public ApplicationInstance remark(String remark) {
        this.remark = remark;
        return this;
    }
}