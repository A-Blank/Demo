package com.imer.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.imer.Bean.ChatMessage;
import com.imer.R;

import java.util.List;

/**
 * Created by 丶 on 2017/2/22.
 */

public class MessageListViewAdapter extends BaseAdapter {


    private LayoutInflater mInflater; // 布局文件转换为View
    private List<ChatMessage> mDatas;

    public MessageListViewAdapter(Context context, List<ChatMessage> mDatas) {
        mInflater = LayoutInflater.from(context);
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        ChatMessage chatMessage = mDatas.get(position);
       return chatMessage.getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder viewHolder;
        if (convertView == null) {
            if (getItemViewType(position) ==0) {
                convertView = mInflater.inflate(R.layout.item_msg_from, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.msg = (TextView) convertView.findViewById(R.id.id_from_msg_info);
            } else {
                convertView = mInflater.inflate(R.layout.item_msg_to, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.msg = (TextView) convertView.findViewById(R.id.id_to_msg_info);
            }
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // 设置数据

        viewHolder.msg.setText(mDatas.get(position).getMsg());
        return convertView;
    }

    class ViewHolder {
        private TextView name;
        private TextView msg;
    }
}
