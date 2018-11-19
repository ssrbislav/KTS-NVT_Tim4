package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.TrolleyDTO;
import com.sbvtransport.sbvtransport.model.Trolley;
import com.sbvtransport.sbvtransport.repository.TrolleyRepository;

@Service
public class TrolleyService implements ITrolleyService {

	@Autowired
	TrolleyRepository trolleyRepository;

	@Override
	public List<Trolley> findAll() {
		
		return trolleyRepository.findAll();
	}

	@Override
	public Trolley getOne(Long id) {
		
		return trolleyRepository.getOne(id);
	}

	@Override
	public Trolley create(TrolleyDTO trolley) {
		
		Trolley newTrolley = new Trolley(trolley.getCode(), trolley.getSpeed(), trolley.getId_line(), trolley.isLate(), trolley.getName());
		
		return trolleyRepository.save(newTrolley);
	}

	@Override
	public Trolley update(Trolley trolley) {
		
		Optional<Trolley> updateTrolley = trolleyRepository.findById(trolley.getId());
		updateTrolley.get().setCode(trolley.getCode());
		updateTrolley.get().setSpeed(trolley.getSpeed());
		updateTrolley.get().setName(trolley.getName());
		updateTrolley.get().setLate(trolley.isLate());
		updateTrolley.get().setLine(trolley.getLine());

		return trolleyRepository.save(updateTrolley.get());
	}

	@Override
	public boolean delete(Long id) {
		
		for (Trolley trolley : findAll())
			if (trolley.getId() == id) {
				trolleyRepository.delete(trolley);
				return true;
			}
		return false;
	}

	@Override
	public boolean codeExist(String code) {
		for (Trolley trolley : findAll()) {
			if(trolley.getCode().equals(code)){
				return true;
			}
		}
		return false;
	}

}
