package com.example.duong.trackmysleepquality.database

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update

// lớp DAO sẽ được chú thích bằng annotation DAO
// interface đại diện cho DAO
@Dao
interface SleepDatabaseDAO {
    @Insert
    fun insert(night: SleepNight)

    @Update
    fun update(night: SleepNight)

    // get ra 1 night bất kì
    // :key thể hiện tham chiếu tới đối số của hàm và ? ở kiểu để thể hiện hàm có thể null
    @Query("SELECT * from daily_sleep_quality_table WHERE nightId = :key")
    fun get(key:Long):SleepNight?

    @Query("DELETE FROM daily_sleep_quality_table")
    fun delete()

    // get ra một đêm mới nhất, sắp xếp giảm dần theo nightId
    @Query("SELECT * FROM daily_sleep_quality_table ORDER BY nightId DESC LIMIT 1")
    fun getTonight() : SleepNight?

    // getAll trả về một list<SleepNight> dưới dạng LiveData
    @Query("SELECT * FROM daily_sleep_quality_table")
    fun getAllNights() : LiveData<List<SleepNight>>

    @Query("SELECT * FROM daily_sleep_quality_table WHERE nightId = :key")
    fun getNightById(key:Long):LiveData<SleepNight>
}