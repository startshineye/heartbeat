package net.iegreen.web.validator;

import net.iegreen.domain.dto.user.UserRegisterDto;
import net.iegreen.infrastructure.MatchUtils;
import net.iegreen.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Shengzhao Li
 */
@Component
public class UserRegisterDtoValidator extends AbstractPasswordValidator implements Validator {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegisterDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserRegisterDto formDto = (UserRegisterDto) target;

        validateUsername(formDto, errors);
        validateEmail(formDto, errors);

        validatePassword(formDto.getPassword(), formDto.getRePassword(), errors);
    }


    private void validateEmail(UserRegisterDto userFormDto, Errors errors) {
        String email = userFormDto.getEmail();
        if (StringUtils.isEmpty(email)) {
            return;
        }
        if (!MatchUtils.isEmail(email)) {
            errors.rejectValue("email", null, "错误的邮箱格式");
        }
    }

    private void validateUsername(UserRegisterDto userFormDto, Errors errors) {
        String username = userFormDto.getUsername();
        if (StringUtils.isEmpty(username)) {
            errors.rejectValue("username", null, "账号是必须的");
            return;
        }
        boolean exist = userService.isExistUsername(username);
        if (exist) {
            errors.rejectValue("username", null, "账号已经存在");
        }
    }
}