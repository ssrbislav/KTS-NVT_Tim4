package com.sbvtransport.e2e;

import com.sbvtransport.e2e.pages.user.UserPage;
import com.sbvtransport.e2e.tests.general.LoginTest;
import com.sbvtransport.e2e.tests.general.RegisterTest;
import com.sbvtransport.e2e.tests.user.UserBuyTicketTest;
import com.sbvtransport.e2e.tests.user.UserPageTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    LoginTest.class, RegisterTest.class,
    UserPageTest.class, UserBuyTicketTest.class
})
public class SbvTransportE2EApplicationTests {

  @Test
  public void contextLoads() {

  }

}
