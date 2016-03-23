package io.haydar.csb;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by gjy on 16/3/17.
 */
public class CustomSeekBar extends FrameLayout implements View.OnTouchListener {

    private int mNormalBackground;      //默认颜色
    private int mSelectBackground;      //滑动过的颜色
    private int mSection;       //分为几段
    private int mDefSection;    //默认选择短
    private Paint mPaint;
    private int mWidth;
    private int mHeight;
    private ImageView mImageView;
    private int mImgSrc;
    LayoutParams mImgLP;
    private int sectionWidth;
    private int imgWidth;
    private int rightBorder;
    private boolean isFirst = true;
    private int normalLineLength;
    private int selectLineLength;
    private OnValueChanged mOnValueChanged;
    private boolean isSelect;
    private int minValue;

    public CustomSeekBar(Context context) {
        super(context);
    }

    public CustomSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CustomSeekBar);
        mNormalBackground = a.getColor(R.styleable.CustomSeekBar_normal_background, context.getResources().getColor(R.color.custom_normal_bg));
        mSelectBackground = a.getColor(R.styleable.CustomSeekBar_select_background, context.getResources().getColor(R.color.custom_select_bg));
        mSection = a.getInteger(R.styleable.CustomSeekBar_section, 5);
        mDefSection = a.getInteger(R.styleable.CustomSeekBar_default_selection, 1);
        mImgSrc = a.getResourceId(R.styleable.CustomSeekBar_circle_src, R.drawable.circle);
        isSelect = a.getBoolean(R.styleable.CustomSeekBar_select, false);
        minValue = a.getInt(R.styleable.CustomSeekBar_min_value, 0);
        a.recycle();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        this.setWillNotDraw(false);
        initImageView(context);

    }

    private void initImageView(Context context) {
        mImageView = new ImageView(context);
        mImgLP = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_VERTICAL);
        mImageView.setLayoutParams(mImgLP);
        mImageView.setImageResource(mImgSrc);
        mImageView.setFocusable(true);
        mImageView.setOnTouchListener(this);
        this.addView(mImageView);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(10);
        mPaint.setColor(mSelectBackground);

        canvas.drawLine(imgWidth / 2, mHeight / 2, normalLineLength + imgWidth / 2, mHeight / 2, mPaint);
        mPaint.setColor(mNormalBackground);
        canvas.drawLine(normalLineLength + imgWidth / 2, mHeight / 2, normalLineLength + selectLineLength + imgWidth / 2, mHeight / 2, mPaint);
        for (int i = 0; i < mSection; i++) {
            if (i < mDefSection) {
                mPaint.setColor(mSelectBackground);
            } else {
                mPaint.setColor(mNormalBackground);
            }
            canvas.drawCircle(sectionWidth * (i + 1) + imgWidth / 2, mHeight / 2, 10, mPaint);
        }
        mImageView.setLayoutParams(mImgLP);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth() - mImageView.getMeasuredWidth();
        mHeight = getMeasuredHeight();
        sectionWidth = mWidth / mSection;
        imgWidth = mImageView.getMeasuredWidth();
        if (isFirst) {
            mImgLP.leftMargin = (sectionWidth * mDefSection);
            normalLineLength = sectionWidth * mDefSection;
            selectLineLength = sectionWidth * (mSection - mDefSection);
        }

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                isFirst = false;
                rightBorder = getRight() - getLeft() - imgWidth;
                break;
            case MotionEvent.ACTION_MOVE:
                int newLeft = (int) (event.getRawX() - imgWidth / 2 - getLeft());
                if (newLeft > 0 && newLeft < rightBorder) {
                    mImgLP.leftMargin = newLeft;
                } else if (newLeft < getLeft() + imgWidth / 2) {
                    mImgLP.leftMargin = 0;
                } else if (newLeft > getRight() - getLeft() - imgWidth / 2) {
                    mImgLP.leftMargin = (sectionWidth * mSection);
                }
                normalLineLength = mImgLP.leftMargin;
                selectLineLength = mWidth - normalLineLength;
                mDefSection = normalLineLength / sectionWidth;
                invalidate();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:

                if (!isSelect) {
                    return false;
                }
                isFirst = true;
                mDefSection = Math.round(normalLineLength / (float) sectionWidth);
                if (mDefSection <= minValue) {
                    mDefSection = minValue;
                }
                mImgLP.leftMargin = (sectionWidth * mDefSection);
                normalLineLength = sectionWidth * mDefSection;
                selectLineLength = sectionWidth * (mSection - mDefSection);
                mOnValueChanged.onValueChanged(mDefSection);
                invalidate();
                break;
        }
        return true;
    }

    public interface OnValueChanged {
        void onValueChanged(int value);
    }

    public void setOnValueChanged(OnValueChanged mOnValueChanged) {
        this.mOnValueChanged = mOnValueChanged;
    }
}
