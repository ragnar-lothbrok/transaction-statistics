package com.n26.transactions.Impl;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.n26.transactions.api.TransactionStatisticsService;
import com.n26.transactions.dto.Transaction;
import com.n26.transactions.dto.TransactionStatistics;
import com.n26.transactions.helper.TransactionHelper;

@Service
public class TransactionStatisticsServiceImpl implements TransactionStatisticsService {

	private final static Logger LOGGER = LoggerFactory.getLogger(TransactionStatisticsServiceImpl.class);

	@Override
	public TransactionStatistics getTransactionStatistics() {
		LOGGER.info("Fetching last 60 seconds tx data...");
		Transaction transaction = TransactionHelper.transactionsStats.firstEntry().getKey();
		TransactionStatistics statistics = TransactionHelper.transactionsStats.firstEntry().getValue();
		if (transaction.getTimestamp() < (Instant.now().getEpochSecond() - 60))
			return new TransactionStatistics(0, 0, 0, 0, 0);
		else
			return statistics;
	}

}
