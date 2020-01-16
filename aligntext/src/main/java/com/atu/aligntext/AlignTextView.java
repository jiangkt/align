package com.atu.aligntext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class AlignTextView extends View {
    private float textSize;
    private @ColorInt
    int textColor;
    private Paint textPaint;

    private String text;
    private Rect[] rects;
    private String[] singleTexts;
    private int textLenght;
    private int textBoundsLenght;

    private Paint.FontMetrics fontMetrics;
    private float textBaseLineY;
    private int space;

    public void setText(String text) {
        this.text = text;
        textPosition();
    }

    private void textPosition() {

        textLenght = text.length();
        rects = new Rect[textLenght];
        singleTexts = new String[textLenght];
        for (int i = 0; i < textLenght; i++) {
            singleTexts[i] = String.valueOf(text.charAt(i));
            Rect bounds = new Rect();
            textPaint.getTextBounds(singleTexts[i], 0, singleTexts[i].length(), bounds);
            rects[i] = bounds;
            textBoundsLenght += rects[i].width();
        }

        invalidate();
    }

    public AlignTextView(Context context) {
        this(context, null);
    }

    public AlignTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AlignTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        obtainAttributes(context, attrs);
        init();
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView);

        textSize = ta.getDimension(R.styleable.AlignTextView_text_size, 0);
        textColor = ta.getColor(R.styleable.AlignTextView_text_color, Color.parseColor("#000000"));

        ta.recycle();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    private void init() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);

        fontMetrics = textPaint.getFontMetrics();
        textBaseLineY = -fontMetrics.ascent + getPaddingTop();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (fontMetrics.descent + textBaseLineY + paddingBottom));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int textLeft = getPaddingLeft();
        if (textLenght > 0) {
            if (textLenght == 1){
                canvas.drawText(singleTexts[0], getWidth() / 2 , textBaseLineY, textPaint);
                return;
            }

            int paddingLeft = getPaddingLeft();
            int paddingRight = getPaddingRight();
            space = (getWidth() - textBoundsLenght - paddingLeft - paddingRight) / (textLenght - 1);
        }
        for (int i = 0; i < textLenght; i++) {
            if (i > 0) {
                lastTextHalfWidth += rects[i - 1].width();
            }
            float boundHalfWidth = rects[i].width() / 2F;
            float curx = textLeft + boundHalfWidth + (i * space) + lastTextHalfWidth;
            canvas.drawText(singleTexts[i], curx, textBaseLineY, textPaint);
        }
    }

    int lastTextHalfWidth = 0;
}
