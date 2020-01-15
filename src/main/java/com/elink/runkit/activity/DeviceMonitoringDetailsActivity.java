package com.elink.runkit.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.elink.runkit.R;
import com.elink.runkit.bean.BaseBean;
import com.elink.runkit.bean.MonitoringPointDetailsBean;
import com.elink.runkit.log.L;
import com.elink.runkit.presenter.MonitoringPointDetailsPresenter;
import com.elink.runkit.util.ToastUtil;
import com.elink.runkit.view.DataView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author Evloution
 * @date 2020-01-15
 * @email 15227318030@163.com
 * @description 监测点详情界面
 */
public class DeviceMonitoringDetailsActivity extends AppCompatActivity {

    @BindView(R.id.back_img)
    ImageView backImg; // 返回上一页按钮
    @BindView(R.id.activity_devicemonitoring_reload_btn)
    Button activityDevicemonitoringReloadBtn; // 重新加载按钮
    @BindView(R.id.activity_devicemonitoring_reload_linearlayout)
    LinearLayout activityDevicemonitoringReloadLinearlayout; // 重新加载按钮背景
    @BindView(R.id.activity_devicemonitoring_linearlayout)
    LinearLayout activityDevicemonitoringLinearlayout; // 主体页面
    @BindView(R.id.devicedetails_name_txt)
    TextView devicedetailsNameTxt; // 名称
    @BindView(R.id.devicedetails_monipause_txt)
    TextView devicedetailsMonipauseTxt; // 是否正在监测
    @BindView(R.id.devicedetails_status_txt)
    TextView devicedetailsStatusTxt; // 状态
    @BindView(R.id.devicedetails_warngrade_txt)
    TextView devicedetailsWarngradeTxt; // 告警级别
    @BindView(R.id.devicedetails_monitype_txt)
    TextView devicedetailsMonitypeTxt; // 监测方式
    @BindView(R.id.devicedetails_moniinterval_txt)
    TextView devicedetailsMoniintervalTxt; // 监测周期
    @BindView(R.id.devicedetails_onlinerate_txt)
    TextView devicedetailsOnlinerateTxt; // 在线率

    private MonitoringPointDetailsPresenter monitoringPointDetailsPresenter = null;
    String monitoringId = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_devicemonitoringdetails);
        ButterKnife.bind(this);
        // 获得传过来的id
        monitoringId = getIntent().getStringExtra("monitoringId");
        L.e("monitoringID:" + monitoringId);

        initView();
        initData();
    }

    private void initView() {
        monitoringPointDetailsPresenter = new MonitoringPointDetailsPresenter(this);
    }

    private void initData() {
        monitoringPointDetailsPresenter.onCreate();
        monitoringPointDetailsPresenter.getPointByIdPresenter(monitoringId);
        monitoringPointDetailsPresenter.attachView(new DataView<BaseBean<MonitoringPointDetailsBean>>() {
            @Override
            public void onSuccess(BaseBean<MonitoringPointDetailsBean> TBean) {
                L.e("onSuccess：");
                devicedetailsNameTxt.setText(TBean.data.NAME);
                devicedetailsMonipauseTxt.setText(String.valueOf(TBean.data.MONIPAUSE));
                devicedetailsStatusTxt.setText(String.valueOf(TBean.data.STATUS));
                devicedetailsWarngradeTxt.setText(String.valueOf(TBean.data.WARNGRADE));
                devicedetailsMonitypeTxt.setText(String.valueOf(TBean.data.MONITYPE));
                devicedetailsMoniintervalTxt.setText(String.valueOf(TBean.data.MONIINTERVAL));
            }

            @Override
            public void onError(String error) {
                L.e("onError：" + error);
                ToastUtil.show(DeviceMonitoringDetailsActivity.this, error);
            }

            @Override
            public void showProgress() {

            }

            @Override
            public void hideProgress() {

            }
        });
    }

    @OnClick({R.id.back_img, R.id.activity_devicemonitoring_reload_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back_img: // 返回上一页
                this.finish();
                break;
            case R.id.activity_devicemonitoring_reload_btn: // 重新加载按钮
                break;
        }
    }
}
