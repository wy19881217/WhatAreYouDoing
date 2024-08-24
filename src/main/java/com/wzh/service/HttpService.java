package com.wzh.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class HttpService {
    public static String httpGet(String url) throws IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.connect();
            InputStream is = httpURLConnection.getInputStream();
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            String response = new String(bytes, "UTF-8");
            response = response.replaceAll("'","\"");
            return response;
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public static String httpPost(String url, Map<String, String> head, Map<String, String> param) throws IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            for (Map.Entry<String, String> entry : head.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setDoOutput(true);
            OutputStream os = httpURLConnection.getOutputStream();

            String body = "";
            for (Map.Entry<String, String> entry : param.entrySet()) {
                body += "&" + entry.getKey() + "=" + entry.getValue();
            }
            body = body.replaceFirst("&", "");
            os.write(body.getBytes("UTF-8"));
            os.flush();

            httpURLConnection.connect();
            String response = "{}";
            if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() < 300) {
                InputStream is = httpURLConnection.getInputStream();
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                response = new String(bytes, "UTF-8");
            } else if (httpURLConnection.getResponseCode() == 404) {
                response = "{'resultcode':'100418','resultmsg':'404'}";
            } else {
                InputStream is = httpURLConnection.getErrorStream();
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                response = new String(bytes, "UTF-8");
            }
            return response;
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public static String httpPost(String url, Map<String, String> head, String json) throws IOException {
        HttpURLConnection httpURLConnection = null;
        try {
            httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            for (Map.Entry<String, String> entry : head.entrySet()) {
                httpURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
            }
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            OutputStream os = httpURLConnection.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            String response = "{}";
            if (httpURLConnection.getResponseCode() >= 200 && httpURLConnection.getResponseCode() < 300) {
                InputStream is = httpURLConnection.getInputStream();
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                response = new String(bytes, "UTF-8");
            } else if (httpURLConnection.getResponseCode() == 404) {
                response = "{'resultcode':'100418','resultmsg':'The deviceData is not existed.'}";
            } else {
                InputStream is = httpURLConnection.getErrorStream();
                byte[] bytes = new byte[is.available()];
                is.read(bytes);
                response = new String(bytes, "UTF-8");
            }
            return response;
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
