package com.geekbang.designpattern._02_factory;

import com.geekbang.designpattern._02_factory.IRuleConfigParser;
import com.geekbang.designpattern._02_factory.RuleConfig;
import com.geekbang.designpattern._02_factory.abs_fac.AbstractRuleConfigFactory;
import com.geekbang.designpattern._02_factory.abs_fac.RuleFactoryMap;
import com.geekbang.designpattern._02_factory.fac_method.IRuleConfigParserFactory;
import com.geekbang.designpattern._02_factory.fac_method.RuleConfigParserFactoryMap;
import com.geekbang.designpattern._02_factory.simple.RuleConfigParserFactory;

public class RuleConfigSource {
    /**
     * 使用简单工厂获取配置解析器
     *
     * @param ruleConfigFilePath
     * @return
     */
    public RuleConfig load(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        /**
         * 从简单工厂中根据后缀获取配置解析器
         */
        IRuleConfigParser parser = RuleConfigParserFactory.createParser(ruleConfigFileExtension);
        if (parser == null) {
            throw new RuntimeException(
                    "Rule config file format is not supported: " + ruleConfigFilePath);
        }
        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    /**
     * 使用简单工厂+工厂方法获取配置解析器
     *
     * @param ruleConfigFilePath
     * @return
     */
    public RuleConfig loadByFactoryMethod(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParse(ruleConfigFileExtension);
        if (parserFactory == null) {
            throw new RuntimeException("Rule config file format is not support");
        }
        IRuleConfigParser parser = parserFactory.createParser();
        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    /**
     * 为了扩展性，方便在其他种类的配置文件的解析器出现的时候方便扩展
     * 抽象出产品工厂，由其子类进行产品生成
     * <p>
     * 比如说，如果存在一类System的配置需要解析，如果像工厂方法一样每一种解析器用一个类来生成
     * 会产生类爆炸
     *
     * @param ruleConfigFilePath
     * @return
     */
    public RuleConfig loadByAbstractFactory(String ruleConfigFilePath) {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);
        AbstractRuleConfigFactory fac = RuleFactoryMap.getParse(ruleConfigFileExtension);
        IRuleConfigParser ruleParser = fac.getRuleParser();
        fac.getSystemParser();
        return ruleParser.parse("");
    }


    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "json";
    }
}