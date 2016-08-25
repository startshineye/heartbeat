package net.iegreen.infrastructure.hibernate;

import net.iegreen.domain.user.*;
import net.iegreen.infrastructure.hibernate.queryhelper.impl.ListUsersQueryHelper;
import net.iegreen.infrastructure.hibernate.queryhelper.impl.WeixinUsersQueryHelper;
import com.google.common.collect.ImmutableMap;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */
@Repository("userRepository")
public class UserRepositoryHibernate extends AbstractRepositoryHibernate<User> implements UserRepository {

    @Override
    public User findByUsername(String username) {
        final List<User> list = find("from User u where u.username = :username and u.archived = false", ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Privilege> findUserPrivileges(User user) {
        final String hql = " select up.privilege from UserPrivilege up where up.archived = false and up.user = :user";
        final Query query = session().createQuery(hql)
                .setParameter("user", user);
        return query.list();
    }

    @Override
    public List<User> findListUsers(Map<String, Object> map) {
        ListUsersQueryHelper queryHelper = new ListUsersQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalListUsers(Map<String, Object> map) {
        ListUsersQueryHelper queryHelper = new ListUsersQueryHelper(session(), map);
        return queryHelper.getAmount();
    }

    @Override
    public User findByUsernameIgnoreArchived(String username) {
        final List<User> list = find("from User u where u.username = :username ", ImmutableMap.of("username", username));
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public int deleteUserPrivileges(User user) {
        String hql = " from UserPrivilege up where up.user = :user ";
        final List<UserPrivilege> privileges = find(hql, ImmutableMap.of("user", user));
        deleteAll(privileges);
        return privileges.size();
    }

    @Override
    public List<WeixinUser> findWeixinUsersByUsername(String username) {
        return find(" from WeixinUser wu where wu.archived = 0 and wu.hbUsername = :username ", ImmutableMap.of("username", username));
    }

    @Override
    public SystemSetting findSystemSetting() {
        String hql = " from SystemSetting ss where ss.archived = false ";
        SystemSetting systemSetting = (SystemSetting) session().createQuery(hql).uniqueResult();
        if (systemSetting == null) {
            systemSetting = new SystemSetting();
        }
        return systemSetting;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<String> findWeixinUserGuids(String instanceGuid) {
        String hql = " select aiw.weixinUser.guid from ApplicationInstanceWeixinUser aiw where aiw.archived = 0 and aiw.applicationInstance.guid = :instanceGuid";
        return session().createQuery(hql).setParameter("instanceGuid", instanceGuid).list();
    }

    @Override
    public WeixinUser findWeixinUserByGuid(String guid) {
        return findByGuid(guid, WeixinUser.class);
    }

    @Override
    public List<WeixinUser> findWeixinUsersByInstanceGuid(String instanceGuid) {
        String hql = " select aiw.weixinUser from ApplicationInstanceWeixinUser aiw where aiw.archived = 0 and aiw.applicationInstance.guid = :instanceGuid";
        return find(hql, ImmutableMap.of("instanceGuid", instanceGuid));
    }

    @Override
    public List<WeixinUser> findWeixinUserList(Map<String, Object> map) {
        WeixinUsersQueryHelper queryHelper = new WeixinUsersQueryHelper(session(), map);
        return queryHelper.getResults();
    }

    @Override
    public int totalWeixinUserList(Map<String, Object> map) {
        WeixinUsersQueryHelper queryHelper = new WeixinUsersQueryHelper(session(), map);
        return queryHelper.getAmount();
    }
}