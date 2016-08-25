package net.iegreen.infrastructure;

import net.iegreen.domain.shared.Application;
import net.iegreen.infrastructure.json.JsonUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 2016/5/19
 *
 * @author Shengzhao Li
 */
public abstract class MinixinUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MinixinUtils.class);

    private static ConcurrentHashMap<String, AccessToken> accessTokenMap = new ConcurrentHashMap<>();


    private MinixinUtils() {
    }


    public static boolean sendMsg(String openId, String content) {

        final String url = sendMsgUrl();
        HttpClientPostHandler postHandler = new HttpClientPostHandler(url);

        try {
            postHandler.addHeader("Content-Type", "application/json;charset=" + Application.ENCODING);
            /* 没有写oauth2，所以不需要
            postHandler.addHeader("Authorization", "bearer " + getToken());
            */

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("openId", openId);
            jsonObject.put("content", content);

            final String json = jsonObject.toString();
            LOGGER.debug("Send data: {} to url: {}", json, url);
            postHandler.httpEntity(new StringEntity(json, Application.ENCODING));
        } catch (Exception e) {
            LOGGER.warn("SendMsg Error", e);
            return false;
        }

        final String text = postHandler.handleAsString();
        return StringUtils.isNotEmpty(text) ? Boolean.valueOf(text) : false;
    }


    public static String getToken() {

        String token;
        if (accessTokenMap.isEmpty()) {
            token = retrieveToken();
        } else {
            final AccessToken accessToken = accessTokenMap.elements().nextElement();
            if (accessToken.expired()) {
                token = retrieveToken();
            } else {
                token = accessToken.getAccessToken();
            }
        }
        return token;
    }

    private static String retrieveToken() {

        HttpClientPostHandler postHandler = new HttpClientPostHandler(tokenUrl());
        postHandler.addRequestParam("client_id", Application.minixinAppKey());
        postHandler.addRequestParam("client_secret", Application.minixinAppSecret());
        postHandler.addRequestParam("grant_type", "client_credentials");
        postHandler.addRequestParam("scope", "read");

        final String text = postHandler.handleAsString();
        final AccessToken accessToken = JsonUtils.textToBean(new AccessToken(), text).initialExpiredTime();
        accessTokenMap.clear();
        accessTokenMap.put(accessToken.getAccessToken(), accessToken);

        return accessToken.getAccessToken();
    }


    private static String tokenUrl() {
        final String minixinHost = Application.minixinHost();
        if (minixinHost.endsWith("/")) {
            return minixinHost + "oauth/token";
        }
        return minixinHost + "/oauth/token";
    }

    private static String sendMsgUrl() {
        final String minixinHost = Application.minixinHost();
        if (minixinHost.endsWith("/")) {
            return minixinHost + "api/hb/send";
        }
        return minixinHost + "/api/hb/send";
    }


    //  {"access_token":"a81ebb5f-4daf-452d-a46a-7a6c13799e33","token_type":"bearer","expires_in":43199,"scope":"read"}
    private static class AccessToken implements Serializable {


        private static final long serialVersionUID = 6423343427932747251L;

        private long expiredTime;
        private String accessToken;
        private int expiresIn;
        private String scope;
        private String tokenType;

        public AccessToken() {
        }

        public AccessToken initialExpiredTime() {
            // 20秒用于程序执行误差
            this.expiredTime = System.currentTimeMillis() + ((this.expiresIn - 20) * 1000L);
            return this;
        }

        public boolean expired() {
            return expiredTime >= System.currentTimeMillis();
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getTokenType() {
            return tokenType;
        }

        public void setTokenType(String tokenType) {
            this.tokenType = tokenType;
        }
    }


}
