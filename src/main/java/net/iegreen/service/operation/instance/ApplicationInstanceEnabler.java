package net.iegreen.service.operation.instance;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.infrastructure.scheduler.DynamicJob;
import net.iegreen.infrastructure.scheduler.JobParamManager;
import net.iegreen.service.operation.job.MonitoringInstanceJob;
import net.iegreen.infrastructure.scheduler.DynamicSchedulerFactory;
import org.apache.commons.lang.StringUtils;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Shengzhao Li
 */
public class ApplicationInstanceEnabler {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInstanceEnabler.class);

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private String guid;

    public ApplicationInstanceEnabler(String guid) {
        this.guid = guid;
    }

    public boolean enable() {
        final ApplicationInstance instance = instanceRepository.findByGuid(guid, ApplicationInstance.class);
        if (instance.enabled()) {
            LOGGER.debug("<{}> Instance[guid={}] already enabled, ignore it", username(), instance.guid());
            return false;
        }

        final boolean addSuccessful = startupMonitoringJob(instance);
        if (!addSuccessful) {
            LOGGER.debug("<{}> NOTE: Add MonitoringJob[jobName={}] failed", username(), instance.jobName());
            return false;
        }

        //update
        instance.enabled(true);
        LOGGER.debug("<{}> Update ApplicationInstance[guid={}] enabled=true,jobName={}", username(), instance.guid(), instance.jobName());

        return true;
    }
    /***
     * 将任务加入调度中心
     * @param instance
     * @return
     */
    private boolean startupMonitoringJob(ApplicationInstance instance) {
        final String jobName = getAndSetJobName(instance);

        DynamicJob job = new DynamicJob(jobName)
                .cronExpression(instance.frequency().getCronExpression())
                .target(MonitoringInstanceJob.class)
                .addJobData(MonitoringInstanceJob.APPLICATION_INSTANCE_GUID, instance.guid());

        return executeStartup(instance, job);
    }

    private boolean executeStartup(ApplicationInstance instance, DynamicJob job) {
        boolean result = false;
        try {
            if (DynamicSchedulerFactory.existJob(job)) {
                result = DynamicSchedulerFactory.resumeJob(job);
                LOGGER.debug("<{}> Resume  [{}] by ApplicationInstance[guid={},instanceName={}] result: {}", username(), job, instance.guid(), instance.instanceName(), result);
            } else {
                result = DynamicSchedulerFactory.registerJob(job);
                LOGGER.debug("<{}> Register  [{}] by ApplicationInstance[guid={},instanceName={}] result: {}", username(), job, instance.guid(), instance.instanceName(), result);
            }
        } catch (SchedulerException e) {
            LOGGER.error("<{}> Register [" + job + "] failed", username(), e);
        }
        return result;
    }

    private String getAndSetJobName(ApplicationInstance instance) {
        String jobName = instance.jobName();
        if (StringUtils.isEmpty(jobName)) {
            jobName = JobParamManager.generateMonitoringInstanceJobName(instance.guid());
            instance.jobName(jobName);
        }
        return jobName;
    }

    private String username() {
        return SecurityUtils.currentUsername();
    }
}