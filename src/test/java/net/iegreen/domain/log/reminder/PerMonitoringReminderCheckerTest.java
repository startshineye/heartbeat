package net.iegreen.domain.log.reminder;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.infrastructure.AbstractRepositoryTest;
import net.iegreen.infrastructure.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author Shengzhao Li
 */
public class PerMonitoringReminderCheckerTest extends AbstractRepositoryTest {


    @Autowired
    private LogRepository logRepository;

    @Test
    public void testIsNeedReminder() throws Exception {

        ApplicationInstance applicationInstance = new ApplicationInstance()
                .continueFailedTimes(1)
                .instanceName("Andaily");
        logRepository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:12", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog);

        fullClean();

        //case 1
        PerMonitoringReminderChecker reminderChecker = new PerMonitoringReminderChecker(monitorLog);
        final boolean needReminder = reminderChecker.isNeedReminder();
        assertTrue(needReminder);


        //case 2
        FrequencyMonitorLog monitorLog2 = new FrequencyMonitorLog()
                .instance(applicationInstance).normal(false)
                .costTime(23).createTime(DateUtils.getDate("2014-12-12 12:12:10", DateUtils.DEFAULT_DATE_TIME_FORMAT));
        logRepository.saveOrUpdate(monitorLog2);

        fullClean();

        reminderChecker = new PerMonitoringReminderChecker(monitorLog);
        assertTrue(reminderChecker.isNeedReminder());

    }
}