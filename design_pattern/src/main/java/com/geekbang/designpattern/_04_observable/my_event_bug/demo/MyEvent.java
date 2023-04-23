package com.geekbang.designpattern._04_observable.my_event_bug.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * @author jzm
 * @date 2023/4/23 : 16:12
 */
@AllArgsConstructor
@ToString
@Data
public class MyEvent {

    protected String text;
}
