package com.cz.learnnetty.chatroom.message;

//import com.cz.learnnetty.chatroom.server.session.Group;
import com.cz.learnnetty.chatroom.server.session.Group;
import lombok.Data;

import java.util.List;

@Data
public class GroupListResponseMessage extends AbstractResponseMessage{

    private List<Group> groupList;

    @Override
    public int getMsgType() {
        return GroupListResponseMessage;
    }
}
