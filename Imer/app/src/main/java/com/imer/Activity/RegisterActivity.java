package com.imer.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imer.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by 丶 on 2017/2/21.
 */

public class RegisterActivity extends Activity implements View.OnClickListener {

    private Button Btn_register;
    private EditText Edt_userId;
    private  EditText Edt_password;
    private EditText Edt_verification;
    private Button Btn_getvericode;
    private String UserId;
    private String PassWord;
    private String VerificationCode;
    private boolean isRegisterSuccess;
    private  EventHandler eventHandler;
    private Intent intent=new Intent();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        Init();
//        SMSSDK.initSDK(this,"1724006c6a95a","d81b663d97304b307e0d041c3da6fe56");
//        eventHandler=new EventHandler(){
//
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//
//                if (result == SMSSDK.RESULT_COMPLETE) {
//                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
//
//                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
//
//                    }
//                } else {
//                    ((Throwable) data).printStackTrace();
//                }
//            }
//        };
//
//        SMSSDK.registerEventHandler(eventHandler);


    }

    public void Init(){

        Btn_register= (Button) findViewById(R.id.btn_register);
        Btn_register.setOnClickListener(this);

        Edt_userId= (EditText) findViewById(R.id.edt_userID);
        Edt_password= (EditText) findViewById(R.id.edt_psd);



    }

    public void getVerificationCode(){

        UserId=Edt_userId.getText().toString()+"";
        PassWord=Edt_password.getText().toString()+"";
        SMSSDK.getVerificationCode("86",UserId);

    }

    public void Register(){


        UserId=Edt_userId.getText().toString()+"";
        PassWord=Edt_password.getText().toString()+"";
        JMessageClient.register(UserId, PassWord, new BasicCallback() {
            @Override
            public void gotResult(int i, String s) {
                if (i == 0) {
                    SharedPreferences.Editor editor= PreferenceManager.getDefaultSharedPreferences(RegisterActivity.this).edit();
                    editor.putString("username",UserId);
                    editor.commit();
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    intent.setClass(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this, "注册失败,错误代码:"+i, Toast.LENGTH_SHORT).show();
                }
            }
        });



    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
//            case R.id.btn_getvericode:
//                getVerificationCode();
//                break;
            case R.id.btn_register:
                Register();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
