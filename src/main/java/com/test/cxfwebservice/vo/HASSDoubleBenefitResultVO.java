/*
 * Created on Mar 1, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.test.cxfwebservice.vo;

/**
 * @author LaurenceCheung
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HASSDoubleBenefitResultVO extends HASSResultContext {

    private ArrayOfHASSDuplicationReferenceVO duplicationReferenceList;
    
        
    /**
     * @return Returns the duplicationReferenceList.
     */
    public ArrayOfHASSDuplicationReferenceVO getDuplicationReferenceList() {
        return duplicationReferenceList;
    }
    /**
     * @param duplicationReferenceList The duplicationReferenceList to set.
     */
    public void setDuplicationReferenceList(ArrayOfHASSDuplicationReferenceVO duplicationReferenceList) {
    	this.duplicationReferenceList = duplicationReferenceList;
    }
    

}
