package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.model.Document;

public interface IDocumentService {

	Document getOne(Long id);

	List<Document> findAll();

	Document create(Document document);

	Document update(Document document);

	boolean delete(Long id);

}
