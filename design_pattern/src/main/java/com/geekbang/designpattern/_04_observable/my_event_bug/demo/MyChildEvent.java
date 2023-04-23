package com.geekbang.designpattern._04_observable.my_event_bug.demo;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author jzm
 * @date 2023/4/23 : 16:22s
 */
@ToString
@Data
public class MyChildEvent extends MyEvent {

    public MyChildEvent(String text) {
        super(text);
    }
}
