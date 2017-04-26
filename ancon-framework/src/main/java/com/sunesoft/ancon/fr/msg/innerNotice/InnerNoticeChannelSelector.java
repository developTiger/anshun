package com.sunesoft.ancon.fr.msg.innerNotice;

import com.sunesoft.ancon.fr.msg.MessageChannel;
import com.sunesoft.ancon.fr.msg.MessageChannelSelector;
import com.sunesoft.ancon.fr.msg.ChannelType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by zhouz on 2016/5/18.
 */
@Component
public class InnerNoticeChannelSelector implements MessageChannelSelector {
    private final ChannelType channelType = ChannelType.InnerNotice;

    @Autowired
    private InnerNoticeChennel innerNoticeChennel;

    @Override
    public MessageChannel getChannel(ChannelType selectType) {
        if (channelType == selectType) {
            return innerNoticeChennel;
        }

        return null;
    }
}
