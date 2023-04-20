package com.geekbang.designpattern._02_factory.abs_fac;

import com.geekbang.designpattern._02_factory.IRuleConfigParser;
import com.geekbang.designpattern._02_factory.ISystemConfigParse;

/**
 * @author jzm
 * @date 2023/4/20 : 15:16
 */
public class XmlRuleConfigFactory implements AbstractRuleConfigFactory{
    @Override
    public IRuleConfigParser getRuleParser() {
        return null;
    }

    @Override
    public ISystemConfigParse getSystemParser() {
        return null;
    }
}
