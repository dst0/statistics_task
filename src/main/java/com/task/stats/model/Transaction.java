package com.task.stats.model;

import java.util.Objects;

public class Transaction implements Comparable<Transaction> {

    private double amount;
    private long timestamp;

    public Transaction() {
    }

    public Transaction(double amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public int compareTo(Transaction tran) {
        if (this.getTimestamp() == tran.getTimestamp()) {
            return 0;
        } else if (this.getTimestamp() < tran.getTimestamp()) {
            return -1;
        }
        return 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAmount(), getTimestamp());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Double.compare(that.getAmount(), getAmount()) == 0
                && getTimestamp() == that.getTimestamp();
    }
}
