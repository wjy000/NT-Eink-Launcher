package com.etang.nt_eink_launcher.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.etang.nt_eink_launcher.MainActivity;
import com.etang.nt_eink_launcher.util.AppInfo;
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
                    R.layout.desktop_gridview_item, null);
            holder = new Holder();
            holder.ico = (ImageView) convertView.findViewById(R.id.iv);
            holder.Name = (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.ico.setImageDrawable(appInfos.get(position).getIco());
        holder.Name.setText(appInfos.get(position).getName());
        get_appname_info(holder);
        return convertView;
    }

    private void get_appname_info(Holder holder) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE);
        String appname_state = sharedPreferences.getString("appname_state", null);
        try {
            if (sharedPreferences != null) {
                if (appname_state.equals("one")) {
                    holder.Name.setSingleLine(true);
                    holder.Name.setVisibility(View.VISIBLE);
                }
                if (appname_state.equals("nope")) {
                    holder.Name.setSingleLine(false);
                    holder.Name.setVisibility(View.VISIBLE);
                }
                if (appname_state.equals("hind")) {
                    holder.Name.setVisibility(View.GONE);
                }
                if (appname_state.isEmpty()) {
                    holder.Name.setSingleLine(false);
                    holder.Name.setVisibility(View.VISIBLE);
                }
            } else {
                SharedPreferences.Editor editor = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE).edit();
                editor.putString("appname_state", "nope");
                editor.apply();
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE).edit();
            editor.putString("appname_state", "nope");
            editor.apply();
            get_appname_info(holder);
        }
    }

    static class Holder {
        ImageView ico;
        TextView Name;
    }
}
