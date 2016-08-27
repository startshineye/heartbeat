package net.iegreen.service.operation.job;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.log.FrequencyMonitorLog;

/**
 * @author Shengzhao Li
 */
public class PerHeartBeatExecutor extends PerHeartBeatExecutorTemplate {

	public PerHeartBeatExecutor(String instanceGuid) {
		super(instanceGuid);
	}

	@Override
	public FrequencyMonitorLog generateMonitorLog(ApplicationInstance instance) {
		FrequencyMonitorLogGenerator monitorLogGenerator = new FrequencyMonitorLogGenerator(instance);
		return monitorLogGenerator.generate();
	}

}