package net.iegreen.service.operation.search;

import net.iegreen.domain.dto.HBSearchDto;
import net.iegreen.domain.dto.HBSearchResultDto;
import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.log.LogRepository;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.paginated.PaginatedLoader;

import java.util.List;
import java.util.Map;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public class MonitorLogSearcher implements HBSearcher {

    private transient LogRepository logRepository = BeanProvider.getBean(LogRepository.class);

    public MonitorLogSearcher() {
    }

    @Override
    public HBSearchDto search(HBSearchDto searchDto) {
        final Map<String, Object> map = searchDto.queryMap();
        return searchDto.load(new PaginatedLoader<HBSearchResultDto>() {
            @Override
            public List<HBSearchResultDto> loadList() {
                List<FrequencyMonitorLog> monitorLogs = logRepository.findHBSearchMonitorLogs(map);
                return HBSearchResultDto.toMonitorLogDtos(monitorLogs);
            }

            @Override
            public int loadTotalSize() {
                return logRepository.totalHBSearchMonitorLogs(map);
            }
        });
    }
}
