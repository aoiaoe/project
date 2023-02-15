package com.cz.spring_boot_drools.liteflow;

import com.yomahub.liteflow.core.NodeComponent;
import org.springframework.stereotype.Component;

@Component("b")
public class BCmp extends NodeComponent {

	@Override
	public void process() {
		//do your business
		System.out.println("组件B");
	}
}
