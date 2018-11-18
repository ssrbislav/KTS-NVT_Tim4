package com.sbvtransport.sbvtransport.service;

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
		
		Subway newSubway = new Subway(subway.getId(), subway.getCode(), subway.getSpeed(), subway.getId_line(), subway.isLate(), subway.getName());

		return subwayRepository.save(newSubway);
	}

	@Override
	public Subway update(SubwayDTO subway) {

		Optional<Subway> updateSubway = subwayRepository.findById(subway.getId());
		updateSubway.get().setCode(subway.getCode());
		updateSubway.get().setSpeed(subway.getSpeed());
		updateSubway.get().setName(subway.getName());
		updateSubway.get().setLate(subway.isLate());
		updateSubway.get().setLine(subway.getId_line());

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

}
