package net.iegreen.service;

import net.iegreen.domain.dto.HBSearchDto;
import net.iegreen.domain.dto.IndexAdditionInstanceDto;
import net.iegreen.domain.dto.IndexDto;
import net.iegreen.domain.dto.MonitoringInstanceDto;

/**
 * @author Shengzhao Li
 */

public interface IndexService {

    IndexDto loadIndexDto(IndexDto indexDto);

    IndexAdditionInstanceDto loadIndexAdditionInstanceDto(IndexAdditionInstanceDto additionInstanceDto);

    MonitoringInstanceDto loadMonitoringInstanceDto(String guid);

    HBSearchDto loadHBSearchDto(HBSearchDto searchDto);

    boolean loadAllowUserRegister();
}