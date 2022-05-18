package com.example.safyweather.alertscreen

import com.example.safyweather.model.AlertData

interface OnAlertClickListener {
    fun onRemoveAlertBtnClick(alert:AlertData)
}