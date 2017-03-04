package com.imer.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.imer.R;

import cn.jpush.im.android.api.ContactManager;
import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.callback.GetUserInfoCallback;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by 丶 on 2017/2/23.
 */

public class SearchActivity extends Activity implements View.OnClickListener {

    private EditText Edt_contactname;
    private Button Btn_Search;
    private LinearLayout linearLayout;
    private LayoutInflater mInflater;
    private TextView Tv_contactSearched;
    private Button Btn_friendAdd;
    private View view;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);
        Init();


    }

    public void Init(){

        Edt_contactname= (EditText) findViewById(R.id.edt_contactId);

        Btn_Search= (Button) findViewById(R.id.btn_searchContact);
        Btn_Search.setOnClickListener(this);

        linearLayout= (LinearLayout) findViewById(R.id.linear_search);

        mInflater=LayoutInflater.from(this);
        view=mInflater.inflate(R.layout.item_contact_search,null);

        Tv_contactSearched= (TextView) view.findViewById(R.id.tv_contactname);

        //设置动态添加布局的属性
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        Btn_friendAdd= (Button) view.findViewById(R.id.btn_addfriend);
        Btn_friendAdd.setOnClickListener(this);
    }

    public void ContactSearch(){

        String ContactName=Edt_contactname.getText()+"";
        JMessageClient.getUserInfo(ContactName, new GetUserInfoCallback() {
            @Override
            public void gotResult(int i, String s, UserInfo userInfo) {

                if(i==0){
                    Toast.makeText(SearchActivity.this,"查找联系人成功",Toast.LENGTH_SHORT).show();
                    username=userInfo.getUserName();
                    Tv_contactSearched.setText(username);
                    linearLayout.addView(view);
                }
                else{
                    Toast.makeText(SearchActivity.this,"查找联系人失败,错误代码:"+i,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    public void FriendAdd(){

        ContactManager.sendInvitationRequest(username, "", "", new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    Toast.makeText(SearchActivity.this,"好友请求发送成功",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SearchActivity.this,"好友请求发送失败,错误代码:"+i,Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btn_searchContact:
                ContactSearch();
                break;
            case R.id.btn_addfriend:
                FriendAdd();
                break;
        }

    }
}
