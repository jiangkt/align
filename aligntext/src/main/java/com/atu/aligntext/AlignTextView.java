package com.atu.aligntext;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;

public class AlignTextView extends View {
    private float textSize;
    private String textColon;
    private @ColorInt
    int textColor;
    private Paint textPaint;

    private String text;
    private Rect[] rects;
    private String[] singleTexts;
    private int textLength;
    private float textBoundsLength = 0;
    private float textColonBoundsLength = 0;

    private Paint.FontMetrics fontMetrics;
    private float textBaseLineY;
    private float space;
    private float colonPaddingLeft;
    private float colonPaddingRight;
    private int alignStyle;

    public void setText(@Nullable String text) {
        this.text = text;
        textRect();
        invalidate();
    }

    public void setTextColon(@Nullable String textColon) {
        this.textColon = textColon;
        textColonRect();
        invalidate();
    }

    private void textColonRect() {
        if (TextUtils.isEmpty(textColon)) {
            return;
        }

        textColon = textColon.trim();
        textColonBoundsLength = 0;
        Rect rect = new Rect();
        textPaint.getTextBounds(textColon, 0, textColon.length(), rect);
        textColonBoundsLength = rect.width();
    }

    private void textRect() {
        if (TextUtils.isEmpty(text)) {
            return;
        }

        text = text.trim();
        textLength = text.length();
        rects = new Rect[textLength];
        singleTexts = new String[textLength];
        textBoundsLength = 0;
        for (int i = 0; i < textLength; i++) {
            singleTexts[i] = String.valueOf(text.charAt(i));
            Rect bounds = new Rect();
            textPaint.getTextBounds(singleTexts[i], 0, singleTexts[i].length(), bounds);
            rects[i] = bounds;
            textBoundsLength += rects[i].width();
        }
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
        initPaint();
        textRect();
        textColonRect();
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.AlignTextView);

        textSize = ta.getDimension(R.styleable.AlignTextView_text_size, 0);
        textColor = ta.getColor(R.styleable.AlignTextView_text_color, Color.parseColor("#000000"));
        textColon = ta.getString(R.styleable.AlignTextView_text_colon);
        text = ta.getString(R.styleable.AlignTextView_text);
        colonPaddingLeft = ta.getDimension(R.styleable.AlignTextView_colon_padding_left, 0);
        colonPaddingRight = ta.getDimension(R.styleable.AlignTextView_colon_padding_right, 0);
        alignStyle = ta.getInt(R.styleable.AlignTextView_align_style, 0);

        ta.recycle();
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
        initPaint();
        invalidate();
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
        initPaint();
        invalidate();
    }

    private void initPaint() {
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
        float paddingBottom = getPaddingBottom();
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) (fontMetrics.descent + textBaseLineY + paddingBottom));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        float paddingLeft = getPaddingLeft();
        float paddingRight = getPaddingRight();
        float spaceWidth = getWidth() - paddingLeft - paddingRight;

        if (!TextUtils.isEmpty(textColon)){
            canvas.drawText(textColon, textColonBoundsLength / 2 + getWidth() - paddingRight - textColonBoundsLength - colonPaddingRight, textBaseLineY, textPaint);
            spaceWidth -= (textColonBoundsLength + colonPaddingLeft + colonPaddingRight);
        }

        float curWidth = 0;
        if (textLength > 0) {
            if (textLength == 1) {
                canvas.drawText(singleTexts[0], spaceWidth / 2, textBaseLineY, textPaint);
                return;
            }

            spaceWidth -= textBoundsLength;
            curWidth += paddingLeft;
            if (alignStyle == 1) {
                space = spaceWidth / (textLength + 1);
                curWidth += space;
            } else {
                space = spaceWidth / (textLength - 1);
            }
        }

        for (int i = 0; i < textLength; i++) {
            curWidth += rects[i].width() / 2.0F;

            canvas.drawText(singleTexts[i], curWidth, textBaseLineY, textPaint);
            curWidth += rects[i].width() / 2.0F;
            curWidth += space;
        }
    }

}
