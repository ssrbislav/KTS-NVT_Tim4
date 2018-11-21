package com.sbvtransport.sbvtransport.service;

import java.util.List;

import com.sbvtransport.sbvtransport.model.Administrator;
import com.sbvtransport.sbvtransport.model.Document;

public interface IAdministratorService {
	
	List<Administrator> findAll();
	Administrator create(Administrator administrator);
	Administrator update(Administrator administrator);
	boolean delete (Long id);
	boolean validatePassengerDocument(Long passengerId);
	public List<Document> retriveUnvalidateDocuments();

}
