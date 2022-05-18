package com.example.safyweather.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.safyweather.model.AlertData

@Dao
interface AlertsDAO {
    @Query("SELECT * FROM alerts")
    fun getAllStoredAlerts(): LiveData<List<AlertData>>

    @Insert(onConflict = REPLACE)
    fun insertAlert(alert: AlertData)

    @Delete
    fun deleteAlert(alert: AlertData)
}