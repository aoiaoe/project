package com.cz.springbootes.vo;

import com.cz.springbootes.entity.Id;
import lombok.Data;

/**
 * @author jzm
 * @date 2022/4/14 : 17:54
 */
@Data
public class EsVo extends Id<String> {
    private String word;
}
