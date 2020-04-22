package com.george.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HttpBuilder {

    private HttpRequest request;

    private HttpBuilder() {
    }

    public static HttpBuilder get(String url) {
        String[] tokens = url.split("https{0,1}:/*");
        url = tokens[tokens.length - 1];
        int index = url.indexOf("/");
        String hostUrl;
        String resourceUrl;
        if (index == -1) {
            hostUrl = url;
            resourceUrl = "";
        } else {
            hostUrl = url.substring(0, index);
            resourceUrl = url.substring(index);
        }
        tokens = hostUrl.split(":");
        String ip = tokens[0];
        int port = tokens.length == 2 ? Integer.parseInt(tokens[1]) : 80;
        return get(ip, port, resourceUrl);
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
