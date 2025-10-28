package com.ucb.framework.transactionBD

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "transaction")
data class TransactionTable(
    @ColumnInfo(name = "name")
    var name: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "amount")
    var amount: Double = 0.0

    @ColumnInfo(name = "description")
    var description: String = ""

    @RequiresApi(Build.VERSION_CODES.O)
    @ColumnInfo(name = "date")
    var date: String = LocalDateTime.now().toString()
}