package com.imer.Adapter;

import android.content.Context;
import android.telecom.Call;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.imer.Bean.Invitation;
import com.imer.Entity.Contact;
import com.imer.Interface.CallBack;
import com.imer.R;

import java.util.List;

/**
 * Created by 丶 on 2017/2/23.
 */

public class RequestListViewAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private List<Invitation> mDatas;
    private LayoutInflater mInflater;
    private CallBack callBack;

    public RequestListViewAdapter(Context context, List<Invitation> Datas, CallBack callBack){

        mContext=context;
        mDatas=Datas;
        this.callBack=callBack;
        mInflater=LayoutInflater.from(mContext);

    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=mInflater.inflate(R.layout.item_request,parent, false);
            viewHolder.Tv_fromusername=(TextView) convertView.findViewById(R.id.tv_fromusername);
            viewHolder.Btn_accept= (Button) convertView.findViewById(R.id.btn_accept);
            viewHolder.Btn_refuse= (Button) convertView.findViewById(R.id.btn_refuse);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.Tv_fromusername.setText("用户"+mDatas.get(position).getUserName()+"请求添加你为好友");

        String fromUserName=mDatas.get(position).getUserName();
        viewHolder.Btn_accept.setOnClickListener(this);
        viewHolder.Btn_accept.setTag(fromUserName);

        viewHolder.Btn_refuse.setOnClickListener(this);
        viewHolder.Btn_refuse.setTag(fromUserName);
        return convertView;
    }

    @Override
    public void onClick(View v) {

        switch(v.getId())
        {
            case R.id.btn_accept:
                callBack.RequestAcceptBtn_Down((String) v.getTag());
                break;
            case R.id.btn_refuse:
                callBack.RequestRefuseBtn_Down((String) v.getTag());
                break;
        }

    }


    class ViewHolder{

        TextView Tv_fromusername;
        Button Btn_accept;
        Button Btn_refuse;
    }


}
