package com.hdyg.common.util;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
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
    //自定义item格式
    public static final int LEVEL_1 = 1;
    public static final int LEVEL_2 = 2;
    public static final int LEVEL_3 = 3;
    private Context mContext;
    //时间选择器相关
    private boolean year = false, month = false, day = false, hour = false, min = false, second = false;
    private String tTitleStr = "时间选择", tConfirmStr = "确认", tCancleStr = "取消";
    private int startYear = 2000, endYear = 2040, tTitleSize = 18,
            tTitleColor = Color.BLACK, tContentSize = 14, tConfirmColor = Color.GRAY,
            tCancleColo = Color.GRAY, tTitleBgColor = Color.WHITE, tContentBgColor = Color.WHITE;
    private String Y = "年", M = "月", D = "日", H = "时", Min = "分", S = "秒";
    private boolean isShowLable = false;   //false 不显示lable  true显示
    private OnTimeSelectedListener timeListener;
    //省市区相关
    private CityPickerView mPicker;
    private String titleStr = "选择城市", confirStr = "确认", cancleStr = "取消", defaultPro = "福建省", defaultCity = "厦门市", defaultArea = "湖里区";
    private String titleColor = "#585858", confirmColor = "#585858", cancleColor = "#585858", titleBgColor = "#E9E9E9", lineColor = "#03a9f4";
    private int titleSize = 18, confirmSize = 16, cancleSize = 16;
    private OnSelectedListener proListener;
    //自定义数据相关
    private int level = LEVEL_3;
    private List<String> options1Items, options2Items, options3Items; //第一列 第二列 第三列数据
    private OnItemSelectedListener customListener;
    private int cLineColor = Color.BLACK, cTextColor = Color.BLACK, cTextSize = 20;
    private boolean isLink = false;//是否联动  默认false不联动


    /**
     * private 的构造方法，避免外界直接使用new 来初始化对象
     */
    public ProCityUtil(Context mContext) {
        this.mContext = mContext;
        init();
    }

    /**
     * 设置显示时间类型  年月日时分秒  true为显示  false不显示
     */
    public ProCityUtil setTimeType(boolean isYearShow, boolean isShowMonth, boolean isShowDay, boolean isShowHour, boolean isShowMin, boolean isShowSecond) {
        this.year = isYearShow;
        this.month = isShowMonth;
        this.day = isShowDay;
        this.hour = isShowHour;
        this.min = isShowMin;
        this.second = isShowSecond;
        return this;
    }

    /**
     * 设置时间选择器相关参数
     * 填0或null或"" 为使用默认参数
     */
    public ProCityUtil setTimeParam(int startYear, int endYear, String tTitleStr, int tTitleSize, int tTitleColor,
                                    String tConfirmStr, int tContentSize, int tConfirmColor,
                                    String tCancleStr, int tCancleColo, int tTitleBgColor, int tContentBgColor, boolean lableLang, boolean isShowLable) {
        if (startYear != 0) {
            this.startYear = startYear;
        }
        if (endYear != 0) {
            this.endYear = endYear;
        }
        if (!TextUtils.isEmpty(tTitleStr)) {
            this.tTitleStr = tTitleStr;
        }
        if (tTitleSize != 0) {
            this.tTitleSize = tTitleSize;
        }
        if (tTitleColor != 0) {
            this.tTitleColor = tTitleColor;
        }
        if (!TextUtils.isEmpty(tConfirmStr)) {
            this.tConfirmStr = tConfirmStr;
        }
        if (tConfirmColor != 0) {
            this.tConfirmColor = tConfirmColor;
        }
        if (!TextUtils.isEmpty(tCancleStr)) {
            this.tCancleStr = tCancleStr;
        }
        if (tCancleColo != 0) {
            this.tCancleColo = tCancleColo;
        }
        if (tTitleBgColor != 0) {
            this.tTitleBgColor = tTitleBgColor;
        }
        if (tContentBgColor != 0) {
            this.tContentBgColor = tContentBgColor;
        }
        if (tContentSize != 0) {
            this.tContentSize = tContentSize;
        }
        if (!lableLang) {
            Y = "Y";
            M = "M";
            D = "D";
            H = "H";
            Min = "m";
            S = "s";
        }
        this.isShowLable = isShowLable;
        return this;
    }

    /**
     * 设置时间选择器监听
     */
    public ProCityUtil setTimeListener(OnTimeSelectedListener listener) {
        this.timeListener = listener;
        return this;
    }

    /**
     * 设置省市区相关参数
     */
    public ProCityUtil setProCityAreaParam(String titleStr, int titleSize, String titleColor, String titleBgColor,
                                           String confirStr, int confirmSize, String confirmColor, String cancleStr,
                                           int cancleSize, String cancleColor, String lineColor, String defaultPro,
                                           String defaultCity, String defaultArea) {
        if (!TextUtils.isEmpty(titleStr)) {
            this.titleStr = titleStr;
        }
        if (titleSize != 0) {
            this.titleSize = titleSize;
        }
        if (!TextUtils.isEmpty(titleColor)) {
            this.titleColor = titleColor;
        }
        if (!TextUtils.isEmpty(titleBgColor)) {
            this.titleBgColor = titleBgColor;
        }
        if (!TextUtils.isEmpty(confirStr)) {
            this.confirStr = confirStr;
        }
        if (confirmSize != 0) {
            this.confirmSize = confirmSize;
        }
        if (!TextUtils.isEmpty(confirmColor)) {
            this.confirmColor = confirmColor;
        }
        if (!TextUtils.isEmpty(cancleStr)) {
            this.cancleStr = cancleStr;
        }
        if (cancleSize != 0) {
            this.cancleSize = cancleSize;
        }
        if (!TextUtils.isEmpty(cancleColor)) {
            this.cancleColor = cancleColor;
        }
        if (!TextUtils.isEmpty(defaultPro)) {
            this.defaultPro = defaultPro;
        }
        if (!TextUtils.isEmpty(defaultCity)) {
            this.defaultCity = defaultCity;
        }
        if (!TextUtils.isEmpty(defaultArea)) {
            this.defaultArea = defaultArea;
        }
        if (!TextUtils.isEmpty(lineColor)) {
            this.lineColor = lineColor;
        }
        return this;
    }

    /**
     * 设置省市区监听 自带数据
     */
    public ProCityUtil setProCityAreaListener(OnSelectedListener listener) {
        this.proListener = listener;
        return this;
    }

    /**
     * 自定义
     * 设置自定义省市区数据 不联动
     */
    public ProCityUtil setCustomDatas(int level, List<String> options1Items, List<String> options2Items, List<String> options3Items) {
        this.level = level;
        this.options1Items = options1Items;
        this.options2Items = options2Items;
        this.options3Items = options3Items;
        return this;
    }

    /**
     * 自定义
     * 设置自定义参数
     */
    public ProCityUtil setCustomParam(int cLineColor, int cTextColor, int cTextSize,boolean isLink) {
        if (cLineColor != 0) {
            this.cLineColor = cLineColor;
        }
        if (cTextColor != 0) {
            this.cTextColor = cTextColor;
        }
        if (cTextSize != 0) {
            this.cTextSize = cTextSize;
        }
        this.isLink = isLink;
        return this;
    }

    /**
     * 自定义
     * 设置自定义回调监听
     */
    public ProCityUtil setCustomListener(OnItemSelectedListener customListener) {
        this.customListener = customListener;
        return this;
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

    // 省市区
    private void init() {
        mPicker = new CityPickerView();
        mPicker.init(mContext);
        //配置
        CityConfig cityConfig = new CityConfig.Builder()
                .title(titleStr)//标题
                .titleTextSize(titleSize)//标题文字大小
                .titleTextColor(titleColor)//标题文字颜  色
                .titleBackgroundColor(titleBgColor)//标题栏背景色
                .confirTextColor(confirmColor)//确认按钮文字颜色
                .confirmText(confirStr)//确认按钮文字
                .confirmTextSize(confirmSize)//确认按钮文字大小
                .cancelTextColor(cancleColor)//取消按钮文字颜色
                .cancelText(cancleStr)//取消按钮文字
                .cancelTextSize(cancleSize)//取消按钮文字大小
                .setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS)//显示类，只显示省份一级，显示省市两级还是显示省市区三级
                .showBackground(true)//是否显示半透明背景
                .visibleItemsCount(5)//显示item的数量
                .province(defaultPro)//默认显示的省份
                .city(defaultCity)//默认显示省份下面的城市
                .district(defaultArea)//默认显示省市下面的区县数据
                .provinceCyclic(true)//省份滚轮是否可以循环滚动
                .cityCyclic(true)//城市滚轮是否可以循环滚动
                .districtCyclic(true)//区县滚轮是否循环滚动
//                .setCustomItemLayout(R.layout.item_city)//自定义item的布局
//                .setCustomItemTextViewId(R.id.item_city_name_tv)//自定义item布局里面的textViewid
                .drawShadows(true)//滚轮不显示模糊效果
                .setLineColor(lineColor)//中间横线的颜色
                .setLineHeigh(5)//中间横线的高度
                .setShowGAT(true)//是否显示港澳台数据，默认不显示
                .build();
        //添加配置
        mPicker.setConfig(cityConfig);
    }

    /**
     * 显示省市区  自带数据
     */
    public void showProCityPicker() {
        if (proListener == null) {
            return;
        }
        mPicker.showCityPicker();
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {
                super.onSelected(province, city, district);
                proListener.onSelected(province, city, district);
            }
        });
    }

    /**
     * 显示时间
     */
    public void showTimePicker() {
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(startYear, 0, 1);
        endDate.set(endYear, 11, 31);
        TimePickerView timePicker = new TimePickerBuilder(mContext, (date, v) -> {
            timeListener.onSelected(date);
        }).setType(new boolean[]{year, month, day, hour, min, second})// 默认全部显示
                .setCancelText(tCancleStr)//取消按钮文字
                .setSubmitText(tConfirmStr)//确认按钮文字
                .setContentTextSize(tContentSize)//滚轮文字大小
                .setTitleSize(tTitleSize)//标题文字大小
                .setTitleText(tTitleStr)//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(tTitleColor)//标题文字颜色
                .setSubmitColor(tConfirmColor)//确定按钮文字颜色
                .setCancelColor(tCancleColo)//取消按钮文字颜色
                .setTitleBgColor(tTitleBgColor)//标题背景颜色 Night mode
                .setBgColor(tContentBgColor)//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel(Y, M, D, H, Min, S)//默认设置为年月日时分秒
                .isCenterLabel(isShowLable) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        timePicker.show();
    }

    /**
     * 显示自定义数据pickerView
     */
    public void showCustomBuilder() {
        if (customListener == null) {
            return;
        }
        OptionsPickerView pvOptions = new OptionsPickerBuilder(mContext, (options1, options2, options3, v) -> {
            customListener.onSelected(options1, options2, options3);
        }).setDividerColor(cLineColor)
                .setTextColorCenter(cTextColor) //设置选中项文字颜色
                .setContentTextSize(cTextSize)
                .build();
        switch (level) {
            case LEVEL_1:
                pvOptions.setPicker(options1Items);//一级选择器
                break;
            case LEVEL_2:
                pvOptions.setPicker(options1Items, options2Items);//一级选择器
                break;
            case LEVEL_3:
                if (isLink){
                    pvOptions.setPicker(options1Items, options2Items, options3Items);//不联动  联动需调用setPicker
                }else {
                    pvOptions.setNPicker(options1Items, options2Items, options3Items);//联动
                }
                break;
        }
        pvOptions.show();
    }

}
