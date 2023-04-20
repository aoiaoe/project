package com.geekbang.designpattern._02_factory.abs_fac;

import com.geekbang.designpattern._02_factory.IRuleConfigParser;
import com.geekbang.designpattern._02_factory.ISystemConfigParse;

public interface AbstractRuleConfigFactory {


    IRuleConfigParser getRuleParser();


    ISystemConfigParse getSystemParser();
}
