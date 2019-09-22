package email;

public class EmailParser {
	private String emailAddress;
	private String domainName;
	private String topLevelDomainName;
	
	public EmailParser(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getDomainName() {
		domainName = parseEmailToGetDomainName();
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public String getTopLevelDomainName() {
		topLevelDomainName = parseEmailToGetTopLevelDomainName();
		return topLevelDomainName;
	}

	public void setTopLevelDomainName(String topLevelDomainName) {
		this.topLevelDomainName = topLevelDomainName;
	}
	
	private String parseEmailToGetDomainName() {
		int atIndex = emailAddress.indexOf('@');
		int dotIndex = emailAddress.lastIndexOf('.');
		if(atIndex == -1 || dotIndex == -1) {
			return null;
		}
		return emailAddress.substring(atIndex, dotIndex - 1);
	}
	
	private String parseEmailToGetTopLevelDomainName() {
		int dotIndex = emailAddress.lastIndexOf('.');
		if(dotIndex == -1) {
			return null;
		}
		return emailAddress.substring(dotIndex + 1);
	}
	
	
}
