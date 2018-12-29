package com.sbvtransport.sbvtransport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import com.sbvtransport.sbvtransport.controller.BusControllerTest;
import com.sbvtransport.sbvtransport.controller.SubwayControllerTest;
import com.sbvtransport.sbvtransport.controller.TrolleyControllerTest;
import com.sbvtransport.sbvtransport.repository.BusRepositoryTest;
import com.sbvtransport.sbvtransport.repository.SubwayRepositoryTest;
import com.sbvtransport.sbvtransport.repository.TrolleyRepositoryTest;
import com.sbvtransport.sbvtransport.service.BusServiceJUnitTest;
import com.sbvtransport.sbvtransport.service.BusServiceTest;
import com.sbvtransport.sbvtransport.service.SubwayServiceJUnitTest;
import com.sbvtransport.sbvtransport.service.SubwayServiceTest;
import com.sbvtransport.sbvtransport.service.TrolleyServiceJUnitTest;
import com.sbvtransport.sbvtransport.service.TrolleyServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ BusControllerTest.class, SubwayControllerTest.class, TrolleyControllerTest.class,
		BusRepositoryTest.class, SubwayRepositoryTest.class, TrolleyRepositoryTest.class, BusServiceJUnitTest.class,
		BusServiceTest.class, SubwayServiceJUnitTest.class, SubwayServiceTest.class, TrolleyServiceJUnitTest.class,
		TrolleyServiceTest.class })
public class SbvTransportApplicationTests {

	@Test
	public void contextLoads() {
	}

}
