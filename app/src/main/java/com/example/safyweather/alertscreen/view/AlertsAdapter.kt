package com.example.safyweather.alertscreen.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.safyweather.R
import com.example.safyweather.model.AlertData
import com.example.safyweather.utilities.Converters

class AlertsAdapter :RecyclerView.Adapter<AlertsAdapter.AlertsViewHolder> {

    private var context: Context
    private var alerts:List<AlertData>
    private var onClickHandler: OnAlertClickListener

    constructor(context: Context,alerts:List<AlertData>,onClickHandler: OnAlertClickListener){
        this.context = context
        this.alerts = alerts
        this.onClickHandler = onClickHandler
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AlertsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.alert_item_layout,parent,false)
        return AlertsViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlertsViewHolder, position: Int) {
        holder.fromTime.text = "${alerts[position].fromDate.hours}:${alerts[position].fromDate.minutes}"
        holder.fromDate.text = Converters.getDateFromInt(alerts[position].fromDate.date,alerts[position].fromDate.month,alerts[position].fromDate.year)
        //Log.i("TAG", "onBindViewHolder: ${alerts[position].fromDate.date} --- ${alerts[position].fromDate.month} --- ${alerts[position].fromDate.year}")
        holder.toTime.text = "${alerts[position].toDate.hours}:${alerts[position].toDate.minutes}"
        holder.toDate.text = Converters.getDateFromInt(alerts[position].toDate.date,alerts[position].toDate.month,alerts[position].toDate.year)
        holder.removeBtn.setOnClickListener{onClickHandler.onRemoveAlertBtnClick(alerts[position])}
    }

    override fun getItemCount(): Int {
        return alerts.size
    }

    fun setAlertsList(alerts: List<AlertData>){
        this.alerts = alerts
    }

    inner class AlertsViewHolder(private val view: View):RecyclerView.ViewHolder(view){
        val alertConstraint: ConstraintLayout
            get() = view.findViewById(R.id.alertItemConstraint)
        val removeBtn:ImageView
            get() = view.findViewById(R.id.removeAlert)
        val fromTime:TextView
            get() = view.findViewById(R.id.fromTimeAlert)
        val toTime:TextView
            get() = view.findViewById(R.id.toTimeAlert)
        val fromDate:TextView
            get() = view.findViewById(R.id.fromDateAlert)
        val toDate:TextView
            get() = view.findViewById(R.id.toDateAlert)
    }

}