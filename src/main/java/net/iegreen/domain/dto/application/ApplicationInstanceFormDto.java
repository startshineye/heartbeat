package net.iegreen.domain.dto.application;

import net.iegreen.domain.application.ApplicationInstance;
import net.iegreen.domain.application.ApplicationInstanceURL;
import net.iegreen.domain.application.DatabaseType;
import net.iegreen.domain.application.HeartBeatFrequency;
import net.iegreen.domain.application.InstanceMonitorURLParameter;
import net.iegreen.infrastructure.HttpClientHandler;
import org.apache.http.entity.ContentType;

import java.util.ArrayList;
import java.util.List;

/**
 * 15-1-4
 *
 * @author Shengzhao Li
 */
public class ApplicationInstanceFormDto extends ApplicationInstanceDto {


    private static final long serialVersionUID = 6377318702441859115L;
    private List<InstanceMonitorURLParameterDto> urlParameters = new ArrayList<>();

    private List<String> weixinUserGuids = new ArrayList<>();

    public ApplicationInstanceFormDto() {
        super();
        //set default max connection time
        this.maxConnectionSeconds = this.frequency.getSeconds();
    }

    public ApplicationInstanceFormDto(ApplicationInstance instance) {
        super(instance);

        initialURL(instance);
    }

    private void initialURL(ApplicationInstance instance) {
        final ApplicationInstanceURL instanceURL = instance.instanceURL();
        for (InstanceMonitorURLParameter urlParameter : instanceURL.urlParameters()) {
            this.urlParameters.add(new InstanceMonitorURLParameterDto(urlParameter));
        }
    }

    public List<String> getWeixinUserGuids() {
        return weixinUserGuids;
    }

    public void setWeixinUserGuids(List<String> weixinUserGuids) {
        this.weixinUserGuids = weixinUserGuids;
    }


    public List<InstanceMonitorURLParameterDto> getUrlParameters() {
        return urlParameters;
    }

    public int getUrlParametersSize() {
        return urlParameters.size();
    }

    public void setUrlParameters(List<InstanceMonitorURLParameterDto> urlParameters) {
        this.urlParameters = urlParameters;
    }


    public HeartBeatFrequency[] getFrequencies() {
        return HeartBeatFrequency.values();
    }

    public DatabaseType[] getDatabaseTypes() {
        return DatabaseType.values();
    }
    
    public List<ContentType> getContentTypes() {
        return HttpClientHandler.CONTENT_TYPES;
    }


    public ApplicationInstance updateDomain(ApplicationInstance instance) {
        instance.instanceURL().contentType(contentType);
        return instance.instanceName(instanceName)
                .monitorUrl(monitorUrl)
                .privateInstance(privateInstance)
                .requestMethod(requestMethod)
                .continueFailedTimes(continueFailedTimes)
                .maxConnectionSeconds(maxConnectionSeconds)
                .email(email)
                .frequency(frequency)
                .remark(remark)
                .password(password)
                .username(username)
                .databaseType(databaseType)
                .instanceType(instanceType)
                .testsql(testsql)
                .sqlurl(sqlurl)
                .databaseurl(databaseurl)
                .ftpurl(ftpurl);
    }
}
