package net.iegreen.service;

import net.iegreen.domain.dto.log.FrequencyMonitorLogListDto;
import net.iegreen.domain.dto.log.ReminderLogListDto;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */

public interface LogService {

    FrequencyMonitorLogListDto loadFrequencyMonitorLogListDto(FrequencyMonitorLogListDto listDto);

    ReminderLogListDto loadReminderLogListDto(ReminderLogListDto listDto);
}