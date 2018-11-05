package com.sbvtransport.sbvtransport.dto;

public class ValidateDocument {
	
	private Long idPassenger;
	private boolean validate;
	
	public ValidateDocument(){
		
	}

	public ValidateDocument(Long idPassenger, boolean validate) {
		super();
		this.idPassenger = idPassenger;
		this.validate = validate;
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

	@Override
	public String toString() {
		return "validateDocument [idPassenger=" + idPassenger + ", validate=" + validate + "]";
	}
	
	

}
