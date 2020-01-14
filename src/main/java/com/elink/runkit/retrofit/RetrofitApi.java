package com.elink.runkit.retrofit;

import com.elink.runkit.bean.BaseBean;
import com.elink.runkit.bean.BaseDataListBean;
import com.elink.runkit.bean.HistogramBean;
import com.elink.runkit.bean.MonitoringPointBean;
import com.elink.runkit.bean.PointsInfoBean;
import com.elink.runkit.bean.ReportPoliceBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @Description：管理Retrofit的各种数据请求(post、get)，包含请求api、请求参数
 * @Author： Evloution_
 * @Date： 2019-11-27
 * @Email： 15227318030@163.com
 */
public interface RetrofitApi {

    // 获取监测点数目
    @POST("connector/getPointsInfo")
    Observable<BaseBean<PointsInfoBean>> getPointsInfo();

    // 获取首页图表数据
    @POST("connector/getHistogramList")
    @FormUrlEncoded
    Observable<BaseBean<HistogramBean>> getHistogramList(@Field("pointType") String pointType);

    /**
     * 获取监测点列表
     * @param pointname 设备名称
     * @param pointip 设备ip
     * @param limit 显示几条数据
     * @param page 请求几页数据
     * @return
     */
    @POST("connector/getPointList")
    @FormUrlEncoded
    Observable<BaseDataListBean<MonitoringPointBean>> getPointList(@Field("pointname") String pointname, @Field("pointip") String pointip, @Field("limit") String limit, @Field("page") String page);

    /**
     * 获取告警信息
     * @param limit     显示几条数据
     * @param page      请求几页数据
     * @return
     */
    @POST("connector/getWarnList")
    @FormUrlEncoded
    Observable<BaseDataListBean<ReportPoliceBean>> getWarnList(@Field("limit") String limit, @Field("page") String page);

    /**
     * 告警信息确认
     * @param id 告警记录的id
     * @return
     */
    @POST("connector/confirmWarn")
    @FormUrlEncoded
    Observable<BaseBean> confirmWarn(@Field("id") String id);
}
