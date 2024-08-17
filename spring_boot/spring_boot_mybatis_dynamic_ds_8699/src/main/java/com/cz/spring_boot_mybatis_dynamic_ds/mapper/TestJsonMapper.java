package com.cz.spring_boot_mybatis_dynamic_ds.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cz.spring_boot_mybatis_dynamic_ds.entity.TestJson;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestJsonMapper extends BaseMapper<TestJson> {

    TestJson selectById11(int id);

}
