package kpl.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author tangwei
 * @date 2018/9/10 15:14
 */
public class ApiTest {

    public static void main(String[] args) throws IOException {
        String url = "http://www.baidu.com";
        sendUrlByGet(url);


    }


    public static void sendUrlByGet(String request) throws IOException {
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
    }

    private String buildSign(String accessToken) throws Exception {
        String timestamp="2018-01-22 22:20:40";
        String version = "1.0";
        String signMethod = "md5";
        String format = "json" ;
        String method = "jd.kpl.open.cloudtrade.address.getcounties";
        String paramJson = "{\"cityId\":1298}" ;
        String appKey = "939c113356f0431a93a8d0cefe25735c";
        String appSecret = "9f47ad8376e1451296b15607dcfd280f";
        //第一步，按照顺序填充参数

        Map<String, String> map = new TreeMap();
        map.put("timestamp", timestamp);
        map.put("v", version);
        map.put("sign_method", signMethod);
        map.put("method", method);
        //param_json为空的时候需要写成 "{}"
        map.put("param_json", paramJson);
        map.put("access_token", accessToken);
        map.put("app_key", appKey);
        StringBuilder sb = new StringBuilder(appSecret);

        //按照规则拼成字符串
        for (Map.Entry entry : map.entrySet()) {
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            //检测参数是否为空
            if (areNotEmpty(new String[]{name, value})) {
                sb.append(name).append(value);
            }

        }

        sb.append(appSecret);
        return md5(sb.toString());
    }

    public static String md5(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] bytes = md.digest(source.getBytes("utf-8"));


        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() == 1) {
                sign.append("0");
            }
            sign.append(hex.toUpperCase());
        }
        return sign.toString();
    }



    public static boolean areNotEmpty(String[] values) {
        boolean result = true;
        if ((values == null) || (values.length == 0))
            result = false;
        else {
            for (String value : values) {
                result &= !isEmpty(value);
            }
        }
        return result;

    }
    public static boolean isEmpty(String value) {
        int strLen;
        if ((value == null) || ((strLen = value.length()) == 0))
            return true;
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(value.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
