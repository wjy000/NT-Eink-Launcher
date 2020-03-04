package com.etang.nt_eink_launcher.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etang.nt_launcher.R;

import java.util.ArrayList;
import java.util.List;

public class DeskTopGridViewBaseAdapter extends BaseAdapter {

    Context context;
    List<AppInfo> appInfos = new ArrayList<AppInfo>();

    public DeskTopGridViewBaseAdapter(List<AppInfo> appInfos, Context context) {
        this.appInfos = appInfos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return appInfos.size();
    }

    @Override
    public Object getItem(int position) {
        return appInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.gridview_item, null);
            holder = new Holder();
            holder.ico = (ImageView) convertView.findViewById(R.id.iv);
            holder.Name = (TextView) convertView.findViewById(R.id.tv);
            holder.line_appinfo = (LinearLayout) convertView.findViewById(R.id.line_appinfo);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.ico.setImageDrawable(appInfos.get(position).getIco());
        holder.Name.setText(appInfos.get(position).getName());
//        holder.line_appinfo.setBackgroundResource(R.drawable.shaper_desk_top_show);
        get_appname_info(holder);
        return convertView;
    }

    private void get_appname_info(Holder holder) {
        Log.e("APP", "加载");
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE);
        String appname_state = sharedPreferences.getString("appname_state", null);
        String appblok_state = sharedPreferences.getString("appblok_state", null);
        try {
            if (sharedPreferences != null) {
                if (appname_state.equals("one")) {
                    Log.e("APP", "1");
                    holder.Name.setSingleLine(true);
                    holder.Name.setVisibility(View.VISIBLE);
                }
                if (appname_state.equals("nope")) {
                    Log.e("APP", "2");
                    holder.Name.setSingleLine(false);
                    holder.Name.setVisibility(View.VISIBLE);
                }
                if (appname_state.equals("hind")) {
                    Log.e("APP", "3");
                    holder.Name.setVisibility(View.GONE);
                }
                if (appname_state.isEmpty()) {
                    Log.e("APP", "ERROR");
                    holder.Name.setSingleLine(false);
                    holder.Name.setVisibility(View.VISIBLE);
                }
                if (appblok_state.isEmpty()) {
                    Log.e("APP", "ERROR");
                    holder.line_appinfo.setBackground(null);
                }
                if (appblok_state.equals("hind_blok")) {
                    Log.e("APP", "隐藏");
                    holder.line_appinfo.setBackground(null);
                }
                if (appblok_state.equals("show_blok")) {
                    Log.e("APP", "显示");
                    holder.line_appinfo.setBackgroundResource(R.drawable.shaper_desk_top_show);
                }
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE).edit();
            editor.putString("appname_state", "nope");
            editor.putString("appblok_state", "hind_blok");
            editor.apply();
            get_appname_info(holder);
        }
    }

    static class Holder {
        ImageView ico;
        TextView Name;
        LinearLayout line_appinfo;
    }
}
