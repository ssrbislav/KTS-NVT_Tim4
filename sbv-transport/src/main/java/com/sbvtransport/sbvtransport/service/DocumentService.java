package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.dto.DocumentDTO;
import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.model.Passenger;
import com.sbvtransport.sbvtransport.model.Ticket;
import com.sbvtransport.sbvtransport.repository.DocumentRepository;
import com.sbvtransport.sbvtransport.repository.PassengerRepository;

@Service
public class DocumentService implements IDocumentService {

	@Autowired
	DocumentRepository documentRepository;
	
	@Autowired
	PassengerRepository passengerRepository;

	@Override
	public List<Document> findAll() {

		return documentRepository.findAll();
	}

	@Override
	public Document getOne(Long id) {

		return documentRepository.getOne(id);
	}

	@Override
	public Document create(DocumentDTO document) {
		
		Passenger p = passengerRepository.getOne(document.getIdPassenger());
		
		Document d = new Document(document.getDateOfUpload(), document.getImageLocation(), p);

		return documentRepository.save(d);
	}

	@Override
	public Document update(DocumentDTO document) {

		Optional<Document> updateDoc = documentRepository.findById(document.getId());
		updateDoc.get().setImageLocation(document.getImageLocation());
		updateDoc.get().setDateOfUpload(document.getDateOfUpload());
		
		return documentRepository.save(updateDoc.get());
	}

	@Override
	public boolean delete(Long id) {

		for (Document doc : findAll()) {
			if (doc.getId() == id) {
				documentRepository.delete(doc);
				return true;
			}
		}
		return false;
	}

}
