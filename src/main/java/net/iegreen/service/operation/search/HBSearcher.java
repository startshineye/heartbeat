package net.iegreen.service.operation.search;

import net.iegreen.domain.dto.HBSearchDto;

/**
 * 15-3-13
 *
 * @author Shengzhao Li
 */
public interface HBSearcher {

    HBSearchDto search(HBSearchDto searchDto);
}
