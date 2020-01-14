package com.elink.runkit.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.elink.runkit.R;
import com.elink.runkit.adapter.MonitoringPointAdapter;
import com.elink.runkit.bean.BaseDataListBean;
import com.elink.runkit.bean.MonitoringPointBean;
import com.elink.runkit.log.L;
import com.elink.runkit.map.together.MapTogetherManager;
import com.elink.runkit.presenter.MonitoringPointPresenter;
import com.elink.runkit.util.ToastUtil;
import com.elink.runkit.view.DataView;
import com.elink.runkit.widget.LoadListView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Evloution
 * @date 2020-01-08
 * @email 15227318030@163.com
 * @description
 */
public class DeviceMonitoringFragment extends Fragment {

    @BindView(R.id.fragment_devicemonitoring_reload_btn)
    Button fragmentDevicemonitoringReloadBtn; // 重新加载按钮
    @BindView(R.id.fragment_devicemonitoring_reload_linearlayout)
    LinearLayout fragmentDevicemonitoringReloadLinearlayout; // 重新加载按钮的LinearLayout
    @BindView(R.id.fragment_devicemonitoring_linearlayout)
    LinearLayout fragmentDevicemonitoringLinearlayout; // 主体的LinearLayout
    @BindView(R.id.fragment_devicemonitoring_mapview)
    MapView fragmentDevicemonitoringMapview; // 地图的显示
    Unbinder unbinder;
    @BindView(R.id.search_img)
    ImageView searchImg; // 搜索按钮
    @BindView(R.id.devices_total_txt)
    TextView devicesTotalTxt; // 设备列表总数
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout swiperefreshlayout; // 下拉刷新
    @BindView(R.id.devicelist_listview)
    LoadListView devicelistListview; // 设备列表

    private AMap aMap = null;
    private Marker marker = null;
    public Map<String, Marker> markerMap = new ConcurrentHashMap<>();
    public static float ORGZOON = 30; // 地图初始化比例尺,地图比例尺

    private MonitoringPointPresenter monitoringPointPresenter = null;
    private MonitoringPointAdapter monitoringPointAdapter = null;
    private List<MonitoringPointBean> monitoringPointList = null;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_devicemonitoring, container, false);
        unbinder = ButterKnife.bind(this, view);
        fragmentDevicemonitoringMapview.onCreate(savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData(0, "1");
    }

    private void initView() {
        monitoringPointList = new ArrayList<>();
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = fragmentDevicemonitoringMapview.getMap();
        }
        aMap.showIndoorMap(true); // 是否显示室内地图。 true：显示室内地图；false：不显示；
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是 false。
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10)); // 设置地图缩放比例
        //设置自定义弹窗
        //aMap.setInfoWindowAdapter(new WindowAdapter(this, monitoringPointList));
        //绑定信息窗点击事件
        //aMap.setOnInfoWindowClickListener(new WindowAdapter(this));
        // 设置maker点击时的响应
        //aMap.setOnMarkerClickListener(new WindowAdapter(this));
        // 地图的滑动监听
        // aMap.setOnCameraChangeListener(onCameraChangeListener);
        // 设置点击 地图的点击事件
        /*aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                marker.hideInfoWindow();
            }
        });*/

        monitoringPointPresenter = new MonitoringPointPresenter(getContext());

        // 下拉刷新
        swiperefreshlayout.setColorSchemeResources(R.color.colorPrimary, R.color.blueness_two, R.color.blueness_three);
        swiperefreshlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("刷新", "下拉刷新");
                initData(1, "1");
            }
        });

        // 上拉加载
        devicelistListview.setInterface(new LoadListView.ILoadListener() {
            @Override
            public void onLoad() {
                L.e("上拉加载");
                page++;
                initData(2, Integer.toString(page));
            }
        });

        // 列表的点击事件
        devicelistListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L.e("点击的是：" + monitoringPointList.get(position).ID);
            }
        });
    }

    private void initData(final int code, final String page) {
        L.e("initData中的code：+ page：" + page);
        monitoringPointPresenter.onCreate();
        monitoringPointPresenter.getPointListPresenter("", "", "10", page);
        monitoringPointPresenter.attachView(new DataView<BaseDataListBean<MonitoringPointBean>>() {
            @Override
            public void onSuccess(BaseDataListBean<MonitoringPointBean> TBean) {
                L.e("onSuccess：" + TBean.getData().size());
                // 给页面赋值
                devicesTotalTxt.setText("共" + TBean.count + "家");
                // 如果时下拉刷新和第一次进入时，要重新初始化 monitoringPointList，否则下拉刷新时也会往list中add数据
                if (code == 1 || code == 0) {
                    monitoringPointList = new ArrayList<>();
                }
                // 将数据放入list中，再传到adapter
                for (int i = 0; i < TBean.getData().size(); i++) {
                    monitoringPointList.add(TBean.getData().get(i));
                }
                // 第一次加载数据和下拉刷新时走这里
                if ("1".equals(page) || page == "1") {
                    // 这个判断是为了只创建一次 MonitoringPointAdapter对象，如果上拉加载时再次走这里的话会直接弹回顶部。
                    monitoringPointAdapter = new MonitoringPointAdapter(getContext(), monitoringPointList);
                    devicelistListview.setAdapter(monitoringPointAdapter);
                }
                updateNormalMarkers();
                // 请求成功后取消刷新框
                isCloseLoad(code);
            }

            @Override
            public void onError(String error) {
                L.e("onError：" + error);
                ToastUtil.show(getContext(), error);
                // 请求失败后取消刷新框
                isCloseLoad(code);
            }

            @Override
            public void showProgress() {

            }

            @Override
            public void hideProgress() {

            }
        });
    }

    /**
     * 判断应该关闭下拉刷新框还是上拉加载框
     *
     * @param code 等于 1时，是下拉刷新
     *             等于 2时，是上拉加载
     */
    private void isCloseLoad(int code) {
        if (code == 1) {
            // 下拉刷新
            //为了保险起见可以先判断当前是否在刷新中（旋转的小圈圈在旋转）....
            if (swiperefreshlayout.isRefreshing()) {
                //关闭刷新动画
                swiperefreshlayout.setRefreshing(false);
                // 刷新后将 page改为 1
                page = 1;
            }
        } else if (code == 2) {
            // 上拉加载 后关闭加载框
            monitoringPointAdapter.notifyDataSetChanged();
            devicelistListview.loadComplete();
        }
    }

    @OnClick({R.id.fragment_devicemonitoring_reload_btn, R.id.search_img})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_devicemonitoring_reload_btn: // 重新加载数据按钮
                break;
            case R.id.search_img: // 搜索按钮
                break;
        }
    }

    /**
     * 更新普通网点数据
     */
    private void updateNormalMarkers() {
        // 判断上一次更新marker操作的操作类型,若上次显示的是聚合网点,则先清空地图,然后清空网点信息,在刷新地图marker
        aMap.clear();
        markerMap.clear();

        loadMarker(monitoringPointList, 0, 0);
    }

    /**
     * 初始化marker数据
     */
    private void loadMarker(List<MonitoringPointBean> monitoringPointBeanList, int code, int postion) {
        LatLng latLng = null;
        if (monitoringPointBeanList == null || monitoringPointBeanList.size() == 0) {
            return;
        }
        for (int i = 0; i < monitoringPointBeanList.size(); i++) {
            MonitoringPointBean MonitoringPointBean = monitoringPointBeanList.get(i);
            latLng = new LatLng(MonitoringPointBean.getLATITUDE(), MonitoringPointBean.getLONGITUDE());
            MarkerOptions options = new MarkerOptions();
            options.anchor(0.5f, 1.0f);
            options.position(latLng);
            options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_place_black_green_24dp));

            marker = aMap.addMarker(options);
            marker.setObject(MonitoringPointBean);
            marker.setZIndex(ORGZOON);
            marker.setPeriod(postion);
        }
        if (code == 1) { // code 等于 1 说明是点击的列表后走进这个方法
            // 点击列表后根据经纬度将地图缩放比例调到15
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
            marker.showInfoWindow();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        fragmentDevicemonitoringMapview.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        fragmentDevicemonitoringMapview.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        fragmentDevicemonitoringMapview.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        fragmentDevicemonitoringMapview.onDestroy();
        unbinder.unbind();
    }
}