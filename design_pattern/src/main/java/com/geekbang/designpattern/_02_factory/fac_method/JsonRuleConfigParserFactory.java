package com.geekbang.designpattern._02_factory.fac_method;

import com.geekbang.designpattern._02_factory.IRuleConfigParser;
import com.geekbang.designpattern._02_factory.RuleConfig;

/**
 * @author jzm
 * @date 2023/4/20 : 15:10
 */
public class JsonRuleConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new IRuleConfigParser() {
            @Override
            public RuleConfig parse(String configText) {
                return null;
            }
        };
    }
}
