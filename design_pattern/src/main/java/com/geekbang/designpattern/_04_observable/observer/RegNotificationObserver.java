package com.geekbang.designpattern._04_observable.observer;

import com.geekbang.designpattern._04_observable.service.RegNotificationImpl;

/**
 * @author jzm
 * @date 2023/4/21 : 17:55
 */
public class RegNotificationObserver implements UserObserver{

//    @Autowired
    private RegNotificationImpl regNotification;

    @Override
    public void regSuccess(Long userId) {
        regNotification.notification(userId);
    }
}
