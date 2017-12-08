package OverloadedIdentifiers;

public class OverloadedIndentifiersSystemTest { // Should FAIL Overloaded Identifier
	
	int GoGetEggs; // Should FAIL Overloaded Identifier
	String Eggs; // Should PASS Overloaded Identifier
	
	public void Class () { // Should PASS Overloaded Identifier
		int catDog; // Should FAIL Overloaded Identifier
	}
	
	public void TooManyVerbsRunDo() { // Should FAIL Overloaded Identifier
		int doRun; // Should FAIL Overloaded Identifier
		String boat; // Should PASS Overloaded Identifier
	}
}

