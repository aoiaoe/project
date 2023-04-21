package com.geekbang.designpattern._04_observable.observer;

import com.geekbang.designpattern._04_observable.service.RegPromotionServiceImpl;

/**
 * @author jzm
 * @date 2023/4/21 : 17:50
 */
public class RegPromotionObserver implements UserObserver {

//    @Autowired
    private RegPromotionServiceImpl promotionService;

    @Override
    public void regSuccess(Long userId) {
        promotionService.promotion(userId);
    }
}
