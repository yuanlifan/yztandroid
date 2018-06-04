package com.ylfcf.yzt.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Description: 自定义滑动ViewPager
 */
public class CustomViewPager extends ViewPager {

  private boolean isScrollable;

  public CustomViewPager(Context context) {
    super(context);
  }

  public CustomViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    return isScrollable && super.onTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return isScrollable && super.onInterceptTouchEvent(ev);
  }

  public boolean isScrollable() {
    return isScrollable;
  }

  public void setScrollable(boolean isScrollable) {
    this.isScrollable = isScrollable;
  }
}
