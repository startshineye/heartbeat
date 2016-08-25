package net.iegreen.domain.shared.paginated;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface Paginated<T> {

    List<T> getList();

    int getPageNumber();

    int getPerPageSize();

    int getTotalSize();

    String getSortName();

    PaginatedSort getSort();

    int getTotalPages();
}