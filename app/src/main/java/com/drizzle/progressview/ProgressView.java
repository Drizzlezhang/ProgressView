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
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by drizzle on 16/1/6.
 */
public class ProgressView extends View {

	private OnProgressChangeListener progressChangeListener;

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
	//进度条起始位置
	private int startLocation;

	//进度条四个起始位置
	public static final int STARTTOP = 1;
	public static final int STARTBOTTOM = 2;
	public static final int STARTLEFT = 3;
	public static final int STARTRIGHT = 4;

	//进度值
	private int progress = 0;

	//设定是否需要背景圆弧,默认需要
	private boolean needBackCircle;
	//设定是否需要进度圆颜色,默认需要
	private boolean needBackColor;

	//写字的画笔
	private Paint textPaint;
	private Rect textRect;
	//背景色的画笔
	private Paint backPaint;
	//背景圆形的画笔
	private Paint backCirclePaint;
	//进度圆的画笔
	private Paint progressPaint;
	private RectF progressRectf;

	int mLastX, mLastY = 0;

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
		startLocation = array.getInt(R.styleable.ProgressView_start_location, STARTTOP);
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
		backPaint.setStyle(Paint.Style.FILL);
		backCirclePaint = new Paint();
		backCirclePaint.setAntiAlias(true);
		backCirclePaint.setStyle(Paint.Style.STROKE);
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
		//先画背景颜色
		if (needBackColor) {
			backPaint.setColor(backColor);
			//RadialGradient lg =
			//	new RadialGradient(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleradius, Color.RED, Color.BLUE,
			//		Shader.TileMode.REPEAT);  //
			//backPaint.setShader(lg);
			canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleradius * progress / 100,
				backPaint);
		}
		//画背景圆弧
		if (needBackCircle) {
			backCirclePaint.setStrokeWidth(circlestrokewidth);
			backCirclePaint.setColor(circlebackcolor);
			canvas.drawCircle(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleradius, backCirclePaint);
		}
		//再画进度圆弧
		progressPaint.setStrokeWidth(circlestrokewidth);
		progressPaint.setColor(circlestrokecolor);
		//RadialGradient lg =
		//	new RadialGradient(getMeasuredWidth() / 2, getMeasuredHeight() / 2, circleradius, Color.RED, Color.BLUE,
		//		Shader.TileMode.REPEAT);
		//progressPaint.setShader(lg);
		progressRectf = new RectF(getMeasuredWidth() / 2 - circleradius, getMeasuredHeight() / 2 - circleradius,
			getMeasuredWidth() / 2 + circleradius, getMeasuredHeight() / 2 + circleradius);
		canvas.drawArc(progressRectf, getStartDegree(startLocation), 360 * progress / 100, false, progressPaint);
		//再画文字
		if (text == null) {
			text = "";
		}
		textPaint.setColor(textColor);
		textPaint.setTextSize(textSize);
		textPaint.getTextBounds(text, 0, text.length(), textRect);
		canvas.drawText(text, (getWidth() - textRect.width()) / 2, (getHeight() + textRect.height()) / 2, textPaint);
	}

	public void setProgress(int progress) {
		if (progress >= 0 && progress <= 100) {
			this.progress = progress;
			progressChangeListener.onProgressChange(progress);
			invalidate();
		} else if (progress < 0) {
			this.progress = 0;
			invalidate();
		} else if (progress > 100) {
			this.progress = 100;
			invalidate();
		}
	}

	/**
	 * 实现手指拖动全屏
	 */
	@Override public boolean onTouchEvent(MotionEvent event) {
		int x = (int) event.getRawX();//获取的是相对屏幕的坐标而不是相对于view的坐标
		int y = (int) event.getRawY();
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				break;
			case MotionEvent.ACTION_MOVE:
				int deltaX = x - mLastX;
				int deltaY = y - mLastY;
				int translationX = (int) getTranslationX() + deltaX;
				int translationY = (int) getTranslationY() + deltaY;
				setTranslationX(translationX);
				setTranslationY(translationY);
				break;
			case MotionEvent.ACTION_UP:
				break;
			default:
				break;
		}
		mLastX = x;
		mLastY = y;
		return true;
	}

	//结束动画
	public void finish() {
		ProgressAnimation animation = new ProgressAnimation(this, this.getProgress(), 100);
		animation.setDuration((100 - this.getProgress()) * 5);
		animation.setInterpolator(new AccelerateInterpolator());
		this.startAnimation(animation);
	}

	//自定义动画
	public void playAnimation(int start, int end, int duration) {
		ProgressAnimation animation = new ProgressAnimation(this, start, end);
		animation.setDuration(duration);
		this.startAnimation(animation);
	}

	public void setOnProgressChangeListener(OnProgressChangeListener progressChangeListener) {
		this.progressChangeListener = progressChangeListener;
	}

	private int getStartDegree(int startInt) {
		switch (startInt) {
			case STARTTOP:
				return 270;
			case STARTBOTTOM:
				return 90;
			case STARTLEFT:
				return 180;
			case STARTRIGHT:
				return 0;
			default:
				break;
		}
		return 270;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		invalidate();
	}

	public void setText(int textId) {
		this.text = getResources().getString(textId);
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

	public void setStartLocation(int startLocation) {
		this.startLocation = startLocation;
	}

	private class ProgressAnimation extends Animation {
		private ProgressView progressView;
		private int startProgress;
		private int endProgress;

		public ProgressAnimation(ProgressView progressView, int startProgress, int endProgress) {
			this.progressView = progressView;
			this.startProgress = startProgress;
			this.endProgress = endProgress;
		}

		@Override protected void applyTransformation(float interpolatedTime, Transformation t) {
			int progress = (int) (startProgress + ((endProgress - startProgress) * interpolatedTime));
			progressView.setProgress(progress);
			progressView.requestLayout();
		}
	}
}
