package com.cz.spring_boot_drools;

import com.alibaba.fastjson.JSONObject;
import com.cz.spring_boot_drools.esayrule.RuleResult;
import lombok.extern.slf4j.Slf4j;
import org.jeasy.rules.api.Facts;
import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;
import org.jeasy.rules.mvel.MVELRule;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@SpringBootTest
class SpringBootDroolsApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	void jsonExpressionTest() {
		// 输入项
		JSONObject dataSource = new JSONObject();
		dataSource.put("name","哈哈1呵呵");
		dataSource.put("age",19);
		String regex = "[\\u4e00-\\u9fa5]{2}";
		Pattern compile = Pattern.compile(regex);
		Matcher matcher = compile.matcher("哈哈1呵呵");
		dataSource.put("compile", compile);
		dataSource.put("matcher", matcher);

//		System.out.println(matcher.matches());
		while (matcher.find()) {
			System.out.println(matcher.group());
		}
		// 结果
		RuleResult result = new RuleResult();
		// 规则实例
		Facts facts = new Facts();
		facts.put("fact",dataSource);
		facts.put("result",result);

		// when
//		String when = "fact.get(\"name\").startsWith(\"哈\") && fact.get(\"age\") >= 18";
		String when = "fact.get(\"compile\").matcher(fact.get(\"name\")).find()";
		// then
		String then = "if(!fact.get(\"compile\").matcher(fact.get(\"name\")).find()){result.setValue(fact.get(\"name\"));}";
		// 规则内容
		Rule testModeRule = new MVELRule()
				.name("test rule")
				.description("rule for test")
				.when(when)
				.then(then);
		// 规则集合
		Rules rules = new Rules();
		// 将规则添加到集合
		rules.register(testModeRule);
		//创建规则执行引擎，并执行规则
		RulesEngine rulesEngine = new DefaultRulesEngine();
		rulesEngine.fire(rules, facts);
		log.info("result:{}",result.toString());
	}

}
