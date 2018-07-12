package spring;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tangwei
 * @date 2018/7/12 14:13
 */
@Service("userService")
public class UserServiceImpl implements  UserService {

    private static  final Map<String,User> userMap = new HashMap<String, User>();

    public User findByName(String userName) {
        return userMap.get(userName);
    }

    static {
        userMap.put("Clay",new User("Clay","Clay@jd.com"));
        userMap.put("Craig",new User("Craig","adas@jd.com"));
    }



}
