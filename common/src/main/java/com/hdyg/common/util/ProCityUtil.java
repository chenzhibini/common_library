package com.hdyg.common.util;

import android.content.Context;
import android.graphics.Color;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.hdyg.common.bean.ProBean;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citypickerview.CityPickerView;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author CZB
 * @describe 省市区工具
 * @time 2019/11/4 18:06
 */
public class ProCityUtil {
    //时间格式
    public static final int HHMM = 1;
    //自定义item格式
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;
    private Context mContext;
    private int timeType;
    private CityPickerView mPicker;
    private TimePickerView timePickerView;
    private boolean year = false,month = false,day = false,hour = false,min = false,second = false;
    private List<ProBean> proBeans,cityBeans,areaBeans;
    private int level;

    /**
     * private 的构造方法，避免外界直接使用new 来初始化对象
     */
    public ProCityUtil(Context mContext) {
        this.mContext = mContext;
        init();
    }

    public ProCityUtil(Context mContext,int level) {
        this.mContext = mContext;
        this.level = level;
    }

    public ProCityUtil(Context mContext,int timeType,boolean both) {
        this.mContext = mContext;
        this.timeType = timeType;
        initTime();
        init();
    }

    public interface OnSelectedListener {
        void onSelected(ProvinceBean province, CityBean city, DistrictBean district);
    }
    public interface OnTimeSelectedListener {
        void onSelected(Date date);
    }
    public interface OnItemSelectedListener {
        void onSelected(int options1, int options2, int options3);
    }

    private void init(){
        mPicker=new CityPickerView();
        mPicker.init(mContext);
        //配置
        CityConfig cityConfig = new CityConfig.Builder()
                .title("选择城市")//标题
                .titleTextSize(18)//标题文字大小
                .titleTextColor("#585858")//标题文字颜  色
                .titleBackgroundColor("#E9E9E9")//标题栏背景色
                .confirTextColor("#585858")//确认按钮文字颜色
                .confirmText("确认")//确认按钮文字
                .confirmTextSize(16)//确认按钮文字大小
                .cancelTextColor("#585858")//取消按钮文字颜色
                .cancelText("取消")//取消按钮文字
                .cancelTextSize(16)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(5)//显示item的数量
                .province("福建省")//默认显示的省份
                .city("厦门市")//默认显示省份下面的城市
                .district("湖里区")//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
//                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
//                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(true)//滚轮不显示模糊效果
                .setLineColor("#03a9f4")//中间横线的颜色
                .setLineHeigh(5)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();
        //添加配置
        mPicker.setConfig(cityConfig);
    }
    private void initTime(){
        switch (timeType){
            case HHMM:
                hour = true;
                min = true;
                break;
        }
    }

    /**
     * 显示省市区  自带数据
     * @param listener 回调
     */
    public void showProCityPicker(final OnSelectedListener listener){

        mPicker.showCityPicker();
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                listener.onSelected(province,city,district);
            }
        });
    }

    /**
     * 显示时间
     * @param listener
     */
    public void showTimePicker(final OnTimeSelectedListener listener){
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(1990, 0, 1);
        endDate.set(2040, 11, 31);
        TimePickerView timePicker = new TimePickerBuilder(mContext, (date, v) -> {
            listener.onSelected(date);
        }).setType(new boolean[]{year, month, day, hour, min, second})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setContentTextSize(14)//滚轮文字大小
                .setTitleSize(18)//标题文字大小
                .setTitleText("时间选择")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.BLACK)//标题文字颜色
                .setSubmitColor(Color.GRAY)//确定按钮文字颜色
                .setCancelColor(Color.GRAY)//取消按钮文字颜色
                .setTitleBgColor(Color.WHITE)//标题背景颜色 Night mode
                .setBgColor(Color.WHITE)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        timePicker.show();
    }

    /**
     * 自定义数据
     * @param listener 监听
     * @param options1Items 第一列数据
     * @param options2Items 第二列数据
     * @param options3Items 第三列数据
     */
    public void showProPickBuilder(OnItemSelectedListener listener,List<String> options1Items,List<String> options2Items,List<String> options3Items){
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext,(options1, options2, options3, v) -> {
            listener.onSelected(options1,options2,options3);
        }).setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        switch (level){
            case LEVEL_1:
                pvOptions.setPicker(options1Items);//一级选择器
                break;
            case LEVEL_2:
                pvOptions.setPicker(options1Items,options2Items);//一级选择器
                break;
            case LEVEL_3:
                pvOptions.setPicker(options1Items,options2Items,options3Items);//一级选择器
                break;
        }
        pvOptions.show();
    }

}
