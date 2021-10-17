package mediator;

import java.util.ArrayList;
import java.util.Scanner;

public class Test {

	public static void main(String[] args) throws Exception {
	ArrayList<String> tabs = new ArrayList<String>();
	tabs.add("20,000 Leagues Under the Sea");
	tabs.add("A Nightmare on Elm Street");
	tabs.add("Alice in Wonderland");
	tabs.add("Around the World in 80 Days");
	tabs.add("Ben-Hur");
	tabs.add("Carrie");
	tabs.add("Casino Royale");
	tabs.add("Cat People");
	tabs.add("Clash of the Titans");
	tabs.add("Crash");
	tabs.add("Dangerous Liaisons");
	tabs.add("Dawn of the Dead");
	tabs.add("Day of the Dead");
	tabs.add("Death at a Funeral");
	tabs.add("Footloose");
	tabs.add("Friday the 13th");
	tabs.add("Godzilla");
	tabs.add("Halloween");
	tabs.add("Hamlet");
	tabs.add("Hercules");
	tabs.add("House of Wax");
	tabs.add("King Kong");
	tabs.add("Left Behind");
	tabs.add("Night of the Living Dead");
	tabs.add("Notorious");

	for (int i = 0; i<tabs.size();i++) {
		System.err.println("===================== "+ tabs.get(i) + "===================== ");
		affichae(tabs.get(i));
		System.err.println("-------------------------------------------------------------------");
	}
	
	
	
	
		
		
		
		
		
		
		
		
	}
	
	public static void affichae(String x) throws Exception {
		Movie movie = new Movie();
		ArrayList<Movie> tabs = movie.getMovieInfo(x);
		for (int i = 0; i < tabs.size(); i++) {
			System.out.println(tabs.get(i));
		}
	}

}
