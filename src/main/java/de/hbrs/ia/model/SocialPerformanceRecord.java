package de.hbrs.ia.model;

import org.bson.Document;

public class SocialPerformanceRecord {
    private String year;
    private Integer performance;

    public SocialPerformanceRecord(String year, Integer performance) {
        this.year = year;
        this.performance = performance;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Document toDocument() {
        return new Document("year", this.year).append("performance", this.performance);
    }
}
