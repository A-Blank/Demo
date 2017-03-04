package com.imer.Utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 丶 on 2017/3/3.
 */

public class ActivityController {

    private static List<Activity> activityList=new ArrayList<Activity>();

    public static void addActivity(Activity activity){
        activityList.add(activity);
    }

    public static void removeActivity(Activity activity){
        activityList.remove(activity);
    }

    public static boolean isActivityInStack(Activity activity){

        if(activityList.contains(activity))
            return true;
        return false;

    }

}
