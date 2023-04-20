package com.geekbang.designpattern._02_factory;

import com.geekbang.designpattern._02_factory.RuleConfig;

public interface IRuleConfigParser {
    RuleConfig parse(String configText);
}
