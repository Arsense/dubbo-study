package city.hunter.weapon;

/**
 * @author tangwei  server的服务在这里注册
 * @date 2018/7/30 19:36
 */
public class RegistryConfig {

    private  String address;
    private  String username;
    private  String password;
    private  int    port;
    private  String protocol;


    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }



    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
