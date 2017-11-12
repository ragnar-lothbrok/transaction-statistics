package com.n26.transactions.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.api.TransactionStatisticsService;
import com.n26.transactions.dto.TransactionStatistics;

@RestController
public class TransactionStatisticsController {

	final static Logger logger = LoggerFactory.getLogger(TransactionStatisticsController.class);

	@Autowired
	private TransactionStatisticsService transactionStatisticsService;

	@RequestMapping(value = "/statistics", method = RequestMethod.GET)
	public TransactionStatistics getTransactionStats(HttpServletRequest request, HttpServletResponse response) {
		logger.info("Transaction stats request received.");

		return transactionStatisticsService.getTransactionStatistics();
	}
}
