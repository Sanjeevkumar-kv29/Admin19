package com.example.resource_19.Adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.resource_19.R
import com.example.resource_19.dataClasses.HelpPostDetailsDataClass
import java.util.*

class adapterhelppost(val context: Context?, val dataArray: ArrayList<HelpPostDetailsDataClass>) : RecyclerView.Adapter<adapterhelppost.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.helpchatrvlyt, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder: called.")
        val item = dataArray[position]

        holder.title.text = item.posttitlename
        holder.name.text = "Posted By - "+item.publishername
        holder.city.text = item.publishercityname
        holder.body.text = item.postbody
        holder.timeadded.text = "Posted at - "+item.posttimeadded
    }

    override fun getItemCount(): Int {
        return dataArray.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.posttitle)
        val name: TextView = view.findViewById(R.id.publishername)
        val city: TextView = view.findViewById(R.id.publishercity)
        val body: TextView = view.findViewById(R.id.postbodytv)
        val timeadded: TextView = view.findViewById(R.id.posttimeadded)
    }


}
