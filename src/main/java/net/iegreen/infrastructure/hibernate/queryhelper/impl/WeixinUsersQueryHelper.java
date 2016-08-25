package net.iegreen.infrastructure.hibernate.queryhelper.impl;

import net.iegreen.domain.user.WeixinUser;
import net.iegreen.infrastructure.hibernate.queryhelper.AbstractQueryHelper;
import net.iegreen.infrastructure.hibernate.queryhelper.ParameterFilter;
import net.iegreen.infrastructure.hibernate.queryhelper.SortCriterionFilter;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Map;

/**
 * @author Shengzhao Li
 */
public class WeixinUsersQueryHelper extends AbstractQueryHelper<WeixinUser> {

    private Map<String, Object> map;

    public WeixinUsersQueryHelper(Session session, Map<String, Object> map) {
        super(session);
        this.map = map;

        addNickNameFilter();
        addSortFilter();
    }

    private void addSortFilter() {
        addSortCriterionFilter(new SortCriterionFilter() {
            @Override
            public String getSubHql() {
                return " ai.createTime desc ";
            }
        });
    }

    private void addNickNameFilter() {
        final String nickName = (String) map.get("nickName");
        if (StringUtils.isNotEmpty(nickName)) {
            addFilter(new ParameterFilter() {
                @Override
                public void setParameter(Query query) {
                    query.setParameter("nickName", "%" + nickName + "%");
                }

                @Override
                public String getSubHql() {
                    return " and ai.nickName like :nickName ";
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
        return " select count(ai.id) from WeixinUser ai where ai.archived = false ";
    }

    @Override
    public String getResultHql() {
        return " from WeixinUser ai where ai.archived = false ";
    }
}
