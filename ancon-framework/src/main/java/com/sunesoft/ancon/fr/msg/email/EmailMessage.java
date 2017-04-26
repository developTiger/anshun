package com.sunesoft.ancon.fr.msg.email;

import com.sunesoft.ancon.fr.msg.Msg;

/**
 * Created by zhouz on 2016/5/18.
 */
public class EmailMessage extends Msg{
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
