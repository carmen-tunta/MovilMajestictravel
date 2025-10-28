package com.ucb.framework.transactionBD

import androidx.room.Dao
import androidx.room.Query
import com.ucb.domain.Transaction

@Dao
interface ITransactionDAO {
    @Query("SELECT * FROM `transaction`")
    fun getTransactions(): List<Transaction>
}