package cp213;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Utilities for working with Movie objects.
 *
 * @author your name, id, email here
 * @version 2024-09-01
 */
public class MovieUtilities {

    /**
     * Counts the number of movies in each genre given in Movie.GENRES. An empty
     * movies list should produce a count array of: [0,0,0,0,0,0,0,0,0,0,0]
     *
     * @param movies List of movies.
     * @return Number of genres across all Movies. One entry for each genre in the
     *         Movie.GENRES array.
     */
    public static int[] genreCounts(final ArrayList<Movie> movies) {
    	
    	int[] count = new int[11];
    	
    	for (int i = 0; i < movies.size(); i++) {
    		int genreIndex = movies.get(i).getGenre();
    		
    		count[genreIndex]++;
    	}

	return count;
    }

    /**
     * Creates a Movie object by requesting data from a user. Uses the format:
     *
     * <pre>
    Title:
    Year:
    Director:
    Rating:
    Genres:
    0: science fiction
    1: fantasy
    ...
    10: mystery

    Enter a genre number:
     * </pre>
     *
     * @param keyboard A keyboard (System.in) Scanner.
     * @return A Movie object.
     */
    public static Movie getMovie(final Scanner keyboard) {

    	System.out.println("Title: ");
    	String title = keyboard.nextLine();
    	System.out.println("Year: ");
    	int year = keyboard.nextInt();
    	keyboard.nextLine();
    	System.out.println("Director: ");
    	String director = keyboard.nextLine();
    	System.out.println("Rating: ");
    	double rating = -1.0;
    	while (true) {
    	    if (keyboard.hasNextDouble()) {
    	        rating = keyboard.nextDouble();
    	        keyboard.nextLine(); 
    	        if (rating >= 0.0 && rating <= 10.0) {
    	            break; 
    	        } else {
    	            System.out.println("Rating: ");
    	        }
    	    } else {
    	        System.out.println("Rating: ");
    	        keyboard.next();
    	    }
    	}
    	System.out.println("Genres: ");
    	System.out.println(Movie.genresMenu());
    	System.out.println("Enter a genre number: ");
    	int genre = -1;
    	while (true) {
    	    if (keyboard.hasNextInt()) {
    	        genre = keyboard.nextInt();
    	        keyboard.nextLine(); 
    	        if (genre >= 0 && genre < Movie.GENRES.length) {
    	            break; 
    	        } else {
    	            System.out.println("Genre: ");
    	        }
    	    } else {
    	        System.out.println("Genre: ");
    	        keyboard.next(); 
    	    }
    	}

    	return new Movie(title, year, director, rating, genre);
    	
    
	
    }

    /**
     * Creates a list of Movies whose genre is equal to the genre parameter.
     *
     * @param movies List of movies.
     * @param genre  Genre to compare against.
     * @return List of movies of genre.
     */
    public static ArrayList<Movie> getByGenre(final ArrayList<Movie> movies, final int genre) {
    
    ArrayList<Movie> movieList = new ArrayList<>();
    
    if (genre < 0 || genre >= Movie.GENRES.length) {
    	return movieList;
    }

    
	for (int i = 0; i < movies.size(); i++) {
		int currGenre = movies.get(i).getGenre();
		if (currGenre == genre) {
			movieList.add(movies.get(i));
			
		}
	}
	
	
	return movieList;
    }

    /**
     * Creates a list of Movies whose ratings are equal to or higher than rating.
     *
     * @param movies List of movies.
     * @param rating Rating to compare against.
     * @return List of movies of rating or higher.
     */
    public static ArrayList<Movie> getByRating(final ArrayList<Movie> movies, final double rating) {

	ArrayList<Movie> movieList = new ArrayList<>();
	
	for (int i = 0; i < movies.size(); i++) {
		double currRating = movies.get(i).getRating();
		if (currRating >= rating) {
			movieList.add(movies.get(i));
		}
	}

	return movieList;
    }

    /**
     * Creates a list of Movies from a particular year.
     *
     * @param movies List of movies.
     * @param year   Year to compare against.
     * @return List of movies of year.
     */
    public static ArrayList<Movie> getByYear(final ArrayList<Movie> movies, final int year) {

	ArrayList<Movie> movieList = new ArrayList<>();
	
	for (int i = 0; i < movies.size(); i++) {
		int currYear = movies.get(i).getYear();
		if (currYear == year) {
			movieList.add(movies.get(i));
		}
	}
	return movieList;
    }

    /**
     * Asks a user to select a genre from a list of genres displayed by calling
     * Movie.genresMenu() and returns an integer genre code. The genre must be a
     * valid index to an item in Movie.GENRES.
     *
     * @param keyboard A keyboard (System.in) Scanner.
     * @return An integer genre code.
     */
    public static int readGenre(final Scanner keyboard) {

	System.out.println(Movie.genresMenu());
	System.out.println("Enter a genre: ");
	String genre = keyboard.nextLine().toLowerCase();
	
	for (int i = 0; i < Movie.GENRES.length; i++) {
		if (Movie.GENRES[i].equalsIgnoreCase(genre)) {
			return i;
		}
	}
	
	System.out.println("Invalid genre entered. Please try again.");
	return readGenre(keyboard);
	
    }

    /**
     * Creates and returns a Movie object from a line of formatted string data.
     *
     * @param line A vertical bar-delimited line of movie data in the format
     *             title|year|director|rating|genre
     * @return The data from line as a Movie object.
     */
    public static Movie readMovie(final String line) {

	String[] attributes = line.split("\\|");

		
		String title = attributes[0];
		int year = Integer.parseInt(attributes[1]);
		String director = attributes[2];
		double rating = Double.parseDouble(attributes[3]);
		int genre = Integer.parseInt(attributes[4]);
		
		Movie movie = new Movie(title, year, director, rating, genre);
		
		return movie;
	}
	
    

    /**
     * Reads a list of Movies from a file.
     *
     * @param fileIn A Scanner of a Movie data file in the format
     *               title|year|director|rating|genre
     * @return A list of Movie objects.
     */
    public static ArrayList<Movie> readMovies(final Scanner fileIn) {

    ArrayList<Movie> movies = new ArrayList<>();
    
    while (fileIn.hasNextLine()) {
    	String line = fileIn.nextLine();
    	
    	Movie movie = readMovie(line);
    	
    	movies.add(movie);
    	
    	
    }

	return movies;
    }

    /**
     * Writes the contents of a list of Movie to a PrintStream.
     *
     * @param movies A list of Movie objects.
     * @param ps     Output PrintStream.
     */
    public static void writeMovies(final ArrayList<Movie> movies, PrintStream ps) {

	for (Movie movie : movies) {
		ps.println(movie);
	}

    }

}
