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
            queryResult = convertinputStreamToString(is);
            return new JSONArray(queryResult);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public boolean Insert(String table,String columns, String values) {
        StringBuilder result = new StringBuilder();
        try {
            //digamos que 1,2,3 cambia a '+1+','+2+','+3+'
            values="'"+values.replace(",","','")+"'";
            URL url = new URL("https://proyectoreque.000webhostapp.com/connect/insert.php?table=" + table + "&columns="+columns +"&values="+values);
            Log.println(Log.DEBUG, "url", url.toString());
            URLConnection conn = url.openConnection();
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();
            InputStream is = httpConn.getInputStream();
            Log.println(Log.DEBUG,"insert",convertinputStreamToString(is));
            return convertinputStreamToString(is).equals("inserted");

        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
    //fabri
    public void AddMovie(String name, String description, String image, String year) throws IOException {
            String link="https://proyectoreque.000webhostapp.com/connect/movie.php";
            String data  = URLEncoder.encode("description", "UTF-8") + "=" +
            URLEncoder.encode(description, "UTF-8");
            data += "&" + URLEncoder.encode("name", "UTF-8") + "=" +
            URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("year", "UTF-8") + "=" +
            URLEncoder.encode(year, "UTF-8");
            data += "&" + URLEncoder.encode("url", "UTF-8") + "=" +
            URLEncoder.encode(image, "UTF-8");

             URL reqURL = new URL(link); //the URL we will send the request to
             HttpURLConnection connection = (HttpURLConnection) reqURL.openConnection();
             String post = data;
             connection.setRequestMethod("POST");
             connection.setDoOutput(true);
             connection.connect();
             OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream()); //we will write our request data here
             writer.write(post);
             writer.flush();
             writer.close();
        }
}

