package email;

public class ValidateEmail {
	
	private String emailAddress;
	
	public ValidateEmail(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	public boolean testEmail() {
		RegexMatching regexMatching = new RegexMatching(emailAddress);
		if(regexMatching.matchWithRegex() == false) {
			return false;
		}
		
		EmailParser emailParser = new EmailParser(emailAddress);
		String domainName = emailParser.getDomainName();
		String topLevelDomainName = emailParser.getTopLevelDomainName();
		if(domainName == null || topLevelDomainName == null) return false;
		
		DomainNameMatching domainNameMatching = new DomainNameMatching(domainName);
		if(domainNameMatching.doMisMatchOneCharacter()) {
			System.out.println("One character mismatch found in Domain Name");
		}
		
		TLDMatching tldMatching = new TLDMatching(topLevelDomainName);
		if(tldMatching.doMisMatchOneCharacter()) {
			System.out.println("One character mismatch found in Top-Level Domain Name");
		}
		
		LookupAtMXRecord lookupAtMXRecord = new LookupAtMXRecord(emailAddress);
		try {
			if(lookupAtMXRecord.isAddressValid()) {
				System.out.println("Valid response from XM record");
				return true;
			}
		} catch(Exception e) {
			System.out.println("Exception occured during checking at MX record. Exception: " + e);
			return false;
		}
		
		return true;
	}
}
