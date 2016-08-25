package net.iegreen.domain.log.reminder;

import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.MonitoringReminderLog;

/**
 * @author Shengzhao Li
 */
public interface PerMonitoringReminderSender {


    public boolean support(FrequencyMonitorLog monitorLog);

    public void send(MonitoringReminderLog reminderLog, FrequencyMonitorLog monitorLog);

}