package net.iegreen.service.operation.instance;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceRepository;
import net.iegreen.domain.application.ApplicationInstanceWeixinUser;
import net.iegreen.domain.application.InstanceMonitorURLParameter;
import net.iegreen.domain.dto.application.ApplicationInstanceFormDto;
import net.iegreen.domain.dto.application.InstanceMonitorURLParameterDto;
import net.iegreen.domain.shared.BeanProvider;
import net.iegreen.domain.shared.security.SecurityUtils;
import net.iegreen.domain.user.UserRepository;
import net.iegreen.domain.user.WeixinUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * 15-1-4
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceFormDtoPersister {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationInstanceFormDtoPersister.class);

    private transient ApplicationInstanceRepository instanceRepository = BeanProvider.getBean(ApplicationInstanceRepository.class);
    private transient UserRepository userRepository = BeanProvider.getBean(UserRepository.class);
    private ApplicationInstanceFormDto formDto;

    public ApplicationInstanceFormDtoPersister(ApplicationInstanceFormDto formDto) {
        this.formDto = formDto;
    }

    public void persist() {
        if (formDto.isNewly()) {
            createInstance();
        } else {
            updateInstance();
        }
    }

    private void updateInstance() {
        ApplicationInstance instance = instanceRepository.findByGuid(formDto.getGuid(), ApplicationInstance.class);
        if (instance.enabled()) {
            throw new IllegalStateException("Only Disabled ApplicationInstance support edit");
        }
        updateWeixinUsers(instance, false);
        formDto.updateDomain(instance);
        setRequestParams(instance);
        LOGGER.debug("<{}> Update ApplicationInstance [{}]", SecurityUtils.currentUsername(), instance);
    }

    private void createInstance() {
        ApplicationInstance instance = formDto.updateDomain(new ApplicationInstance());
        instance.creator(SecurityUtils.currentUser());

        setRequestParams(instance);

        instanceRepository.saveOrUpdate(instance);
        updateWeixinUsers(instance, true);
        LOGGER.debug("<{}> Create ApplicationInstance [{}]", SecurityUtils.currentUsername(), instance);
    }

    private void updateWeixinUsers(ApplicationInstance instance, boolean newly) {
        if (!newly) {
            //clean old
            final int amount = instanceRepository.deleteApplicationInstanceWeixinUsers(instance);
            LOGGER.debug("<{}> delete old ApplicationInstanceWeixinUsers: {}", SecurityUtils.currentUsername(), amount);
        }
        final List<String> weixinUserGuids = formDto.getWeixinUserGuids();
        if (weixinUserGuids == null || weixinUserGuids.isEmpty()) {
            return;
        }
        List<ApplicationInstanceWeixinUser> newWeixinUsers = new ArrayList<>();
        for (String weixinUserGuid : weixinUserGuids) {
            WeixinUser weixinUser = userRepository.findWeixinUserByGuid(weixinUserGuid);
            newWeixinUsers.add(new ApplicationInstanceWeixinUser(instance, weixinUser));
        }
        userRepository.saveOrUpdateAll(newWeixinUsers);
        LOGGER.debug("<{}> Create newWeixinUsers: {}", SecurityUtils.currentUsername(), newWeixinUsers);
    }

    private void setRequestParams(ApplicationInstance instance) {
        final List<InstanceMonitorURLParameter> urlParameters = instance.instanceURL().urlParameters();
        if (!instance.isNewly()) {
            //remove old
            instanceRepository.deleteAll(urlParameters);
            urlParameters.clear();
        }

        final List<InstanceMonitorURLParameterDto> urlParameterDtos = formDto.getUrlParameters();
        for (InstanceMonitorURLParameterDto urlParameterDto : urlParameterDtos) {
            if (urlParameterDto.available()) {
                urlParameters.add(urlParameterDto.newDomain());
            } else {
                LOGGER.debug("Ignore URL parameter[{}], because key is empty", urlParameterDto);
            }
        }

    }
}
