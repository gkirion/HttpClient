package com.george.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpBuilder {

    private HttpRequest request;

    private HttpBuilder() {
    }

    public static HttpBuilder get(String host, int port, String url) {
        HttpBuilder builder = new HttpBuilder();
        builder.request = new HttpRequest(host, port);
        builder.request.setUrl(url);
        return builder;
    }

    public HttpBuilder headers(String key, String value) {
        request.addHeader(key, value);
        return this;
    }

    public HttpBuilder body(String content) {
        request.setContent(content);
        return this;
    }

    public HttpResponse send() throws IOException {
        return request.get();
    }

}
