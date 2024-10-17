package de.hbrs.ia.model;

import org.bson.Document;

public class SocialPerformanceRecord {
    private String year;
    private Integer performance;
    private Integer ID;

    public SocialPerformanceRecord(String year, Integer performance, Integer ID) {
        this.year = year;
        this.performance = performance;
        this.ID = ID;
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

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }
}
