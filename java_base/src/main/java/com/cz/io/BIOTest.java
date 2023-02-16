package com.cz.io;

import java.io.*;
import java.util.concurrent.TimeUnit;

/**
 * @author jzm
 * @date 2022/11/11 : 17:47
 */
public class BIOTest {


    public static void main(String[] args) {
        File file = new File("/Users/sephiroth/Desktop/学习/test1.txt");

//        String s = "我们把这种数据的传输，可以看做是一种数据的流动，按照流动的方向，以内存为基准，分为输入input 和输出output";
//        try (BufferedOutputStream is = new BufferedOutputStream(new FileOutputStream(file))){
//            for (int i = 0; i < 10000000; i++) {
//
//                is.write((s + i + "\n").getBytes());
//            }
//            is.flush();
//        }catch (Exception e){
//            e.printStackTrace();
//        }

//        byte[] bytes = new byte[1024];
//        try (BufferedInputStream in = new BufferedInputStream(new FileInputStream(file))){
//            while(in.read(bytes) != -1){
//                System.out.println(new String(bytes));
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }

        try(BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String s = null;
            while ((s = reader.readLine()) != null){
                System.out.println(s);
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
