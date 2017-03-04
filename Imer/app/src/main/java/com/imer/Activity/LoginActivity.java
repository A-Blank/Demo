package com.imer.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.imer.R;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.api.BasicCallback;

/**
 * Created by 丶 on 2017/2/21.
 */

public class LoginActivity extends Activity implements View.OnClickListener {

    private Button Btn_login;
    private EditText Edt_username;
    private  EditText Edt_password;
    private Button Btn_gotoregister;
    private Intent intent=new Intent();
    private ProgressDialog progressDialog;
    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        JMessageClient.setDebugMode(true);
        JMessageClient.init(this);

        editor= PreferenceManager.getDefaultSharedPreferences(LoginActivity.this).edit();
        prefs=PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLogin=prefs.getBoolean("islogin",false);
        if(isLogin){
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        Init();
    }

    public void Init(){

        Btn_login= (Button) findViewById(R.id.btn_login);
        Btn_login.setOnClickListener(this);

        Edt_username= (EditText) findViewById(R.id.edt_userID);
        Edt_username.setOnClickListener(this);

        Edt_password= (EditText) findViewById(R.id.edt_psd);
        Edt_password.setOnClickListener(this);

        Btn_gotoregister= (Button) findViewById(R.id.btn_register);
        Btn_gotoregister.setOnClickListener(this);
    }

    public void Login(){

        final String username=Edt_username.getText().toString();
        String password=Edt_password.getText().toString();
        JMessageClient.login(username, password, new BasicCallback() {

            @Override
            public void gotResult(int i, String s) {
                if(i==0){
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();

                    editor.putBoolean("islogin",true);
                    editor.putString("username",username);
                    editor.commit();
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }
                else{
                    Toast.makeText(LoginActivity.this,"登录失败,错误代码:"+i,Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btn_login:
                progressDialog=new ProgressDialog(this);
                progressDialog.show();
                Login();
                break;
            case R.id.btn_register:
                intent.setClass(this,RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }
}
