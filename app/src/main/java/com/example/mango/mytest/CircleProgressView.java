package com.example.mango.mytest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by mango on 2018/8/27.
 */

public class CircleProgressView extends View {

    /**
     * 组件属性
     */
    private int radius;
    private int ringSzie;
    private int ringColor;
    private int circleColor;
    private int reachColor;
    private int unReachColor;
    private int textColor;
    private int textSize;
    private String CircleProgressViewType;
    private String CircleProgressTextType;

    /**
     * 进度相关
     */
    private float start=0;
    private float end=0;
    private float all=0;
    private long duration = 0;
    private float showStart;
    private float showEnd=0;
    private Paint paint;
    private Paint paint1;
    private Paint paint2;
    private Paint paint3;
    public ProgressListener progressListener ;

    /**
     * 组件布局相关
     *
     * @param context
     */
    private int width;
    private int height;


    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public CircleProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        /**
         *获取自定义的属性值，做全局变量存储
         */
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressView);
        radius = DisplayUtils.dp2px(context, array.getDimensionPixelSize(R.styleable.CircleProgressView_radius, 0));
        ringSzie = DisplayUtils.dp2px(context, array.getDimensionPixelSize(R.styleable.CircleProgressView_ringSzie, 0));
        ringColor = array.getInt(R.styleable.CircleProgressView_ringColor, 0);
        circleColor = array.getInt(R.styleable.CircleProgressView_circleColor, 0);
        reachColor = array.getInt(R.styleable.CircleProgressView_reachColor, 0);
        unReachColor = array.getInt(R.styleable.CircleProgressView_unReachColor, 0);
        textColor = array.getInt(R.styleable.CircleProgressView_textColor, 0);
        textSize = DisplayUtils.sp2px(context, array.getDimension(R.styleable.CircleProgressView_textSzie, 0));
        CircleProgressViewType = array.getString(R.styleable.CircleProgressView_CircleProgressViewType);
        CircleProgressTextType = array.getString(R.styleable.CircleProgressView_CircleProgressTextType);
        array.recycle();
        initPaint();
    }

    /**
     * Android中View共有三种测量模式，EXACTLY、UNSPECIFIED、AT_MOST
     * 针对UNSPECIFIED模式一般在自定义view中不涉及，此处暂不做考虑
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            if (heightMode == MeasureSpec.EXACTLY) {
                /** 宽高均为精确模式，在布局文件中指定宽高具体指，或为match_parent **/
                if (widthSize != heightSize) {
                    if (widthSize > heightSize) {
                        if (radius * 2 > heightSize) {
                            radius = heightSize / 2;
                            width = widthSize;
                            height = heightSize;
                        } else {
                            width = widthSize;
                            height = heightSize;
                        }
                    } else {
                        if (radius * 2 > widthSize) {
                            radius = widthSize / 2;
                            width = widthSize;
                            height = heightSize;
                        } else {
                            width = widthSize;
                            height = heightSize;
                        }
                    }
                } else {
                    if (radius * 2 > widthSize) {
                        radius = widthSize / 2;
                        height = heightSize;
                        width = widthSize;
                    } else {
                        height = heightSize;
                        width = widthSize;
                    }
                }
            }
            if (heightMode == MeasureSpec.AT_MOST) {
                /** 高为最大值模式，在布局文件中表现为wrap_content属性 **/
                if (radius * 2 > widthSize) {
                    radius = widthSize / 2;
                    width = widthSize;
                    height = radius * 2;
                } else {
                    width = widthSize;
                    height = radius * 2;
                }
            }
        }
        if (widthMode == MeasureSpec.AT_MOST) {
            if (heightMode == MeasureSpec.AT_MOST) {
                /** 宽高均为最大值模式，在布局文件中指定宽高具体指，或为match_parent **/
                width = radius * 2;
                height = radius * 2;
            }
            if (heightMode == MeasureSpec.EXACTLY) {
                /** 长为精确模式，在布局文件中指定宽高具体指，或为match_parent **/
                if (radius * 2 > heightSize) {
                    radius = heightSize / 2;
                    height = heightSize;
                    width = radius * 2;
                } else {
                    height = heightSize;
                    width = radius * 2;
                }
            }
        }
        setMeasuredDimension(width, height);
    }


    /**
     * onLayout方法在自定义ViewGroup中比较重要，在自定义view中并不需要太多操作
     **/
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("Layout标记", "onLayout: " + left);
        Log.d("Layout标记", "onLayout: " + top);
        Log.d("Layout标记", "onLayout: " + right);
        Log.d("Layout标记", "onLayout: " + bottom);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!(CircleProgressViewType == null)) {
            switch (CircleProgressViewType) {
                case "RfcCircle":
                    drawRfcCircle(showStart, showEnd, canvas);
                    break;
                default:
                    drawRfcCircle(showStart, showEnd, canvas);
                    break;
            }
        } else {
            drawRfcCircle(showStart, showEnd, canvas);
        }
        if(progressListener!=null){
            progressListener.setProgress((int) showEnd, (int) all);
        }
    }

    /**
     * 绘制圆环进度
     * @param canvas
     */
    public void drawRfcCircle(float showStart, float showEnd, Canvas canvas) {
        RectF rectf_head = new RectF(0, 0, radius * 2, radius * 2);//确定外切矩形范围
        rectf_head.offset((width - radius * 2) / 2, (height - radius * 2) / 2);
        /**绘制未走过的外圆弧*/
        canvas.drawArc(rectf_head, showEnd, 360, true, paint3);
        /**绘制走过的外圆弧*/
        canvas.drawArc(rectf_head, showStart, showEnd, true, paint);//绘制圆弧，含圆心
        /**绘内圆弧，不含圆环尺寸*/
        RectF rectf_head1 = new RectF(0, 0, (radius - ringSzie) * 2, (radius - ringSzie) * 2);//确定外切矩形范围
        rectf_head1.offset((width - radius * 2) / 2 + ringSzie, (height - radius * 2) / 2 + ringSzie);
        canvas.drawArc(rectf_head1, 0, 360, true, paint1);
        drawTextProgress(showStart, showEnd, canvas);
    }


    /**
     * 绘制文字进度并控制文字进度的样式分数/百分数
     * @param showStart
     * @param showEnd
     * @param canvas
     */
    public void drawTextProgress(float showStart, float showEnd, Canvas canvas) {
        String textProgress = "";
        switch (CircleProgressTextType) {
            case "score":
                textProgress = (int) showEnd + "/" + (int) all + "";
                break;
            case "percent":
                textProgress = (int) ((showEnd / all) * 100) + "%";
                break;
        }
        Paint.FontMetrics fontMetrics = paint2.getFontMetrics();
        float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
        float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
        Rect rect = new Rect(0, 0, radius * 2, radius * 2);
        rect.offset((width - radius * 2) / 2, (height - radius * 2) / 2);
        int baseLineY = (int) (rect.centerY() - top / 2 - bottom / 2);//基线中间点的y轴计算公式
        canvas.drawText(textProgress, rect.centerX(), baseLineY, paint2);
    }


    /**
     * 进度匀速控制方法
     * @param startProgress
     * @param endProgress
     * @param duration
     */
    public void ShowCircleProgressView(float startProgress, float endProgress, float allProgress, long duration) {
        start = startProgress;
        showStart = startProgress;
        end = endProgress;
        showEnd = startProgress;
        all = allProgress;
        this.duration = duration;
        final long sleepTime = (long) (this.duration / (endProgress - startProgress));
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (showEnd != end) {
                    if ((showEnd + 1) < end) {
                        showEnd++;
                    } else {
                        showEnd = end;
                    }
                    postInvalidate();
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    /**
     * 初始化画笔工具
     */
    public void initPaint() {
        paint = new Paint();
        paint.setAntiAlias(true);//使用抗锯齿功能
        if (ringColor != 0) {
            paint.setColor(ringColor);
        } else {
            paint.setColor(0xFFA4C739);
        }
        if (reachColor != 0) {
            paint.setColor(reachColor);
        }
        paint.setStyle(Paint.Style.FILL);
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        /**默认将进度圆环的内圆颜色设置为该View的背景颜色 **/
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            ColorDrawable colordDrawable = (ColorDrawable) background;
            int color = colordDrawable.getColor();
            paint1.setColor(color);
            setBackgroundDrawable(null);
        } else {
            if (ringColor != 0) {
                paint1.setColor(circleColor);
            } else {
                paint1.setColor(getResources().getColor(R.color.white));
            }
        }
        paint1.setStyle(Paint.Style.FILL);
        paint2 = new Paint();
        paint2.setAntiAlias(true);
        paint2.setColor(textColor);
        paint2.setStyle(Paint.Style.FILL);
        if (textSize != 0) {
            paint2.setTextSize(textSize);
        } else {
            paint2.setTextSize(30f);
        }
        paint2.setTextAlign(Paint.Align.CENTER);
        paint3 = new Paint();
        paint3.setAntiAlias(true);
        if (unReachColor != 0) {
            paint3.setColor(unReachColor);
        } else {
            paint3.setColor(paint1.getColor());
        }
        paint3.setStyle(Paint.Style.FILL);
    }


    /**
     * 获取画笔工具
     *
     * @param paintId
     * @return
     */
    public Paint getPaint(int paintId) {
        switch (paintId) {
            case 0:
                return paint;
            case 1:
                return paint1;
            case 2:
                return paint2;
            case 3:
                return paint3;
            default:
                return paint;
        }
    }

    /**
     * 回调的内部接口
     */
    public interface ProgressListener {
        void setProgress(int currentProgress, int allProgress);
    }

    /**
     * 进度回调的监听
     *
     * @param progressListener
     */
    public void setProgressListener(ProgressListener progressListener) {
        this.progressListener = progressListener;
    }



    /**
     * 设置圆环进度的半径（包括圆环）
     *
     * @param radius
     */
    public void setRadius(int radius) {
        if (radius * 2 < DisplayUtils.px2dp(this.getContext(), width) && radius * 2 < DisplayUtils.px2dp(this.getContext(), height)) {
            this.radius = DisplayUtils.dp2px(this.getContext(), radius);
        }
    }

    /**
     * 设置圆环的尺寸
     *
     * @param ringSzie
     */
    public void setRingSzie(int ringSzie) {
        if (ringSzie < DisplayUtils.px2dp(this.getContext(), radius)) {
            this.ringSzie = DisplayUtils.dp2px(this.getContext(), ringSzie);
        }
    }

    /**
     * 设置圆环的颜色
     *
     * @param ringColor
     */
    public void setRingColor(int ringColor) {
        this.ringColor = ringColor;
    }

    /**
     * 设置文字进度的颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置文字进度的尺寸
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }


    /**
     * 设置进度条的样式
     *
     * @param circleProgressViewType
     */
    public void setCircleProgressViewType(String circleProgressViewType) {
        this.CircleProgressViewType = circleProgressViewType;
    }

    /**
     * 设置文字进度的样式
     *
     * @param circleProgressTextType
     */
    public void setCircleProgressTextType(String circleProgressTextType) {
        this.CircleProgressTextType = circleProgressTextType;
    }

    /**
     * 设置未走过的进度条的颜色
     *
     * @param unReachColor
     */
    public void setUnReachColor(int unReachColor) {
        this.unReachColor = unReachColor;
    }

    /**
     * 设置走过的进度条的颜色
     *
     * @param reachColor
     */
    public void setReachColor(int reachColor) {
        this.reachColor = reachColor;
    }


}
