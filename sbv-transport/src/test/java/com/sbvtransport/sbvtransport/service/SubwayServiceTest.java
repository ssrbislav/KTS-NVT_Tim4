package com.sbvtransport.sbvtransport.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Subway;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@Transactional
public class SubwayServiceTest {

	@Autowired
	private ISubwayService subwayService;

	@Autowired
	private ILineService lineService;

	@Test
	public void findAllTest() {
		List<Subway> subways = subwayService.findAll();
		assertThat(subways).hasSize(3);

	}

	@Test
	public void getOneTest() {

		Subway findSubway = subwayService.getOne(1L);

		assertThat(findSubway).isNotNull();
		assertThat(findSubway.getId()).isEqualTo(1L);
		assertThat(findSubway.getCode()).isEqualTo("nova_linija2_subway_3ka");
		assertThat(findSubway.getName()).isEqualTo("3ka");
		assertThat(findSubway.isLate()).isEqualTo(false);
		assertThat(findSubway.getLine().getId()).isEqualTo(2L);
		assertThat(findSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(findSubway.getLine().getName()).isEqualTo("nova_linija2");

		// find subway that doesn't exist
		Subway findSubway2 = subwayService.getOne(10L);
		assertThat(findSubway2).isNull();

	}

	@Test
	@Transactional
	@Rollback(true)
	public void createTest() {

		SubwayDTO subway = new SubwayDTO(false, "107ca", 2L);

		int dbSizeBeforeAdd = subwayService.findAll().size();

		Subway dbSubway = subwayService.create(subway);
		assertThat(dbSubway).isNotNull();

		List<Subway> subways = subwayService.findAll();
		assertThat(subways).hasSize(dbSizeBeforeAdd + 1);
		assertThat(dbSubway.getCode()).isEqualTo("nova_linija2_subway_107ca");
		assertThat(dbSubway.getId()).isEqualTo(4L);
		assertThat(dbSubway.getName()).isEqualTo("107ca");
		assertThat(dbSubway.isLate()).isEqualTo(false);
		assertThat(dbSubway.getLine().getId()).isEqualTo(2L);
		assertThat(dbSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(dbSubway.getLine().getName()).isEqualTo("nova_linija2");

//		// create a subway with a line that doesn't exist
//		SubwayDTO subway2 = new SubwayDTO(false, "107ca", 10L);
//		Subway dbSubway2 = subwayService.create(subway2);
//		assertThat(dbSubway2).isNull();
//
//		// create a subway with a line that isn't a correct type
//		SubwayDTO subway3 = new SubwayDTO(false, "107ca", 1L);
//		Subway dbSubway3 = subwayService.create(subway3);
//		assertThat(dbSubway3).isNull();

	}

	// change a test for update because you need to change a update depending
	// the angular
	@Test
	@Transactional
	@Rollback(true)
	public void updateTest() {

		Subway subway = new Subway();
		subway.setId(1L);
		subway.setName("8ca");
		subway.setLate(true);
		subway.setLocation(null);
		subway.setTimetable(null);
		subway.setCode("nova_linija2_subway_8ca");
		subway.setLine(lineService.getOne(2L));

		Subway dbSubway = subwayService.update(subway);
		assertThat(dbSubway).isNotNull();
		assertThat(dbSubway.getId()).isEqualTo(1L);
		assertThat(dbSubway.getCode()).isEqualTo("nova_linija2_subway_8ca");
		assertThat(dbSubway.getName()).isEqualTo("8ca");
		assertThat(dbSubway.isLate()).isEqualTo(true);
		assertThat(dbSubway.getLine().getId()).isEqualTo(2L);
		assertThat(dbSubway.getLine().getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(dbSubway.getLine().getName()).isEqualTo("nova_linija2");
	}

	@Test
	@Transactional
	@Rollback(true)
	public void deleteTest() {

		int dbSizeBeforeRemove = subwayService.findAll().size();
		boolean successful = subwayService.delete(2L);
		assertThat(successful).isNotNull();
		assertThat(successful).isEqualTo(true);

		List<Subway> subways = subwayService.findAll();
		assertThat(subways).hasSize(dbSizeBeforeRemove - 1);

		Subway dbSubway = subwayService.getOne(2L);
		assertThat(dbSubway).isNull();

		// delete bus that doesn't exist
		boolean successful2 = subwayService.delete(10L);
		assertThat(successful2).isNotNull();
		assertThat(successful2).isEqualTo(false);
	}

	@Test
	public void checkLineTest() {

		Line dbLine = subwayService.checkLine(2L);
		assertThat(dbLine).isNotNull();
		assertThat(dbLine.getId()).isEqualTo(2L);
		assertThat(dbLine.getLine_type()).isEqualTo(TypeTransport.subway);
		assertThat(dbLine.getName()).isEqualTo("nova_linija2");

		// line exist but it is not good type
		Line dbLine2 = subwayService.checkLine(1L);
		assertThat(dbLine2).isNull();

	}
	
	@Test(expected = NoSuchElementException.class)
	public void checkLineTest2() {
		// line doesn't exist
		subwayService.checkLine(10L);

	}

}
