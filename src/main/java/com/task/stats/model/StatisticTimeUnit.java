package com.task.stats.model;

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
