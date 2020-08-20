package com.lw.leftslidelib;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

/**
 * @author luowang8
 * @date 2020-08-20 10:39
 */
public class CustomLinearLayout extends LinearLayout {
	
	public static final String TAG = "CustomLinearLayout";
	
	/** 触摸监听 */
	private OnTouchListener mOnTouchListener;
	
	/** 坐标 */
	private Point mPoint;
	
	
	public CustomLinearLayout(Context context) {
		super(context);
	}
	
	public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}
	
	public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}
	
	public void setOnTouchListener(OnTouchListener onTouchListener) {
		mOnTouchListener = onTouchListener;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
		int actionMasked = event.getActionMasked();
		
		Log.e(TAG, "onTouchEvent: actionMasked = " + actionMasked);
		
		
		return super.onTouchEvent(event);
	}
	
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		int actionMasked = ev.getActionMasked();
		switch (actionMasked) {
			
			
			case MotionEvent.ACTION_DOWN:
				if (mOnTouchListener != null) {
					
					mPoint = new Point((int)ev.getRawX(), (int)ev.getRawY());
					
					mOnTouchListener.doTouch(mPoint);
				}
				break;
			
			default:
				
				break;
		}
		
		return super.onInterceptTouchEvent(ev);
	}
	
	/**
	 * 触摸监听
	 */
	public interface OnTouchListener {
		
		/** 触摸处理 */
		void doTouch(Point point);
	}
}
