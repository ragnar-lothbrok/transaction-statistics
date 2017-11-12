package com.n26.transactions.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.n26.transactions.api.TransactionService;
import com.n26.transactions.dto.Transaction;
import com.n26.transactions.dto.exceptions.GenericException;
import com.n26.transactions.helper.TransactionHelper;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Async
	@Override
	public void saveTransaction(Transaction transaction) {
		try {
			TransactionHelper.tranactionsQueue.put(transaction);
			TransactionHelper.calculateStatsFromQueue();
		} catch (InterruptedException e) {
			LOGGER.error("Exception occured while adding data to blocking queue = {} ", e);
			throw new GenericException(e.getMessage());
		}
	}

}
