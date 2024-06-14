package com.jzm.im.processor;

import com.jzm.common.bean.Users;
import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.common.constant.ProtocolConstants;
import com.jzm.common.util.QuickResponse;
import com.jzm.im.session.ServerSession;
import com.jzm.im.session.SessionMap;
import org.springframework.stereotype.Service;

@Service
public class LoginProcessor implements Processor {

    @Override
    public ProtoMsg.HeadType getType() {
        return ProtoMsg.HeadType.LOGIN_REQUEST;
    }

    @Override
    public boolean process(ServerSession session, ProtoMsg.Message msg) {

        ProtoMsg.LoginRequest loginRequest = msg.getLoginRequest();
        long sequence = msg.getSequence();

        Users user = Users.fromMsg(loginRequest);
        ProtoMsg.Message response = null;
        if (!checkUsers(user)) {
            //构造登录失败的报文
            response = QuickResponse.buildLogResponse(ProtocolConstants.ResultCodeEnum.NO_TOKEN, sequence, "-1");
            //发送登录失败的报文
            session.writeAndFlush(response);
            return false;
        }

        session.setUser(user);

        // 登录成功，绑定session和channel
        session.reverseBind();

        response = QuickResponse.buildLogResponse(ProtocolConstants.ResultCodeEnum.SUCCESS, sequence, session.getSessionId());
        session.writeAndFlush(response);

        return true;
    }

    private boolean checkUsers(Users user) {
        if (SessionMap.instance().hasLogin(user)) {
            return false;
        }
        //校验用户,比较耗时的操作,需要100 ms以上的时间
        //方法1：调用远程用户restfull 校验服务
        //方法2：调用数据库接口校验
        return true;
    }
}
