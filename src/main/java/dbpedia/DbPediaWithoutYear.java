package dbpedia;
import java.util.ArrayList;
import java.util.List;
import javax.management.Query;

import org.apache.http.HttpException;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

public class DbPediaWithoutYear {
	public static final String SERVICE = "http://dbpedia.org/sparql";
	
	public static final String PREFIX = ""     
            + "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" 
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
            + "PREFIX dbo:     <http://dbpedia.org/ontology/>"
            + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
            + "PREFIX : <http://dbpedia.org/resource/> \n" ;	
	
	public static List<String>  getActeurs(String title) throws org.apache.http.HttpException{
		String acteur = "";
		List<String> acteurs_list = new ArrayList<String>();
		String string_query = "SELECT ?aname WHERE {\n" + 
				"?film a dbo:Film ;\n" + 
				"         foaf:name \"MOVIE_TITLE\"@en ;\n".replaceAll("MOVIE_TITLE",title) +
				"         dbo:starring ?actor .\n" + 
				"?actor foaf:name ?aname.\n}";
		

        ParameterizedSparqlString  qs = new ParameterizedSparqlString(PREFIX +string_query);
        QueryExecution exec = QueryExecutionFactory.sparqlService(SERVICE, qs.asQuery());
        ResultSet results = exec.execSelect();
        
        while (results.hasNext()) {
        	QuerySolution solution = results.nextSolution();
        	acteur = solution.getLiteral("?aname").toString().replace("@en", "");
            acteurs_list.add(acteur);
        }
		return acteurs_list;
	}
	
	public static List<String> getProducteurs(String title) throws org.apache.http.HttpException {
		String acteur = "";
		List<String> acteurs_list = new ArrayList<String>();
		String string_query = "SELECT ?colname WHERE { \n" +
				"?film a dbo:Film ; \n" + 
				"foaf:name \"MOVIE_TITLE\"@en ; \n".replaceAll("MOVIE_TITLE",title) + 
				"dbo:producer ?producer . \n" + 
				"?producer foaf:name ?colname . }";
		

        ParameterizedSparqlString  qs = new ParameterizedSparqlString(PREFIX +string_query);
        QueryExecution exec = QueryExecutionFactory.sparqlService(SERVICE, qs.asQuery());
        ResultSet results = exec.execSelect();
        
        while (results.hasNext()) {
        	QuerySolution solution = results.nextSolution();
        	//System.out.println(solution.toString());
        	acteur = solution.getLiteral("?colname").toString().replace("@en", "");
            acteurs_list.add(acteur);
        }
		return acteurs_list;
	}
	
	public static List<String> getRealisateur(String title) throws org.apache.http.HttpException {
		String realisateur="";
		List<String> realisateur_list = new ArrayList<String>();
		String string_query = "SELECT $name WHERE {\n" + 
				"$film a dbo:Film ;\n" + 
				"         foaf:name \"MOVIE_TITLE\"@en .\n".replace("MOVIE_TITLE",title) + 
				"$film dbo:director $name .\n}";

        ParameterizedSparqlString  qs = new ParameterizedSparqlString(PREFIX +string_query);
        QueryExecution exec = QueryExecutionFactory.sparqlService(SERVICE, qs.asQuery());
        ResultSet results = exec.execSelect();
        while (results.hasNext()) {
        	String[] solution = results.nextSolution().toString().split("/");
        	realisateur = solution[solution.length-1].replace("MOVIE_TITLE",title);
        	realisateur = realisateur.replace("_"," "); realisateur = realisateur.replace("> )",""); 
        	realisateur_list.add(realisateur);
        }
		
		return realisateur_list;
	}
	
	// Take an actor name and returns a list of movies where he plays in.
	public static List<String> getFilms(String acteur) throws org.apache.http.HttpException {
		String movie = "";
		List<String> movies_list = new ArrayList<String>();
		String string_query = "SELECT $title WHERE {\n $film rdf:type dbo:Film;\n" + 
                "         foaf:name $title;\n dbo:starring $acteur.\n" + 
                "$acteur foaf:name \"ACTOR_NAME\"@en\n}".replaceAll("ACTOR_NAME",acteur);
		

        ParameterizedSparqlString  qs = new ParameterizedSparqlString(PREFIX +string_query);
        QueryExecution exec = QueryExecutionFactory.sparqlService(SERVICE, qs.asQuery());
        ResultSet results = exec.execSelect();
        
        while (results.hasNext()) {
        	QuerySolution solution = results.nextSolution();
        	movie = solution.getLiteral("?title").toString().replace("@en", "");
        	movies_list.add(movie);
        }
		
		return movies_list;
	}

	public static void main(String[] args) throws HttpException {
		//System.out.println(getProducteurs("Avatar"));
		System.out.println(getActeurs("20,000 Leagues Under the Sea"));
		//System.out.println(getFilms("Angelina Jolie"));
		//System.out.println(getRealisateur("Ed Wood"));
		/*
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
			System.out.println("acteurs : " + getActeurs(tabs.get(i)));
			System.out.println("producteur : "+ getProducteurs(tabs.get(i)));
			System.out.println("realisateur : " + getRealisateur(tabs.get(i)));
			System.err.println("-------------------------------------------------------------------");
		}
		
		*/
		
		
		
    }

	
	
	
	
	
	
	
}
