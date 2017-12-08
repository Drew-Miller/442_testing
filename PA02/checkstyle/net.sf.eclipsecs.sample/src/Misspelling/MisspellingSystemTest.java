package Misspelling;

public class MisspellingSystemTest { // Should PASS misspelling check
	int dog; // should PASS misspelling check
	
	private String doooSomeStufff () {	// Should FAIL misspelling check
		dog = 2;
		String bananana = "banana"; 	// Should FAIL misspelling test
		return bananana;
	}
}