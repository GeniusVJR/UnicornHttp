package com.geniusvjr.http;

import android.webkit.URLUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by Stay on 24/6/15.
 * Powered by www.stay4it.com
 */
public class HttpUrlConnectionUtil {
    public static HttpURLConnection execute(Request request) throws AppException {
        if (!URLUtil.isNetworkUrl(request.url)) {
            throw new AppException("the url :" + request.url + " is not valid");
        }
        switch (request.method) {
            case GET:
            case DELETE:
                return get(request);
            case POST:
            case PUT:
                return post(request);
        }

        return null;
    }


    private static HttpURLConnection get(Request request) throws AppException {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(15 * 3000);
            connection.setReadTimeout(15 * 3000);

            addHeader(connection, request.headers);

            return connection;
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }
    }


    private static HttpURLConnection post(Request request) throws AppException {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) new URL(request.url).openConnection();
            connection.setRequestMethod(request.method.name());
            connection.setConnectTimeout(15 * 3000);
            connection.setReadTimeout(15 * 3000);
            connection.setDoOutput(true);


            addHeader(connection, request.headers);

            OutputStream os = connection.getOutputStream();
            os.write(request.content.getBytes());
        } catch (IOException e) {
            throw new AppException(e.getMessage());
        }

        return connection;
    }

    private static void addHeader(HttpURLConnection connection, Map<String, String> headers) {
        if (headers == null || headers.size() == 0)
            return;

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            connection.addRequestProperty(entry.getKey(), entry.getValue());
        }
    }
}
