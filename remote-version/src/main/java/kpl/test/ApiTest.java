package kpl.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author tangwei
 * @date 2018/9/10 15:14
 */
public class ApiTest {

    public static void main(String[] args) throws IOException {
        String url = "http://www.baidu.com";
        sendUrlByGet(url);


    }


    public static boolean sendUrlByGet(String request) throws IOException {
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true); //可以发送数据
        connection.setDoInput(true); //可以接受数据
        connection.setRequestMethod("GET");

        connection.setRequestProperty
                ("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String response;
        while((response = in.readLine()) != null) {
            System.out.println(response);
        }




        return true;
    }

}
