package com.test.cxfwebservice.vo;

import java.io.Serializable;

public class HASSResultContext implements Serializable {
    private boolean successInd;
    private String exception;
    
	public String getException() {
		return exception;
	}
	
	public void setException(String exception) {            
		this.exception = exception;
	}
	
	public boolean isSuccessInd() {
		return successInd;
	}
	
	public void setSuccessInd(boolean successInd) {
		this.successInd = successInd;
	}
	
}
