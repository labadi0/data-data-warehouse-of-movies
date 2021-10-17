package mediatorV2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

public class MediatorV2 {
	public static void main(String[] args) throws SQLException, Exception {
		
		
		
		/*
		Movie movie = new Movie();
		ArrayList<Movie> tabs = movie.getMovieInfo("Mortal");
		for (int i = 0; i < tabs.size(); i++) {
			System.out.println(tabs.get(i));
		}
		*/
	
		
		/*
		Movie movie = new Movie();
		movie.printAllMoviesByActorName("Angelina Jolie");
		*/
		
		
		
		 Scanner in = new Scanner(System.in); 
         System.out.println("make your choice  ");  
         System.out.println("1 if you want to get information about film by title");
         System.out.println("2 if you want to get all the movies information of an actor");
         
         String choix = in.nextLine();  
         System.out.println("you choose : " + choix);   
         if (choix.equals("1")) {
    		  
        	 System.out.println("give me the name of the movie");
        	 
        	 Scanner in2 = new Scanner(System.in); 
        	 String movieName = in2.nextLine();	
        	 System.out.println("the name of movie is " + movieName);
        	 
        	 MovieV2 movie = new MovieV2();
     		ArrayList<MovieV2> tabs = movie.getMovieInfo(movieName);
     		for (int i = 0; i < tabs.size(); i++) {
     			System.out.println(tabs.get(i));
     		}
        	 
        	 
        	 
         }
         else if ( choix.equals("2")) {
        	 System.out.println("give me the actor name");

        	 Scanner in3 = new Scanner(System.in); 
        	 String actor = in3.nextLine();	 
        	 System.out.println("the actor name : " + actor);
        	 
        	 MovieV2 movie = new MovieV2();
     		movie.printAllMoviesByActorName(actor);
        	 
        	 
         }
         
         
                    
         
		
 		
		


    }


}
