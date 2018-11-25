package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Line;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.SubwayDTO;
import com.sbvtransport.sbvtransport.model.Subway;
import com.sbvtransport.sbvtransport.repository.SubwayRepository;

@Service
public class SubwayService implements ISubwayService {

  @Autowired
  SubwayRepository subwayRepository;
  @Autowired
  LineService lineService;

  @Override
  public List<Subway> findAll() {
    return subwayRepository.findAll();
  }

  @Override
  public Subway getOne(Long id) {

    return subwayRepository.getOne(id);
  }

  @Override
  public Subway create(SubwayDTO subway) {
    Line line = lineService.getOne(subway.getId_line());
    String code = "";
    Subway newSubway = new Subway(code, line, subway.isLate(), subway.getName());
    code = line.getName() + ":" + (subwayRepository.findAll().size() + 1)  + ":" + "bus";
    newSubway.setCode(code);
    return subwayRepository.save(newSubway);
  }

  @Override
  public Subway update(Subway subway) {

    Optional<Subway> updateSubway = subwayRepository.findById(subway.getId());
    updateSubway.get().setCode(subway.getCode());
    updateSubway.get().setName(subway.getName());
    updateSubway.get().setLate(subway.isLate());
    updateSubway.get().setLine(subway.getLine());
    return subwayRepository.save(updateSubway.get());

  }

  @Override
  public boolean delete(Long id) {

    for (Subway subway : findAll())
      if (subway.getId() == id) {
        subwayRepository.delete(subway);
        return true;
      }
    return false;
  }

  @Override
  public boolean codeExist(String code) {
    for (Subway subway : findAll()) {
      if(subway.getCode().equals(code)){
        return true;
      }
    }
    return false;
  }

}
