package dbpedia;

import java.util.ArrayList;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

public class Panic {
	
	
	
	public static ArrayList<String> requeteProdPlusieursfilm(String titre,String date){             
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
	
	
	public static ArrayList<String> requeteActeurPlusieursfilm(String titre,String date){             
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
	
	
	public static ArrayList<String> requeteRealisateurPlusieursfilm(String titre,String date){             
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws org.apache.http.HttpException {
			//System.out.println(requeteProdPlusieursfilm("Hercules","1997"));
		ArrayList<String> ara = requeteRealisateurPlusieursfilm("Hercules","1997");
		
		for (int i = 0; i < ara.size(); i++) {
			System.out.println(ara.get(i));
			//System.out.println(",");
		}
		
		
	
	}

}
