package com.imer.Activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ListView;
import android.widget.Toast;

import com.imer.Adapter.RequestListViewAdapter;
import com.imer.Bean.Invitation;
import com.imer.DB.ImerDB;
import com.imer.Interface.CallBack;
import com.imer.R;

import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by 丶 on 2017/2/23.
 */

public class FriendRequestActivity extends Activity implements CallBack {

    private ListView listView;
    private RequestListViewAdapter mAdapter;
    private List<Invitation> Datas;
    private ImerDB imerDB;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friendrequest_page);

        Init();
    }

    public void Init(){

        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        String username=prefs.getString("username","");
        imerDB=ImerDB.getInstance(this);
        Datas=imerDB.loadInvitation(username);
        listView= (ListView) findViewById(R.id.lv_friendrequest);
        mAdapter=new RequestListViewAdapter(this,Datas,this);
        listView.setAdapter(mAdapter);


    }

    @Override
    public void RequestAcceptBtn_Down(String fromUserName) {
        ContactManager.acceptInvitation(fromUserName, "", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    Toast.makeText(FriendRequestActivity.this,"添加好友成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(FriendRequestActivity.this,"添加好友失败",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void RequestRefuseBtn_Down(String fromUserName) {

        ContactManager.declineInvitation(fromUserName, "", "", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {

            }
        });

    }
}
