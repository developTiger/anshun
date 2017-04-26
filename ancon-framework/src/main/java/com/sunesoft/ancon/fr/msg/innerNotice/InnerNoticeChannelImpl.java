package com.sunesoft.ancon.fr.msg.innerNotice;

import com.sunesoft.ancon.fr.msg.Msg;
import org.springframework.stereotype.Service;

/**
 * Created by zhouz on 2016/5/18.
 */
@Service("innerNoticeChennel")
public class InnerNoticeChannelImpl implements InnerNoticeChennel {
    @Override
    public void process(Msg message) {
        System.out.println(((InnerNotice)message).getMessage());
    }
}
