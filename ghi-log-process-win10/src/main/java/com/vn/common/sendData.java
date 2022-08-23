package com.vn.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class sendData {

    public boolean sendData(String data) throws NoSuchAlgorithmException, KeyManagementException {

        String ApiUrl = "";
        try {
            ApiUrl = readFileToString("api.txt");
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(sendData.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (ApiUrl.trim().equals("")) {
            return false;
        } else {
            ApiUrl = ApiUrl.trim();
        }
        System.out.println(ApiUrl);

        //Kiểm tra kết nối
        boolean testURL = false;
        try {
            URL url = new URL(ApiUrl);
            URLConnection connection = url.openConnection();
            connection.connect();
            testURL = true;
            System.out.println("Connected: " + ApiUrl);
        } catch (MalformedURLException e) {
            System.out.println("Not connected: " + ApiUrl);
            testURL = false;
        } catch (IOException e) {
            System.out.println("Not connected: " + ApiUrl);
            testURL = false;
        }

        if (testURL == false) {
            return false;
        }
        System.out.println("Chuyen du lieu len server (neu co):\n" + data);

        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(ApiUrl);

            StringEntity input = new StringEntity(data);
            input.setContentType("application/json");
            postRequest.setEntity(input);

            HttpResponse response = (HttpResponse) httpClient.execute(postRequest);

            if (((org.apache.http.HttpResponse) response).getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + ((org.apache.http.HttpResponse) response).getStatusLine().getStatusCode());
            }

            BufferedReader br = new BufferedReader(
                    new InputStreamReader((((HttpEntityEnclosingRequestBase) response).getEntity().getContent())));

            String output;

            while ((output = br.readLine()) != null) {
                System.out.println(output);
            }

            httpClient.getConnectionManager().shutdown();
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    static public String readFileToString(String filename) throws FileNotFoundException, IOException {
        File file = new File(filename);
        FileInputStream fis = new FileInputStream(file);
        byte[] data = new byte[(int) file.length()];
        fis.read(data);
        fis.close();

        String str = new String(data, "UTF-8");
        return str;
    }
}
