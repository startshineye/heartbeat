package net.iegreen.infrastructure.hibernate.queryhelper.impl;

import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import net.iegreen.infrastructure.hibernate.queryhelper.Filter;
import net.iegreen.infrastructure.hibernate.queryhelper.ParameterFilter;
import net.iegreen.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * 15-2-13
 *
 * @author Shengzhao Li
 */
public class FrequencyMonitorLogListQueryHelper extends AbstractQueryHelper<FrequencyMonitorLog> {

    private Map<String, Object> map;

    public FrequencyMonitorLogListQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addInstanceGuidFilter();
        addPrivateInstanceFilter();
        addNormalFilter();
        addSortFilter();
    }


    private void addPrivateInstanceFilter() {
        addFilter(new Filter() {
            @Override
            public String getSubHql() {
                if (SecurityUtils.currentUser() == null) {
                    return " and b.privateInstance = false ";
                } else {
                    return "";
                }
            }
        });
    }


    private void addNormalFilter() {
        final Integer normal = (Integer) map.get("normal");
        if (normal == 1 || normal == 2) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("normal", normal == 1);
                }

                @Override
                public String getSubHql() {
                    return " and ai.normal = :normal";
                }
            });
        }
    }

    private void addSortFilter() {
        final String orderItem = (String) map.get("orderItem");
        addSortCriterionFilter(new SortCriterionFilter() {
            @Override
            public String getSubHql() {
                return " ai." + orderItem + " desc ";
            }
        });
    }

    private void addInstanceGuidFilter() {
        final String instanceGuid = (String) map.get("instanceGuid");
        if (StringUtils.isNotEmpty(instanceGuid)) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("instanceGuid", instanceGuid);
                }

                @Override
                public String getSubHql() {
                    return " and b.guid = :instanceGuid ";
                }
            });
        }
    }


    @Override
    public int getStartPosition() {
        return (Integer) map.get("startIndex");
    }

    @Override
    public int getItemsAmountPerPage() {
        return (Integer) map.get("perPageSize");
    }

    @Override
    public String getAmountHql() {
        return " select count(ai.id) from FrequencyMonitorLog ai join ai.instance b where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " select ai from FrequencyMonitorLog ai join ai.instance b where ai.archived = false ";
    }
}
