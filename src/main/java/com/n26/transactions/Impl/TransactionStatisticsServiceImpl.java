package com.n26.transactions.Impl;

import java.time.Instant;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.n26.transactions.api.TransactionStatisticsService;
import com.n26.transactions.dto.Transaction;
import com.n26.transactions.dto.TransactionStatistics;
import com.n26.transactions.helper.TransactionHelper;

@Service
public class TransactionStatisticsServiceImpl implements TransactionStatisticsService {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionStatisticsServiceImpl.class);

	@Autowired
	private TransactionHelper transactionHelper;

	@Override
	public TransactionStatistics getTransactionStatistics() {
		LOGGER.info("Fetching last 60 seconds tx data...");
		Entry<Transaction, TransactionStatistics> transactionStatsEntry = transactionHelper.firstEntry();
		Transaction transaction = transactionStatsEntry.getKey();
		TransactionStatistics statistics = transactionStatsEntry.getValue();
		if (transaction.getTimestamp() < (Instant.now().getEpochSecond() - 60))
			return new TransactionStatistics(0, 0, 0, 0, 0);
		else
			return statistics;
	}

}
