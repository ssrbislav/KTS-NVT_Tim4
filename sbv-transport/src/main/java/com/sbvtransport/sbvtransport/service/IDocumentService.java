package com.sbvtransport.sbvtransport.service;

import java.util.List;
import com.sbvtransport.sbvtransport.dto.DocumentDTO;
import com.sbvtransport.sbvtransport.model.Document;

public interface IDocumentService {

	Document getOne(Long id);

	List<Document> findAll();

	Document create(DocumentDTO document);

	Document update(DocumentDTO document);

	boolean delete(Long id);

}
