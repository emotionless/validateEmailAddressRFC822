package email;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

public class LookupAtMXRecord {

	private String emailAddress;

	public LookupAtMXRecord(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	// This code has been copied from online to check if MX record.
	private static int hear(BufferedReader in) throws IOException {
		String line = null;
		int res = 0;
		while ((line = in.readLine()) != null) {
			String pfx = line.substring(0, 3);
			try {
				res = Integer.parseInt(pfx);
			} catch (Exception ex) {
				res = -1;
			}
			if (line.charAt(3) != '-')
				break;
		}
		return res;
	}

	private static void say(BufferedWriter wr, String text) throws IOException {
		wr.write(text + "\r\n");
		wr.flush();
		return;
	}

	private ArrayList<String> getMX(String hostName) throws NamingException {
		// Perform a DNS lookup for MX records in the domain
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
		DirContext ictx = new InitialDirContext(env);
		Attributes attrs = ictx.getAttributes(hostName, new String[] { "MX" });
		Attribute attr = attrs.get("MX");
		// if we don't have an MX record, try the machine itself
		if ((attr == null) || (attr.size() == 0)) {
			attrs = ictx.getAttributes(hostName, new String[] { "A" });
			attr = attrs.get("A");
			if (attr == null)
				throw new NamingException("No match for name '" + hostName + "'");
		}
		// Huzzah! we have machines to try. Return them as an array list
		// NOTE: We SHOULD take the preference into account to be absolutely
		// correct. This is left as an exercise for anyone who cares.
		ArrayList<String> res = new ArrayList<String>();
		NamingEnumeration<?> en = attr.getAll();
		while (en.hasMore()) {
			String x = (String) en.next();
			String f[] = x.split(" ");
			if (f[1].endsWith("."))
				f[1] = f[1].substring(0, (f[1].length() - 1));
			res.add(f[1]);
		}
		return res;
	}

	public boolean isAddressValid() {
		// Find the separator for the domain name
		int pos = emailAddress.indexOf('@');
		// If the address does not contain an '@', it's not valid
		if (pos == -1)
			return false;
		// Isolate the domain/machine name and get a list of mail exchangers
		String domain = emailAddress.substring(++pos);
		ArrayList<String> mxList = null;
		try {
			mxList = getMX(domain);
		} catch (NamingException ex) {
			return false;
		}
		// Just because we can send mail to the domain, doesn't mean that the
		// address is valid, but if we can't, it's a sure sign that it isn't
		if (mxList.size() == 0)
			return false;
		// Now, do the SMTP validation, try each mail exchanger until we get
		// a positive acceptance. It *MAY* be possible for one MX to allow
		// a message [store and forwarder for example] and another [like
		// the actual mail server] to reject it. This is why we REALLY ought
		// to take the preference into account.
		for (int mx = 0; mx < mxList.size(); mx++) {
			boolean valid = false;
			try {
				int res;
				Socket skt = new Socket((String) mxList.get(mx), 25);
				BufferedReader rdr = new BufferedReader(new InputStreamReader(skt.getInputStream()));
				BufferedWriter wtr = new BufferedWriter(new OutputStreamWriter(skt.getOutputStream()));
				res = hear(rdr);
				if (res != 220) {
					extracted();
				}
				say(wtr, "EHLO orbaker.com");
				res = hear(rdr);
				if (res != 250) {
					extracted();
				}
				// validate the sender address
				say(wtr, "MAIL FROM: <tim@orbaker.com>");
				res = hear(rdr);
				if (res != 250) {
					extracted();
				}
				say(wtr, "RCPT TO: <" + emailAddress + ">");
				res = hear(rdr);
				// be polite
				say(wtr, "RSET");
				hear(rdr);
				say(wtr, "QUIT");
				hear(rdr);
				if (res != 250)
					extracted();
				valid = true;
				rdr.close();
				wtr.close();
				skt.close();
			} catch (Exception ex) {
				// Do nothing but try next host
			} finally {
				if (valid)
					return true;
			}
		}
		return false;
	}

	private void extracted() throws Exception {
		throw new Exception("Address is not valid!");
	}
}
