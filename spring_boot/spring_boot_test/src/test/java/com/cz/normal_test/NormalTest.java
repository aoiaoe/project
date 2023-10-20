package com.cz.normal_test;

import com.cz.spring_boot_test.utils.DateUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 普通的单元测试，如只测试算法之类的，不需要用到spring的组件
 * 就可以在测试方法上加入@Test注解直接启动测试
 */
public class NormalTest {

    @Test
    public void test() {
        String result = DateUtils.formatNow("yyyy-MM-dd");
        System.out.println(result);
//        asset.assertEquals("2020-12-20", result);
        assert "2020-12-20".equals(result);
    }

    @Test
    public void testListIte(){
        List<String> list = new ArrayList(){{
            add("a");
            add("b");
            add("c");
        }};

        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            list.add("d");
            System.out.println(iterator.next());
        }
    }
}
