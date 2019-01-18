package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.ScheduleDTO;
import com.sbvtransport.sbvtransport.enumeration.DemographicTicketType;
import com.sbvtransport.sbvtransport.enumeration.TicketType;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.enumeration.Zone;
import com.sbvtransport.sbvtransport.model.Schedule;
import java.util.List;

public interface IScheduleService {


  Schedule getOne(Long id);
  List<Schedule> findAll();
  Schedule create(ScheduleDTO schedule);
  Schedule update(Schedule schedule);
  boolean delete (Long id);

}
