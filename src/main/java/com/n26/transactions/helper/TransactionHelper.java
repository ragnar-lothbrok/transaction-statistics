package com.n26.transactions.helper;

import java.time.Instant;
import java.util.DoubleSummaryStatistics;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import com.n26.transactions.dto.Transaction;
import com.n26.transactions.dto.TransactionStatistics;

public class TransactionHelper {

	public static volatile BlockingQueue<Transaction> tranactionsQueue = new LinkedBlockingQueue<Transaction>();

	public static volatile ConcurrentSkipListMap<Transaction, TransactionStatistics> transactionsStats = new ConcurrentSkipListMap<Transaction, TransactionStatistics>();

	public static void calculateStatsFromQueue() {
		while (!tranactionsQueue.isEmpty()) {
			Transaction transaction = tranactionsQueue.poll();
			transactionsStats.put(transaction, new TransactionStatistics(0, 0, 0, 0, 0));
			
			//Removing older transactions
			transactionsStats.entrySet()
					.removeIf(entry -> entry.getKey().getTimestamp() < (Instant.now().getEpochSecond() - 60));

			transactionsStats.entrySet().parallelStream()
					.filter(t2 -> t2.getKey().getTimestamp() >= transaction.getTimestamp()).forEach(t3 -> {
						DoubleSummaryStatistics stats = findStats(t3.getKey());
						transactionsStats.put(t3.getKey(), new TransactionStatistics(stats.getSum(), stats.getAverage(),
								stats.getMax(), stats.getMin(), stats.getCount()));
					});

		}
	}

	private static DoubleSummaryStatistics findStats(Transaction t3) {
		DoubleSummaryStatistics stats = transactionsStats.entrySet().parallelStream()
				.filter(t -> t3.getTimestamp() >= t.getKey().getTimestamp()
						&& t.getKey().getTimestamp() >= (Instant.now().getEpochSecond() - 60))
				.collect(Collectors.summarizingDouble(e -> e.getKey().getAmount()));
		return stats;
	}

}
