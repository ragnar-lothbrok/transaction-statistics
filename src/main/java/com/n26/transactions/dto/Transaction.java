package com.n26.transactions.dto;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import com.n26.transactions.annotations.ValidTimestamp;

public class Transaction implements Serializable, Comparable<Transaction> {

	private static final long serialVersionUID = -114741793556887790L;

	final static AtomicLong NEXT_ID = new AtomicLong(1);

	private final long id = NEXT_ID.getAndIncrement();

	@ValidTimestamp
	private long timestamp;

	private double amount;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", timestamp=" + timestamp + ", amount=" + amount + "]";
	}

	@Override
	public int compareTo(Transaction o) {
		return this.timestamp > o.timestamp ? -1
				: (this.timestamp < o.timestamp ? 1 : ((this.id > o.id) ? -1 : (this.id < o.id) ? 1 : 0));
	}

}
