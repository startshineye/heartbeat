package net.iegreen.infrastructure.hibernate.queryhelper.impl;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.infrastructure.MatchUtils;
import net.iegreen.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import net.iegreen.infrastructure.hibernate.queryhelper.Filter;
import net.iegreen.infrastructure.hibernate.queryhelper.ParameterFilter;
import net.iegreen.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * 15-1-3
 *
 * @author Shengzhao Li
 */
public class HBSearchInstancesQueryHelper extends AbstractQueryHelper<ApplicationInstance> {

    private Map<String, Object> map;

    public HBSearchInstancesQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addSearchFilter();
        addPrivateInstanceFilter();
        addSortFilter();
    }

    private void addPrivateInstanceFilter() {
        addFilter(new Filter() {
            @Override
            public String getSubHql() {
                if (SecurityUtils.currentUser() == null) {
                    return " and ai.privateInstance = false ";
                } else {
                    return "";
                }
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

    private void addSearchFilter() {
        final String key = getKey();
        final boolean positiveNumber = MatchUtils.isPositiveNumber(key);

        addFilter(new ParameterFilter() {
            @Override
            public void setParameter(Query query) {
                query.setParameter("likeKey", "%" + key + "%");
                if (positiveNumber) {
                    query.setParameter("key", Integer.valueOf(key));
                }
            }

            @Override
            public String getSubHql() {
                return " and (ai.instanceName like :likeKey " +
                        " or ai.instanceURL.monitorUrl like :likeKey " +
                        " or ai.email like :likeKey " +
                        (positiveNumber ? " or ai.maxConnectionSeconds = :key " : "") + ")";
            }
        });
    }

    private String getKey() {
        return (String) map.get("key");
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
        return " select count(ai.id) from ApplicationInstance ai where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " from ApplicationInstance ai where ai.archived = false ";
    }
}
