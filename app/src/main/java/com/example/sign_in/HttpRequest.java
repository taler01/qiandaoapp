package com.example.sign_in;

import android.os.StrictMode;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;

public class HttpRequest {
    public static String sendPost(String url, String param,String token){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        String result = "";
        try{
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            if (token.equals("")) {
            }else{
                conn.setRequestProperty("TOKEN",token);
            }
            conn.setDoOutput(true);
            conn.setDoInput(true);
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null){
                result += "\n" + line;
            }
            conn.connect();
            int code = conn.getResponseCode();
            if ((code == 401) | (code == 403) | (code == 404) | (code == 500)){
                State state = new State();
                state.setState("false");
                Gson gson = new Gson();
                result = gson.toJson(state);
                Log.d("HttpRequest", String.valueOf(code));
                return result;
            }
        } catch (Exception e){
            Log.d("HttpRequest", "发送POST请求出现异常: " + e);
            e.printStackTrace();
        }
        if (result.length() <= 0){
            State state = new State();
            state.setState("false");
            Gson gson = new Gson();
            result = gson.toJson(state);
            return result;
        }
        return result;
    }
    public static String sendGet(String url, String param,String token){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        String result = "";
        String urlName = "";
        if (param.length() != 0){
            urlName = url + "?" + param;
        }else{
            urlName = url;
        }
        try{
            URL realUrl = new URL(urlName);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("TOKEN",token);
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.connect();
            int code = conn.getResponseCode();
            if ((code == 401) | (code == 403) | (code == 404) | (code == 500)){
                State state = new State();
                state.setState("false");
                Gson gson = new Gson();
                result = gson.toJson(state);
                return result;
            }
            Map<String, List<String>> map = conn.getHeaderFields();
            for (String key : map.keySet()) {
                out.println(key + "-->" + map.get(key));
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            out.println("发送GET请求出现异常" + e);
            e.printStackTrace();
        }
        if (result.length() <= 0){
            State state = new State();
            state.setState("false");
            Gson gson = new Gson();
            result = gson.toJson(state);
            return result;
        }
        return result;


    }
    public static String sendPut(String url, String param, String token){
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
        String result = "";
        try{
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept","*/*");
            conn.setRequestProperty("connection","Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            conn.setRequestProperty("TOKEN",token);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/json");
            conn.setDoInput(true);
            conn.setRequestMethod("PUT");
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(param);
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8));
            String line;
            while ((line = in.readLine()) != null){
                result += "\n" + line;
            }
            conn.connect();
            int code = conn.getResponseCode();
            if ((code == 401) | (code == 403) | (code == 404) | (code == 500)){
                State state = new State();
                state.setState("false");
                Gson gson = new Gson();
                result = gson.toJson(state);
                return result;
            }
        } catch (Exception e){
            out.println("发送PUT请求出现异常" + e);
            e.printStackTrace();
        }
        if (result.length() <= 0){
            State state = new State();
            state.setState("false");
            Gson gson = new Gson();
            result = gson.toJson(state);
            return result;
        }
        return result;
    }
}
class State{
    private String state;

    public void setState(String state){
        this.state = state;
    }
    public String getState(){
        return state;
    }
}
