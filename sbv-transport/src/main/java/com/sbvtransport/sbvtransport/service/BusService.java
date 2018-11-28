package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.BusDTO;
import com.sbvtransport.sbvtransport.model.Bus;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.model.Transport;
import com.sbvtransport.sbvtransport.repository.BusRepository;

@Service
public class BusService implements IBusService {

  @Autowired
  BusRepository busRepository;

  @Autowired
  LineService lineService;

  @Override
  public List<Bus> findAll() {

    return busRepository.findAll();
  }

  @Override
  public Bus getOne(Long id) {

    return busRepository.getOne(id);
  }

  @Override
  public Bus create(BusDTO bus) {
    //Jugoslav-proveri da nije null line --------------------------------------------------------------------------------------
    //Need to change code
    Line line = lineService.getOne(bus.getId_line());
    String code = "";
    Bus newBus = new Bus();
    if (busRepository.findAll().isEmpty()) {
      code = line.getName() + ":" +  "1" + ":" + "bus";
    } else {
      code = line.getName() + ":" + (
          busRepository.findAll().get(busRepository.findAll().size()-1).getId() + 1) + ":" + "bus";
    }
    newBus.setCode(code);
    newBus.setLine(line);
    newBus.setLate(bus.isLate());
    newBus.setName(bus.getName());
    return busRepository.save(newBus);
  }

  @Override
  public Bus update(Bus bus) {

    Optional<Bus> updateBus = busRepository.findById(bus.getId());
    updateBus.get().setCode(bus.getCode());
    updateBus.get().setName(bus.getName());
    updateBus.get().setLate(bus.isLate());
    updateBus.get().setLine(bus.getLine());
//		updateBus.get().setTimetable(bus.getTimetable());

    return busRepository.save(updateBus.get());
  }

  @Override
  public boolean delete(Long id) {
    for (Bus bus : findAll())
      if (bus.getId() == id) {
        busRepository.delete(bus);
        return true;
      }
    return false;
  }

  @Override
  public boolean codeExist(String code) {
    for (Bus bus : findAll()) {
      if(bus.getCode().equals(code)){
        return true;
      }
    }
    return false;
  }

}
