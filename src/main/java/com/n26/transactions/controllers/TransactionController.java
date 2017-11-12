package com.n26.transactions.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.n26.transactions.api.TransactionService;
import com.n26.transactions.dto.Transaction;

@RestController
public class TransactionController {

	final static Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private TransactionService transactionService;

	@RequestMapping(value = "/transactions", method = RequestMethod.POST)
	public ResponseEntity<Object> saveTransaction(HttpServletRequest request, HttpServletResponse response,
			@RequestBody @Valid Transaction transaction) {
		logger.info("Transaction received = {} ", transaction);
		transactionService.saveTransaction(transaction);
		return new ResponseEntity<Object>(HttpStatus.CREATED);
	}
}
