package com.imer.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.imer.Adapter.MessageListViewAdapter;
import com.imer.Bean.ChatMessage;
import com.imer.DB.ImerDB;
import com.imer.DB.MyDatabaseHelper;
import com.imer.R;
import com.imer.Utils.ActivityController;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.event.MessageEvent;
import cn.jpush.im.android.api.model.Conversation;
import cn.jpush.im.android.api.model.Message;


/**
 * Created by 丶 on 2017/2/22.
 */

public class ChattingActivity extends Activity implements View.OnClickListener{

    private ListView mListView;
    private EditText Edt_msg;
    private Button Btn_send;
    private MessageListViewAdapter mAdapter;
    private List<ChatMessage> mDatas=new ArrayList<ChatMessage>();
    private String objectId;
    private String msg;
    private String username;
    private String toUserName;
    private MyDatabaseHelper dbHelper;
    private SharedPreferences prefs;
    private ImerDB imerDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityController.addActivity(this);
        setContentView(R.layout.chatiing_page);
        toUserName=getIntent().getStringExtra("toUserName");
        Init();


    }

    public void Init(){

        imerDB=ImerDB.getInstance(this);
        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        username=prefs.getString("username","");
        mDatas=imerDB.loadMessage(toUserName);

        mListView= (ListView) findViewById(R.id.lv_msg);

        /**
         * 设置ListView自动滚动到最后一条item
         */
//        mListView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        /**
         * // 设置ListView滑动条不可见
         */
        mListView.setVerticalScrollBarEnabled(false);
        mListView.setStackFromBottom(true);


        mAdapter=new MessageListViewAdapter(this,mDatas);
        mListView.setAdapter(mAdapter);

        Edt_msg= (EditText) findViewById(R.id.edt_msg);

        Btn_send= (Button) findViewById(R.id.btn_send);
        Btn_send.setOnClickListener(this);



    }


    public void SendMessage(){

        msg=Edt_msg.getText()+"";
        Message message=JMessageClient.createSingleTextMessage(toUserName,msg);
        JMessageClient.sendMessage(message);
        Edt_msg.setText("");

        ChatMessage chatMessage=new ChatMessage();
        chatMessage.setName(username);
        chatMessage.setFromUserName(toUserName);
        chatMessage.setMsg(msg);
        chatMessage.setType(1);

        imerDB.saveMessage(chatMessage);

        mDatas.add(chatMessage);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btn_send:
                SendMessage();
                break;
        }

    }

    public void onEvent(MessageEvent event){

//        Toast.makeText(this,"消息接收成功",Toast.LENGTH_SHORT).show();
        Message message=event.getMessage();
        String targetname=message.getFromName();
        String msg=message.getContent().toString();

        MyDatabaseHelper dbHelper=new MyDatabaseHelper(ChattingActivity.this,"Imer.db",null,1);
        SQLiteDatabase  db=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("targetusername",targetname);
        values.put("msg",msg);
        values.put("type",0);
        db.insert("Message",null,values);
//        Log.i("TAG","消息接收成功:"+msg);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }

    class Receiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if(ActivityController.isActivityInStack(ChattingActivity.this)){
                mDatas.add((ChatMessage) intent.getSerializableExtra("chatmessage"));
                mAdapter.notifyDataSetChanged();
            }
        }
    }

}
