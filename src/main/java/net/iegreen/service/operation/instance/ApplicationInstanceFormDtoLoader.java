package net.iegreen.service.operation.instance;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.dto.application.ApplicationInstanceFormDto;
import net.iegreen.domain.dto.user.WeixinUserDto;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.domain.user.UserRepository;
import net.iegreen.domain.user.WeixinUser;
import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * 2016/5/17
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceFormDtoLoader {


    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);

    private String guid;

    public ApplicationInstanceFormDtoLoader(String guid) {
        this.guid = guid;
    }

    public ApplicationInstanceFormDto load() {

        ApplicationInstanceFormDto formDto;

        if (StringUtils.isNotEmpty(guid)) {
            formDto = loadFormDto();
        } else {
            formDto = new ApplicationInstanceFormDto();
        }

        final List<WeixinUser> weixinUsers = userRepository.findWeixinUsersByUsername(SecurityUtils.currentUsername());
        formDto.setWeixinUserDtos(WeixinUserDto.toDtos(weixinUsers));

        return formDto;
    }

    private ApplicationInstanceFormDto loadFormDto() {
        ApplicationInstance instance = instanceRepository.findByGuid(guid, ApplicationInstance.class);
        if (instance.enabled()) {
            throw new IllegalStateException("Only Disabled ApplicationInstance support edit");
        }
        ApplicationInstanceFormDto formDto = new ApplicationInstanceFormDto(instance);

        List<String> weixinUserGuids = userRepository.findWeixinUserGuids(instance.guid());
        formDto.setWeixinUserGuids(weixinUserGuids);
        return formDto;
    }
}
