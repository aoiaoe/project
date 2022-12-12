package com.cz.classloader;

import com.zhg2yqq.wheels.dynamic.code.IClassExecuter;
import com.zhg2yqq.wheels.dynamic.code.IStringCompiler;
import com.zhg2yqq.wheels.dynamic.code.RunClassHandler;
import com.zhg2yqq.wheels.dynamic.code.core.ClassExecuter;
import com.zhg2yqq.wheels.dynamic.code.core.StringJavaCompiler;
import com.zhg2yqq.wheels.dynamic.code.dto.CalTimeDTO;
import com.zhg2yqq.wheels.dynamic.code.dto.ExecuteResult;
import com.zhg2yqq.wheels.dynamic.code.dto.Parameters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 参考： https://github.com/520zhgzhg/dynamic-code
 * @author jzm
 * @date 2022/12/12 : 15:41
 */
public class DynamicJavaCode {

    public static void main(String[] args) throws Exception {
        // 安全替换（key:待替换的类名,例如:java/io/File，value:替换成的类名,例如:com/zhg2yqq/wheels/dynamic/code/hack/HackFile）
        Map<String, String> hackers = new HashMap<>();
        // 是否需要返回编译、调用源码方法运行用时
        CalTimeDTO calTime = new CalTimeDTO();
        // 编译器
        IStringCompiler compiler = new StringJavaCompiler();
        // 执行器
        IClassExecuter<ExecuteResult> executer = new ClassExecuter();

        RunClassHandler handler = new RunClassHandler(compiler, executer, calTime, hackers);
        // 预编译加载源码类
        List<String> code = new ArrayList<>();
        code.add("package com.cz.classloader;\n" +
                "\n" +
                "public class Test {\n" +
                "\n" +
                "    public String test(String name){\n" +
                "        return \"hello world \" + name  + \" from zhg dynamic code !\";\n" +
                "    }\n" +
                "}");
        handler.loadClassFromSources(code);

        // 执行指定类方法
        // 严格按顺序构造待调用方法的入参参数
        Parameters params = new Parameters();
        String[] pars = new String[1];
        pars[0] = "cz";
        params.add("cz");
        // 传入参数调用类指定方法获取执行结果
        ExecuteResult result = handler.runMethod("com.cz.classloader.Test", "test", params);

        System.out.println("动态代码执行结果:" +  result.getReturnVal());
        // 当然，如果确实需要重新编译覆盖类，也可重新编译
        // handler.loadClassFromSource("Java源代码1");
    }
}
