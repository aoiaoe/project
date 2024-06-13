package com.cz.springbootmybatissourcecode.mapper;

import com.cz.springbootmybatissourcecode.entity.TestDeadlock;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestDeadLock {

    TestDeadlock selectForUpdate(int c);

    int insertTestDeadLock(TestDeadlock param);
}
