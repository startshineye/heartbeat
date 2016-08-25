package net.iegreen.service.operation.search;

import net.iegreen.domain.dto.HBSearchDto;
import org.springframework.util.StringUtils;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public class HBSearchDtoLoader {
    private HBSearchDto searchDto;

    public HBSearchDtoLoader(HBSearchDto searchDto) {
        this.searchDto = searchDto;
    }

    public HBSearchDto load() {
        //Ignore empty key
        if (StringUtils.isEmpty(searchDto.getKey())) {
            return searchDto;
        }
        final HBSearcher hbSearcher = searchDto.getSearchType().resolveSearcher();
        return hbSearcher.search(searchDto);
    }
}
