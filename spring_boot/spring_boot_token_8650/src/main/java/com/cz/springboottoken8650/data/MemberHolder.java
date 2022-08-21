package com.cz.springboottoken8650.data;

import com.cz.springboottoken8650.entity.MemberUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MemberHolder {

    private Map<String, MemberUser> map = new HashMap<>();

    @PostConstruct
    public void inti() {
        log.info("初始化member数据....");
        for (int i = 0; i < 5L; i++) {
            map.put("user" + i, new MemberUser(i, "user" + i, "pwd" + i, "13512314123" + i, "1100@11." + i));
        }
    }

    public MemberUser getMember(String i) {
        return map.get(i);
    }
}
