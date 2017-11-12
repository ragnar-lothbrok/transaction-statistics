package com.n26.transactions.api;

import com.n26.transactions.dto.Transaction;

public interface TransactionService {

	void saveTransaction(Transaction transaction);
}
