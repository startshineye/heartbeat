package net.iegreen.domain.log;

import net.iegreen.domain.AbstractDomain;
import net.iegreen.domain.application.ApplicationInstance;
import org.apache.commons.lang.StringUtils;

import javax.persistence.*;

/**
 * 每次监控若有发送提醒(reminder),则记录一条日志
 *
 * @author Shengzhao Li
 */
@Entity
@Table(name = "monitoring_reminder_log")
public class MonitoringReminderLog extends AbstractDomain {


    private static final long serialVersionUID = 6278857846399205090L;
    @ManyToOne
    @JoinColumn(name = "monitor_log_id")
    private FrequencyMonitorLog monitorLog;

    @ManyToOne
    @JoinColumn(name = "instance_id")
    private ApplicationInstance instance;

    //是否为监控由不正常 -> 正常的提醒日志
    @Column(name = "change_normal")
    private boolean changeNormal;


    //提醒接收的 邮件地址
    @Column(name = "receive_email")
    private String receiveEmail;

    //HTML format
    @Column(name = "email_content")
    private String emailContent;

    //提醒接收的 微信openId
    @Column(name = "openid")
    private String openId;

    //微信的内容
    @Column(name = "wechat_content")
    private String weChartContent;

    /*
   * Default constructor
   * */
    public MonitoringReminderLog() {
    }


    public String openId() {
        return openId;
    }

    public MonitoringReminderLog openId(String openId) {
        this.openId = openId;
        return this;
    }

    public MonitoringReminderLog appendOpenId(String openId) {
        if (StringUtils.isEmpty(this.openId)) {
            this.openId = openId;
        } else {
            this.openId = this.openId + "," + openId;
        }
        return this;
    }

    public String weChartContent() {
        return weChartContent;
    }

    public MonitoringReminderLog weChartContent(String weChartContent) {
        this.weChartContent = weChartContent;
        return this;
    }

    public String emailContent() {
        return emailContent;
    }

    public MonitoringReminderLog emailContent(String emailContent) {
        this.emailContent = emailContent;
        return this;
    }

    public MonitoringReminderLog(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
        this.instance = monitorLog.instance();
    }

    public ApplicationInstance instance() {
        return instance;
    }

    public MonitoringReminderLog instance(ApplicationInstance instance) {
        this.instance = instance;
        return this;
    }

    public FrequencyMonitorLog monitorLog() {
        return monitorLog;
    }

    public MonitoringReminderLog monitorLog(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
        return this;
    }

    public boolean changeNormal() {
        return changeNormal;
    }

    public MonitoringReminderLog changeNormal(boolean changeNormal) {
        this.changeNormal = changeNormal;
        return this;
    }

    public String receiveEmail() {
        return receiveEmail;
    }

    public MonitoringReminderLog receiveEmail(String receiveEmail) {
        this.receiveEmail = receiveEmail;
        return this;
    }
}