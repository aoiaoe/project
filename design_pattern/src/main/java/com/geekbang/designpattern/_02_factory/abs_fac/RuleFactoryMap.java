package com.geekbang.designpattern._02_factory.abs_fac;

import com.geekbang.designpattern._02_factory.fac_method.IRuleConfigParserFactory;
import com.geekbang.designpattern._02_factory.fac_method.JsonRuleConfigParserFactory;
import com.geekbang.designpattern._02_factory.fac_method.XmlRuleConfigParserFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jzm
 * @date 2023/4/20 : 15:21
 */
public class RuleFactoryMap {

    private static final Map<String, AbstractRuleConfigFactory> parser = new HashMap<>();
    static {
        parser.put("xml", new XmlRuleConfigFactory());
        parser.put("json", new JsonRuleConfigFactory());
    }
    public static AbstractRuleConfigFactory getParse(String ruleConfigFileExtension) {
        return parser.get(ruleConfigFileExtension);
    }

}
