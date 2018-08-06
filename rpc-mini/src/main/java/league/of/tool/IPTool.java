package league.of.tool;

import java.net.*;
import java.util.Enumeration;
import java.util.List;

/**
 * @author tangwei
 * @date 2018/8/6 10:03
 */
public class IPTool {


    private static String localIP = null;

    static {
        //得到所有网卡信息然后遍历
        String ip = null;
        Enumeration allNetworkInterface;
        try {
            allNetworkInterface = NetworkInterface.getNetworkInterfaces();
            while (allNetworkInterface.hasMoreElements()) {
                NetworkInterface networkInterface = (NetworkInterface)allNetworkInterface.nextElement();
                List<InterfaceAddress> allAddress = networkInterface.getInterfaceAddresses();
                for (InterfaceAddress address : allAddress) {
                    InetAddress Ip = address.getAddress();
                    if(Ip != null && Ip instanceof Inet4Address) {
                        if ("127.0.0.1".equals(Ip.getHostAddress())) {
                            continue;
                        }
                        ip = Ip.getHostAddress();
                        break;
                    }

                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
            throw  new RuntimeException("获取本机IP 失败");
        }
    localIP = ip;

    }


    public static String getRealIp() {
        String localIp = null;// 本地IP，如果没有配置外网IP则返回它
        String netIp = null;// 外网IP

        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            boolean finded = false;
            InetAddress ip = null;

            while (networkInterfaces.hasMoreElements() && !finded) {

                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> address = networkInterface.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && !ip.getHostAddress().contains(":")){
                        netIp = ip.getHostAddress();    // 外网IP
                        finded = true;
                        break;
                    } else if (ip.isSiteLocalAddress()
                            && !ip.isLoopbackAddress()
                            && !ip.getHostAddress().contains(":")) {
                        localIp = ip.getHostAddress();  //本地IP  第一个判断不一样
                    }
                }

            }
            if (netIp != null && !"".equals(netIp)) {
                return netIp;
            } else {
                return localIp;
            }

        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("获取外网IP 失败");

        }
    }



    public static String getLocalIP() {
        return localIP;
    }

    public static void setLocalIP(String localIP) {
        IPTool.localIP = localIP;
    }
}
