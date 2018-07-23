package com.task.stats.model;

import java.util.Objects;

public class StatisticTimeUnit {

    private long count;
    private Double max = null;
    private Double min = null;
    private double sum;
    private long unitTime;

    public StatisticTimeUnit() {
    }

    public StatisticTimeUnit(long timeInUnits) {
        setTime(timeInUnits);
    }

    public StatisticTimeUnit(StatisticTimeUnit value) {
        setMax(value.getMax());
        setMin(value.getMin());
        setCount(value.getCount());
        setSum(value.getSum());
        setTime(value.getTime());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatisticTimeUnit that = (StatisticTimeUnit) o;
        return getCount() == that.getCount() &&
                Double.compare(that.getSum(), getSum()) == 0 &&
                unitTime == that.unitTime &&
                Objects.equals(getMax(), that.getMax()) &&
                Objects.equals(getMin(), that.getMin());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCount(), getMax(), getMin(), getSum(), unitTime);
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public long getTime() {
        return unitTime;
    }

    private void setTime(long unitTime) {
        this.unitTime = unitTime;
    }

}
