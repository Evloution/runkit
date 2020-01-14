package com.elink.runkit.echart;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;
import com.github.abel533.echarts.series.Line;
import com.github.abel533.echarts.series.Pie;
import com.github.abel533.echarts.style.ItemStyle;

public class EchartOptionUtil {

    /**
     * 折线图
     *
     * @param xAxis
     * @param yAxis
     * @return
     */
    public static GsonOption getLineChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("折线图");
        option.legend("销量");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        ItemStyle dataStyle = new ItemStyle();
        dataStyle.normal().label().show(true).formatter("{b}\n({d}%)");

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        // pie  bar
        Line line = new Line();
        line.smooth(false).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(line);
        return option;
    }

    /**
     * 柱状图
     *
     * @param xAxis
     * @param yAxis
     * @return
     */
    public static GsonOption getBarChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();

        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        ItemStyle dataStyle = new ItemStyle();
        dataStyle.normal().label().show(true).formatter("{b}\n({d}%)");

        CategoryAxis categorxAxis = new CategoryAxis();
        //categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        // pie  bar
        Bar bar = new Bar();
        bar.data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
//        Line line = new Line();
//        line.smooth(false).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(bar);
        return option;
    }

    /**
     * 饼图
     *
     * @param xAxis
     * @param yAxis
     * @return
     */
    public static GsonOption getPieChartOptions(Object[] xAxis, Object[] yAxis) {
        GsonOption option = new GsonOption();
        option.title("饼图");
        option.legend("销量");
        option.tooltip().trigger(Trigger.axis);

        ValueAxis valueAxis = new ValueAxis();
        option.yAxis(valueAxis);

        ItemStyle dataStyle = new ItemStyle();
        dataStyle.normal().label().show(true).formatter("{b}\n({d}%)");

        CategoryAxis categorxAxis = new CategoryAxis();
        categorxAxis.axisLine().onZero(false);
        categorxAxis.boundaryGap(true);
        categorxAxis.data(xAxis);
        option.xAxis(categorxAxis);

        // pie  bar
        Pie pie = new Pie();
        pie.center("48%", "45%").radius("55", "80").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        /*pie.clockWise(false).center("48%", "45%").radius("55", "80")
                .itemStyle(dataStyle).data();*/
        //line.smooth(false).name("销量").data(yAxis).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
        option.series(pie);
        return option;
    }
}
