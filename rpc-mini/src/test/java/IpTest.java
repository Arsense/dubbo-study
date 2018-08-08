import league.of.tool.IPTool;
import org.junit.Test;

/**
 * @author tangwei
 * @date 2018/8/6 10:24
 */
public class IpTest {



    @Test
    public void testIp() {
       int a = 1;

        String localIp = IPTool.getLocalIP();
        String realIp = IPTool.getRealIp();
    }

}
