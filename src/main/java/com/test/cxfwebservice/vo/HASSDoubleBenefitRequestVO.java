package com.test.cxfwebservice.vo;

public class HASSDoubleBenefitRequestVO extends HASSRequestContext {

    private String benefitReferenceType;
    private String benefitReferenceNum;
    private String memberICType;
    private String memberICNumber;
        
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
