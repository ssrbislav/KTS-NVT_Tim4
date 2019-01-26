package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.sbvtransport.sbvtransport.dto.DocumentDTO;
import com.sbvtransport.sbvtransport.model.Document;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value = true)
public class DocumentServiceTest {

	@Autowired
	private IDocumentService documentService;

	@Test
	public void findAllTest() {

		List<Document> documents = documentService.findAll();
		assertThat(documents).hasSize(2);

	}

	@Test
	public void getOneTest() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		Document d = documentService.getOne(1L);

		assertNotNull(d);
		assertThat(d.getId()).isEqualTo(1L);
		assertThat(fmt.format(d.getDateOfUpload())).isEqualTo("2019-01-10");
		assertThat(d.getImageLocation()).isEqualTo("lokacija");
		assertThat(d.getPassenger().getId()).isEqualTo(3L);
	}

	// document that doesn't exist
	@Test
	public void getOneTest2() {

		Document d = documentService.getOne(43543654L);
		assertNull(d);

	}
	
	//create document
	@Test
	@Transactional
	@Rollback(value = true)
	public void createTest(){
		
		DocumentDTO newDocument = new DocumentDTO(new Date(), "lokacija", 3L);
		
	}
}
