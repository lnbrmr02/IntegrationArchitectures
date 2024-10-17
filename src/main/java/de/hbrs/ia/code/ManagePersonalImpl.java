package de.hbrs.ia.code;

import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;

import java.util.List;

public class ManagePersonalImpl implements ManagePersonal {

    @Override
    public void createSalesMan(SalesMan record) {

    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {

    }

    @Override
    public SalesMan readSalesMan(int sid) {
        return null;
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        return List.of();
    }

    @Override
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        return List.of();
    }
}
