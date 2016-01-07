package com.drizzle.progressview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by drizzle on 16/1/6.
 */
public class ProgressView extends View {

	//属性
	//中心文字(默认为空)
	private String text;
	//中心文字大小(默认12sp)
	private int textSize;
	//中心文字颜色(默认黑色)
	private int textColor;
	//圆圈半径(默认30dp)
	private int circleradius;
	//外圆弧描边宽度(默认4dp)
	private int circlestrokewidth;
	//外圆弧进度值描边颜色
	private int circlestrokecolor;
	//外圆弧描边背景颜色
	private int circlebackcolor;
	//进度圆颜色
	private int backColor;

	//进度值
	private int progress = 0;

	//设定是否需要背景圆弧,默认需要
	private boolean needBackCircle;
	//设定是否需要进度圆颜色,默认需要
	private boolean needBackColor;

	//写字的画笔
	private Paint textPaint;
	private Rect textRect;
	//背景圆形和背景色的画笔
	private Paint backPaint;
	//进度圆的画笔
	private Paint progressPaint;
	private RectF progressRectf;

	public ProgressView(Context context) {
		this(context, null);
	}

	public ProgressView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyleAttr, 0);
		text = array.getString(R.styleable.ProgressView_progress_text);
		textSize = array.getDimensionPixelSize(R.styleable.ProgressView_progress_text_size,
			(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics()));
		textColor = array.getColor(R.styleable.ProgressView_progress_text_color, Color.BLACK);
		circleradius = array.getDimensionPixelSize(R.styleable.ProgressView_progress_radius,
			(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
		circlestrokewidth = array.getDimensionPixelSize(R.styleable.ProgressView_progress_stroke_width,
			(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics()));
		circlestrokecolor = array.getColor(R.styleable.ProgressView_progress_stroke_color, Color.WHITE);
		circlebackcolor = array.getColor(R.styleable.ProgressView_progress_back_color, Color.BLACK);
		needBackCircle = array.getBoolean(R.styleable.ProgressView_need_back_circle, true);
		needBackColor = array.getBoolean(R.styleable.ProgressView_back_color, true);
		backColor = array.getColor(R.styleable.ProgressView_back_color, Color.BLACK);
		array.recycle();
		initPaints();
	}

	private void initPaints() {
		//初始化画笔
		textPaint = new Paint();
		textRect = new Rect();
		textPaint.setAntiAlias(true);
		backPaint = new Paint();
		backPaint.setAntiAlias(true);
		progressPaint = new Paint();
		progressPaint.setAntiAlias(true);
		progressPaint.setStyle(Paint.Style.STROKE);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);
		int width;
		int height;
		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			width = (int) (getPaddingLeft() + getPaddingRight() + circleradius * 2 + circlestrokewidth);
		}
		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			height = (int) (getPaddingTop() + getPaddingBottom() + circleradius * 2 + circlestrokewidth);
		}
		setMeasuredDimension(width, height);
	}

	@Override protected void onDraw(Canvas canvas) {
		//先画背景颜色和背景圆弧
		if (needBackColor) {
			backPaint.setStyle(Paint.Style.FILL);
			backPaint.setColor(backColor);
			canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleradius * progress / 100,
				backPaint);
		}
		if (needBackCircle) {
			backPaint.setStyle(Paint.Style.STROKE);
			backPaint.setStrokeWidth(circlestrokewidth);
			backPaint.setColor(circlebackcolor);
			canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleradius, backPaint);
		}
		//再画进度圆弧
		progressPaint.setStrokeWidth(circlestrokewidth);
		progressPaint.setColor(circlestrokecolor);
		progressRectf = new RectF(getMeasuredWidth() / 2 - circleradius, getMeasuredHeight() / 2 - circleradius,
			getMeasuredWidth() / 2 + circleradius, getMeasuredHeight() / 2 + circleradius);
		canvas.drawArc(progressRectf, 270, 360 * progress / 100, false, progressPaint);
		//再画文字
		if (text == null) {
			text = "";
		}
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.getTextBounds(text, 0, text.length(), textRect);
		canvas.drawText(text, (getWidth() - textRect.width()) / 2, (getHeight() + textRect.height()) / 2, textPaint);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		invalidate();
	}

	public int getTextSize() {
		return textSize;
	}

	public void setTextSize(int textSize) {
		this.textSize = textSize;
		invalidate();
	}

	public int getTextColor() {
		return textColor;
	}

	public void setTextColor(int textColor) {
		this.textColor = textColor;
		invalidate();
	}

	public int getCircleradius() {
		return circleradius;
	}

	public void setCircleradius(int circleradius) {
		this.circleradius = circleradius;
		invalidate();
	}

	public int getCirclestrokewidth() {
		return circlestrokewidth;
	}

	public void setCirclestrokewidth(int circlestrokewidth) {
		this.circlestrokewidth = circlestrokewidth;
		invalidate();
	}

	public int getCirclestrokecolor() {
		return circlestrokecolor;
	}

	public void setCirclestrokecolor(int circlestrokecolor) {
		this.circlestrokecolor = circlestrokecolor;
		invalidate();
	}

	public int getCirclebackcolor() {
		return circlebackcolor;
	}

	public void setCirclebackcolor(int circlebackcolor) {
		this.circlebackcolor = circlebackcolor;
		invalidate();
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		if (progress >= 0 && progress <= 100) {
			this.progress = progress;
			invalidate();
		}
	}

	public boolean isNeedBackCircle() {
		return needBackCircle;
	}

	public void setNeedBackCircle(boolean needBackCircle) {
		this.needBackCircle = needBackCircle;
		invalidate();
	}

	public boolean isNeedBackColor() {
		return needBackColor;
	}

	public void setNeedBackColor(boolean needBackColor) {
		this.needBackColor = needBackColor;
		invalidate();
	}

	public int getBackColor() {
		return backColor;
	}

	public void setBackColor(int backColor) {
		this.backColor = backColor;
	}
}
