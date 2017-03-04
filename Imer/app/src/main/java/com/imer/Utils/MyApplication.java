package com.imer.Utils;

import android.app.Application;
import android.util.Log;

import com.imer.Entity.Contact;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by 丶 on 2017/2/25.
 */

public class MyApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("TAG", "getDatas: ");
    }

}
