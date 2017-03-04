package com.imer.Service;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.util.Log;

import com.imer.Activity.ChattingActivity;
import com.imer.Activity.FriendRequestActivity;
import com.imer.Bean.ChatMessage;
import com.imer.Bean.Invitation;
import com.imer.DB.ImerDB;
import com.imer.DB.MyDatabaseHelper;
import com.imer.Entity.Contact;
import com.imer.Utils.MyApplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.content.TextContent;
import cn.jpush.im.android.api.event.ContactNotifyEvent;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Message;

/**
 * Created by 丶 on 2017/2/22.
 */

public class JMService extends Service {

    private ChattingActivity MsgReceiver;
    private FriendRequestActivity RequestReceiver;
    private SharedPreferences prefs;
    private ImerDB imerDB;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        imerDB=ImerDB.getInstance(this);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        JMessageClient.registerEventReceiver(this);
        Log.i("TAG","Service Create");


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("TAG","Service Start");
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        Log.i("TAG","Service Stop");
        JMessageClient.unRegisterEventReceiver(this);

    }

    public void onEvent(MessageEvent event){

        String username=prefs.getString("username","");
        Message message=event.getMessage();
        String fromname=message.getFromName();
        String msg=null;

        switch ((message.getContentType()))
        {
            case text:
                TextContent textContent= (TextContent) message.getContent();
                msg=textContent.getText();
                break;
        }
        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setName(username);
        chatMessage.setFromUserName(fromname);
        chatMessage.setMsg(msg);
        chatMessage.setType(0);
        imerDB.saveMessage(chatMessage);
        Intent intent=new Intent();
        intent.setAction("com.imer.Chatting");
        intent.putExtra("chatmessage",(Serializable)chatMessage);
        sendBroadcast(intent);
//        Log.i("TAG","消息接收成功:"+msg);

    }

    public void onEvent(ContactNotifyEvent event){
        String username=prefs.getString("username","");
        String fromUsername = event.getFromUsername();
        Invitation invitation=new Invitation();
        invitation.setUserName(username);
        invitation.setFromUserName(fromUsername);

        switch (event.getType()) {
            case invite_received://收到好友邀请
                imerDB.saveInvitation(invitation);
                break;
            case invite_accepted://对方接收了你的好友邀请
                //...
                break;
            case invite_declined://对方拒绝了你的好友邀请
                //...
                break;
            case contact_deleted://对方将你从好友中删除
                //...
                break;
            default:
                break;
        }

    }


}
