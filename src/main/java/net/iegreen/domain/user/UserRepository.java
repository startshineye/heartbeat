package net.iegreen.domain.user;

import net.iegreen.domain.shared.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author Shengzhao Li
 */

public interface UserRepository extends Repository {


    User findByUsername(String username);

    List<Privilege> findUserPrivileges(User user);

    List<User> findListUsers(Map<String, Object> map);

    int totalListUsers(Map<String, Object> map);

    User findByUsernameIgnoreArchived(String username);

    int deleteUserPrivileges(User user);

    List<WeixinUser> findWeixinUsersByUsername(String username);

    //Just one instance
    //If not found,will return default
    SystemSetting findSystemSetting();

    List<String> findWeixinUserGuids(String instanceGuid);

    WeixinUser findWeixinUserByGuid(String guid);

    List<WeixinUser> findWeixinUsersByInstanceGuid(String instanceGuid);

    List<WeixinUser> findWeixinUserList(Map<String, Object> map);

    int totalWeixinUserList(Map<String, Object> map);
}