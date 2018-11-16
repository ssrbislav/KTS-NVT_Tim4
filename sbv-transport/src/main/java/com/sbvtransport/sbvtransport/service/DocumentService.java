package com.sbvtransport.sbvtransport.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.repository.DocumentRepository;

@Service
public class DocumentService implements IDocumentService {

	@Autowired
	DocumentRepository documentRepository;

	@Override
	public List<Document> findAll() {

		return documentRepository.findAll();
	}

	@Override
	public Document getOne(Long id) {

		return documentRepository.getOne(id);
	}

	@Override
	public Document create(Document document) {

		return documentRepository.save(document);
	}

	@Override
	public Document update(Document document) {

		Optional<Document> updateDoc = documentRepository.findById(document.getId());
		updateDoc.get().setImageLocation(document.getImageLocation());
		updateDoc.get().setDateOfUpload(document.getDateOfUpload());
		
		return documentRepository.save(document);
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
