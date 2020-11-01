package com.etang.nt_launcher.tool.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etang.nt_launcher.R;
import com.etang.nt_launcher.tool.savearrayutil.SaveArrayImageUtil;

import java.io.File;
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
                    R.layout.list_gridview_item, null);
            holder = new Holder();
            holder.ico = (ImageView) convertView.findViewById(R.id.iv_gridview_app_icon);
            holder.Name = (TextView) convertView.findViewById(R.id.tv_gridview_app_name);
            holder.line_appinfo = (LinearLayout) convertView.findViewById(R.id.line_appinfo);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.ico.setImageBitmap(appInfos.get(position).getIco());
        get_iconsize(context, holder);
        holder.Name.setText(appInfos.get(position).getName());
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList = SaveArrayImageUtil.getSearchArrayList(context);
        for (int i = 1; i < arrayList.size(); i++) {
            String str = arrayList.get(i);
            String[] all = str.split("-");
            if (appInfos.get(position).getPackageName().equals(all[0])) {
                holder.ico.setImageURI(Uri.fromFile(new File("sdcard/ntlauncher/" + all[1])));
            }
        }
        get_appname_info(holder);//读取APP名称和边框大小设置
        return convertView;
    }

    private void get_iconsize(Context context, Holder holder) {
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences("info", context.MODE_PRIVATE);
            int size = Integer.valueOf(sharedPreferences.getString("icon_size", null));
            ViewGroup.LayoutParams params = holder.ico.getLayoutParams();
            params.height = size;
            params.width = size;
            holder.ico.setLayoutParams(params);
        } catch (Exception e) {
            SharedPreferences sharedPreferences = context.getSharedPreferences("info", context.MODE_PRIVATE);
            sharedPreferences.edit().putString("icon_size", "45").commit();
            get_iconsize(context, holder);
        }
    }

    /**
     * 读取APP名称和边框大小设置
     *
     * @param holder
     */
    private void get_appname_info(Holder holder) {
        SharedPreferences sharedPreferences;
        sharedPreferences = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE);
        String appname_state = sharedPreferences.getString("appname_state", null);
        String appblok_state = sharedPreferences.getString("appblok_state", null);
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
                if (appname_state.equals("null")) {
                    holder.Name.setSingleLine(true);
                    holder.Name.setVisibility(View.VISIBLE);
                }
                if (appblok_state.equals("null")) {
                    holder.line_appinfo.setBackground(null);
                }
                if (appblok_state.equals("hind_blok")) {
                    holder.line_appinfo.setBackground(null);
                }
                if (appblok_state.equals("show_blok")) {
                    holder.line_appinfo.setBackgroundResource(R.drawable.shaper_zhijiao_lins_show);
                }
                if (appblok_state.equals("show_nocolor_blok")) {
                    holder.line_appinfo.setBackgroundResource(R.drawable.shaper_zhijiao_lins_nocolor);
                }
            }
        } catch (Exception e) {
            SharedPreferences.Editor editor = context.getSharedPreferences("info_app_list_state", context.MODE_PRIVATE).edit();
            editor.putString("appname_state", "null");
            editor.putString("appblok_state", "show_nocolor_blok");
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
