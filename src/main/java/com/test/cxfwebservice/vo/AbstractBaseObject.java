/*
 * Created on Sep 11, 2005
 */
package com.test.cxfwebservice.vo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Hashtable;

/**
 * @author edwinwong
 */
public abstract class AbstractBaseObject implements Serializable {
	//private static final long serialVersionUID = -5847970856014844645L;
	
    static public final int WF_DEFAULT = 0;

    static public final int WF_NO_ACTION = 1;

    static public final int WF_CANCEL_CHECK_OUT = 2;

    /**
     * @return Returns the accessibleFields.
     */
    public Hashtable getAccessibleFields() {
        return accessibleFields;
    }
    /**
     * @param accessibleFields The accessibleFields to set.
     */
    public void setAccessibleFields(Hashtable accessibleFields) {
        this.accessibleFields = accessibleFields;
    }
    private Hashtable accessibleFields = new Hashtable();

    private String wfProcessInstanceName = "";

    private String wfWiOid = "";

    private String wfPTemplate = "";

    /**
     * @return Returns the encryptedFields.
     */
    public Hashtable getEncryptedFields() {
        return encryptedFields;
    }
    /**
     * @param encryptedFields The encryptedFields to set.
     */
    public void setEncryptedFields(Hashtable encryptedFields) {
        this.encryptedFields = encryptedFields;
    }
    private String wfTransferToUid = "";

    private Integer wfAction = new Integer(WF_DEFAULT);

    private Hashtable encryptedFields = new Hashtable();

    private String lastRecTxnTypeCode;

    private Timestamp lastRecTxnDate;

    private String lastRecTxnUserId;

    private String pageNo;

    private String pageSize;


    /*
     * @depercated
     * 
     * @see #getWfProcessTemplateName
     */
    public String getWfPTemplate() {
        return wfPTemplate;
    }

    /*
     * @depercated
     * 
     * @see #setWfProcessTemplateName
     */
    public void setWfPTemplate(String template) {
        wfPTemplate = template;
    }

    /*
     * @depercated
     * 
     * @see #getWfTransferToUid
     */
    public String getToUid() {
        return wfTransferToUid;
    }

    /*
     * @depercated
     * 
     * @see #setWfTransferToUid
     */
    public void setToUid(String transferToUid) {
        this.wfTransferToUid = transferToUid;
    }

    /*
     * @depercated
     * 
     * @see #getWfWiOid
     */
    public String getWiOid() {
        return wfWiOid;
    }

    /*
     * @depercated
     * 
     * @see #setWfWiOid
     */
    public void setWiOid(String wiOid) {
        this.wfWiOid = wiOid;
    }

    public String getWfProcessTemplateName() {
        return wfPTemplate;
    }

    public void setWfProcessTemplateName(String template) {
        wfPTemplate = template;
    }

    public String getWfTransferToUid() {
        return wfTransferToUid;
    }

    public void setWfTransferToUid(String transferToUid) {
        this.wfTransferToUid = transferToUid;
    }

    public String getWfWiOid() {
        return wfWiOid;
    }

    public void setWfWiOid(String wiOid) {
        this.wfWiOid = wiOid;
    }

    public Integer getWfAction() {
        return wfAction;
    }

    public void setWfAction(Integer action) {
        if (action == null)
            action = new Integer(WF_DEFAULT);
        this.wfAction = action;
    }

    public void setWfAction(int action) {
        this.wfAction = new Integer(action);
    }

    public String getWfProcessInstanceName() {
        return wfProcessInstanceName;
    }

    public void setWfProcessInstanceName(String piName) {
        this.wfProcessInstanceName = piName;
    }

    public boolean isAccessible(String fieldname) {
        String result = (String) accessibleFields.get(fieldname);
        return result != null && result.equals("all");
    }

    public void setAccessible(String fieldname, String value) {
        accessibleFields.put(fieldname, value);
    }

    public String getEncryptedText(String fieldName) {
        return "";
    }

    /**
     * @return Returns the lastRecTxnDate.
     */
    public Timestamp getLastRecTxnDate() {
        return lastRecTxnDate;
    }

    /**
     * @param lastRecTxnDate
     *            The lastRecTxnDate to set.
     */
    public void setLastRecTxnDate(Timestamp lastRecTxnDate) {
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

    	lastRecTxnDate= null;
   		try{
   			lastRecTxnDate= Timestamp.valueOf(lastRecTxnDateString);
   		}
   		catch(IllegalArgumentException e){}
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
