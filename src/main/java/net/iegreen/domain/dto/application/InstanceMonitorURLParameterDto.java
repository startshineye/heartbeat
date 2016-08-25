package net.iegreen.domain.dto.application;

import net.iegreen.domain.application.InstanceMonitorURLParameter;
import net.iegreen.domain.dto.AbstractDto;
import org.apache.commons.lang.StringUtils;

/**
 * 15-4-25
 *
 * @author Shengzhao Li
 */
public class InstanceMonitorURLParameterDto extends AbstractDto {


    private String key;

    private String value;

    private boolean randomValue = false;


    public InstanceMonitorURLParameterDto() {
    }

    public InstanceMonitorURLParameterDto(InstanceMonitorURLParameter parameter) {
        super(parameter.guid());
        this.key = parameter.key();

        this.value = parameter.value();
        this.randomValue = parameter.randomValue();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isRandomValue() {
        return randomValue;
    }

    public void setRandomValue(boolean randomValue) {
        this.randomValue = randomValue;
    }


    /**
     * If it is random, true
     * otherwise, key is not empty
     *
     * @return True is available
     */
    public boolean available() {
        return randomValue || StringUtils.isNotEmpty(key);
    }

    public InstanceMonitorURLParameter newDomain() {
        final InstanceMonitorURLParameter urlParameter = new InstanceMonitorURLParameter()
                .key(key).randomValue(randomValue);
        if (!randomValue) {
            urlParameter.value(value);
        }
        return urlParameter;
    }
}
