package com.sbvtransport.sbvtransport.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.sbvtransport.sbvtransport.dto.DocumentDTO;
import com.sbvtransport.sbvtransport.model.Document;
import com.sbvtransport.sbvtransport.service.DocumentService;
@CrossOrigin
@RestController
@RequestMapping(value = "api/document")
public class DocumentController {

	@Autowired
	DocumentService documentService;

	@RequestMapping(value = "", method = RequestMethod.GET)
	public ResponseEntity<List<Document>> getAll() {

		List<Document> documents = documentService.findAll();

		return new ResponseEntity<>(documents, HttpStatus.OK);
	}

	@RequestMapping(value = "/getDocument/{id}", method = RequestMethod.GET)
	public ResponseEntity<Document> getOne(@PathVariable Long id) {

		Document doc = documentService.getOne(id);

		return new ResponseEntity<Document>(doc, HttpStatus.OK);
	}

	@RequestMapping(value = "/addDoc", method = RequestMethod.POST)
	public ResponseEntity<Document> create(@RequestBody DocumentDTO doc) {

		Document newDoc = documentService.create(doc);

		return new ResponseEntity<Document>(newDoc, HttpStatus.OK);
	}

	@RequestMapping(value = "/updateDoc", method = RequestMethod.POST)
	public ResponseEntity<Document> update(@RequestBody DocumentDTO doc) {

		Document updateDoc = documentService.update(doc);

		return new ResponseEntity<>(updateDoc, HttpStatus.OK);
	}

	@RequestMapping(value = "/deleteDoc/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id) {

		boolean deleteDoc = documentService.delete(id);

		return new ResponseEntity<Boolean>(deleteDoc, HttpStatus.OK);
	}

}
