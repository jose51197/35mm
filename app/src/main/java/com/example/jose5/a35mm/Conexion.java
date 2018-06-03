package com.example.jose5.a35mm;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by jose5 on 6/2/2018.
 */
public class Conexion {
    private String queryResult;

    public JSONArray Query(String query) {
        query = query.replace(" ", "%20");
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://proyectoreque.000webhostapp.com/connect/select.php?query=" + query);
            Log.println(Log.DEBUG, "url", url.toString());
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            InputStream is = httpConn.getInputStream();
            is.toString();
            queryResult = convertinputStreamToString(is);
            return new JSONArray(queryResult);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String convertinputStreamToString(InputStream ists) throws IOException {
        if (ists != null) {
            StringBuilder sb = new StringBuilder();
            String line;

            try {
                BufferedReader r1 = new BufferedReader(new InputStreamReader(
                        ists, "UTF-8"));
                while ((line = r1.readLine()) != null) {
                    sb.append(line).append("\n");
                }
            } finally {
                ists.close();
            }
            return sb.toString();
        } else {
            return "";
        }
    }
}

