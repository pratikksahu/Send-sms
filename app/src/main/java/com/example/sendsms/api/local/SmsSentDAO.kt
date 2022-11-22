package com.example.sendsms.api.local

import androidx.room.*


@Dao
interface SmsSentDAO {
    //either interface or abstract class as we don't provide method body we just annotate
    @Insert
    fun insert(sms: SmsSent?)

    @Update
    fun  //(onConflict = OnConflictStrategy.REPLACE)
            update(sms: SmsSent?)

    @Delete
    fun delete(sms: SmsSent?)

    @Query("DELETE FROM sms_history")
    fun deleteAll()

    @Query("SELECT * FROM sms_history WHERE name LIKE '%' || :filter  || '%' ORDER BY " +
            "CASE WHEN :isAsc = 1 THEN time END ASC, CASE WHEN :isAsc = 0 THEN time END DESC")
    suspend fun getAll(filter:String , isAsc:Boolean):List<SmsSent?>?
}