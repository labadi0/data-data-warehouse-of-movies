package mediatorV2;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpException;

import dbpedia.DbPediaWithoutYear;
import dbpedia.dbpedia_requests;
import localdb.JdbcConnection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import omdb_api.OmdbWebServiceXML;
import omdb_api.OmdbWebServiceXMLWithoutYear;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieV2 {
	private String idMovie;
	private String releaseDate;
	private String movieName;
	private String productionBudget;
	private String domesticGross;
	private String worldwideGross;
	private String movieDistributor;
	private String movieGenre;
	private List<String> realisateur = new ArrayList<String>();
	private List<String> acteurs = new ArrayList<String>();
	private List<String> producteur = new ArrayList<String>();;
	private String resume;

	public void setInfoMysqlTable(String movie) throws SQLException {
		ArrayList<String> movieInfo = JdbcConnection.getMovieInfo(movie);
		setIdMovie(movieInfo.get(0));
		setReleaseDate(movieInfo.get(1));
		setMovieName(movieInfo.get(2));
		setProductionBudget(movieInfo.get(3));
		setDomesticGross(movieInfo.get(4));
		setWorldwideGross(movieInfo.get(5));
		setMovieDistributor(movieInfo.get(6));
		setMovieGenre(movieInfo.get(7));
	}

	public void setTheResume(String movie, String year) throws Exception {
		setResume(OmdbWebServiceXML.getMoviePlot(movie, year));
	}
	
	public void setTheResumeWithoutYear(String movie) throws Exception {
		setResume(OmdbWebServiceXMLWithoutYear.getMoviePloto(movie));
	}
	public void setRealisateurActeurProducteur(String movie, String year) throws HttpException {
		setRealisateur(dbpedia_requests.getRealisateur(movie, year));
		setActeurs(dbpedia_requests.getActeurs(movie, year));
		setProducteur(dbpedia_requests.getProducteurs(movie, year));
	}

	public void getMovieByTitle(String movie, String year) throws SQLException, Exception {
		setInfoMysqlTable(movie);
		setResume(movie);
		setRealisateurActeurProducteur(movie, year);
	}
	
	public void displayAllFilmInfoByNameActor(String actor) throws SQLException, Exception {
		List<String> movies = dbpedia_requests.getFilms(actor);
		for (int i = 0; i < movies.size(); i++) {
			// Movie movie = new Movie();
			// movie.getMovieByTitle(movies.get(i));
			// System.out.println(movie.display());

			if (containsSpecialChar(movies.get(i)) == false) {
				// System.err.println("not contains special char ");
				MovieV2 movie = new MovieV2();
				// movie.getMovieByTitle(movies.get(i));
				// System.out.println(movie.display());
				ArrayList<MovieV2> tabs = movie.getMovieInfo(movies.get(i));
				for (int k = 0; k < tabs.size(); k++) {
					System.out.println(tabs.get(k));
				}
			}
			else {
				System.out.println("======================" + movies.get(i)
						+ "  : it contains special char  =====================");
				continue;
			}
		}
	}

	public String displayRealisateur() {
		String s = "[Realisateur : ";
		for (String t : this.realisateur) {
			s += t + ",";
		}
		s += "]";
		return s;
	}

	public String displayActeurs() {
		String s = "[Acteur : ";
		for (String t : this.acteurs) {
			s += t + ",";
		}
		s += "]";
		return s;
	}

	public String displayProducteur() {
		String s = "[producteur : ";
		for (String t : this.producteur) {
			s += t + ",";
		}
		s += "]";
		return s;
	}

	public String display() {
		String s = "[ releaseDate : " + this.releaseDate + "| movieName : " + this.movieName + "| productionBudget : "
				+ this.productionBudget + "| domesticGross : " + this.domesticGross + "| worldwideGross : "
				+ this.worldwideGross + "| movieDistributor : " + this.movieDistributor + "| movieGenre : " + movieGenre
				+ " || " + displayRealisateur() + " || " + displayActeurs() + " || " + displayProducteur();
		return s;
	}

	public boolean containsSpecialChar(String title) {
		String str = title;
		// Specify all posible special charcters
		String specialCharacters = "'";
		boolean found = false;
		for (int i = 0; i < specialCharacters.length(); i++) {
			// Checking if the input string contain any of the specified Characters
			if (str.contains(Character.toString(specialCharacters.charAt(i)))) {
				found = true;
				//System.out.println("String contains Special Characters");
				break;
			}
		}

		if (found == false) {
			//System.out.println("String doesn't have any Special Characters");
		}
		return found;
	}

	public ArrayList<MovieV2> getMovieInfo(String name) throws Exception{
		ArrayList<MovieV2> movies = new ArrayList<MovieV2>();		
		ArrayList<String> id = JdbcConnection.getID(name);
		if (id.size() != 0) {
		ArrayList<ArrayList<String>> tabs = JdbcConnection.getMoviesss(name);
		System.err.println(tabs.size());
		for (int i = 0; i < tabs.size(); i++) {
			MovieV2 movie = new MovieV2();
			for (int j = 0; j < tabs.get(i).size(); j++) {
				ArrayList<String> infoMovie = tabs.get(i);
				movie.setIdMovie(infoMovie.get(0));
				movie.setReleaseDate(infoMovie.get(1));
				movie.setMovieName(infoMovie.get(2));
				movie.setProductionBudget(infoMovie.get(3));
				movie.setDomesticGross(infoMovie.get(4));
				movie.setWorldwideGross(infoMovie.get(5));
				movie.setMovieDistributor(infoMovie.get(6));
				movie.setMovieGenre(infoMovie.get(7));
				String year = yearSpliter(movie.getReleaseDate());
				movie.setTheResume(name,year);
				movie.setRealisateurActeurProducteur(name,year);		
			}
			movies.add(movie);
			}	
		}
		else {
			MovieV2 movie = new MovieV2();
			//System.err.println("========================= hna ============================");
			//movie.setTheResumeWithoutYear(name);
			//movie.setActeurs(DbPediaWithoutYear.getActeurs(name));
			//movie.setRealisateur(DbPediaWithoutYear.getRealisateur(name));
			//movie.setProducteur(DbPediaWithoutYear.getProducteurs(name));			
			//movies.add(movie);		
		}
		return movies;
	}

	public String yearSpliter(String year) {
		String[] tab = year.split("/");
		return tab[2];
	}
	
	
	public void printAllMoviesByActorName(String actor ) throws Exception {
		List<String> movies = dbpedia_requests.getFilms(actor);	
		for (int i = 0; i < movies.size();i++) {
			if (containsSpecialChar(movies.get(i)) == false) {
			ArrayList<MovieV2> moviesInfo = getMovieInfo(movies.get(i));
			for (int j = 0; j < moviesInfo.size(); j++) {				
				System.out.println(moviesInfo.get(j));
			}
			}
			else {
				//System.out.println("==============="+ movies.get(i) + "================ contains special char");
				ArrayList<MovieV2> moviesInfo = getMovieInfo(refactorSpecialChar(movies.get(i)));
				for (int j = 0; j < moviesInfo.size(); j++) {				
					System.out.println(moviesInfo.get(j));
				}
			
			
			
			}	
		}

		
	}
	
	
	
	
	
	
	public String refactorSpecialChar(String info) {
		String x = info.replaceAll("'", "");
		return x;
		
	}
	
	
	
	

}
