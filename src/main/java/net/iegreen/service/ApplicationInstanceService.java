package net.iegreen.service;

import net.iegreen.domain.dto.application.ApplicationInstanceFormDto;
import net.iegreen.domain.dto.application.ApplicationInstanceListDto;
import net.iegreen.domain.dto.application.InstanceStatisticsDto;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */

public interface ApplicationInstanceService {

    void loadApplicationInstanceListDto(ApplicationInstanceListDto listDto);

    ApplicationInstanceFormDto loadApplicationInstanceFormDto(String guid);

    void persistApplicationInstance(ApplicationInstanceFormDto formDto);

    boolean enableApplicationInstance(String guid);
    /***
     * job执行动作
     * @param instanceGuid
     */
    void executePerHeartBeatByInstanceGuid(String instanceGuid);

    boolean stopMonitoringApplicationInstance(String guid);

    boolean deleteApplicationInstance(String guid);

    InstanceStatisticsDto loadInstanceStatisticsDto(String guid);
}