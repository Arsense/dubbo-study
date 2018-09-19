package spider;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @author tangwei
 * @date 2018/9/15 20:34
 */
public class BaiduTest {

    public static void main(String[] args) throws Exception {
        BaiduTest test = new BaiduTest();
        test.sendMessage();

    }
    public  void sendMessage() throws Exception {
        String request = "https://pan.baidu.com/s/1bzcGFG";
        URL url = new URL(request);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setDoInput(true); //可以发送数据
        connection.setDoInput(true); //可以接受数据
        connection.setRequestMethod("POST");
        connection.setRequestProperty
                ("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String temp;
        StringBuilder response = new StringBuilder();
        while((temp = in.readLine()) != null) {
            response.append(temp).append("\n");
        }

        System.out.println(response.toString());
        String[] responseArray = response.toString().split(",");
        if (responseArray[1].length() < 14 || !"resultCode".equals(responseArray[1].substring(1,11))){
            System.out.println("接口服务端异常");

            return;
        }
        String msg = responseArray[1].substring(13,14);
        if (StringUtils.isEmpty(msg) || !"0".equals(msg)) {
            System.out.println("接口调用失败");
            return;
        }
        System.out.println("接口调用成功");
    }
}
