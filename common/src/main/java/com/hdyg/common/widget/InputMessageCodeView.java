package com.hdyg.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.appcompat.widget.AppCompatEditText;
import com.hdyg.common.R;

/**
 * @author CZB
 * @describe 自定义密码输入框/验证码输入框
 * @time 2019/5/16 17:08
 */
public class InputMessageCodeView extends AppCompatEditText {

    private Paint mPaintBg;//画圆角矩形的背景
    private Paint mPaintStroke;//画圆角矩形的边框
    private Paint mPaintText;//画字 非密码框的时候

    private int mItemWidth = 0;//每个item的宽度

    private int mInterval = dp2px(5);//每个item的间隔大小  px

    private int mStrokeSize = dp2px(1); //边框大小 也是上下边距 px

    private int mitemSize = 6;//字数的个数

    private boolean mIsPassWordMode = false;//是否是密码模式 是  则使用圆点
    private int mPasswordRadius = dp2px(8); //密码的半径 px
    private boolean mIsOvalMode = false;//是否使用圆点  默认为使用*字符  只有密码模式下有效


    private int mBaseLineY = 0;//文字的y下笔点

    private float mBeginX = 0;//开始画的坐标x
    private int mBeginY = 0;//开始画的坐标y

    private OnEdittextPwdClickListener onEdittextPwdClickListener;


    //颜色
    private int mBgColor = Color.parseColor("#FFF0EAEC");
    private int mUnSelectColor = Color.parseColor("#FFC3C1C2");
    private int mSelectColor = Color.parseColor("#FF65A1DD");
    private int mTextColor = Color.parseColor("#FF413F40");

    public InputMessageCodeView(Context context) {
        this(context, null);
    }

    public InputMessageCodeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setMinHeight(dp2px(35)); //最小35dp
        setInputType(InputType.TYPE_CLASS_NUMBER);//默认只能输入数字
        initAttr(context, attrs);

        initPaint();

        setFilters(new InputFilter[]{new InputFilter.LengthFilter(mitemSize)});

        // 不显示光标
        setCursorVisible(false);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        mItemWidth = getHeight() - mStrokeSize * 2; //宽度等于高度减去上下边距

        if (mItemWidth * mitemSize > getWidth()) {//当item个数*宽度 ,大于view的宽度的时候 重新设置item宽度保证完整显示
            mItemWidth = getWidth() / mitemSize - mInterval * 3;
        }

        float a = mItemWidth * mitemSize; //每一个输入框的宽度
        float b = mInterval * (mitemSize + 1); //所有框的间距和  数量+1
        float abw = getWidth() - (a + b); //剩余可用宽度
        mBeginX = abw / 2 + mInterval;//开始画的位置,加上第一个间距

        mBeginY = getHeight() / 2 - mItemWidth / 2;
        // 绘制背景矩形 和边框
        drawOutBg(canvas);
//        // 绘制输入时的变化
        drawInput(canvas);
    }


    /**
     * 初始化属性
     */
    private void initAttr(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.input_message_View);

        mitemSize = array.getInt(R.styleable.input_message_View_itemNumber, 6);//默认可输入6个
        mIsPassWordMode = array.getBoolean(R.styleable.input_message_View_isPassword, false); //是否密码模式
        mIsOvalMode = array.getBoolean(R.styleable.input_message_View_isOval, false);
        //颜色设置
        mUnSelectColor = array.getColor(R.styleable.input_message_View_unSelectStrokeColor, mUnSelectColor);//未输入颜色
        mSelectColor = array.getColor(R.styleable.input_message_View_unSelectStrokeColor, mSelectColor);//有输入的颜色
        mTextColor = array.getColor(R.styleable.input_message_View_text_Color, mTextColor);//文字的颜色
        mBgColor = array.getColor(R.styleable.input_message_View_itemBgColor, mBgColor);//item的背景色


        array.recycle();
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        //设置背景色画笔 灰色
        mPaintBg = new Paint();
        mPaintBg.setStyle(Paint.Style.FILL);//铺满
        mPaintBg.setColor(mBgColor);
        mPaintBg.setAntiAlias(true);
        mPaintBg.setDither(true);

        //边框色画笔  没输入是灰色 输入是淡蓝色
        mPaintStroke = new Paint();
        mPaintStroke.setStyle(Paint.Style.STROKE);//铺满
        mPaintStroke.setStrokeWidth(mStrokeSize);//边框大小
        mPaintStroke.setColor(mUnSelectColor);
        mPaintStroke.setAntiAlias(true);
        mPaintStroke.setDither(true);


        //文字画笔
        mPaintText = new Paint();
        mPaintText.setStyle(Paint.Style.FILL);//铺满
        mPaintText.setColor(mTextColor);
        mPaintText.setAntiAlias(true);
        mPaintText.setDither(true);
        mPaintText.setTextSize(dp2px(mIsPassWordMode ? 25 : 20));

        //该方法即为设置基线上那个点究竟是left,center,还是right  这里我设置为center
        mPaintText.setTextAlign(Paint.Align.CENTER);
    }


    /**
     * 绘制背景矩形 和边框
     */
    private void drawOutBg(Canvas canvas) {

        mPaintStroke.setColor(Color.GRAY);

        int top = mBeginY;
        int bottom = top + mItemWidth;


        for (int i = 0; i < mitemSize; i++) {
            float left = i == 0 ? mBeginX : mItemWidth * i + mInterval * i + mBeginX;
            float right = left + mItemWidth;
            RectF rect_bg = new RectF(left, top, right, bottom);// 设置个新的长方形
            canvas.drawRoundRect(rect_bg, 10, 10, mPaintBg);//第二个参数是x半径，第三个参数是y半径

            canvas.drawRoundRect(rect_bg, 10, 10, mPaintStroke);//第二个参数是x半径，第三个参数是y半径
        }

    }


    /**
     * 输入时的变化
     *
     * @param canvas
     */
    private void drawInput(Canvas canvas) {

        int passwordLength = getText().length();

        //获取Y轴基准 用于文字居中
        Paint.FontMetrics fontMetrics = mPaintText.getFontMetrics();
        float text_top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float text_bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        mBaseLineY = (int) (getHeight() / 2 - text_top / 2 - text_bottom / 2);//基线中间点的y轴计算公式

        //画文字
        for (int i = 0; i < passwordLength; i++) {
            float x = i == 0 ? mBeginX : mItemWidth * i + mInterval * i + mBeginX;
            //如果不是密码模式
            if (!mIsPassWordMode) {
                canvas.drawText(String.valueOf(getText().charAt(i)), x + mItemWidth / 2, mBaseLineY, mPaintText);
                continue;
            }


            //如果是密码模式
            if (mIsOvalMode)
                canvas.drawCircle(x + mItemWidth / 2, getHeight() / 2, mPasswordRadius, mPaintText);
            else
                canvas.drawText("*", x + mItemWidth / 2, mBaseLineY, mPaintText);

        }

        //画边框
        int top = mBeginY;
        int bottom = top + mItemWidth;
        for (int i = 0; i < mitemSize; i++) {
            mPaintStroke.setColor(i < passwordLength ? mSelectColor : mUnSelectColor);
            float left = i == 0 ? mBeginX : mItemWidth * i + mInterval * i + mBeginX;
            float right = left + mItemWidth;
            RectF rect_bg = new RectF(left, top, right, bottom);// 设置个新的长方形
            canvas.drawRoundRect(rect_bg, 10, 10, mPaintStroke);//第二个参数是x半径，第三个参数是y半径
        }
        //输入个数跟定义的个数一致时 回调
        if (passwordLength == mitemSize){
            String pwd = getText().toString();
            onEdittextPwdClickListener.onEditPwdListener(pwd);
        }

    }

    /**
     * dp 转 px
     */
    private int dp2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dip, getResources().getDisplayMetrics());
    }

    public interface OnEdittextPwdClickListener {
        void onEditPwdListener(String pwd);
    }

    public void setOnItemClickListener(OnEdittextPwdClickListener listener) {
        this.onEdittextPwdClickListener = listener;
    }

}
