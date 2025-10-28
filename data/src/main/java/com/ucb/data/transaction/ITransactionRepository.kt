package com.ucb.data.transaction

import com.ucb.domain.Transaction

interface ITransactionRepository {
    suspend fun insertTransaction(transaction: Transaction)
    suspend fun getTransactions(): List<Transaction>
}