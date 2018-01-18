package com.example.keyboard.closekeyboard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MotionEvent
import com.smi.commonlib.utils.keyboard.TouchEmptyCloseKeyBoardUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rv = this.findViewById<RecyclerView>(R.id.rv)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = TouchAdapter(this)
    }

    private val mTouchEmptyCloseKeyBoardUtils:TouchEmptyCloseKeyBoardUtils by lazy {
        TouchEmptyCloseKeyBoardUtils()
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        mTouchEmptyCloseKeyBoardUtils.autoClose(this, ev)
        return super.dispatchTouchEvent(ev)
    }

}
