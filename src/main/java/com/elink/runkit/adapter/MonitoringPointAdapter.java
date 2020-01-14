package com.elink.runkit.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elink.runkit.R;
import com.elink.runkit.bean.MonitoringPointBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Evloution
 * @date 2020-01-13
 * @email 15227318030@163.com
 * @description 监测点列表的adapter
 */
public class MonitoringPointAdapter extends BaseAdapter {

    private Context context;
    private List<MonitoringPointBean> monitoringPointBeans;
    private LayoutInflater mInflater;

    public MonitoringPointAdapter(Context context, List<MonitoringPointBean> monitoringPointBeans) {
        this.context = context;
        this.monitoringPointBeans = monitoringPointBeans;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return monitoringPointBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return monitoringPointBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_fragment_devicemonitoring_devicedetails, null, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.itemDevicemonitoringDeviceNameTxt.setText(monitoringPointBeans.get(position).NAME); // 监测点名称
        viewHolder.itemDevicemonitoringDeviceStatusTxt.setText(String.valueOf(monitoringPointBeans.get(position).STATUS)); // 监测点状态
        viewHolder.itemDevicemonitoringDevicePrimaryTxt.setText(String.valueOf(monitoringPointBeans.get(position).ISPRIMARY)); // 是否为主监测点
        viewHolder.itemDevicemonitoringDeviceWarngradeTxt.setText(String.valueOf(monitoringPointBeans.get(position).WARNGRADE)); // 监测点的告警级别
        viewHolder.itemDevicemonitoringDeviceMonitypeTxt.setText(String.valueOf(monitoringPointBeans.get(position).MONITYPE)); // 监测点的监测方式
        viewHolder.itemDevicemonitoringDeviceMonipauseTxt.setText(String.valueOf(monitoringPointBeans.get(position).MONIPAUSE)); // 监测点是否还在监测
        viewHolder.itemDevicemonitoringDeviceIpandportTxt.setText(monitoringPointBeans.get(position).IP + ":" + String.valueOf(monitoringPointBeans.get(position).PORT)); // ip 和 port
        viewHolder.itemDevicemonitoringDeviceEventtimeTxt.setText(monitoringPointBeans.get(position).EVENTTIME); // 事件的时间
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.item_devicemonitoring_device_name_txt)
        TextView itemDevicemonitoringDeviceNameTxt; // 监测点名称
        @BindView(R.id.item_devicemonitoring_device_status_txt)
        TextView itemDevicemonitoringDeviceStatusTxt; // 监测点状态
        @BindView(R.id.item_devicemonitoring_device_status_bg_ll)
        LinearLayout itemDevicemonitoringDeviceStatusBgLl; // 监测点状态背景
        @BindView(R.id.item_devicemonitoring_device_primary_txt)
        TextView itemDevicemonitoringDevicePrimaryTxt; // 是否为主监测点
        @BindView(R.id.item_devicemonitoring_device_warngrade_txt)
        TextView itemDevicemonitoringDeviceWarngradeTxt; // 监测点的告警级别
        @BindView(R.id.item_devicemonitoring_device_monitype_txt)
        TextView itemDevicemonitoringDeviceMonitypeTxt; // 监测点的监测方式
        @BindView(R.id.item_devicemonitoring_device_monipause_txt)
        TextView itemDevicemonitoringDeviceMonipauseTxt; // 监测点是否还在监测
        @BindView(R.id.item_devicemonitoring_device_ipandport_txt)
        TextView itemDevicemonitoringDeviceIpandportTxt; // ip 和 port
        @BindView(R.id.item_devicemonitoring_device_eventtime_txt)
        TextView itemDevicemonitoringDeviceEventtimeTxt; // 事件的时间

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
