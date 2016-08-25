package net.iegreen.service.operation.user;

import net.iegreen.domain.dto.user.UserRegisterDto;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.user.Privilege;
import net.iegreen.domain.user.User;
import net.iegreen.domain.user.UserPrivilege;
import net.iegreen.domain.user.UserRepository;
import net.iegreen.infrastructure.ThreadLocalHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public class RegisterUserHandler {

    private static final Logger LOG = LoggerFactory.getLogger(RegisterUserHandler.class);

    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);
    private UserRegisterDto formDto;

    public RegisterUserHandler(UserRegisterDto formDto) {
        this.formDto = formDto;
    }

    public void handle() {
        User newUser = formDto.toDomain();
        userRepository.saveOrUpdate(newUser);

        addDefaultPrivileges(newUser);
        LOG.debug("[{}] register a new User: {}", ThreadLocalHolder.clientIp(), newUser);
    }

    private void addDefaultPrivileges(User newUser) {
        final List<Privilege> privileges = Privilege.registeredUserPrivileges();
        for (Privilege privilege : privileges) {
            UserPrivilege userPrivilege = new UserPrivilege(newUser, privilege);
            userRepository.saveOrUpdate(userPrivilege);
        }
    }
}