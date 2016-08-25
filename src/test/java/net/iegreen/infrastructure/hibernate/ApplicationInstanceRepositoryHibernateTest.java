package net.iegreen.infrastructure.hibernate;

import net.iegreen.domain.application.*;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.MonitoringReminderLog;
import net.iegreen.domain.shared.GuidGenerator;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.domain.user.WeixinUser;
import net.iegreen.infrastructure.AbstractRepositoryTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.testng.Assert.*;

/**
 * 15-1-2
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceRepositoryHibernateTest extends AbstractRepositoryTest {

    @Autowired
    private ApplicationInstanceRepository repository;


    @Test
    public void deleteApplicationInstanceWeixinUsers() {

        ApplicationInstance instance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(instance);

        final int i = repository.deleteApplicationInstanceWeixinUsers(instance);
        assertEquals(i, 0);
    }


    @Test
    public void testApplicationInstanceWeixinUser() {
        ApplicationInstance instance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(instance);

        WeixinUser weixinUser = new WeixinUser().nickName("no").openId("esdf");
        repository.saveOrUpdate(weixinUser);


        ApplicationInstanceWeixinUser user = new ApplicationInstanceWeixinUser()
                .applicationInstance(instance).weixinUser(weixinUser);
        repository.saveOrUpdate(user);

        fullClean();

        final ApplicationInstanceWeixinUser weixinUser1 = repository.findByGuid(user.guid(), ApplicationInstanceWeixinUser.class);
        assertNotNull(weixinUser1);
        assertNotNull(weixinUser1.applicationInstance());
        assertNotNull(weixinUser1.weixinUser());
    }

    @Test
    public void testInstanceMonitorURLParameter() {
        ApplicationInstance instance = new ApplicationInstance()
                .instanceName("Andaily");

        final InstanceMonitorURLParameter parameter = new InstanceMonitorURLParameter()
                .key("guid").value(GuidGenerator.generate()).instance(instance);
        instance.addMonitorURLParameter(parameter);

        final InstanceMonitorURLParameter parameter2 = new InstanceMonitorURLParameter()
                .key("name").value("odedd").instance(instance);
        instance.addMonitorURLParameter(parameter2);

        repository.saveOrUpdate(instance);

        fullClean();

        final ApplicationInstance instance1 = repository.findByGuid(instance.guid(), ApplicationInstance.class);
        assertNotNull(instance1);
        final ApplicationInstanceURL url = instance1.instanceURL();
        assertNotNull(url);

        final List<InstanceMonitorURLParameter> parameters = url.urlParameters();
        assertEquals(parameters.size(), 2);

        final InstanceMonitorURLParameter parameter1 = parameters.get(0);
        assertNotNull(parameter1);
        assertNotNull(parameter1.key());
        assertNotNull(parameter1.value());
        assertNotNull(parameter1.instance());

    }


    @Test
    public void findApplicationInstanceList() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("instanceName", "A");

        List<ApplicationInstance> list = repository.findApplicationInstanceList(map);
        assertEquals(list.size(), 1);

        int i = repository.totalApplicationInstanceList(map);
        assertEquals(i, 1);
    }

    @Test
    public void findHBSearchInstances() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        Map<String, Object> map = new HashMap<>();
        map.put("user", SecurityUtils.currentUser());
        map.put("perPageSize", 20);
        map.put("startIndex", 0);
        map.put("key", "A");

        fullClean();

        final List<ApplicationInstance> list = repository.findHBSearchInstances(map);
        assertEquals(list.size(), 1);

        final int i = repository.totalHBSearchInstances(map);
        assertEquals(i, 1);
    }

    @Test
    public void findAllEnabledInstances() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        fullClean();

        List<ApplicationInstance> list = repository.findAllEnabledInstances();
        assertTrue(list.size() == 0);
    }


    @Test
    public void deleteInstanceFrequencyMonitorLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance);
        repository.saveOrUpdate(monitorLog);

        fullClean();

        final int count = repository.deleteInstanceFrequencyMonitorLogs(applicationInstance.guid());
        assertEquals(count, 1);


    }

    @Test
    public void deleteInstanceMonitoringReminderLogs() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        repository.saveOrUpdate(applicationInstance);

        FrequencyMonitorLog monitorLog = new FrequencyMonitorLog()
                .instance(applicationInstance);
        repository.saveOrUpdate(monitorLog);

        MonitoringReminderLog reminderLog = new MonitoringReminderLog()
                .instance(applicationInstance);
        repository.saveOrUpdate(reminderLog);

        fullClean();

        final int count = repository.deleteInstanceMonitoringReminderLogs(applicationInstance.guid());
        assertEquals(count, 1);


    }

    @Test
    public void testApplicationInstance() {
        ApplicationInstance applicationInstance = new ApplicationInstance()
                .instanceName("Andaily");
        applicationInstance.instanceURL().contentType("text/xml");

        repository.saveOrUpdate(applicationInstance);

        fullClean();

        ApplicationInstance instance = repository.findByGuid(applicationInstance.guid(), ApplicationInstance.class);
        assertNotNull(instance);
        assertNotNull(instance.requestMethod());
        assertNotNull(instance.instanceName());
        assertFalse(instance.privateInstance());

        assertNotNull(instance.instanceURL().contentType());

    }

}
