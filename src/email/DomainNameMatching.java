package email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.CommonUtil;

public class DomainNameMatching implements MisMatchCharacter {
	
	private String domainName;
	private final List<String> preDomainList = new ArrayList<String>(Arrays.asList("yahoo", "aol", "hotmail", "comcast", "sbcglobal", "msn", "cox", "verizon"));
	
	public DomainNameMatching(String domainName) {
		this.domainName = domainName;
	}

	@Override
	public boolean doMisMatchOneCharacter() {
		return CommonUtil.doMismatchCharacter(domainName, preDomainList, 1);
	}

}
