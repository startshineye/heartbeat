package net.iegreen.domain.log.reminder;

import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.domain.shared.BeanProvider;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public class PerMonitoringReminderChecker {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private final FrequencyMonitorLog monitorLog;

    public PerMonitoringReminderChecker(FrequencyMonitorLog monitorLog) {
        this.monitorLog = monitorLog;
    }


    /**
     * 判断是否需要发送提醒
     *
     * @return True is need, otherwise not need
     */
    public boolean isNeedReminder() {

        if (monitorLog.normal()) {
            return changeNormalNeedReminder();
        } else {
            return changeNotNormalNeedReminder();
        }

    }


    /*
    * 若是变为不正常, 则需要根据 Instance 设置来判断是否发送
    * */
    private boolean changeNotNormalNeedReminder() {

        //查询设置的最近几条监控记录
        final int continueFailedTimes = monitorLog.instance().continueFailedTimes();
        List<FrequencyMonitorLog> lastLogs = logRepository.findLastLogsByCurrentLog(monitorLog, continueFailedTimes);

        for (FrequencyMonitorLog log : lastLogs) {
            //只要有一条是正常则不需要发送
            if (log.normal()) {
                return false;
            }
        }
        return true;
    }

    /*
    * 若是变为正常,则只判断最近的一条数据即可
    * */
    private boolean changeNormalNeedReminder() {
        FrequencyMonitorLog lastLog = logRepository.findLastLogByCurrentLog(monitorLog);
        if (lastLog == null) {
            return !monitorLog.normal();
        } else {
            return lastLog.normal() != monitorLog.normal();
        }
    }
}