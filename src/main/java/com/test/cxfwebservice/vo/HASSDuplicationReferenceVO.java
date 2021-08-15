package com.test.cxfwebservice.vo;

public class HASSDuplicationReferenceVO extends HASSAbstractBaseObject {

    private String memberICType;
    private String memberICNumber;
    private String benefitReferenceType;
    private String benefitReferenceNum;
    private String internalKey;
    private String creationDate;
    private String expiryDate;
    
    /**
     * @return Returns the benefitReferenceNum.
     */
    public String getBenefitReferenceNum() {
        return benefitReferenceNum;
    }
    /**
     * @param benefitReferenceNum The benefitReferenceNum to set.
     */
    public void setBenefitReferenceNum(String benefitReferenceNum) {
        this.benefitReferenceNum = benefitReferenceNum;
    }
    /**
     * @return Returns the benefitReferenceType.
     */
    public String getBenefitReferenceType() {
        return benefitReferenceType;
    }
    /**
     * @param benefitReferenceType The benefitReferenceType to set.
     */
    public void setBenefitReferenceType(String benefitReferenceType) {
        this.benefitReferenceType = benefitReferenceType;
    }
    /**
     * @return Returns the creationDate.
     */
    public String getCreationDate() {
        return creationDate;
    }
    /**
     * @param creationDate The creationDate to set.
     */
    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
    /**
     * @return Returns the expiryDate.
     */
    public String getExpiryDate() {
        return expiryDate;
    }
    /**
     * @param expiryDate The expiryDate to set.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    /**
     * @return Returns the inernalKey.
     */
    public String getInternalKey() {
        return internalKey;
    }
    /**
     * @param inernalKey The inernalKey to set.
     */
    public void setInternalKey(String internalKey) {
        this.internalKey = internalKey;
    }
    /**
     * @return Returns the memberICNumber.
     */
    public String getMemberICNumber() {
        return memberICNumber;
    }
    /**
     * @param memberICNumber The memberICNumber to set.
     */
    public void setMemberICNumber(String memberICNumber) {
        this.memberICNumber = memberICNumber;
    }
    /**
     * @return Returns the memberICType.
     */
    public String getMemberICType() {
        return memberICType;
    }
    /**
     * @param memberICType The memberICType to set.
     */
    public void setMemberICType(String memberICType) {
        this.memberICType = memberICType;
    }
    
    
    
}
