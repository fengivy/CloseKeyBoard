package com.example.keyboard.closekeyboard

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * author ivy
 * Created by SMI on 2018/1/17.
 */
class TouchAdapter(val context: Context) : RecyclerView.Adapter<TouchAdapter.TouchViewHolder>() {
    override fun onBindViewHolder(holder: TouchViewHolder?, position: Int) {

    }

    override fun getItemCount(): Int {
        return 100
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TouchViewHolder {
        return TouchViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_touch,parent,false))
    }

    inner class TouchViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }
}