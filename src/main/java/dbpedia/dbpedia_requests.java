package dbpedia;
import java.util.ArrayList;
import java.util.List;


import org.apache.jena.atlas.web.HttpException;
import org.apache.jena.query.ParameterizedSparqlString;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.ResourceFactory;

import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;


public class dbpedia_requests {
	
	
	
	public static final String SERVICE = "http://dbpedia.org/sparql";
	
	public static final String PREFIX = ""     
            + "prefix rdfs:    <http://www.w3.org/2000/01/rdf-schema#>\n" 
			+ "PREFIX rdf: <http://www.w3.org/1999/02/22-rdf-syntax-ns#>"
            + "PREFIX dbo:     <http://dbpedia.org/ontology/>"
            + "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"
            + "PREFIX : <http://dbpedia.org/resource/> \n" ;	
	/*
	public static List<String>  getActeurs(String title,String date) throws org.apache.http.HttpException {
		List<String> acteurs_list = new ArrayList<String>();
		String acteur = "";
		
		String string_query = "SELECT  ?aname WHERE {\n" + 
				"?film a dbo:Film ;\n" + 
				"         foaf:name \"MOVIE_TITLE\"@en ;\n".replaceAll("MOVIE_TITLE",title) +
				"         dbo:starring ?actor .\n" + 
				"?actor foaf:name ?aname.\n"
				+ "?film dbo:releaseDate ?releaseDate.FILTER (year(?releaseDate) ="+date+")    }  ";
		

        ParameterizedSparqlString  qs = new ParameterizedSparqlString(PREFIX +string_query);
        QueryExecution exec = QueryExecutionFactory.sparqlService(SERVICE, qs.asQuery());
        
        ResultSet results = exec.execSelect();
        System.err.println(results);
        while (results.hasNext()) {
        	QuerySolution solution = results.nextSolution();
        	acteur = solution.getLiteral("?aname").toString().replace("@en", "");
            acteurs_list.add(acteur);
        }
		return acteurs_list;
	}
	
	public static List<String> getProducteurs(String title,String date) throws org.apache.http.HttpException {
		String acteur = "";
		List<String> acteurs_list = new ArrayList<String>();
		String string_query = "SELECT ?colname WHERE { \n" +
				"?film a dbo:Film ; \n" + 
				"foaf:name \"MOVIE_TITLE\"@en ; \n".replaceAll("MOVIE_TITLE",title) + 
				"dbo:producer ?producer . \n" + 
				"?producer foaf:name ?colname .\n"+
				"?film dbo:releaseDate ?releaseDate.FILTER (year(?releaseDate) ="+date+")}";
		

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
	
	public static List<String> getRealisateur(String title,String date) throws org.apache.http.HttpException {
		String realisateur="";
		List<String> realisateur_list = new ArrayList<String>();
		String string_query = "SELECT $name WHERE {\n" + 
				"$film a dbo:Film ;\n" + 
				"         foaf:name \"MOVIE_TITLE\"@en .\n".replace("MOVIE_TITLE",title) + 
				"$film dbo:director $name .\n"+
				"?film dbo:releaseDate ?releaseDate.FILTER (year(?releaseDate) ="+date+")}";

				

        ParameterizedSparqlString  qs = new ParameterizedSparqlString(PREFIX +string_query);
        QueryExecution exec = QueryExecutionFactory.sparqlService(SERVICE, qs.asQuery());
        ResultSet results = exec.execSelect();
        if (results.hasNext()) {
        	//System.out.println("yessssssssssssssssssssss");
        }
        else {
        	//System.out.println("noooooooooooooooooooooooo");
        }
        
        while (results.hasNext()) {
        	String[] solution = results.nextSolution().toString().split("/");
        	realisateur = solution[solution.length-1].replace("MOVIE_TITLE",title);
        	realisateur = realisateur.replace("_"," "); realisateur = realisateur.replace("> )",""); 
        	realisateur_list.add(realisateur);
        }
		
		return realisateur_list;
	}
	*/
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
	

	
	
	//===================================================================================================
	
	
	
	
	public static ArrayList<String> getProducteurs(String titre,String date){             
		ArrayList<String> tab = new ArrayList<String>();
	      String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
	    			           "PREFIX dbo: <http://dbpedia.org/ontology/>"+
	    		  
								"SELECT ?film ?r WHERE {" +
								"?film a dbo:Film ;" +
								"foaf:name '"+ titre +"'@en ;" +
								"dbo:producer ?r ." +
								"}";
	      
	      Query query = QueryFactory.create(queryString);        
	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	      try{
	    	  String res ="\nLe(s) producteurs(s) sont (par année de film) :  \n";
	    	  String doublons="test";
	    	  ResultSet results = qexec.execSelect();
	    	  while(results.hasNext()){
	    		  
	    		  
	    		  QuerySolution soln = results.nextSolution();
	    		  String resultat = soln.toString();
	    		  
	    		  int debutacteur = resultat.indexOf("?r");
	    		  int finacteur = resultat.length();
	    		  int debut = resultat.indexOf("resource/");
	    		  int fin = resultat.indexOf(")>",debut);
	    		  int findif = resultat.indexOf(">",debut);
	    		  String acteur = resultat.substring(debutacteur+34,finacteur-3);
	    		  acteur = acteur.replace("_"," ");
	    		  if (resultat.contains("("+date)) {
			    		 if (res.contains("_miniseries")){
					    	 res = res.replace("_minis","");
					    	 }
					    	 if(res.contains("_")) {
					    		 res = res.replace("_"," ");
					    		 
					    	 }
					    	 if(res.contains("film producer")) {
					    		 res = res.replace("film producer","");
					    	 }
				    		  
			   	    	 res = res.replace("_"," ");
			    		 res += " " + acteur+ "\n";
			    		 tab.add(acteur);
	    		  }

	    		// res += " " + acteur;
	    		// res += "\n";
	    		  
	    	  
	    	  
	    	  
	    	  } 
	    	  res +="\n";
	    	  return tab;
	      }
	      finally{
	    	  qexec.close();
	      }

	}
	
	
	public static ArrayList<String> getActeurs(String titre,String date){             
		ArrayList<String> tab = new ArrayList<String>();
		String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
		           "PREFIX dbo: <http://dbpedia.org/ontology/>"+
	  
					"SELECT ?film ?r WHERE {" +
					"?film a dbo:Film ;" +
					"foaf:name '"+ titre +"'@en ;" +
					"dbo:starring ?r ." +
					"}";
	      
	      Query query = QueryFactory.create(queryString);        
	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	      try{
	    	  String res ="\nLe(s) producteurs(s) sont (par année de film) :  \n";
	    	  String doublons="test";
	    	  ResultSet results = qexec.execSelect();
	    	  while(results.hasNext()){
	    		  
	    		  
	    		  QuerySolution soln = results.nextSolution();
	    		  String resultat = soln.toString();
	    		  
	    		  int debutacteur = resultat.indexOf("?r");
	    		  int finacteur = resultat.length();
	    		  int debut = resultat.indexOf("resource/");
	    		  int fin = resultat.indexOf(")>",debut);
	    		  int findif = resultat.indexOf(">",debut);
	    		  String acteur = resultat.substring(debutacteur+34,finacteur-3);
	    		  acteur = acteur.replace("_"," ");
	    		  if (resultat.contains("("+date)) {
			    		 if (res.contains("_miniseries")){
					    	 res = res.replace("_minis","");
					    	 }
					    	 if(res.contains("_")) {
					    		 res = res.replace("_"," ");
					    		 
					    	 }
					    	 if(res.contains("film producer")) {
					    		 res = res.replace("film producer","");
					    	 }
				    		  
			   	    	 res = res.replace("_"," ");
			    		 res += " " + acteur+ "\n";
			    		 tab.add(acteur);
	    		  }

	    		// res += " " + acteur;
	    		// res += "\n";
	    		  
	    	  
	    	  
	    	  
	    	  } 
	    	  res +="\n";
	    	  return tab;
	      }
	      finally{
	    	  qexec.close();
	      }

	}
	
	
	public static ArrayList<String> getRealisateur(String titre,String date){             
		ArrayList<String> tab = new ArrayList<String>();
		String queryString = "PREFIX foaf: <http://xmlns.com/foaf/0.1/>"+
		           "PREFIX dbo: <http://dbpedia.org/ontology/>"+
		           "PREFIX dbp:  <http://dbpedia.org/property/>"+
	  
					"SELECT ?film  ?director WHERE {" + 
					"?film a dbo:Film ;" + 
					"foaf:name '"+ titre +"'@en ;" +
					"dbo:director ?director ." + 
					"}";
		
	      Query query = QueryFactory.create(queryString);        
	      QueryExecution qexec = QueryExecutionFactory.sparqlService("http://dbpedia.org/sparql", query);
	      try{
	    	  String res ="\nLe(s) producteurs(s) sont (par année de film) :  \n";
	    	  String doublons="test";
	    	  ResultSet results = qexec.execSelect();
	    	  while(results.hasNext()){
	    		  
	    		  
	    		  QuerySolution soln = results.nextSolution();
	    		  String resultat = soln.toString();
	    		  
	    		  int debutacteur = resultat.indexOf("?director");
	    		  int finacteur = resultat.length();
	    		  int debut = resultat.indexOf("resource/");
	    		  int fin = resultat.indexOf(")>",debut);
	    		  int findif = resultat.indexOf(">",debut);
	    		  String acteur = resultat.substring(debutacteur+41,finacteur-3);
	    		  acteur = acteur.replace("_"," ");
	    		  if (resultat.contains("("+date)) {
			    		 if (res.contains("_miniseries")){
					    	 res = res.replace("_minis","");
					    	 }
					    	 if(res.contains("_")) {
					    		 res = res.replace("_"," ");
					    		 
					    	 }
					    	 if(res.contains("film producer")) {
					    		 res = res.replace("film producer","");
					    	 }
				    		  
			   	    	 res = res.replace("_"," ");
			    		 res += " " + acteur+ "\n";
			    		 //acteur.replaceAll("source", " ");
			    		 tab.add(acteur);
	    		  }

	    		// res += " " + acteur;
	    		// res += "\n";
	    		  
	    	  
	    	  
	    	  
	    	  } 
	    	  res +="\n";
	    	  return tab;
	      }
	      finally{
	    	  qexec.close();
	      }

	}
	
	
	
	
	
	
	
	
	
	
	
	
	//=====================================================================================================
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	public static void main(String[] args) throws org.apache.http.HttpException {
		//System.out.println(getProducteurs("Aliens"));
		//System.out.println(getActeurs("Bravfksdfjskdlfjksllmeheart"));
		
		//System.out.println(getRealisateur("20,000 Leagues Under the Sea","1916"));
		System.out.println(getActeurs("20,000 Leagues Under the Sea","1916"));
		//System.out.println(getProducteurs("20,000 Leagues Under the Sea","1916"));
		//System.out.println(getActeurs("Titanic","1997"));
		
		//System.out.println(getRealisateur("Niruttara","2016"));


		
		
		//System.out.println(getTestOtherQuery());
		
		
		
		
		
		
		
		//System.out.println(getFilms("Angelina Jolie"));
		//System.out.println(getRealisateur("Ed Wood"));
		//System.out.println(getRealisateur("Harry Potter"));
		//System.out.println(getActeurs("Harry Potter"));

    }


}
