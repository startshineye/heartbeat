package net.iegreen.infrastructure;

import org.apache.http.client.methods.RequestBuilder;

/**
 * 15-3-27
 *
 * @author Shengzhao Li
 */
public class HttpClientPostHandler extends HttpClientHandler {

    public HttpClientPostHandler(String url) {
        super(url);
    }


    protected RequestBuilder createRequestBuilder() {
        return RequestBuilder.post();
    }
}
