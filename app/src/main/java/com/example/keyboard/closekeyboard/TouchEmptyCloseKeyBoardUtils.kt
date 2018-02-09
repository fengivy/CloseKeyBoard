package com.smi.commonlib.utils.keyboard

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.FOCUS_BEFORE_DESCENDANTS
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by ivy on 2017/12/27.
 * Description：用于点击非edit text区域收起键盘
 */
class TouchEmptyCloseKeyBoardUtils {
    private var outLocation = IntArray(2)
    private var rect = Rect()
    private val filterViews = mutableListOf<View>()

    fun autoClose(activity: Activity, ev: MotionEvent) : Boolean{
        //判断是down事件才处理
        if(ev.action != MotionEvent.ACTION_DOWN)
            return false
        //获取当前Activity的FocusView和DecorView
        val focusView = activity.window.currentFocus ?: return false
        val decorView = activity.window.decorView
        if (!judgeIsTouchInView(decorView, ev))
            return false
        if (decorView is ViewGroup) {
            val view = getChild(decorView, ev)
            //如果点击的view不是EditText并用不是过滤的view则收起键盘
            if (view !is EditText && !filterViews.contains(view)) {
                decorView.isFocusable = true
                decorView.isFocusableInTouchMode = true
                decorView.descendantFocusability = FOCUS_BEFORE_DESCENDANTS
                focusView.clearFocus()
                closeKeyboard(focusView, focusView.context)
                return true
            }
        }
        return false
    }

    /**
     * 判断点击位置是否落在view上
     */
    private fun judgeIsTouchInView(view: View, ev: MotionEvent): Boolean {
        view.getLocationInWindow(outLocation)
        rect.left = outLocation[0]
        rect.top = outLocation[1]
        rect.right = rect.left + view.width
        rect.bottom = rect.top + view.height
        return rect.contains(ev.rawX.toInt(), ev.rawY.toInt())
    }


    /**
     * 递归遍历查找第一个获得该点击事件的View
     */
    private fun getChild(viewGroup: ViewGroup, ev: MotionEvent): View? {
        if (viewGroup.childCount == 0) {
            return viewGroup
        }
        (viewGroup.childCount - 1 downTo 0)
                .map { viewGroup.getChildAt(it) }
                .filter { judgeIsTouchInView(it, ev) }
                .forEach {
                    if (it is ViewGroup) {
                        val touchView = getChild(it, ev)
                        if (touchView != null && touchView.touchables.size > 0) {
                            return touchView
                        }
                    } else {
                        if (it.touchables.size > 0)
                            return it
                    }
                }
        return null
    }

    private fun closeKeyboard(view: View?, mContext: Context) {
        val imm = mContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}