package com.test.cxfwebservice.vo;

public class ArrayOfHASSDuplicationReferenceVO {

	private HASSDuplicationReferenceVO[] HASSDuplicationReferenceVO;

	public HASSDuplicationReferenceVO[] getHASSDuplicationReferenceVO() {
		return HASSDuplicationReferenceVO == null ? new HASSDuplicationReferenceVO[0] : HASSDuplicationReferenceVO;
	}

	public void setHASSDuplicationReferenceVO(HASSDuplicationReferenceVO[] hASSDuplicationReferenceVO) {
		HASSDuplicationReferenceVO = hASSDuplicationReferenceVO;
	}

}
