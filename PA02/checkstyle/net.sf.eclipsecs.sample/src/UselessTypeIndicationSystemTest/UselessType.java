package UselessTypeIndicationSystemTest;

import java.util.List;

public class UselessType {
	// class full of useless types
	// lets see what works
	private int my_int; // this should fail
	
	private int[] my_intarr; // this should fail
	
	private String name; // this should pass
	
	private List<Integer> int_list; // this should fail
	
	private char my_char; // this should fail
	
	private char another_int; // this should pass
}
