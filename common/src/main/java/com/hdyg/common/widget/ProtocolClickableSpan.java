package com.hdyg.common.widget;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import androidx.annotation.NonNull;

import com.hdyg.common.common.AppManager;
import com.hdyg.common.util.WordsUtil;
import com.hdyg.common.util.dialog.JDialogLsitener;

/**
 * @author CZB
 * @describe 协议文本
 * @time 2020/8/4 9:35
 */
public class ProtocolClickableSpan extends ClickableSpan {

    public static final String TAG_PROTOCOL_USER = "userProtocol";  //用户协议
    public static final String TAG_PROTOCOL_YINSI = "yinsiProtocol";//隐私政策
    public static final String TAG_PROTOCOL_OTHER = "other";        //其他
    private JDialogLsitener.ProtocolListener listener;

    private String tag;
    private Context context = AppManager.getAppManager().currentActivity();
    private int textColor;

    public ProtocolClickableSpan(JDialogLsitener.ProtocolListener listener, Context mContext, String tag, int textColor) {
        super();
        this.listener = listener;
        this.tag = tag;
        this.context = mContext;
        this.textColor = textColor;
    }

    public ProtocolClickableSpan(String tag, int textColor) {
        super();
        this.tag = tag;
        this.textColor = textColor;
    }


    @Override
    public void onClick(@NonNull View widget) {
        switch (tag){
            case TAG_PROTOCOL_USER:
                //用户协议
                listener.userProtocolListener();
                break;
            case TAG_PROTOCOL_YINSI:
                //隐私政策
                listener.yinsiProtocolListener();
                break;
        }
    }

    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setColor(WordsUtil.getColor(textColor));
    }
}
