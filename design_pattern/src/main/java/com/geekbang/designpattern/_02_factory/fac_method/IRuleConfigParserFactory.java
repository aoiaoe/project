package com.geekbang.designpattern._02_factory.fac_method;

import com.geekbang.designpattern._02_factory.IRuleConfigParser;

/**
 * @author jzm
 * @date 2023/4/20 : 15:08
 */
public interface IRuleConfigParserFactory {


    IRuleConfigParser createParser();
}
