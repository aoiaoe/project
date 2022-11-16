package com.cz.script.js;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

/**
 * @author jzm
 * @date 2022/11/9 : 17:34
 */
public class JsEngine {

    public static void main(String[] arags){
        try{
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            StringBuffer script = new StringBuffer();
//            script.append("var a = 0;");
//            script.append("obj.r = 0;");
            script.append("function aa(x){ return x + 1;print}");

            script.append("function test(arg){\n" +
                    "for(;;){}" +
                    "\tif(arg.indexOf(\"今天\") != -1){\n" +
                    "    \targ = arg.replaceAll(\"今天\", \"today\");\n" +
                    "    }\n" +
                    "\treturn arg;\n" +
                    "}");
            KeywordCheckUtils7.checkInsecureKeyword(script.toString());
//            script.append("a = aa(1);");
            //执行这段script脚本
            engine.eval(script.toString());
            // javax.script.Invocable 是一个可选的接口
            // 检查你的script engine 接口是否已实现!
            // 注意：JavaScript engine实现了Invocable接口
            Invocable inv = (Invocable) engine;
            // 获取我们想调用那个方法所属的js对象
            Object obj = inv.invokeFunction("aa", 13);
            System.out.println(obj);
            Object o = inv.invokeFunction("test", "今天天气真好");
            System.out.println(o);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
