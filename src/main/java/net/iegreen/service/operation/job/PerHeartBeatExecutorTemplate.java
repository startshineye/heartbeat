package net.iegreen.service.operation.job;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.reminder.PerMonitoringReminder;
import net.iegreen.domain.shared.BeanProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/***
 * @author pzy
 *
 */
public abstract class PerHeartBeatExecutorTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(PerHeartBeatExecutorTemplate.class);

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private String instanceGuid;

    public PerHeartBeatExecutorTemplate(String instanceGuid) {
    	this.instanceGuid = instanceGuid;
    }

    /*
    * Execute heart-beat
    *
    * 1.Send request and checking response
    * 2.Generate FrequencyMonitorLog
    * 3. If failed will notice
    * */
    public void execute() {
        final ApplicationInstance instance = instanceRepository.findByGuid(instanceGuid, ApplicationInstance.class);
        final FrequencyMonitorLog monitorLog = generateMonitorLog(instance);

        instanceRepository.saveOrUpdate(monitorLog);
        LOGGER.info("Generate and persist FrequencyMonitorLog[{}]", monitorLog);
        //reminder
        remind(monitorLog);
    }

    private  void remind(FrequencyMonitorLog monitorLog) {
        PerMonitoringReminder reminder = new PerMonitoringReminder(monitorLog);
        reminder.remind();
    }

    public abstract FrequencyMonitorLog generateMonitorLog(ApplicationInstance instance) ;
}