package com.task.stats.service;

import com.task.stats.model.StatisticTimeUnit;
import com.task.stats.model.Statistics;
import com.task.stats.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.stream.Stream;

@Service
public class TransactionService {
    private static final int TIME_UNIT_LENGTH = 1; //1 ms
    private static final int STATISTIC_PERIOD_IN_UNITS = 60000;
    private static final long STATISTIC_PERIOD = STATISTIC_PERIOD_IN_UNITS * TIME_UNIT_LENGTH; // 60 seconds
    private AtomicLong dataVersion = new AtomicLong(0);

    private final AtomicReferenceArray<StatisticTimeUnit> statisticUnits = new AtomicReferenceArray<>(
            Stream.generate(StatisticTimeUnit::new)
                    .limit(STATISTIC_PERIOD_IN_UNITS)
                    .toArray(StatisticTimeUnit[]::new)
    );

    public TransactionService() {
        System.out.println("statisticUnits.length: " + statisticUnits.length());
    }

    public boolean addTransaction(Transaction transaction) {
        boolean isTransactionRelevant =
                transaction.getTimestamp() >= (System.currentTimeMillis() - STATISTIC_PERIOD);
        if (isTransactionRelevant) {
            final long timeInUnits = transaction.getTimestamp() / TIME_UNIT_LENGTH;
            final int timeUnitIndex = new Long(timeInUnits % STATISTIC_PERIOD_IN_UNITS).intValue();

            dataVersion.incrementAndGet();

            statisticUnits.updateAndGet(timeUnitIndex, (unit) -> {
                final double transactionAmount = transaction.getAmount();
                StatisticTimeUnit result;

                if (unit.getTime() <= timeInUnits - TransactionService.STATISTIC_PERIOD_IN_UNITS) {
                    result = new StatisticTimeUnit(timeInUnits);
                } else {
                    result = new StatisticTimeUnit(unit);
                }

                result.setCount(result.getCount() + 1);
                result.setSum(result.getSum() + transactionAmount);
                if (result.getMax() == null || result.getMax() < transactionAmount) {
                    result.setMax(transactionAmount);
                }
                if (result.getMin() == null || result.getMin() > transactionAmount) {
                    result.setMin(transactionAmount);
                }

                return result;
            });

            dataVersion.incrementAndGet();
        }
        return isTransactionRelevant;
    }

    public Statistics getStatistics() {
        Statistics statistics = new Statistics();
        long startVersion;
        do {
            startVersion = dataVersion.get();
            Double max = null;
            Double min = null;
            double sum = 0;
            long count = 0;
            for (int i = 0; i < statisticUnits.length(); i++) {
                StatisticTimeUnit unit = statisticUnits.get(i);
                long currentTimeInUnits = System.currentTimeMillis() / TIME_UNIT_LENGTH;
                long timeInUnitsFrom = currentTimeInUnits - STATISTIC_PERIOD_IN_UNITS;
                long timeInUnitsTill = currentTimeInUnits + 1;
                if (unit.getTime() > timeInUnitsFrom && unit.getTime() < timeInUnitsTill && unit.getCount() > 0) {
                    sum += unit.getSum();
                    count += unit.getCount();
                    if (max == null || max < unit.getMax()) {
                        max = unit.getMax();
                    }
                    if (min == null || min > unit.getMin()) {
                        min = unit.getMin();
                    }
                }
            }
            if (count > 0) {
                statistics.setCount(count);
                statistics.setSum(sum);
                statistics.setAvg(sum / count);
                statistics.setMax(max);
                statistics.setMin(min);
            }
        } while (!dataVersion.compareAndSet(startVersion, startVersion));
        return statistics;
    }
}
