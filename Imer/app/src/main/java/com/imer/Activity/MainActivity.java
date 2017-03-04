package com.imer.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.imer.Adapter.ContactListViewAdapter;
import com.imer.Entity.Contact;
import com.imer.Service.JMService;
import com.imer.R;
import com.imer.Utils.ActivityController;

import java.util.ArrayList;
import java.util.List;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.callback.GetUserInfoListCallback;
import cn.jpush.im.android.api.model.UserInfo;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {


    private List<Contact> Datas=new ArrayList<Contact>();
    private ContactListViewAdapter mAdapter;
    private ListView listView;
    private Button Btn_search;
    private Button Btn_request;
    private Button Btn_refresh;
    /**
     * 注销按钮
     */
    private Button Btn_logout;
    private Intent intent=new Intent();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityController.addActivity(this);

        setContentView(R.layout.activity_main);
        Init();


    }

    public void Init(){


        Intent intent=new Intent();
        intent.setClass(this, JMService.class);
        startService(intent);

        getFriendList();

        listView= (ListView) findViewById(R.id.lv_contactname);

        Btn_search= (Button) findViewById(R.id.btn_search);
        Btn_search.setOnClickListener(this);

        Btn_request= (Button) findViewById(R.id.btn_request);
        Btn_request.setOnClickListener(this);

        Btn_refresh= (Button) findViewById(R.id.btn_refresh);
        Btn_refresh.setOnClickListener(this);

        Btn_logout= (Button) findViewById(R.id.btn_logout);
        Btn_logout.setOnClickListener(this);

        mAdapter=new ContactListViewAdapter(this,Datas);
        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(this);

    }

    public void getFriendList(){

        Datas.clear();
        ContactManager.getFriendList(new GetUserInfoListCallback() {
            @Override
            public void gotResult(int i, String s, List<UserInfo> list) {

                if(i==0){

                    for(int num=0;num<list.size();num++){
                        Contact contact=new Contact();
                        String name=list.get(num).getUserName();
                        String id=String.valueOf(list.get(num).getUserID());
                        contact.setUserId(id);
                        contact.setUserName(name);
                        Datas.add(contact);
                    }
                    mAdapter.notifyDataSetChanged();

                }
                else{
                    Toast.makeText(MainActivity.this,"获取好友列表失败",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btn_search:
                intent.setClass(this,SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_request:
                intent.setClass(this,FriendRequestActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_refresh:
                getFriendList();
                break;
            case R.id.btn_logout:
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(this).edit();
                editor.putBoolean("islogin",false);
                editor.commit();
                intent.setClass(this,LoginActivity.class);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        intent.setClass(this,ChattingActivity.class);
        intent.putExtra("toUserName",Datas.get(position).getUserName());
        startActivity(intent);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
    }
}
