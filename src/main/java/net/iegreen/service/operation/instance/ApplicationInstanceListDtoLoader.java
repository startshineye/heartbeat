package net.iegreen.service.operation.instance;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.dto.application.ApplicationInstanceDto;
import net.iegreen.domain.dto.application.ApplicationInstanceListDto;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.paginated.PaginatedLoader;

import java.util.List;
import java.util.Map;

/**
 * 2015/9/22
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceListDtoLoader {

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);

    private ApplicationInstanceListDto listDto;

    public ApplicationInstanceListDtoLoader(ApplicationInstanceListDto listDto) {
        this.listDto = listDto;
    }

    public ApplicationInstanceListDto load() {

        final Map<String, Object> map = listDto.queryMap();

        listDto.load(new PaginatedLoader<ApplicationInstanceDto>() {
            @Override
            public List<ApplicationInstanceDto> loadList() {
                List<ApplicationInstance> instances = instanceRepository.findApplicationInstanceList(map);
                return ApplicationInstanceDto.toDtos(instances);
            }

            @Override
            public int loadTotalSize() {
                return instanceRepository.totalApplicationInstanceList(map);
            }
        });

        return listDto;
    }
}
