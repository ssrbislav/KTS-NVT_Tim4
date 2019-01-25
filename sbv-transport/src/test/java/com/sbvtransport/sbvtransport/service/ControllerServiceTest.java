package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.dto.FilterSearchControllerDTO;
import com.sbvtransport.sbvtransport.model.Controller;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
@Rollback(value = true)
public class ControllerServiceTest {

	@Autowired
	private ControllerService controllerService;

	@Test
	public void findAllTest() {
		List<Controller> allControllers = controllerService.findAll();
		assertThat(allControllers).hasSize(2);
	}

	@Test
	public void getOneTest() {

		Controller findController = controllerService.getOne(1L);
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		assertThat(findController).isNotNull();
		assertThat(findController.getId()).isEqualTo(1L);
		assertThat(findController.getAddress()).isEqualTo("Balzakova 28");
		assertThat(fmt.format(findController.getDate_birth())).isEqualTo("1995/03/30");
		assertThat(findController.getEmail()).isEqualTo("bokaa@gmail.com");
		assertThat(findController.isDeleted()).isEqualTo(false);
		assertThat(findController.getFirst_name()).isEqualTo("Bojana");

		assertThat(findController.getLast_name()).isEqualTo("Corilic");
		assertThat(findController.getPassword()).isEqualTo("lozinka");
		assertThat(findController.getPhone_number()).isEqualTo("0897346576");
		assertThat(findController.getUsername()).isEqualTo("bokaKontroler");

	}

	@Test
	public void getOneTest2() {
		// find controller that doesn't exist
		Controller findcontroller2 = controllerService.getOne(10L);
		assertThat(findcontroller2).isNull();

	}

	// load controller by username
	@Test
	public void loadUserByUsernameTest() {
		Controller findController = controllerService.loadUserByUsername("bokaKontroler");
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		assertThat(findController).isNotNull();
		assertThat(findController.getId()).isEqualTo(1L);
		assertThat(findController.getAddress()).isEqualTo("Balzakova 28");
		assertThat(fmt.format(findController.getDate_birth())).isEqualTo("1995/03/30");
		assertThat(findController.getEmail()).isEqualTo("bokaa@gmail.com");
		assertThat(findController.isDeleted()).isEqualTo(false);
		assertThat(findController.getFirst_name()).isEqualTo("Bojana");

		assertThat(findController.getLast_name()).isEqualTo("Corilic");
		assertThat(findController.getPassword()).isEqualTo("lozinka");
		assertThat(findController.getPhone_number()).isEqualTo("0897346576");
		assertThat(findController.getUsername()).isEqualTo("bokaKontroler");

	}

	@Test
	public void loadUserByUsernameTest2() {
		// load controller that doesn't exist
		Controller findcontroller2 = controllerService.loadUserByUsername("ne postojiiiii");
		assertThat(findcontroller2).isNull();

	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void createTest() {
		Controller newController = new Controller("novi@gmail.com", "novi", "novasifra", "novo ime", "novo prezime",
				"adresa", "3543265443", new Date());
		Controller savedController = controllerService.create(newController);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		assertThat(savedController).isNotNull();
		assertThat(savedController.getId()).isEqualTo(newController.getId());
		assertThat(savedController.getAddress()).isEqualTo(newController.getAddress());
		assertThat(fmt.format(savedController.getDate_birth())).isEqualTo(fmt.format(newController.getDate_birth()));
		assertThat(savedController.getEmail()).isEqualTo(newController.getEmail());
		assertThat(savedController.isDeleted()).isEqualTo(false);
		assertThat(savedController.getFirst_name()).isEqualTo(newController.getFirst_name());

		assertThat(savedController.getLast_name()).isEqualTo(newController.getLast_name());
		assertThat(savedController.getPassword()).isEqualTo(newController.getPassword());
		assertThat(savedController.getPhone_number()).isEqualTo(newController.getPhone_number());
		assertThat(savedController.getUsername()).isEqualTo(newController.getUsername());

	}

	@Test(expected = DataIntegrityViolationException.class)
	@Transactional
	@Rollback(value = true)
	public void createTest2() {
		// save empty controller
		Controller emptyController = new Controller();
		controllerService.create(emptyController);

	}

	// update controller
	@Test
	@Transactional
	@Rollback(value = true)
	public void updateTest() {
		Controller updateController = new Controller(1L, "izmenjen@gmail.com", "izmenjen", "izmenjenasifra",
				"izmenjeno ime", "izmenjeno prezime", "adresa", "3543265443", new Date());
		Controller savedController = controllerService.update(updateController);

		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		assertThat(savedController).isNotNull();
		assertThat(savedController.getId()).isEqualTo(updateController.getId());
		assertThat(savedController.getAddress()).isEqualTo(updateController.getAddress());
		assertThat(fmt.format(savedController.getDate_birth())).isEqualTo(fmt.format(updateController.getDate_birth()));
		assertThat(savedController.getEmail()).isEqualTo(updateController.getEmail());
		assertThat(savedController.isDeleted()).isEqualTo(false);
		assertThat(savedController.getFirst_name()).isEqualTo(updateController.getFirst_name());

		assertThat(savedController.getLast_name()).isEqualTo(updateController.getLast_name());
		assertThat(savedController.getPassword()).isEqualTo(updateController.getPassword());
		assertThat(savedController.getPhone_number()).isEqualTo(updateController.getPhone_number());
		assertThat(savedController.getUsername()).isEqualTo(updateController.getUsername());

	}

	@Test(expected = NoSuchElementException.class)
	public void updateTest2() {
		// update controller that doesn't exist

		Controller updateController = new Controller(324532532L, "izmenjen@gmail.com", "izmenjen", "izmenjenasifra",
				"izmenjeno ime", "izmenjeno prezime", "adresa", "3543265443", new Date());
		controllerService.update(updateController);
	}

	@Test
	@Transactional
	@Rollback(value = true)
	public void deleteTest() {

		boolean successful = controllerService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);

		Controller dbController = controllerService.getOne(2L);
		assertThat(dbController.isDeleted()).isEqualTo(true);

		// delete bus that doesn't exist
		boolean successful2 = controllerService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}

	@Test
	public void filterSearchTest() {
		// filter and search by username
		FilterSearchControllerDTO filterSearch = new FilterSearchControllerDTO("username", "b");
		List<Controller> all = controllerService.filterSearch(filterSearch);
		assertThat(all.size()).isEqualTo(2);

		// filter and search by email
		FilterSearchControllerDTO filterSearch2 = new FilterSearchControllerDTO("email", "BANE");
		List<Controller> all2 = controllerService.filterSearch(filterSearch2);
		assertThat(all2.size()).isEqualTo(1);

		// filter and search by first name
		FilterSearchControllerDTO filterSearch3 = new FilterSearchControllerDTO("fname", "b");
		List<Controller> all3 = controllerService.filterSearch(filterSearch3);
		assertThat(all3.size()).isEqualTo(2);

		// filter and search by last name
		FilterSearchControllerDTO filterSearch4 = new FilterSearchControllerDTO("lname", "b");
		List<Controller> all4 = controllerService.filterSearch(filterSearch4);
		assertThat(all4.size()).isEqualTo(0);

		// filter and search by last name
		FilterSearchControllerDTO filterSearch5 = new FilterSearchControllerDTO("address", "b");
		List<Controller> all5 = controllerService.filterSearch(filterSearch5);
		assertThat(all5.size()).isEqualTo(2);
	}

}
