package com.cz.learnnetty.chatroom.server.handler;

import com.cz.learnnetty.chatroom.message.LoginRequestMessage;
import com.cz.learnnetty.chatroom.message.LoginResponseMessage;
import com.cz.learnnetty.chatroom.message.Message;
import com.cz.learnnetty.chatroom.server.service.LoginService;
import com.cz.learnnetty.chatroom.server.session.Session;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {

    private LoginService loginService;
    private Session session;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestMessage message) throws Exception {
        log.info("处理登录");
        try {
            LoginRequestMessage loginRequestMessage = (LoginRequestMessage) message;
            LoginResponseMessage response = new LoginResponseMessage();
            if (loginService.login(loginRequestMessage)) {
                // 登录成功之后，将当前handler从channel的pipline中移除
                ctx.channel().pipeline().remove(this);
                response.setSuccess(true);
                // 记录SESSION
                session.bind(ctx.channel(), loginRequestMessage.getUserName());
                ctx.writeAndFlush(response);
            } else {
                response.setSuccess(false);
                response.setMsg("账号或者密码错误，登录失败");
                ctx.writeAndFlush(response);
                log.info("账号密码错误，登录失败");
                ctx.close();
            }
        }catch (Exception e){
            e.printStackTrace();
            log.info("登录失败:{}", e);
            ctx.close();
        }
    }
}
