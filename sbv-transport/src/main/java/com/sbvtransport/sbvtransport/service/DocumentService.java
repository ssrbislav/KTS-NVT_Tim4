package com.sbvtransport.sbvtransport.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sbvtransport.sbvtransport.dto.DocumentDTO;
import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.model.Passenger;
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

		return documentRepository.findById(id).orElse(null);
	}

	@Override
	public Document create(DocumentDTO document) {

		Passenger p = passengerRepository.findById(document.getIdPassenger()).orElse(null);
		if(p == null){
			return null;
		}
		Document d = new Document(document.getDateOfUpload(), document.getImageLocation(), p,"need approve");
		return documentRepository.save(d);
	}

	@Override
	public Document update(DocumentDTO document) {

		Document updateDoc = getOne(document.getId());
		if(updateDoc == null){
			return null;
		}
		Passenger p = passengerRepository.findById(document.getIdPassenger()).orElse(null);
		if(p == null ||!( p.getId().equals(updateDoc.getPassenger().getId()))){
			return null;
		}
		updateDoc.setImage_location(document.getImageLocation());
		updateDoc.setDate_of_upload(document.getDateOfUpload());
		updateDoc.setApproved("need approve");

		return documentRepository.save(updateDoc);
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

	@Override
	public Document changeApproved(Document document) {
		
		Document findDocument = getOne(document.getId());
		if(findDocument == null){
			return null;
		}
		
		findDocument.setApproved(document.getApproved());
		
		return documentRepository.save(findDocument);
	}

}
