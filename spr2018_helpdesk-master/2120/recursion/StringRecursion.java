import java.util.ArrayList;

public class StringRecursion{
	

	public static int compareTo(String s1, String s2){
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		String news1, news2;

		//If either string has length 0
		if (s1.length() < 1 || s2.length() < 1){
			if (s1.length() == 0 && s2.length() == 0){
				return 0;
			}
			else if(s1.length() == 0){
				return -1;
			}
			else if (s2.length() == 0){
				return 1;
			}

		}


		//If both string lengths > 0
		//Recursion occurs when both starting characters are the same
		//Recursion called on strings with first char removed
		if (s1.charAt(0) == s2.charAt(0)){
			if (s1.length() == 1)
				news1 = "";
			else
				news1 = s1.substring(1, s1.length());
			if (s2.length() == 1)	
				news2 = "";
			else
				news2 = s2.substring(1, s2.length());

			return compareTo(news1, news2);
		}

		else{
			return s1.charAt(0) - s2.charAt(0);
		}

		
	}

	// Caller method, returns a string
	public static String findMinimum(ArrayList<String> stringArray){
		ArrayList<String> stringArrayTemp = stringArray;
		if (stringArray.size() == 0){
			throw new IllegalArgumentException ("There are no strings in the array.");
		}

		else if (stringArray.size() == 1){
			return stringArray.get(0);
		}

		else{
			recurFindMin(stringArrayTemp);
			return stringArrayTemp.get(0);
		}

		

	}

	// Helper method
	// If the list has two or more elements, compares the first two and removes the smaller one
	private static void recurFindMin(ArrayList<String> stringArrayTemp){
		if (stringArrayTemp.size() >= 2){
			if (compareTo(stringArrayTemp.get(0), stringArrayTemp.get(1)) >= 1){
				stringArrayTemp.remove(0);
				recurFindMin(stringArrayTemp);
			}

			else{
				stringArrayTemp.remove(1);
				recurFindMin(stringArrayTemp);
			}
		}
	}


}// end class