package com.sbvtransport.sbvtransport.repository;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.model.Passenger;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
@Rollback(value=true)
public class DocumentRepositoryTest {

	@Autowired
	private PassengerRepository passengerRepository;
	
	@Autowired
	private DocumentRepository documentRepository;
	
	Passenger passenger;
	
	@Before
	public void init() {
		passenger = new Passenger(false, false, "email@gmail.com", "putnik", "password", "", "", "", "", new Date());
		passengerRepository.save(passenger);
	}
	
	@Test
	@Transactional
	@Rollback(value=true)
	public void testIfDocumentExists() {

		Document document = new Document();
		document.setDate_of_upload(new Date());
		document.setImage_location("lokacija slike");
		document.setPassenger(passenger);
		
		documentRepository.save(document);
		
		Document findDocument = documentRepository.getOne(document.getId());
		
		assertEquals(document.getId(), findDocument.getId());
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void testSaveSubway2() {
		documentRepository.save(new Document());
	}
	
}
