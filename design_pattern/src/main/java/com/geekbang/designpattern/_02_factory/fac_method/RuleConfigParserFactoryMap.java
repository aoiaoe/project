package com.geekbang.designpattern._02_factory.fac_method;

import com.geekbang.designpattern._02_factory.IRuleConfigParser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jzm
 * @date 2023/4/20 : 15:08
 */
public class RuleConfigParserFactoryMap {

    private static final Map<String, IRuleConfigParserFactory> parser = new HashMap<>();
    static {
        parser.put("xml", new XmlRuleConfigParserFactory());
        parser.put("json", new JsonRuleConfigParserFactory());
    }
    public static IRuleConfigParserFactory getParse(String ruleConfigFileExtension) {
        return parser.get(ruleConfigFileExtension);
    }
}
