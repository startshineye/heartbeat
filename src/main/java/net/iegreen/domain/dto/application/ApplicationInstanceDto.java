package net.iegreen.domain.dto.application;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.HeartBeatFrequency;
import net.iegreen.domain.application.MonitorUrlRequestMethod;
import net.iegreen.domain.dto.AbstractDto;
import net.iegreen.domain.dto.user.WeixinUserDto;
import net.iegreen.domain.user.User;
import net.iegreen.infrastructure.DateUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceDto extends AbstractDto {

    private static final long serialVersionUID = 8167165378295695877L;
    protected String createTime;

    protected String instanceName;

    //Monitoring url: http://axxx.com/
    //监测地址URL
    protected String monitorUrl;

    protected MonitorUrlRequestMethod requestMethod = MonitorUrlRequestMethod.GET;

    protected String contentType;

    /**
     * 连接时超时的时间
     * 0,表示无超时
     */
    protected int maxConnectionSeconds;

    //enabled or disabled
    //是否启用
    protected boolean enabled;

    /**
     * 心跳检测频率, 默认30秒
     */
    protected HeartBeatFrequency frequency = HeartBeatFrequency.THIRTY;

    /**
     * 若出现测试正常或不正常时提醒的邮件地址
     * 若有多个请用英文分号(;)分隔
     */
    protected String email;


    /**
     * Schedule中的任务名称,
     * 当启用该监听任务时, 将会有唯一对应的jobName
     */
    protected String jobName;

    /**
     * 备注信息
     */
    protected String remark;

    protected String creatorName;
    protected String creatorGuid;

    protected boolean privateInstance;

    protected int continueFailedTimes = 2;


    protected List<WeixinUserDto> weixinUserDtos = new ArrayList<>();

    public ApplicationInstanceDto() {
    }

    public ApplicationInstanceDto(ApplicationInstance instance) {
        super(instance.guid());
        this.instanceName = instance.instanceName();
        this.createTime = DateUtils.toDateTime(instance.createTime());

        this.email = instance.email();
        this.remark = instance.remark();
        this.jobName = instance.jobName();

        this.maxConnectionSeconds = instance.maxConnectionSeconds();
        this.monitorUrl = instance.monitorUrl();
        this.frequency = instance.frequency();

        this.enabled = instance.enabled();
        this.requestMethod = instance.requestMethod();
        this.continueFailedTimes = instance.continueFailedTimes();

        final User creator = instance.creator();
        this.creatorGuid = creator.guid();
        this.creatorName = creator.username();

        this.contentType = instance.instanceURL().contentType();
        this.weixinUserDtos = WeixinUserDto.toDtos(instance.weixinUsers());
        this.privateInstance = instance.privateInstance();
    }


    public int getContinueFailedTimes() {
        return continueFailedTimes;
    }

    public void setContinueFailedTimes(int continueFailedTimes) {
        this.continueFailedTimes = continueFailedTimes;
    }

    public boolean isPrivateInstance() {
        return privateInstance;
    }

    public void setPrivateInstance(boolean privateInstance) {
        this.privateInstance = privateInstance;
    }

    public List<WeixinUserDto> getWeixinUserDtos() {
        return weixinUserDtos;
    }

    public void setWeixinUserDtos(List<WeixinUserDto> weixinUserDtos) {
        this.weixinUserDtos = weixinUserDtos;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public String getCreatorGuid() {
        return creatorGuid;
    }

    public void setCreatorGuid(String creatorGuid) {
        this.creatorGuid = creatorGuid;
    }

    public MonitorUrlRequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(MonitorUrlRequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public void setInstanceName(String instanceName) {
        this.instanceName = instanceName;
    }

    public String getMonitorUrl() {
        return monitorUrl;
    }

    public void setMonitorUrl(String monitorUrl) {
        this.monitorUrl = monitorUrl;
    }

    public int getMaxConnectionSeconds() {
        return maxConnectionSeconds;
    }

    public void setMaxConnectionSeconds(int maxConnectionSeconds) {
        this.maxConnectionSeconds = maxConnectionSeconds;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public HeartBeatFrequency getFrequency() {
        return frequency;
    }

    public void setFrequency(HeartBeatFrequency frequency) {
        this.frequency = frequency;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static List<ApplicationInstanceDto> toDtos(List<ApplicationInstance> instances) {
        List<ApplicationInstanceDto> dtos = new ArrayList<>(instances.size());
        for (ApplicationInstance instance : instances) {
            dtos.add(new ApplicationInstanceDto(instance));
        }
        return dtos;
    }
}
