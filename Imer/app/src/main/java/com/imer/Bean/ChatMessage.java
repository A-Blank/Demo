package com.imer.Bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by 丶 on 2017/2/22.
 */

public class ChatMessage implements Serializable{


    private String name;            //用户名
    private String fromUserName;    //消息发送方用户名
    private String msg;             //消息内容
    private int type;               //消息方向
    private Date date;

    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public int getType(){
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }


}
