package interview;

import java.util.concurrent.TimeUnit;

//题目: 实现一个类 支持100个线程同时向一个银行账户中存入一元钱
public class ShowMeBug1 {
    private double balance; // 账户余额

    /**
     * 存款
     *
     * @param money 存入金额
     */
    public void deposit(double money) {
        synchronized (ShowMeBug1.class) {
            balance += money;
        }
    }

    /**
     * 获得账户余额
     */
    public double getBalance() {
        return balance;
    }

    public static void main(String[] args) {
        //100个线程同时向一个银行账户中存入一元钱
        ShowMeBug1 showMeBug1 = new ShowMeBug1();
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                showMeBug1.deposit(1);
            }).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(showMeBug1.getBalance());
    }
}