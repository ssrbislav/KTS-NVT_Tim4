package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Trolley;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class TrolleyServiceTest {

	@Autowired
	private ITrolleyService trolleyService;

	@Autowired
	private ILineService lineService;

	@Test
	public void findAllTest() {
		List<Trolley> trolleys = trolleyService.findAll();
		assertThat(trolleys).hasSize(3);

	}

	@Test
	public void getOneTest() {

		Trolley findTrolley = trolleyService.getOne(1L);

		assertThat(findTrolley).isNotNull();
		assertThat(findTrolley.getId()).isEqualTo(1L);
		assertThat(findTrolley.getCode()).isEqualTo("nova_linija3_trolley_4ka");
		assertThat(findTrolley.getName()).isEqualTo("4ka");
		assertThat(findTrolley.isLate()).isEqualTo(false);
		assertThat(findTrolley.getLine().getId()).isEqualTo(3L);
		assertThat(findTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(findTrolley.getLine().getName()).isEqualTo("nova_linija3");

		// find trolley that doesn't exist
		Trolley findtrolley2 = trolleyService.getOne(10L);
		assertThat(findtrolley2).isNull();

	}

	@Test
	@Transactional
	@Rollback(true)
	public void createTest() {

		TrolleyDTO trolley = new TrolleyDTO(false, "55ca", 3L);

		int dbSizeBeforeAdd = trolleyService.findAll().size();

		Trolley dbTrolley = trolleyService.create(trolley);
		assertThat(dbTrolley).isNotNull();

		List<Trolley> trolleys = trolleyService.findAll();
		assertThat(trolleys).hasSize(dbSizeBeforeAdd + 1);
		assertThat(dbTrolley.getCode()).isEqualTo("nova_linija3_trolley_55ca");
		assertThat(dbTrolley.getId()).isEqualTo(4L);
		assertThat(dbTrolley.getName()).isEqualTo("55ca");
		assertThat(dbTrolley.isLate()).isEqualTo(false);
		assertThat(dbTrolley.getLine().getId()).isEqualTo(3L);
		assertThat(dbTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(dbTrolley.getLine().getName()).isEqualTo("nova_linija3");

		// create a trolley with a line that doesn't exist
		TrolleyDTO trolley2 = new TrolleyDTO(false, "55ca", 10L);
		Trolley dbTrolley2 = trolleyService.create(trolley2);
		assertThat(dbTrolley2).isNull();

		// create a trolley with a line that isn't a correct type
		TrolleyDTO trolley3 = new TrolleyDTO(false, "67ca", 2L);
		Trolley dbTrolley3 = trolleyService.create(trolley3);
		assertThat(dbTrolley3).isNull();

	}

	// change a test for update because you need to change a update depending
	// the angular
	@Test
	@Transactional
	@Rollback(true)
	public void updateTest() {

		Trolley trolley = new Trolley();
		trolley.setId(1L);
		trolley.setName("8ca");
		trolley.setLate(true);
		trolley.setLocation(null);
		trolley.setTimetable(null);
		trolley.setCode("nova_linija3_trolley_8ca");
		trolley.setLine(lineService.getOne(3L));

		Trolley dbTrolley = trolleyService.update(trolley);
		assertThat(dbTrolley).isNotNull();
		assertThat(dbTrolley.getId()).isEqualTo(1L);
		assertThat(dbTrolley.getCode()).isEqualTo("nova_linija3_trolley_8ca");
		assertThat(dbTrolley.getName()).isEqualTo("8ca");
		assertThat(dbTrolley.isLate()).isEqualTo(true);
		assertThat(dbTrolley.getLine().getId()).isEqualTo(3L);
		assertThat(dbTrolley.getLine().getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(dbTrolley.getLine().getName()).isEqualTo("nova_linija3");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest() {

		int dbSizeBeforeRemove = trolleyService.findAll().size();
		boolean successful = trolleyService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);

		List<Trolley> trolleys = trolleyService.findAll();
		assertThat(trolleys).hasSize(dbSizeBeforeRemove - 1);

		Trolley dbTrolley = trolleyService.getOne(2L);
		assertThat(dbTrolley).isNull();

		// delete trolley that doesn't exist
		boolean successful2 = trolleyService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}

	@Test
	public void checkLineTest() {

		Line dbLine = trolleyService.checkLine(3L);
		assertThat(dbLine).isNotNull();
		assertThat(dbLine.getId()).isEqualTo(3L);
		assertThat(dbLine.getLine_type()).isEqualTo(TypeTransport.trolley);
		assertThat(dbLine.getName()).isEqualTo("nova_linija3");

		// line exist but it is not good type
		Line dbLine2 = trolleyService.checkLine(2L);
		assertThat(dbLine2).isNull();

		// line doesn't exist
		Line dbLine3 = trolleyService.checkLine(10L);
		assertThat(dbLine3).isNull();

	}

}
