package net.iegreen.service.operation.search;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.dto.HBSearchDto;
import net.iegreen.domain.dto.HBSearchResultDto;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.paginated.PaginatedLoader;

import java.util.List;
import java.util.Map;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceSearcher implements HBSearcher {

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);

    public ApplicationInstanceSearcher() {
    }

    @Override
    public HBSearchDto search(HBSearchDto searchDto) {

        final Map<String, Object> map = searchDto.queryMap();
        return searchDto.load(new PaginatedLoader<HBSearchResultDto>() {
            @Override
            public List<HBSearchResultDto> loadList() {
                List<ApplicationInstance> instances = instanceRepository.findHBSearchInstances(map);
                return HBSearchResultDto.toInstanceDtos(instances);
            }

            @Override
            public int loadTotalSize() {
                return instanceRepository.totalHBSearchInstances(map);
            }
        });

    }
}
