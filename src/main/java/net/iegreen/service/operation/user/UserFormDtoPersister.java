package net.iegreen.service.operation.user;

import net.iegreen.domain.dto.user.UserFormDto;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.domain.user.Privilege;
import net.iegreen.domain.user.User;
import net.iegreen.domain.user.UserPrivilege;
import net.iegreen.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 15-4-12
 *
 * @author Shengzhao Li
 */
public class UserFormDtoPersister {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserFormDtoPersister.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);
    private UserFormDto formDto;

    public UserFormDtoPersister(UserFormDto formDto) {
        this.formDto = formDto;
    }

    public String persist() {
        if (formDto.isNewly()) {
            return createUser();
        } else {
            return updateUser();
        }
    }

    private String updateUser() {
        User user = userRepository.findByGuid(formDto.getGuid(), User.class);
        user.username(formDto.getUsername())
                .phone(formDto.getPhone())
                .email(formDto.getEmail());

        updatePrivileges(user, false);
        LOGGER.debug("<{}> update User: {}", SecurityUtils.currentUsername(), user);
        return user.guid();
    }

    private String createUser() {
        User user = formDto.toDomain()
                .creator(SecurityUtils.currentUser());
        userRepository.saveOrUpdate(user);

        updatePrivileges(user, true);
        LOGGER.debug("<{}> create User: {}", SecurityUtils.currentUsername(), user);
        return user.guid();
    }

    private void updatePrivileges(User user, boolean newly) {
        if (!newly) {
            userRepository.deleteUserPrivileges(user);
        }

        for (Privilege privilege : formDto.getPrivileges()) {
            UserPrivilege userPrivilege = new UserPrivilege(user, privilege);
            userRepository.saveOrUpdate(userPrivilege);
        }
    }
}
