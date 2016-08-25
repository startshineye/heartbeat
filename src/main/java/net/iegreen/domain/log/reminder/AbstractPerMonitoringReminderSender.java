package net.iegreen.domain.log.reminder;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.shared.Application;
import net.iegreen.infrastructure.DateUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 2016/5/19
 *
 * @author Shengzhao Li
 */
public abstract class AbstractPerMonitoringReminderSender implements PerMonitoringReminderSender {

    private static final String INSTANCE_MONITORING_URL = "monitoring/%s.hb";


    protected String instanceUrl(String instanceGuid) {
        String url = String.format(INSTANCE_MONITORING_URL, instanceGuid);
        return Application.host() + url;
    }


    protected Map<String, Object> contentModel(FrequencyMonitorLog monitorLog) {
        final ApplicationInstance instance = monitorLog.instance();

        Map<String, Object> model = new HashMap<>();
        model.put("instanceName", instance.instanceName());
        model.put("url", instance.monitorUrl());
        model.put("costTime", monitorLog.costTime());
        model.put("time", DateUtils.toDateText(monitorLog.createTime(), DateUtils.DEFAULT_DATE_TIME_FORMAT));
        model.put("instanceUrl", instanceUrl(instance.guid()));
        model.put("host", Application.host());
        model.put("remark", monitorLog.remark());
        return model;
    }


}
