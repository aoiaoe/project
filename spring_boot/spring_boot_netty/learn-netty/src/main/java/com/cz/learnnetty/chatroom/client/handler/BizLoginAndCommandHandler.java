package com.cz.learnnetty.chatroom.client.handler;

import com.cz.learnnetty.chatroom.client.command.Command;
import com.cz.learnnetty.chatroom.client.command.CommandFactory;
import com.cz.learnnetty.chatroom.message.ChatRequestMessage;
import com.cz.learnnetty.chatroom.message.LoginRequestMessage;
import com.cz.learnnetty.chatroom.message.LoginResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@ChannelHandler.Sharable
@Slf4j
public class BizLoginAndCommandHandler extends ChannelInboundHandlerAdapter {

    private CountDownLatch cdl = new CountDownLatch(1);
    private AtomicBoolean LOGIN = new AtomicBoolean(false);
    private CommandFactory factory;

    public BizLoginAndCommandHandler(CommandFactory commandFactory){
        this.factory = commandFactory;
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接关闭");
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到消息:{}", msg);
        if(msg instanceof LoginResponseMessage){
            LoginResponseMessage loginResponseMessage = (LoginResponseMessage)msg;
            LOGIN.set(loginResponseMessage.isSuccess());
            log.info("登录状态:{}", loginResponseMessage);
            cdl.countDown();
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            System.out.print("请输入用户名称:");
            String user = scanner.nextLine();
            System.out.print("请输入密码:");
            String pw = scanner.nextLine();
            LoginRequestMessage loginRequestMessage = new LoginRequestMessage();
            loginRequestMessage.setUserName(user);
            loginRequestMessage.setPassword(pw);
            ctx.writeAndFlush(loginRequestMessage);
            try {
                cdl.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!LOGIN.get()){
                log.info("登录失败,退出");
                ctx.channel().close();
                return;
            }
            log.info("登录成功");
            while (true){
                try {
                    TimeUnit.MILLISECONDS.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("============================");
                System.out.println("send [username] [msg]");
                System.out.println("gsend [groupId] [msg]");
                System.out.println("glist");
                System.out.println("gcreate [gname] [user1,user2....]");
                System.out.println("gmem [groupId]");
                System.out.println("gjoin [groupId]");
                System.out.println("gquit [groupId]");
                System.out.println("quit");
                System.out.println("============================");
                System.out.print("请输入指令:");
                String cmd = scanner.nextLine();
                if(StringUtil.isNullOrEmpty(cmd)){
                    System.out.println("输入指令为空，请重新输入");
                    continue;
                }
                String[] cmds = cmd.split(" +");
                if(cmds == null || cmds.length < 1){
                    System.out.println("输入指令为空，请重新输入");
                    continue;
                }
                if("quit".equals(cmds[0])){
                    log.info("退出...");
                    ctx.channel().close();
                    return;
                } else {
                    Command command = factory.detectCmd(cmds[0]);
                    if(command != null) {
                        command.handle(ctx, cmds);
                    }
                }
            }
        }).start();

    }

    public static void main(String[] args) {
        String s = "aa b  c    d";
        for (String s1 : s.split(" +")) {
            System.out.println(s1 + " " + s1.length());
        }
    }
}
