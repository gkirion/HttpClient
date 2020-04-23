package com.george.http;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class HttpRequest {

    private String host;
    private int port;
    private HttpMethod method;
    private String url;
    private Map<String, String> headers;
    private String content;

    public HttpRequest(String host) {
        this(host, 80);
    }

    public HttpRequest(String host, int port) {
        this.host = host;
        this.port = port;
        this.content = "";
        headers = new HashMap<>();
        headers.put("Host", host + ":" + port);
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void setContent(String content) {
        this.content = content;
        headers.put("Content-Length", content.getBytes().length + "");
    }

    public HttpResponse get() throws IOException {
        try (Socket client = new Socket()) {
            client.connect(new InetSocketAddress(host, port));
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(client.getOutputStream());
            BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
            bufferedWriter.write("GET /" + url + " HTTP/1.1\n");
            for (String header : headers.keySet()) {
                bufferedWriter.write(header + ": " + headers.get(header) + "\n");
            }
            bufferedWriter.write("\n");
            bufferedWriter.write(content);
            bufferedWriter.flush();

            InputStreamReader inputStreamReader = new InputStreamReader(client.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            HttpResponse response = new HttpResponse();
            String line = bufferedReader.readLine();
            String[] tokens = line.split(" ");
            response.setResponseCode(Integer.parseInt(tokens[1]));
            response.setResponseMessage(line.substring(tokens[0].length() + tokens[1].length() + 2));
            while(!bufferedReader.readLine().isEmpty());
            response.setResponseBody(bufferedReader.readLine());
            return response;
        }
    }

}
