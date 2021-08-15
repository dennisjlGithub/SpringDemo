package com.test.cxfwebservice.vo;

import java.io.Serializable;

public class HASSRequestContext implements Serializable {
	private String authenticationKey;
	private String userId;
	private String uniqueReferenceNo;

	public HASSRequestContext() {
    //    argsClazz = new Class[] { this.getClass() };
    //    argsValue = new Object[] { this };
	}

    public String getAuthenticationKey() {
		return authenticationKey;
	}

	public void setAuthenticationKey(String authenticationKey) {
		this.authenticationKey = authenticationKey;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
    }

    public String getUniqueReferenceNo() {
        return uniqueReferenceNo;
    }
    public void setUniqueReferenceNo(String uniqueReferenceNo) {
        this.uniqueReferenceNo = uniqueReferenceNo;
    }
}
