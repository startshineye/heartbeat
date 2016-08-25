package net.iegreen.service.impl;

import net.iegreen.domain.dto.HBSearchDto;
import net.iegreen.domain.dto.IndexAdditionInstanceDto;
import net.iegreen.domain.dto.IndexDto;
import net.iegreen.domain.dto.MonitoringInstanceDto;
import net.iegreen.domain.shared.Application;
import net.iegreen.service.IndexService;
import net.iegreen.service.operation.search.HBSearchDtoLoader;
import net.iegreen.service.operation.IndexAdditionInstanceDtoLoader;
import net.iegreen.service.operation.IndexDtoLoader;
import net.iegreen.service.operation.MonitoringInstanceDtoLoader;
import org.springframework.stereotype.Service;

/**
 * @author Shengzhao Li
 */
@Service("indexService")
public class IndexServiceImpl implements IndexService {


    @Override
    public IndexDto loadIndexDto(IndexDto indexDto) {
        IndexDtoLoader dtoLoader = new IndexDtoLoader(indexDto);
        return dtoLoader.load();
    }

    @Override
    public IndexAdditionInstanceDto loadIndexAdditionInstanceDto(IndexAdditionInstanceDto additionInstanceDto) {
        IndexAdditionInstanceDtoLoader dtoLoader = new IndexAdditionInstanceDtoLoader(additionInstanceDto);
        return dtoLoader.load();
    }

    @Override
    public MonitoringInstanceDto loadMonitoringInstanceDto(String guid) {
        MonitoringInstanceDtoLoader dtoLoader = new MonitoringInstanceDtoLoader(guid);
        return dtoLoader.load();
    }

    @Override
    public HBSearchDto loadHBSearchDto(HBSearchDto searchDto) {
        HBSearchDtoLoader dtoLoader = new HBSearchDtoLoader(searchDto);
        return dtoLoader.load();
    }

    @Override
    public boolean loadAllowUserRegister() {
        return Application.systemSetting().allowUserRegister();
    }
}