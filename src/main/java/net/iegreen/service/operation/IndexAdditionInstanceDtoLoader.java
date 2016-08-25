package net.iegreen.service.operation;

import net.iegreen.domain.dto.IndexAdditionInstanceDto;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.infrastructure.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class IndexAdditionInstanceDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private IndexAdditionInstanceDto additionInstanceDto;

    public IndexAdditionInstanceDtoLoader(IndexAdditionInstanceDto additionInstanceDto) {
        this.additionInstanceDto = additionInstanceDto;
    }

    public IndexAdditionInstanceDto load() {

        List<FrequencyMonitorLog> monitorLogs = logRepository.findFrequencyMonitorLogs(additionInstanceDto.getGuid(), additionInstanceDto.getLastLogDate());
        MonitoringChartDataGenerator chartDataGenerator = new MonitoringChartDataGenerator(monitorLogs);
        additionInstanceDto.setAdditionData(chartDataGenerator.generateAdditionData());

        updateLastLogDate(monitorLogs);
        return additionInstanceDto;
    }

    private void updateLastLogDate(List<FrequencyMonitorLog> monitorLogs) {
        if (monitorLogs.isEmpty()) {
            return;
        }
        final Date time = monitorLogs.get(0).createTime();
        additionInstanceDto.setLastLogDate(DateUtils.toDateText(time, DateUtils.DEFAULT_DATE_TIME_FORMAT));
    }
}