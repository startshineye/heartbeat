package net.iegreen.service.operation;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.dto.application.ApplicationInstanceDto;
import net.iegreen.domain.dto.log.FrequencyMonitorLogDto;
import net.iegreen.domain.dto.log.FrequencyMonitorLogListDto;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.paginated.PaginatedLoader;

import java.util.List;
import java.util.Map;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogListDtoLoader {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);

    private FrequencyMonitorLogListDto listDto;

    public FrequencyMonitorLogListDtoLoader(FrequencyMonitorLogListDto listDto) {
        this.listDto = listDto;
    }

    public FrequencyMonitorLogListDto load() {

        loadInstanceDtos();

        final Map<String, Object> map = listDto.queryMap();
        return listDto.load(new PaginatedLoader<FrequencyMonitorLogDto>() {
            @Override
            public List<FrequencyMonitorLogDto> loadList() {
                List<FrequencyMonitorLog> logs = logRepository.findFrequencyMonitorLogList(map);
                return FrequencyMonitorLogDto.toDtos(logs);
            }

            @Override
            public int loadTotalSize() {
                return logRepository.totalFrequencyMonitorLogList(map);
            }
        });
    }

    private void loadInstanceDtos() {
        List<ApplicationInstance> instances = logRepository.findHadLogInstances();
        List<ApplicationInstanceDto> instanceDtos = listDto.getInstanceDtos();
        for (ApplicationInstance instance : instances) {
            instanceDtos.add(new ApplicationInstanceDto(instance));
        }
    }
}
