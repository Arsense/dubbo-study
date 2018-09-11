package kpl.test;

import org.apache.commons.lang3.StringUtils;
import java.io.BufferedReader;
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


//         access_token:faf6e422fe32439d96f5bb469e98b08b8
//         refresh_token:258a4ba6b6b94c82b4b2807ef72b81f59

    private final static String BASE_METHOD = "jd.kpl.open.cloudtrade.address.";
    private static  Map<String, String> urlParamMap = new TreeMap();
    static {

        String accessToken = "faf6e422fe32439d96f5bb469e98b08b8";
        String appKey = "939c113356f0431a93a8d0cefe25735c";
        String signMethod = "md5";
        String format = "json" ;
        String version = "1.0";

        urlParamMap.put("v", version);
        urlParamMap.put("sign_method", signMethod);
        urlParamMap.put("format", format);
        urlParamMap.put("access_token", accessToken);
        urlParamMap.put("app_key", appKey);
    }

    public static void main(String[] args) throws Exception {
        String methodGetcounties = BASE_METHOD + "getcounties";
        String paramJson1 = "{\"cityId\":2454}" ;

        System.out.println("方法" + methodGetcounties + "开始");
        initKplApi(methodGetcounties , paramJson1);
        System.out.println();


        String methodGetprovinces = BASE_METHOD + "getprovinces";
        String paramJson2 = "{}";

        System.out.println("方法" + methodGetprovinces + "开始");
        initKplApi(methodGetprovinces , paramJson2);
        System.out.println();


        String methodGettowns = BASE_METHOD + "gettwons";
        String paramJson3 = "{\"countyId\":1300}" ;

        System.out.println("方法" + methodGettowns + "开始");
        initKplApi(methodGettowns , paramJson3);
        System.out.println();

        String methodGetcities = BASE_METHOD + "getcities";
        String paramJson4 = "{\"provinceId\":1}" ;

        System.out.println("方法" + methodGetcities + "开始");
        initKplApi(methodGetcities , paramJson4);
        System.out.println();

        String methodGetSelfPick = BASE_METHOD + "getselfpick";
        String paramJson5 = "{\"provinceId\":2,\"uid\":\"939c32083arU\",\"cityId\":2837,\"townId\":0,\"countyId\":51928}" ;
        System.out.println("方法" + methodGetSelfPick + "开始");

        initKplApi(methodGetSelfPick , paramJson5);


    }
    private static void initKplApi(String method , String paramJson) throws Exception {
        if(!areNotEmpty(new String[]{method , paramJson})){
            throw  new Exception("method ,paramJson 不能为空");
        }

        //下一个签名的时候不能有
        String signKey = "sign";
        if (urlParamMap.get(signKey) != null){
            urlParamMap.remove(signKey);
        }
        String sign = buildSign(method , paramJson);
        urlParamMap.put(signKey, sign);

        System.out.println("请求参数:  " + urlParamMap.toString());
        System.out.println();

        String url = buildUrl();
        sendMessage(url);
    }

    /**
     * 拼接URL
     * @return
     */
    private static String buildUrl(){
        StringBuilder url =  new StringBuilder("https://router.jd.com/api?");

        int count = 0;
        for (Map.Entry entry : urlParamMap.entrySet()) {
            String name = (String) entry.getKey();
            String value = (String) entry.getValue();
            //检测参数是否为空

            if (areNotEmpty(new String[]{name, value})) {
                count++;
                url.append(name).append("=").append(value);
                if (urlParamMap.entrySet().size() != count) {
                    url.append("&");
                }
            }

        }
        return url.toString();
    }

    /**
     * 发送请求 调用接口
     * @param request
     * @throws Exception
     */
    private static void sendMessage(String request) throws Exception {
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


    /**
     * 构造签名
     * @param method
     * @param paramJson
     * @return
     * @throws Exception
     */
    private static String buildSign(String method , String paramJson) throws Exception {
        String timestamp = String.valueOf(System.currentTimeMillis());
        String appSecret = "9f47ad8376e1451296b15607dcfd280f";
        StringBuilder sb = new StringBuilder(appSecret);

        Map<String, String> map = new TreeMap();
        urlParamMap.put("timestamp", timestamp);
        urlParamMap.put("method", method);
        urlParamMap.put("param_json", paramJson);
        map = urlParamMap;

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

    /**
     * md5加密
     * @param source
     * @return
     * @throws Exception
     */
    private static String md5(String source) throws Exception {
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


    /*
     * 判断多个是否为空
     */
    private static boolean areNotEmpty(String[] values) {
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

    /*
     *  判断是否为空
     */
    private static boolean isEmpty(String value) {
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
