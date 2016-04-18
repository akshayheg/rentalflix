package io.egen.rentalflix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MovieService implements IFlix {
	List<Movie> moviesInStore= new ArrayList<Movie>();
	
	// HashMap to store mapping of users to movies
	HashMap<Integer,String> rentedMap = new HashMap<Integer,String>();
	
	/**
	 * Finds all available movies in the movie store
	 * @return list of movies or empty list
	 */	
	public synchronized List<Movie> findAll() {
		return moviesInStore;		
	}

	/**
	 * Finds all movies in the movie store which contains <strong>name</strong> in the title
	 * @param name String
	 * @return list of movies or empty list
	 */
	public synchronized List<Movie> findByName(String name) {
		if(name!=null)
		{
			String movieName;
			List<Movie> moviesByName= new ArrayList<Movie>();
			for(Movie m: moviesInStore)
			{
				movieName= m.getName();
				if(movieName.contains(name))
					moviesByName.add(m);					
			}
			return moviesByName;
		}		
		return new ArrayList<Movie>();
	}

	/**
	 * Create a new movie in the movie store
	 * @param movie
	 * @return Movie
	 */	

	public synchronized Movie create(Movie movie) {
		if(movie!=null)		
		{			
			moviesInStore.add(movie);
			return movie;
		}
		return new Movie();
	}



	/**
	 * Update an existing movie
	 * @param movie
	 * @return updated movie or throws <strong>IllegalArgumentException</strong> if movie with this id is not found
	 */	
	public synchronized Movie update(Movie movie) throws IllegalArgumentException{

		if(movie!=null)
		{

			int id= movie.getId();
			for(Movie m: moviesInStore)
			{
				if(id==m.getId())
				{
					moviesInStore.remove(m);
					moviesInStore.add(movie);
					return movie;
				}				
			}	
			throw new IllegalArgumentException("Movie store does not contain this movie.");//TODO
		}
		return null;
	}

	/**
	 * Delete an existing movie 
	 * @param id
	 * @return deleted movie or throws <strong>IllegalArgumentException</strong> if movie with this id is not found
	 */	
	public synchronized Movie delete(int id) {
		for(Movie m: moviesInStore)
		{
			if(id==m.getId())
			{
				moviesInStore.remove(m);
				return m;
			}
		}
		throw new IllegalArgumentException("Movie store does not contain movie with given id.");//TODO
	}	

	/**
	 * Rent the movie with movieId to the <strong>user</strong>.
	 * Make sure this movie is not rented already. 
	 * If it is already rented, throw <strong>IllegalArgumentException</strong>
	 * @param movieId
	 * @param user
	 * @return true: if movie can be rented, false otherwise
	 */		
	public synchronized boolean rentMovie(int movieId, String user) throws IllegalArgumentException{

		for(Movie m: moviesInStore)
		{
			if(m.getId()==movieId)
			{
				if(!m.isRented())
				{	
					rentedMap.put(movieId, user);
					m.setRented(true);
					return true;
				}
				else
				{
					throw new IllegalArgumentException("Movie with id "+movieId+" is already rented.");					
				}
			}
		}
		return false;		
	}

	public static void main(String args[])
	{		
		MovieService ob= new MovieService();

		Movie m1= new Movie();
		m1.setId(1);
		m1.setName("Fan");
		Movie movie= ob.create(m1);

		Movie m2= new Movie();
		m2.setId(2);
		m2.setName("300");
		ob.create(m2);

		Movie m3= new Movie();
		m3.setId(3);
		m3.setName("300 matrix");
		ob.create(m3);

		Movie m4= new Movie();
		m4.setId(4);
		m4.setName("300 women");
		ob.create(m4);
		List<Movie> allMovies= ob.findAll();

		List<Movie> moviesByName= ob.findByName("300");

		ob.rentMovie(4, "Akshay");
		//ob.rentMovie(4, "Andie");

		//ob.delete(6);

		allMovies= ob.findAll();

		Movie m5= new Movie();
		m5.setId(2);
		m5.setName("300 hot women");
		//ob.create(m5);
		//ob.update(m5);
		allMovies= ob.findAll();
		System.out.println();
	}
}