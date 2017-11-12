package com.n26.transactions.controllers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.dto.Transaction;
import com.n26.transactions.dto.TransactionStatistics;
import com.n26.transactions.helper.TransactionHelper;

@RestController
public class TransactionStatisticsController {

	final static Logger logger = LoggerFactory.getLogger(TransactionStatisticsController.class);

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public TransactionStatistics getTransactionStats(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Transaction stats request received.");
		
		Transaction transaction = TransactionHelper.transactionsStats.firstEntry().getKey();
		TransactionStatistics statistics = TransactionHelper.transactionsStats.firstEntry().getValue();
		if (transaction.getTimestamp() < (Instant.now().getEpochSecond() - 60))
			return new TransactionStatistics(0, 0, 0, 0, 0);
		else
			return statistics;
	}
}
