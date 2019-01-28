package com.sbvtransport.sbvtransport;

import com.sbvtransport.sbvtransport.controller.*;
import com.sbvtransport.sbvtransport.repository.*;
import com.sbvtransport.sbvtransport.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
		// B
		BusControllerTest.class, SubwayControllerTest.class, TrolleyControllerTest.class,
		BusRepositoryTest.class, SubwayRepositoryTest.class, TrolleyRepositoryTest.class, BusServiceJUnitTest.class,
		BusServiceTest.class, SubwayServiceJUnitTest.class, SubwayServiceTest.class, TrolleyServiceJUnitTest.class,
		TrolleyServiceTest.class,AdminRepositoryTest.class,ControllerRepositoryTest.class, ControllerServiceTest.class,
		ControllerServiceJUnitTest.class, ControllerControllerTest.class, DocumentServiceTest.class, DocumentServiceJUnitTest.class,
		DocumentControllerTest.class,
		// V
		LineControllerTest.class, LocationControllerTest.class, PassengerControllerTest.class,
		PricelistControllerTest.class, StationControllerTest.class, TicketControllerTest.class,
		TimetableControllerTest.class,

		LineRepositoryTest.class, LocationRepositoryTest.class, PassengerRepositoryTest.class,
		PricelistRepositoryTest.class, ScheduleRepositoryTest.class, StationRepositoryTest.class,
		TicketRepositoryTest.class, TimetableRepositoryTest.class,

		LineServiceTest.class, LocationServiceTest.class, StationServiceTest.class,

		LocationServiceJUnitTest.class
		})
public class SbvTransportApplicationTests {

	@Test
	public void contextLoads() {
	}

}
