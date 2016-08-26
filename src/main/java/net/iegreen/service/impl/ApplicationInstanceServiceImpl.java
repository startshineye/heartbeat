package net.iegreen.service.impl;

import net.iegreen.domain.dto.application.ApplicationInstanceFormDto;
import net.iegreen.domain.dto.application.ApplicationInstanceListDto;
import net.iegreen.domain.dto.application.InstanceStatisticsDto;
import net.iegreen.service.ApplicationInstanceService;
import net.iegreen.service.operation.instance.*;
import net.iegreen.service.operation.job.PerHeartBeatExecutor;
import org.springframework.stereotype.Service;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
@Service("applicationInstanceService")
public class ApplicationInstanceServiceImpl implements ApplicationInstanceService {



    @Override
    public void loadApplicationInstanceListDto(ApplicationInstanceListDto listDto) {
        ApplicationInstanceListDtoLoader dtoLoader = new ApplicationInstanceListDtoLoader(listDto);
        dtoLoader.load();
    }

    @Override
    public ApplicationInstanceFormDto loadApplicationInstanceFormDto(String guid) {
        ApplicationInstanceFormDtoLoader dtoLoader = new ApplicationInstanceFormDtoLoader(guid);
        return dtoLoader.load();
    }

    @Override
    public void persistApplicationInstance(ApplicationInstanceFormDto formDto) {
        ApplicationInstanceFormDtoPersister persister = new ApplicationInstanceFormDtoPersister(formDto);
        persister.persist();
    }
    /***
     * 使监控实例生效
     */
    @Override
    public boolean enableApplicationInstance(String guid) {
        ApplicationInstanceEnabler instanceEnabler = new ApplicationInstanceEnabler(guid);
        return instanceEnabler.enable();
    }
    
    /***
     * job 具体监控操作 ，发送http请求
     */
    @Override
    public void executePerHeartBeatByInstanceGuid(String instanceGuid) {
        PerHeartBeatExecutor perHeartBeatExecutor = new PerHeartBeatExecutor(instanceGuid);
        perHeartBeatExecutor.execute();
    }

    @Override
    public boolean stopMonitoringApplicationInstance(String guid) {
        MonitoringApplicationInstanceKiller instanceKiller = new MonitoringApplicationInstanceKiller(guid);
        return instanceKiller.kill();
    }

    @Override
    public boolean deleteApplicationInstance(String guid) {
        ApplicationInstanceDeleter instanceDeleter = new ApplicationInstanceDeleter(guid);
        return instanceDeleter.delete();
    }

    @Override
    public InstanceStatisticsDto loadInstanceStatisticsDto(String guid) {
        InstanceStatisticsDtoLoader statisticsDtoLoader = new InstanceStatisticsDtoLoader(guid);
        return statisticsDtoLoader.load();
    }
}
