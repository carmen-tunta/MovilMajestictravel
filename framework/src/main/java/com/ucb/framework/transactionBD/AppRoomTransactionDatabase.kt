package com.ucb.framework.transactionBD

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.ucb.framework.persistence.AppRoomDatabase


@Database(entities = [TransactionTable::class, IngresosTable::class], version = 1, exportSchema = false)
abstract class AppRoomTransactionDatabase {
    abstract fun transactionDAO(): ITransactionDAO

    companion object {
        @Volatile
        var Instance: AppRoomDatabase? = null

        fun getDatabase(context: Context): AppRoomDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context.applicationContext, AppRoomDatabase::class.java, "transaction_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}