package mediatorV2;

import java.util.Scanner;

public class Test {

	public static void main(String[] args) {
		
		
		String str = "fjsk";
			
		//Specify all posible special charcters
		String specialCharacters=" !#$%&'()*+,-./:;<=>?@[]^_`{|}";
			
		boolean found = false;
			
		for(int i=0; i<specialCharacters.length(); i++){
			    
		    //Checking if the input string contain any of the specified Characters
		    if(str.contains(Character.toString(specialCharacters.charAt(i)))){
		        found = true;
		        System.out.println("String contains Special Characters");
		        break;
		    }
		}
		
	        if(found == false) {
	            System.out.println("String doesn't have any Special Characters");
	           
	        }
	        }
		
		

	}


