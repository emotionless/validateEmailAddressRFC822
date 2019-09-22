package email;

public class EmailValidation {
	
	public static void main(String args[]) {
		String emailAddress = "miloniitju@gmail.com";
		ValidateEmail validateEmail = new ValidateEmail(emailAddress);
		if(validateEmail.testEmail() == true) {
			System.out.println("Email validation passed successfully");
		} else {
			System.out.println("Invalid email address");
		}
	}
	
	
}
