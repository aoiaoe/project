package com.cz.spring_boot_mybatis_dynamic_ds.typehandler;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

/**
 * @author jzm
 * @date 2024/5/23 : 16:32
 */
@MappedTypes(Object.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class Varchar2JsonTypeHandler extends AbstractJsonTypeHandler<Object> {

    private final Class<?> type;

    public Varchar2JsonTypeHandler(Class<?> type){
        this.type = type;
    }

    @Override
    protected Object parse(String json) {
        return JSONObject.parseObject(json, type);
    }

    @Override
    protected String toJson(Object obj) {
        return JSONObject.toJSONString(obj);
    }
}
