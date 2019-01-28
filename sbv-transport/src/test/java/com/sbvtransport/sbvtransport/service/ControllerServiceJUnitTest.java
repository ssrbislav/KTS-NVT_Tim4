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
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import com.sbvtransport.sbvtransport.model.Controller;
import com.sbvtransport.sbvtransport.repository.ControllerRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-test.properties")
public class ControllerServiceJUnitTest {

	@Autowired
	private ControllerService controllerService;

	@MockBean
	private ControllerRepository controllerRepository;

	private static Controller newController = new Controller("novi@gmail.com", "novi", "novasifra", "novo ime",
			"novo prezime", "adresa", "3543265443", null, false);
	private static Controller updateController = new Controller(4L, "izmenjen@gmail.com", "izmenjen", "izmenjenasifra",
			"izmenjeno ime", "izmenjeno prezime", "adresa", "3543265443", new Date(), false);
	private static Optional<Controller> forController = Optional.of(updateController);

	@Before
	public void setUp() throws ParseException {

		List<Controller> controllers = new ArrayList<>();
		String sDate1 = "1995/03/30";
		Date date1 = new SimpleDateFormat("yyyy/MM/dd").parse(sDate1);
		Controller c = new Controller(1L, "bokaa@gmail.com", "boka", "lozinka", "Bojana", "Corilic", "Balzakova 28",
				"0897346576", date1);
		controllers.add(c);
		controllers.add(new Controller(2L, "bane@gmail.com", "bane", "lozinka2", "Bane", "Corilic", "balzakova",
				"4353264634", new Date()));
		Mockito.when(controllerRepository.findAll()).thenReturn(controllers);

		Optional<Controller> oController = Optional.of(c);
		Mockito.when(controllerRepository.findById(1L)).thenReturn(oController);
		Mockito.when(controllerRepository.findById(10L)).thenReturn(null);

		Mockito.when(controllerRepository.findByUsername("boka")).thenReturn(c);
		Mockito.when(controllerRepository.findByUsername("ne postoji")).thenReturn(null);

		Mockito.when(controllerRepository.save(newController)).thenReturn(newController);
		Mockito.when(controllerRepository.save(updateController)).thenReturn(updateController);

		Mockito.when(controllerRepository.findById(4L)).thenReturn(forController);

	}

	@Test
	public void findAllTest() {
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd");

		List<Controller> controllers = controllerService.findAll();
		assertNotNull(controllers);
		assertThat(controllers).hasSize(2);
		assertThat(controllers.get(0).getId()).isEqualTo(1L);
		assertThat(controllers.get(0).getAddress()).isEqualTo("Balzakova 28");
		assertThat(fmt.format(controllers.get(0).getDate_birth())).isEqualTo("1995/03/30");
		assertThat(controllers.get(0).getEmail()).isEqualTo("bokaa@gmail.com");
		assertThat(controllers.get(0).getFirst_name()).isEqualTo("Bojana");

		assertThat(controllers.get(0).getLast_name()).isEqualTo("Corilic");
		assertThat(controllers.get(0).getPassword()).isEqualTo("lozinka");
		assertThat(controllers.get(0).getPhone_number()).isEqualTo("0897346576");
		assertThat(controllers.get(0).getUsername()).isEqualTo("boka");

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
		assertThat(findController.getFirst_name()).isEqualTo("Bojana");

		assertThat(findController.getLast_name()).isEqualTo("Corilic");
		assertThat(findController.getPassword()).isEqualTo("lozinka");
		assertThat(findController.getPhone_number()).isEqualTo("0897346576");
		assertThat(findController.getUsername()).isEqualTo("boka");

	}

	@Test(expected = NullPointerException.class)
	public void getOneTest2() {
		// find controller that doesn't exist
		controllerService.getOne(10L);

	}

	// load controller by username
	@Test
	public void loadUserByUsernameTest() {
		Controller findController = controllerService.loadUserByUsername("boka");
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
		assertThat(findController.getUsername()).isEqualTo("boka");

	}

	@Test
	public void loadUserByUsernameTest2() {
		// load controller that doesn't exist
		Controller fcontroller = controllerService.loadUserByUsername("ne postoji");
		assertNull(fcontroller);

	}

	@Test
	public void createTest() throws ParseException {

		Controller savedController = controllerService.create(newController);

		assertThat(savedController).isNotNull();
		assertThat(savedController.getAddress()).isEqualTo(newController.getAddress());
		assertThat(savedController.getDate_birth()).isEqualTo(newController.getDate_birth());
		assertThat(savedController.getEmail()).isEqualTo(newController.getEmail());
		assertThat(savedController.isDeleted()).isEqualTo(false);
		assertThat(savedController.getFirst_name()).isEqualTo(newController.getFirst_name());

		assertThat(savedController.getLast_name()).isEqualTo(newController.getLast_name());
		assertThat(savedController.getPassword()).isEqualTo(newController.getPassword());
		assertThat(savedController.getPhone_number()).isEqualTo(newController.getPhone_number());
		assertThat(savedController.getUsername()).isEqualTo(newController.getUsername());

	}

	// update controller
	@Test
	public void updateTest() {

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

	@Test
	public void updateTest2() {
		// update controller that doesn't exist

		Controller updateController = new Controller(324532532L, "izmenjen@gmail.com", "izmenjen", "izmenjenasifra",
				"izmenjeno ime", "izmenjeno prezime", "adresa", "3543265443", new Date());
		Controller controller = controllerService.update(updateController);
		assertNull(controller);
	}

}
