package com.imer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imer.Entity.Contact;
import com.imer.R;

import java.util.List;

/**
 * Created by 丶 on 2017/2/23.
 */

public class ContactListViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<Contact> mDatas;
    private LayoutInflater mInflater;

    public ContactListViewAdapter(Context context, List<Contact> Datas){

            this.mContext=context;
            this.mDatas=Datas;
            mInflater=LayoutInflater.from(context);

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
            convertView=mInflater.inflate(R.layout.item_contact,parent, false);
            viewHolder.tv_Contactname=(TextView) convertView.findViewById(R.id.tv_contactname);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder= (ViewHolder) convertView.getTag();
        }

        viewHolder.tv_Contactname.setText(mDatas.get(position).getUserName());



        return convertView;
    }


    class ViewHolder{

        TextView tv_Contactname;

    }

}
