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

    private MongoClient client;
    private MongoDatabase supermongo;
    private MongoCollection<Document> salesmencollection;
    private MongoCollection<Document> socialperformancecollection;
    private ManagePersonalImpl managePersonal;

    @BeforeEach
    void setUp() {
        // Setting up the connection to a local MongoDB with standard port 27017
        // must be started within a terminal with command 'mongod'.
        client = new MongoClient("localhost", 27017);

        // Get database 'highperformance' (creates one if not available)
        supermongo = client.getDatabase("socialPerformanceTest");

        // Get Collection 'salesmen' (creates one if not available)
        salesmencollection = supermongo.getCollection("salesmen");
        socialperformancecollection = supermongo.getCollection("socialperformance");
        managePersonal = new ManagePersonalImpl();
    }

    @Test
    void createSalesMan() {
        SalesMan salesMan = new SalesMan("John", "Doe", 12345);
        managePersonal.createSalesMan(salesMan);

        // Überprüfe die Anzahl der Dokumente in der Sammlung
        long count = salesmencollection.countDocuments();
        System.out.println("Document count after insertion: " + count); // Debugging-Ausgabe

        Document doc = salesmencollection.find(new Document("sid", 12345)).first();
        System.out.println("Retrieved document: " + (doc != null ? doc.toJson() : "null")); // Debugging-Ausgabe

        assertNotNull(doc);
        assertEquals("John", doc.getString("firstname"));
        assertEquals("Doe", doc.getString("lastname"));

        salesmencollection.drop();
        socialperformancecollection.drop();
    }


    @Test
    void addSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Jane", "Doe", 54321);
        ManagePersonalImpl managePersonal = new ManagePersonalImpl();
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord("2024", 100);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        System.out.println(records);
        assertEquals(1, records.size());
        assertEquals("2024", records.get(0).getYear());
        assertEquals(100, records.get(0).getPerformance());
    }

    @Test
    void readSalesMan() {
        SalesMan salesMan = new SalesMan("Alice", "Smith", 67890);
        ManagePersonalImpl managePersonal = new ManagePersonalImpl();
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
        ManagePersonalImpl managePersonal = new ManagePersonalImpl();
        managePersonal.createSalesMan(salesMan1);
        managePersonal.createSalesMan(salesMan2);

        List<SalesMan> allSalesMen = managePersonal.readAllSalesMen();
        assertEquals(2, allSalesMen.size());
    }

    @Test
    void readSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Charlie", "Green", 33333);
        ManagePersonalImpl managePersonal = new ManagePersonalImpl();
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
        ManagePersonalImpl managePersonal = new ManagePersonalImpl();
        managePersonal.createSalesMan(salesMan);

        managePersonal.deleteSalesMan(44444);
        assertNull(managePersonal.readSalesMan(44444));
    }

    @Test
    void deleteSocialPerformanceRecord() {
        SalesMan salesMan = new SalesMan("Frank", "Blue", 55555);
        ManagePersonalImpl managePersonal = new ManagePersonalImpl();
        managePersonal.createSalesMan(salesMan);

        SocialPerformanceRecord record = new SocialPerformanceRecord("2024", 200);
        managePersonal.addSocialPerformanceRecord(record, salesMan);

        managePersonal.deleteSocialPerformanceRecord(record, salesMan);
        List<SocialPerformanceRecord> records = managePersonal.readSocialPerformanceRecord(salesMan);
        assertEquals(0, records.size());
    }
}