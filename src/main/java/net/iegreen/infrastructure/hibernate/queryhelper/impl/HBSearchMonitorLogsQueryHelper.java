package net.iegreen.infrastructure.hibernate.queryhelper.impl;

import net.iegreen.domain.log.FrequencyMonitorLog;
import net.iegreen.infrastructure.MatchUtils;
import net.iegreen.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import net.iegreen.infrastructure.hibernate.queryhelper.ParameterFilter;
import net.iegreen.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * 15-3-14
 *
 * @author Shengzhao Li
 */
public class HBSearchMonitorLogsQueryHelper extends AbstractQueryHelper<FrequencyMonitorLog> {

    private Map<String, Object> map;

    public HBSearchMonitorLogsQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addKeyFilter();
        addSortFilter();
    }

    private void addKeyFilter() {
        final String key = (String) map.get("key");
        final boolean positiveNumber = MatchUtils.isPositiveNumber(key);

        addFilter(new ParameterFilter() {
            @Override
            public void setParameter(Query query) {
                query.setParameter("key", "%" + key + "%");
                if (positiveNumber) {
                    query.setParameter("numb", Long.valueOf(key));
                }
            }

            @Override
            public String getSubHql() {
                return " and (ai.instance.instanceName like :key " +
                        " or ai.remark like :key "
                        + (positiveNumber ? " or ai.costTime = :numb or ai.responseSize = :numb " : "") + ") ";
            }
        });
    }


    private void addSortFilter() {
        addSortCriterionFilter(new SortCriterionFilter() {
            @Override
            public String getSubHql() {
                return " ai.createTime desc ";
            }
        });
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
        return " select count(ai.id) from FrequencyMonitorLog ai where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " from FrequencyMonitorLog ai where ai.archived = false ";
    }
}
