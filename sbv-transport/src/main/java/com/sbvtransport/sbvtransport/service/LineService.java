package com.sbvtransport.sbvtransport.service;

import com.sbvtransport.sbvtransport.dto.LineDTO;
import com.sbvtransport.sbvtransport.enumeration.TypeTransport;
import com.sbvtransport.sbvtransport.model.Line;
import com.sbvtransport.sbvtransport.repository.LineRepository;
import java.util.ArrayList;
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
  public String create(LineDTO lineDTO) {
	  Line line = new Line();
	  line.setName(lineDTO.getName());
	  if (lineDTO.getLine_type().equals("bus")) {
	    line.setLine_type(TypeTransport.bus);
    } else if (lineDTO.getLine_type().equals("subway")) {
	    line.setLine_type(TypeTransport.subway);
    } else if (lineDTO.getLine_type().equals("trolley")) {
	    line.setLine_type(TypeTransport.trolley);
    } else {
	    return "Transport type " + lineDTO.getLine_type() + " doesn't exist!";
    }
    line.setStation_list(new ArrayList<>());
    lineRepository.save(line);
    return "The line has been successfully created!";
  }

  @Override
  public Line update(Line line) {
    Optional<Line> updateLine = lineRepository.findById(line.getId());
    updateLine.get().setName(line.getName());
    updateLine.get().setLine_type(line.getLine_type());
    updateLine.get().setStation_list(line.getStation_list());
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
