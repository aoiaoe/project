package com.cz.jvm;

/**
 *  CPU占用过高
 *  1、top 找出占用最高的进程
 *  2、top -H -p pid 找出占用最高的线程id
 *  3、printf "0x%x\n" tid 将线程id转换为16进制
 *  4、jstack -l pid 查找堆栈信息, 使用16进制线程号即可追踪到线程堆栈
 */
public class JstackTest {

    public static void main(String[] args) {
        while (true){

        }
    }

}
