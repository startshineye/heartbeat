package net.iegreen.service.operation;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.dto.IndexDto;
import net.iegreen.domain.dto.IndexInstanceDto;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.infrastructure.DateUtils;

import java.util.Date;
import java.util.List;

/**
 * 首页加载
 *
 * @author Shengzhao Li
 */
public class IndexDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);
    private IndexDto indexDto;

    public IndexDtoLoader(IndexDto indexDto) {
        this.indexDto = indexDto;
    }

    public IndexDto load() {
        final List<IndexInstanceDto> instanceDtos = indexDto.getInstanceDtos();
        final List<ApplicationInstance> instances = loadInstances();
        for (ApplicationInstance instance : instances) {
            instanceDtos.add(generateIndexInstanceDto(instance));
        }

        return indexDto;
    }

    private List<ApplicationInstance> loadInstances() {
        final boolean enabled = indexDto.isEnabled();
        return enabled ? logRepository.findHadLogInstancesByEnabled(true) : logRepository.findHadLogInstances();
    }

    private IndexInstanceDto generateIndexInstanceDto(ApplicationInstance instance) {
        IndexInstanceDto indexInstanceDto = new IndexInstanceDto(instance);
        List<FrequencyMonitorLog> monitorLogs = logRepository.findLatestFrequencyMonitorLogs(instance, indexDto.getMaxResult());

        MonitoringChartDataGenerator chartDataGenerator = new MonitoringChartDataGenerator(monitorLogs);
        indexInstanceDto.setCategoryData(chartDataGenerator.generateCategoryData());
        indexInstanceDto.setSeriesData(chartDataGenerator.generateSeriesData());

        lastLogDate(indexInstanceDto, monitorLogs);
        return indexInstanceDto;
    }

    private void lastLogDate(IndexInstanceDto indexInstanceDto, List<FrequencyMonitorLog> monitorLogs) {
        final Date time = monitorLogs.isEmpty() ? DateUtils.now() : monitorLogs.get(0).createTime();
        indexInstanceDto.setLastLogDate(DateUtils.toDateText(time, DateUtils.DEFAULT_DATE_TIME_FORMAT));
    }
}