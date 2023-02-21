package com.cz;

//import org.python.core.PyFunction;
//import org.python.core.PyObject;
//import org.python.core.PyString;
//import org.python.util.PythonInterpreter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author jzm
 * @date 2022/11/15 : 09:44
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

//    @PostConstruct
//    public void run(){
//        PythonInterpreter interpreter = new PythonInterpreter();
//        StringBuffer sb = new StringBuffer();
//        sb.append("\n" +
//                "import datetime\n" +
//                "import time\n" +
//                "def test(str):\n" +
//                "    dateObj = datetime.datetime.strptime(str, \"%Y-%m-%d\")\n" +
//                "    localTime = time.localtime(time.time())\n" +
//                "    localYear = localTime[0]\n" +
//                "    return localYear - dateObj.year\n");
//        interpreter.exec(sb.toString());
//
//        // 第一个参数为需要执行的函数（变量）的名字，第二个参数为期望返回的对象类型
//        PyFunction pyFunction = interpreter.get("test", PyFunction.class);
//        int a = 5, b = 10;
//        //调用函数，如果函数需要参数，在Java中必须先将参数转化为对应的“Python类型”
//        PyObject pyobj = pyFunction.__call__(new PyString("1995-07-24"));
//        System.out.println("the anwser is: " + pyobj.toString());
//    }
}
