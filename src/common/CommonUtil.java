package common;

import java.util.List;

public class CommonUtil {
	
	public static boolean doMismatchCharacter(String originalString, List<String> predefinedList, int numberOfCharacters) {
		for(String name : predefinedList ) {
			int mxLen = Math.max(name.length(), originalString.length());
			int mnLen = Math.min(name.length(), originalString.length());
			int misMatchCounter = 0;
			for(int i = 0; i < mnLen; i++) {
				if(name.charAt(i) != originalString.charAt(i)) {
					misMatchCounter++;
				}
			}
			misMatchCounter += (mxLen - mnLen);
			if(misMatchCounter == numberOfCharacters) {
				return true;
			}
		}
		return false;
	}
}
