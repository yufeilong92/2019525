package com.xuechuan.xcedu.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.andview.refreshview.XRefreshView;

/**
 * @version V 1.0 xxxxxxxx
 * @Title: xcedu
 * @Package com.xuechuan.xcedu.weight
 * @Description: todo
 * @author: L-BackPacker
 * @date: 2018/6/2 19:03
 * @verdescript 版本号 修改时间  修改人 修改的概要说明
 * @Copyright: 2018
 */
public class XRefreshViewLayout extends XRefreshView {
    public XRefreshViewLayout(Context context) {
        super(context);
        initGesture();
    }

    public XRefreshViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initGesture();
    }


    private GestureDetector detector;
    private boolean mIsDisallowIntercept = false;
    private void initGesture() {
        detector = new GestureDetector(getContext(),gestureListener);
    }

    private boolean mIsHorizontalMode = false;
    private boolean isFirst = true;
    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        if (e.getAction() == MotionEvent.ACTION_UP) {
            isFirst = true;
            mIsHorizontalMode = false;
            mIsDisallowIntercept = false;
            return super.dispatchTouchEvent(e);
        }
        if (detector.onTouchEvent(e) && mIsDisallowIntercept && mIsHorizontalMode){
            return dispatchTouchEventSupper(e);
        }
        if (mIsHorizontalMode) {
            return dispatchTouchEventSupper(e);
        }
        return super.dispatchTouchEvent(e);
    }

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        this.mIsDisallowIntercept = disallowIntercept;
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    private GestureDetector.OnGestureListener gestureListener = new GestureDetector.OnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float disX, disY;
            if(distanceX < 0) {
                disX = -distanceX;
            } else {
                disX = distanceX;
            }
            if(distanceY < 0) {
                disY = -distanceY;
            } else {
                disY = distanceY;
            }

            if (disX > disY) {
                if (isFirst) {
                    mIsHorizontalMode = true;
                    isFirst = false;
                }
            } else {
                if (isFirst) {
                    mIsHorizontalMode = false;
                    isFirst = false;
                }
                return false;//垂直滑动会返回false
            }

            return true;//水平滑动会返回true
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    };
}
