package com.test.cxfwebservice.vo;

import java.io.Serializable;

public abstract class HASSAbstractBaseObject implements Serializable {

    private String lastRecTxnTypeCode;

    private String lastRecTxnDate;

    private String lastRecTxnUserId;

    private String pageNo;

    private String pageSize;


    public String getEncryptedText(String fieldName) {
        return "";
    }

    /**
     * @return Returns the lastRecTxnDate.
     */
    public String getLastRecTxnDate() {
        return lastRecTxnDate;
    }

    /**
     * @param lastRecTxnDate
     *            The lastRecTxnDate to set.
     */
    public void setLastRecTxnDate(String lastRecTxnDate) {
        this.lastRecTxnDate = lastRecTxnDate;
    }

    /**
     * @return Returns the lastRecTxnDateString.
     */
    public String getLastRecTxnDateString() {
    	return lastRecTxnDate==null ? null : lastRecTxnDate.toString();
    }

    /**
     * @param lastRecTxnDateString
     *            The lastRecTxnDateString to set.
     */
    public void setLastRecTxnDateString(String lastRecTxnDateString) {
        this.lastRecTxnDate = lastRecTxnDateString;
        /*
    	lastRecTxnDate= null;    	
   		try{
   			lastRecTxnDate= Timestamp.valueOf(lastRecTxnDateString);
   		}
   		catch(IllegalArgumentException e){}
   		*/
    }

    /**
     * @return Returns the lastRecTxnTypeCode.
     */
    public String getLastRecTxnTypeCode() {
        return lastRecTxnTypeCode;
    }

    /**
     * @param lastRecTxnTypeCode
     *            The lastRecTxnTypeCode to set.
     */
    public void setLastRecTxnTypeCode(String lastRecTxnTypeCode) {
        this.lastRecTxnTypeCode = lastRecTxnTypeCode;
    }

    /**
     * @return Returns the lastRecTxnUserId.
     */
    public String getLastRecTxnUserId() {
        return lastRecTxnUserId;
    }

    /**
     * @param lastRecTxnUserId
     *            The lastRecTxnUserId to set.
     */
    public void setLastRecTxnUserId(String lastRecTxnUserId) {
        this.lastRecTxnUserId = lastRecTxnUserId;
    }

    /**
     * @return Returns the pageNo.
     */
    public String getPageNo() {
        return pageNo;
    }

    /**
     * @param pageNo
     *            The pageNo to set.
     */
    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    /**
     * @return Returns the pageSize.
     */
    public String getPageSize() {
        return pageSize;
    }

    /**
     * @param pageSize
     *            The pageSize to set.
     */
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
