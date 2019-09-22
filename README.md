# validateEmailAddressRFC822
Validating Email Address by RFC822 Protocol

Email addresses should conform to the RFC822 standard for internet email addresses.The system corrects common user-input errors. Below is a functional description of the algorithm to format, validate, and verify the email addresses captured from the constituent.

Scan received email address string for the @ character and domain name. If either component is missing, the correction algorithm exits and returns an error message indicating that the email address submitted is invalid.

Attempt to match the email domain with the following domains allowing for detection and correction of single character misspellings (such as, Yaho vs. Yahoo, hatmail vs. hotmail): yahoo.com, aol.com, hotmail.com, comcast.net, sbcglobal.net, msn.com, cox.net, verizon.net

Check the domain for a valid MX (mail exchanger) record. If a valid MX record is found, no further correction is done.

Attempt to match the email TLD (top-level domain) with the following TLDs in listed order, allowing for detection and correction of single character misspellings (such as con vs. com, orf vs. org): com, net, org, edu, gov, info, mil, biz, aero, asia, cat, coop, int, jobs, mobi, museum, name, pro, tel, travel

After the email text string constituent input has been processed by the above algorithm, the properly formatted and validated email address is written to the constituent record. If the email address formatting/validating/verifying algorithm cannot continue processing at any step because of errors in the constituent input data, the algorithm reports that invalid input was detected and requests input correction.

Please remember to make sure your application has the following use case  

Someone type the email address 
System validates initial entry 
System validate the authenticity of the email based on  RFC822 standard
