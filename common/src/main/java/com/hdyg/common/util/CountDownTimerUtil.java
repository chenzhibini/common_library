package com.hdyg.common.util;

import android.os.CountDownTimer;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 倒计时工具
 *
 * @author CZB
 * @time 2018/3/13 11:07
 */
public class CountDownTimerUtil {

    private static CountDownTimerUtil _instance = null;

    private CountDownTimer timer;

    /**
     * 私有构造方法
     */
    private CountDownTimerUtil() {
    }

    // 对外提供一个该类的实例，考虑多线程问题，进行同步操作
    public static CountDownTimerUtil getInstance() {
        if (_instance == null) {
            synchronized (CountDownTimerUtil.class) {
                if (_instance == null) {
                    _instance = new CountDownTimerUtil();
                }
            }
        }
        return _instance;
    }

    /**
     * 启动
     *
     * @param view
     * @param nextSendTime
     */
    public void guideStartTimer(final TextView view, long nextSendTime) {
        timer = new CountDownTimer(nextSendTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setText(millisUntilFinished / 1000 + "s");
            }

            @Override
            public void onFinish() {
                view.setText("0s");
            }
        };
        timer.start();
    }

    /**
     * 启动
     *
     * @param view
     * @param nextSendTime
     */
    public void startTimer(final TextView view, long nextSendTime) {
        timer = new CountDownTimer(nextSendTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setEnabled(false);
                view.setText(millisUntilFinished / 1000 + "秒后重试");
            }

            @Override
            public void onFinish() {
                view.setEnabled(true);
                view.setText("重新获取");
            }
        };
        timer.start();
    }

    /**
     * 启动
     *
     * @param view
     * @param nextSendTime
     */
    public void startTimer(final TextView view, final LinearLayout lltTopLeft, long nextSendTime) {
        timer = new CountDownTimer(nextSendTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                lltTopLeft.setEnabled(false);
                view.setText(""+millisUntilFinished / 1000+"");
            }

            @Override
            public void onFinish() {
                lltTopLeft.setEnabled(true);
                view.setText("");
            }
        };
        timer.start();
    }

    //秒转成时分秒
    private static String formatTimeS(long seconds) {
        int temp = 0;
        StringBuffer sb = new StringBuffer();
        if (seconds > 3600) {
            temp = (int) (seconds / 3600);
            sb.append((seconds / 3600) < 10 ? "0" + temp + ":" : temp + ":");
            temp = (int) (seconds % 3600 / 60);
            changeSeconds(seconds, temp, sb);
        } else {
            temp = (int) (seconds % 3600 / 60);
            changeSeconds(seconds, temp, sb);
        }
        return sb.toString();
    }
    private static void changeSeconds(long seconds, int temp, StringBuffer sb) {
        sb.append((temp < 10) ? "0" + temp + ":" : "" + temp + ":");
        temp = (int) (seconds % 3600 % 60);
        sb.append((temp < 10) ? "0" + temp : "" + temp);
    }

    /**
     * 启动- 倒计时
     *
     * @param view
     * @param nextSendTime
     */
    public void shopStartTimer(final TextView view, long nextSendTime) {
        timer = new CountDownTimer(nextSendTime * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                view.setText(formatTimeS(millisUntilFinished/1000));
            }

            @Override
            public void onFinish() {
                view.setText("00:00:00");
            }
        };
        timer.start();
    }

    /**
     * 销毁
     */
    public void stopTimer() {
        if (timer != null) timer.cancel();
    }

}
