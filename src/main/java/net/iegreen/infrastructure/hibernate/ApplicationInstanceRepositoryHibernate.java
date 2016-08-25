package net.iegreen.infrastructure.hibernate;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.application.ApplicationInstanceWeixinUser;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.infrastructure.hibernate.queryhelper.impl.ApplicationInstanceListQueryHelper;
import net.iegreen.infrastructure.hibernate.queryhelper.impl.HBSearchInstancesQueryHelper;
import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
@Repository("applicationInstanceRepository")
public class ApplicationInstanceRepositoryHibernate extends AbstractRepositoryHibernate<ApplicationInstance> implements ApplicationInstanceRepository {


    @Override
    public List<ApplicationInstance> findApplicationInstanceList(Map<String, Object> map) {
        ApplicationInstanceListQueryHelper queryHelper = new ApplicationInstanceListQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalApplicationInstanceList(Map<String, Object> map) {
        ApplicationInstanceListQueryHelper queryHelper = new ApplicationInstanceListQueryHelper(session(), map);
        return queryHelper.getAmount();
    }


    @Override
    public int deleteInstanceFrequencyMonitorLogs(String instanceGuid) {
        final List<FrequencyMonitorLog> list = find("from FrequencyMonitorLog ml where ml.instance.guid = :instanceGuid", ImmutableMap.of("instanceGuid", instanceGuid));
        deleteAll(list);
        return list.size();
    }

    @Override
    public List<ApplicationInstance> findAllEnabledInstances() {
        String hql = " from ApplicationInstance ai where ai.archived = false and ai.enabled = true";
        return find(hql);
    }

    @Override
    public List<ApplicationInstance> findHBSearchInstances(Map<String, Object> map) {
        HBSearchInstancesQueryHelper queryHelper = new HBSearchInstancesQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalHBSearchInstances(Map<String, Object> map) {
        HBSearchInstancesQueryHelper queryHelper = new HBSearchInstancesQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public int deleteInstanceMonitoringReminderLogs(String instanceGuid) {
        final List<FrequencyMonitorLog> list = find("from MonitoringReminderLog ml where ml.instance.guid = :instanceGuid", ImmutableMap.of("instanceGuid", instanceGuid));
        deleteAll(list);
        return list.size();
    }

    @Override
    public int deleteApplicationInstanceWeixinUsers(ApplicationInstance instance) {
        final List<ApplicationInstanceWeixinUser> list = find(" from ApplicationInstanceWeixinUser wu where wu.applicationInstance = :instance", ImmutableMap.of("instance", instance));
        deleteAll(list);
        return list.size();
    }
}