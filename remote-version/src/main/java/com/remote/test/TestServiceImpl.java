package com.remote.test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author tangwei
 * @date 2018/9/3 14:48
 */
public class TestServiceImpl implements TestService {
    @Override
    public String test(String name){
        return "back==>" + name;
    }

    @Override
    public List<String> genericTest(){
        List list = new ArrayList();
        list.add("abc");
        list.add(new Date());
        return list;
    }
}
