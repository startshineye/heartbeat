package net.iegreen.service.operation;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.dto.MonitoringInstanceDto;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.infrastructure.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * @author Shengzhao Li
 */
public class MonitoringInstanceDtoLoader {

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private String guid;

    public MonitoringInstanceDtoLoader(String guid) {
        this.guid = guid;
    }

    public MonitoringInstanceDto load() {
        final ApplicationInstance instance = instanceRepository.findByGuid(guid, ApplicationInstance.class);
        final MonitoringInstanceDto instanceDto = new MonitoringInstanceDto(instance);

        loadChartData(instance, instanceDto);

        return instanceDto;
    }

    private void loadChartData(ApplicationInstance instance, MonitoringInstanceDto instanceDto) {
        //default 20 result
        List<FrequencyMonitorLog> monitorLogs = logRepository.findLatestFrequencyMonitorLogs(instance, 20);

        MonitoringChartDataGenerator chartDataGenerator = new MonitoringChartDataGenerator(monitorLogs);
        instanceDto.setCategoryData(chartDataGenerator.generateCategoryData());
        instanceDto.setSeriesData(chartDataGenerator.generateSeriesData());
        lastLogDate(instanceDto, monitorLogs);
    }


    private void lastLogDate(MonitoringInstanceDto indexInstanceDto, List<FrequencyMonitorLog> monitorLogs) {
        final Date time = monitorLogs.isEmpty() ? DateUtils.now() : monitorLogs.get(0).createTime();
        indexInstanceDto.setLastLogDate(DateUtils.toDateText(time, DateUtils.DEFAULT_DATE_TIME_FORMAT));
    }
}