package net.iegreen.web.validator;

import net.iegreen.domain.dto.user.UserFormDto;
import net.iegreen.domain.user.Privilege;
import net.iegreen.infrastructure.MatchUtils;
import net.iegreen.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

/**
 * @author Shengzhao Li
 */
@Component
public class UserFormDtoValidator extends AbstractPasswordValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserFormDto formDto = (UserFormDto) target;

        validateUsername(formDto, errors);
        validateEmail(formDto, errors);
        validatePrivileges(formDto, errors);

        if (formDto.isNewly()) {
            validatePassword(formDto.getPassword(), formDto.getRePassword(), errors);
        }
    }

    private void validatePrivileges(UserFormDto formDto, Errors errors) {
        final List<Privilege> privileges = formDto.getPrivileges();
        if (privileges == null || privileges.size() < 1) {
            errors.rejectValue("privileges", null, "至少勾选一个权限");
        }
    }


    private void validateEmail(UserFormDto userFormDto, Errors errors) {
        String email = userFormDto.getEmail();
        if (StringUtils.isEmpty(email)) {
            return;
        }
        if (!MatchUtils.isEmail(email)) {
            errors.rejectValue("email", null, "邮箱格式错误");
        }
    }

    private void validateUsername(UserFormDto userFormDto, Errors errors) {
        String username = userFormDto.getUsername();
        if (StringUtils.isEmpty(username)) {
            errors.rejectValue("username", null, "账号是必须的");
            return;
        }
        if (!username.equals(userFormDto.getExistUsername())) {
            boolean exist = userService.isExistUsername(username);
            if (exist) {
                errors.rejectValue("username", null, "账号已经存在");
            }
        }
    }
}