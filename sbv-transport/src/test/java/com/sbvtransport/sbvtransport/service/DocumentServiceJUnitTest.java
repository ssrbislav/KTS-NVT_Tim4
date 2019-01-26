package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.repository.DocumentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value = true)
public class DocumentServiceJUnitTest {

	@Autowired
	private IDocumentService documentService;

	@MockBean
	private DocumentRepository documentRepository;

	@Before
	public void setUp() throws ParseException {

		Passenger p1 = new Passenger(1L);
		Passenger p2 = new Passenger(2L);
		String sDate1 = "2018/03/30";
		Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);

		Document d1 = new Document(1L, date1, "lokacija", p1);
		Document d2 = new Document(2L, date1, "lokacija2", p2);

		List<Document> documents = new ArrayList<>();
		documents.add(d1);
		documents.add(d2);
		Mockito.when(documentRepository.findAll()).thenReturn(documents);

		Optional<Document> oDocument = Optional.of(d1);
		Mockito.when(documentRepository.findById(1L)).thenReturn(oDocument);

	}

	@Test
	public void findAllTest() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		List<Document> documents = documentService.findAll();
		assertThat(documents.size()).isEqualTo(2);
		assertThat(documents.get(0).getId()).isEqualTo(1L);
		assertThat(fmt.format(documents.get(0).getDate_of_upload())).isEqualTo("2018/03/30");
		assertThat(documents.get(0).getImage_location()).isEqualTo("lokacija");
		assertThat(documents.get(0).getPassenger().getId()).isEqualTo(1L);

	}

	@Test
	public void getOneTest() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		Document d = documentService.getOne(1L);

		assertNotNull(d);
		assertThat(d.getId()).isEqualTo(1L);
		assertThat(fmt.format(d.getDate_of_upload())).isEqualTo("2018/03/30");
		assertThat(d.getImage_location()).isEqualTo("lokacija");
		assertThat(d.getPassenger().getId()).isEqualTo(1L);
	}

	// document that doesn't exist
	@Test
	public void getOneTest2() {

		Document d = documentService.getOne(43543654L);
		assertNull(d);

	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void wdeleteTest() {

		boolean tryDelete = documentService.delete(1L);
		assertNotNull(tryDelete);
		assertThat(tryDelete).isEqualTo(true);

	}

	// delete document- id of document doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void wdeleteTest2() {

		boolean tryDelete = documentService.delete(1242332532542L);

		assertNotNull(tryDelete);
		assertThat(tryDelete).isEqualTo(false);

	}

}
