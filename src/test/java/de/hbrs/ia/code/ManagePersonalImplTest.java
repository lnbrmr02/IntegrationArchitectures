package de.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import de.hbrs.ia.model.SalesMan;
import de.hbrs.ia.model.SocialPerformanceRecord;
import de.hbrs.ia.code.ManagePersonalImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagePersonalImplTest {

    private ManagePersonalImpl managePersonal;

    @BeforeEach
    void setUp() {
        managePersonal = new ManagePersonalImpl();
    }

    @AfterEach
    void tearDown() {
        managePersonal.deleteAll();
        //System.out.println(managePersonal);
        managePersonal = null;
    }
    @Test
    void createSalesMan() {
        SalesMan salesMan = new SalesMan("John", "Doe", 12345);
        managePersonal.createSalesMan(salesMan);
        Document doc = managePersonal.readSalesMan(salesMan.getId()).toDocument();
        //System.out.println("Retrieved document: " + (doc != null ? doc.toJson() : "null"));
        assertNotNull(doc);
        assertEquals("John", doc.getString("firstname"));
        assertEquals("Doe", doc.getString("lastname"));
    }

    @Test
    void addSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Jane", "Doe", 54321);
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord("2024", 100);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        //System.out.println(records);
        assertEquals(1, records.size());
        assertEquals("2024", records.get(0).getYear());
        assertEquals(100, records.get(0).getPerformance());
    }

    @Test
    void readSalesMan() {
        SalesMan salesMan = new SalesMan("Alice", "Smith", 67890);
        managePersonal.createSalesMan(salesMan);

        SalesMan retrievedSalesMan = managePersonal.readSalesMan(67890);
        assertNotNull(retrievedSalesMan);
        assertEquals("Alice", retrievedSalesMan.getFirstname());
        assertEquals("Smith", retrievedSalesMan.getLastname());
    }

    @Test
    void readAllSalesMen() {
        SalesMan salesMan1 = new SalesMan("Bob", "Brown", 11111);
        SalesMan salesMan2 = new SalesMan("Eve", "Black", 22222);
        managePersonal.createSalesMan(salesMan1);
        managePersonal.createSalesMan(salesMan2);

        List<SalesMan> allSalesMen = managePersonal.readAllSalesMen();
        assertEquals(2, allSalesMen.size());
    }

    @Test
    void readSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Charlie", "Green", 33333);
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord("2024", 150);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        assertEquals(1, records.size());
        assertEquals("2024", records.get(0).getYear());
        assertEquals(150, records.get(0).getPerformance());
    }

    @Test
    void deleteSalesMan() {
        SalesMan salesMan = new SalesMan("David", "White", 44444);
        managePersonal.createSalesMan(salesMan);

        managePersonal.deleteSalesMan(44444);
        assertNull(managePersonal.readSalesMan(44444));
    }

    @Test
    void deleteSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Frank", "Blue", 55555);
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord("2024", 200);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        managePersonal.deleteSocialPerformanceRecord(record, salesMan);
        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        assertEquals(0, records.size());
    }
}