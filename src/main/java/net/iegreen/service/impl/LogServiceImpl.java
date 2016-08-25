package net.iegreen.service.impl;

import net.iegreen.domain.dto.log.FrequencyMonitorLogListDto;
import net.iegreen.domain.dto.log.ReminderLogListDto;
import net.iegreen.service.LogService;
import net.iegreen.service.operation.FrequencyMonitorLogListDtoLoader;
import net.iegreen.service.operation.ReminderLogListDtoLoader;
import org.springframework.stereotype.Service;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
@Service("logService")
public class LogServiceImpl implements LogService {


    @Override
    public FrequencyMonitorLogListDto loadFrequencyMonitorLogListDto(FrequencyMonitorLogListDto listDto) {
        FrequencyMonitorLogListDtoLoader dtoLoader = new FrequencyMonitorLogListDtoLoader(listDto);
        return dtoLoader.load();
    }

    @Override
    public ReminderLogListDto loadReminderLogListDto(ReminderLogListDto listDto) {
        ReminderLogListDtoLoader dtoLoader = new ReminderLogListDtoLoader(listDto);
        return dtoLoader.load();
    }
}
