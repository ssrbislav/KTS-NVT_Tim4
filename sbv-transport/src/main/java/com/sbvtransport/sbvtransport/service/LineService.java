package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LineService implements ILineService {

  @Autowired
  LineRepository lineRepository;

  @Override
  public Line getOne(Long id) {
    return lineRepository.getOne(id);
  }

  @Override
  public List<Line> findAll() {
    return lineRepository.findAll();
  }

  @Override
  public Line create(Line line) {
	  
    return lineRepository.save(line);
  }

  @Override
  public Line update(Line line) {
    Optional<Line> updateLine = lineRepository.findById(line.getId());
    updateLine.get().setStation_list(line.getStation_list());
    updateLine.get().setStation_type(line.getStation_type());
    return lineRepository.save(updateLine.get());
  }

  @Override
  public boolean delete(Long id) {
    if (lineRepository.findAll().contains(lineRepository.getOne(id))) {
      lineRepository.delete(lineRepository.getOne(id));
      return true;
    }
    return false;
  }


}
