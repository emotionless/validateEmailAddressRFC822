package email;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.CommonUtil;

public class TLDMatching implements MisMatchCharacter {
	
	private String tldName;
	private final List<String> preTLDList = new ArrayList<String> (Arrays.asList("com", "net", "org", "edu", "gov", "info", 
			"mil", "biz", "aero", "asia", "cat", "coop", "int", "jobs", "mobi", "museum", "name", "pro", "tel", "travel"));
	
	public TLDMatching(String tldName) {
		this.tldName = tldName;
	}

	@Override
	public boolean doMisMatchOneCharacter() {
		return CommonUtil.doMismatchCharacter(tldName, preTLDList, 1);
	}
	
}
