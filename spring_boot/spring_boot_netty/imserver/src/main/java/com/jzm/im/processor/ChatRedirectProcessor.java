package com.jzm.im.processor;

import com.jzm.common.bean.msg.ProtoMsg;
import com.jzm.im.session.ServerSession;
import com.jzm.im.session.SessionMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ChatRedirectProcessor implements Processor{

    @Override
    public ProtoMsg.HeadType getType() {
        return ProtoMsg.HeadType.MESSAGE_REQUEST;
    }

    @Override
    public boolean process(ServerSession session, ProtoMsg.Message message) {
        // 聊天处理
        ProtoMsg.MessageRequest msg = message.getMessageRequest();
        log.info("chatMsg | from:{} to {} : {}", msg.getFrom(), msg.getTo(), msg.getContent());
        ServerSession toSession = SessionMap.instance().getSessionBy(msg.getTo());
        if(toSession == null){
            log.info("chatMsg | to:{} 不在线", msg.getTime());
        }  else {
            // 转发
            toSession.writeAndFlush(message);
        }
        return true;
    }
}
