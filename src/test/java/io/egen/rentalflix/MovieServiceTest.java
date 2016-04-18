package io.egen.rentalflix;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Random;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MovieServiceTest {

	MovieService m = new MovieService();

	@Test
	public void createTest() {
		Movie ExpectedOutputOfCreate = new Movie();
		ExpectedOutputOfCreate.setId(5);
		ExpectedOutputOfCreate.setName("Fifth");
		ExpectedOutputOfCreate.setRented(true);

		Movie actualOutputOfCreate = m.create(ExpectedOutputOfCreate);
		assertEquals(ExpectedOutputOfCreate, actualOutputOfCreate);

	}

	@Test
	public void findAllTest() {
		Movie movie;
		Random r = new Random();
		int total = 10+r.nextInt(1000); // Range starts from 10 to 1010
		for(int i=0;i<total;i++){
			movie = new Movie();
			movie.setId(i);
			movie.setName("Dummy name"+i);
			movie.setRented(false);
			m.create(movie);
		}

		int count = m.findAll().size();
		/*
		 * Find all the movies that were inserted
		 * Test should return a count of total
		 */
		assertEquals(count, total);
	}

	@Test
	public void deleteTest() {
		Movie movie;
		Random r = new Random();
		int total = 10+r.nextInt(1000); // Range starts from 10 to 1010
		for(int i=0;i<total;i++){
			movie = new Movie();
			movie.setId(i);
			movie.setName("Dummy name"+i);
			movie.setRented(false);
			m.create(movie);
		}

		int delete_id = total-1;
		m.delete(delete_id);
		int count = m.findAll().size();
		assertEquals(count, total-1);
		List<Movie> list_movie = m.findByName("Dummy name"+delete_id);
		/*
		 * Find the last movie that was deleted
		 * Test should return a count of 0
		 */
		assertEquals(list_movie.size(),0);
	}

	@Test
	public void findByNameTest() {
		Movie movie;
		Random r = new Random();
		int total = 10+r.nextInt(1000); // Range starts from 10 to 1010
		for(int i=0;i<total;i++){
			movie = new Movie();
			movie.setId(i);
			movie.setName("Dummy name"+i);
			movie.setRented(false);
			m.create(movie);
		}
		int find_id = total-1;
		/*
		 * Find the last movie that was inserted
		 * Test should return a count of 1
		 */
		List<Movie> list_movie = m.findByName("Dummy name"+find_id);
		assertEquals(list_movie.size(),1);

		/*
		 * Find the movies that have the name "Dummy name"
		 * Test should return all names = total
		 */
		list_movie = m.findByName("Dummy name");
		assertEquals(list_movie.size(),total);

		/*
		 * Find the movies that have the name "Dummy name"+total
		 * Note : In the for loop for insertion the condition is < and not <=
		 *        so "Dummy name"+total was not inserted
		 * Test should return no names = 0
		 */
		list_movie = m.findByName("Dummy name"+total);
		assertEquals(list_movie.size(),0);

		/*
		 * Find the movies when null is passed as string
		 * Test should return null
		 */
		list_movie = m.findByName(null);
		assertEquals(list_movie.size(),0);

	}

	@Test
	public void rentMovieTest() {
		Movie movie;
		Random r = new Random();
		int total = 10+r.nextInt(1000); // Range starts from 10 to 1010
		for(int i=0;i<total;i++){
			movie = new Movie();
			movie.setId(i);
			movie.setName("Dummy name"+i);
			movie.setRented(false);
			m.create(movie);
		}
		int rentId = r.nextInt(total);
		m.rentMovie(rentId,"Dummy user"+1);
		boolean exceptionThrown = false;
		try {
			m.rentMovie(rentId,"Dummy user"+2);
		}catch(Exception e){
			exceptionThrown = true;
		}
		/*
		 * Rent the movie with rent_id twice
		 * It should throw an exception
		 */
		assertEquals(exceptionThrown,true);

	}

	@Test
	public void updateTest() {
		Movie movie;
		Random r = new Random();
		int total = 10+r.nextInt(1000); // Range starts from 10 to 1010
		for(int i=0;i<total;i++){
			movie = new Movie();
			movie.setId(i);
			movie.setName("Dummy name"+i);
			movie.setRented(false);
			m.create(movie);
		}
		int updateId = r.nextInt(total);
		movie = new Movie();
		movie.setId(updateId);
		movie.setName("Dummy name"+total);
		movie.setRented(false);
		m.update(movie);
		List<Movie> list_movie = m.findByName("Dummy name"+total);
		/*
		 * Update the movie with updateId to a different name
		 * Check if it gets updated
		 */
		assertEquals(list_movie.size(),1);
		assertEquals(updateId , list_movie.get(0).getId());
	}

}
