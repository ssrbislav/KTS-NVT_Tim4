package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.junit.FixMethodOrder;
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
import org.junit.runners.MethodSorters;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value = true)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DocumentServiceTest {

	@Autowired
	private IDocumentService documentService;

	@Test
	public void afindAllTest() {

		List<Document> documents = documentService.findAll();
		assertThat(documents).hasSize(2);
	}

	@Test
	public void getOneTest() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		Document d = documentService.getOne(1L);

		assertNotNull(d);
		assertThat(d.getId()).isEqualTo(1L);
		assertThat(fmt.format(d.getDate_of_upload())).isEqualTo("2019-01-10");
		assertThat(d.getImage_location()).isEqualTo("lokacija");
		assertThat(d.getPassenger().getId()).isEqualTo(3L);
	}

	// document that doesn't exist
	@Test
	public void getOneTest2() {

		Document d = documentService.getOne(43543654L);
		assertNull(d);

	}

	// create document
	@Test
	@Transactional
	@Rollback(value = true)
	public void wcreateTest() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		DocumentDTO newDocument = new DocumentDTO(new Date(), "lokacija3", 5L);
		Document savedDocument = documentService.create(newDocument);

		assertNotNull(savedDocument);
		assertThat(fmt.format(savedDocument.getDate_of_upload())).isEqualTo(fmt.format(newDocument.getDateOfUpload()));
		assertThat(savedDocument.getImage_location()).isEqualTo(newDocument.getImageLocation());
		assertThat(savedDocument.getPassenger().getId()).isEqualTo(newDocument.getIdPassenger());

	}

	// create document with id of passenger that doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void wcreateTest2() {

		DocumentDTO newDocument = new DocumentDTO(new Date(), "lokacija3", 3345643643L);
		Document savedDocument = documentService.create(newDocument);

		assertNull(savedDocument);

	}

	// update document
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest() {

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");

		DocumentDTO updateDocument = new DocumentDTO(1L, new Date(), "izmenjena lokacija", 3L);
		Document savedDocument = documentService.update(updateDocument);

		assertNotNull(savedDocument);
		assertThat(savedDocument.getId()).isEqualTo(updateDocument.getId());
		assertThat(fmt.format(savedDocument.getDate_of_upload())).isEqualTo(fmt.format(updateDocument.getDateOfUpload()));
		assertThat(savedDocument.getImage_location()).isEqualTo(updateDocument.getImageLocation());
		assertThat(savedDocument.getPassenger().getId()).isEqualTo(updateDocument.getIdPassenger());

	}

	// update document- id doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest2() {

		DocumentDTO updateDocument = new DocumentDTO(1243234325325L, new Date(), "izmenjena lokacija", 3L);
		Document savedDocument = documentService.update(updateDocument);

		assertNull(savedDocument);

	}

	// update document- id of passenger doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest3() {

		DocumentDTO updateDocument = new DocumentDTO(1L, new Date(), "izmenjena lokacija", 345346364364L);
		Document savedDocument = documentService.update(updateDocument);

		assertNull(savedDocument);

	}

	// delete document
	@Test
	@Transactional
	@Rollback(value = true)
	public void wdeleteTest() {

		int sizeBeforeDelete = documentService.findAll().size();
		boolean tryDelete = documentService.delete(1L);
		int sizeAfterDelete = documentService.findAll().size();

		assertNotNull(tryDelete);
		assertThat(tryDelete).isEqualTo(true);
		assertThat(sizeAfterDelete).isEqualTo(sizeBeforeDelete - 1);

	}

	// delete document- id of document doesn't exist
	@Test
	@Transactional
	@Rollback(value = true)
	public void wdeleteTest2() {

		int sizeBeforeDelete = documentService.findAll().size();
		boolean tryDelete = documentService.delete(1242332532542L);
		int sizeAfterDelete = documentService.findAll().size();

		assertNotNull(tryDelete);
		assertThat(tryDelete).isEqualTo(false);
		assertThat(sizeAfterDelete).isEqualTo(sizeBeforeDelete);

	}

}
