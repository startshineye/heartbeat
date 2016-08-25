package net.iegreen.web.validator;

import net.iegreen.domain.dto.application.ApplicationInstanceFormDto;
import net.iegreen.infrastructure.MatchUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Shengzhao Li
 */
@Component
public class ApplicationInstanceFormDtoValidator implements Validator {

    private static final String HTTP = "http://";
    private static final String HTTPS = "https://";

    @Override
    public boolean supports(Class<?> aClass) {
        return ApplicationInstanceFormDto.class.equals(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "instanceName", null, "实例名称不能为空");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "requestMethod", null, "请求方式不能为空");

        ApplicationInstanceFormDto formDto = (ApplicationInstanceFormDto) target;
        validateMonitorUrl(formDto, errors);
        validateEmail(formDto, errors);

        validateMaxConnectionSeconds(formDto, errors);
    }

    private void validateMaxConnectionSeconds(ApplicationInstanceFormDto formDto, Errors errors) {
        final int maxConnectionSeconds = formDto.getMaxConnectionSeconds();
        final int seconds = formDto.getFrequency().getSeconds();

        if (maxConnectionSeconds > seconds || maxConnectionSeconds < 0) {
            errors.rejectValue("maxConnectionSeconds", null, "最大连接时间必须为正整数且小于频率时间: " + seconds);
        }
    }

    private void validateEmail(ApplicationInstanceFormDto formDto, Errors errors) {
        String email = formDto.getEmail();
        if (StringUtils.isEmpty(email)) {
            errors.rejectValue("email", null, "邮箱不能为空");
            return;
        }
        String[] emailArray;
        if (email.indexOf(";") > 0) {
            emailArray = email.split(";");
        } else {
            emailArray = new String[]{email};
        }

        for (String s : emailArray) {
            if (!MatchUtils.isEmail(s)) {
                errors.rejectValue("email", null, "不合法的邮箱格式");
                break;
            }
        }
    }

    private void validateMonitorUrl(ApplicationInstanceFormDto formDto, Errors errors) {
        String monitorUrl = formDto.getMonitorUrl();
        if (StringUtils.isEmpty(monitorUrl)) {
            errors.rejectValue("monitorUrl", null, "监控URL不能为空");
            return;
        }

        String lowerUrl = monitorUrl.toLowerCase();
        if (!lowerUrl.startsWith(HTTP) && !lowerUrl.startsWith(HTTPS)) {
            errors.rejectValue("monitorUrl", null, "监控URL只能以 'http://' 或 'https://'开头");
        }

    }


}