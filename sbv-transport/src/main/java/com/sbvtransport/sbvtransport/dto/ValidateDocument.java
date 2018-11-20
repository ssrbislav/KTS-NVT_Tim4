package com.sbvtransport.sbvtransport.dto;

import java.util.Date;

public class ValidateDocument {

	private Long idPassenger;
	private boolean validate;
	private Date dateOfUpload;

	public ValidateDocument() {

	}

	public ValidateDocument(Long idPassenger, boolean validate, Date dateOfUpload) {
		super();
		this.idPassenger = idPassenger;
		this.validate = validate;
		this.dateOfUpload = dateOfUpload;
	}

	public Long getIdPassenger() {
		return idPassenger;
	}

	public boolean isValidate() {
		return validate;
	}

	public void setIdPassenger(Long idPassenger) {
		this.idPassenger = idPassenger;
	}

	public void setValidate(boolean validate) {
		this.validate = validate;
	}

	public Date getDateOfUpload() {
		return dateOfUpload;
	}

	public void setDateOfUpload(Date dateOfUpload) {
		this.dateOfUpload = dateOfUpload;
	}

	@Override
	public String toString() {
		return "validateDocument [idPassenger=" + idPassenger + ", validate=" + validate + "]";
	}

}
