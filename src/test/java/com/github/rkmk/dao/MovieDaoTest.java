package com.github.rkmk.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.github.rkmk.DaoTest;
import com.github.rkmk.GenericFolder;
import com.github.rkmk.mapper.BigDecimalMapperFactory;
import com.github.rkmk.model.Actor;
import com.github.rkmk.model.AssistantDirector;
import com.github.rkmk.model.Director;
import com.github.rkmk.model.Movie;
import com.github.rkmk.model.Song;
import com.google.common.collect.Lists;

public class MovieDaoTest extends DaoTest {
     private Dao dao;

    @Before
    public void before() {
        dao = handle.attach(Dao.class);
    }

    @Test
    public void shouldGetMovieWithSongs() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").build();
        Song song1 = Song.builder().songId(1).songName("Anbe Anbe").movieId(1).build();
        Song song2 = Song.builder().songId(2).songName("Columbus Columbus").movieId(1).build();
        Song song3 = Song.builder().songId(3).songName("Enake Enaka").movieId(1).build();
        Song song4 = Song.builder().songId(4).songName("Poovukul").movieId(1).build();

        insert(jeans);
        insert(song1, song2,song3, song4);

        Movie movie = dao.getMovie(1);

        assertEquals(new Integer(1), movie.getMovieId());
        assertEquals("Jeans", movie.getMovieName());
        assertEquals(4, movie.getSongs().size());
        List<String> songNames = Lists.newArrayList();//movie.getSongs().stream().map(Song::getSongName).collect(toList());
        assertTrue(songNames.containsAll(Arrays.asList("Anbe Anbe", "Columbus Columbus", "Enake Enaka", "Poovukul")));
    }

    @Test
    public void shouldReturnNullWhenNoRecordIsFound() throws Exception {
        Movie movie = dao.getMovie(1);

        assertNull(movie);
    }

    @Test
    public void shouldGetMultipleMovies() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").build();
        Movie bombay = Movie.builder().movieId(2).movieName("Bombay").build();
        Song song1 = Song.builder().songId(1).songName("Anbe Anbe").movieId(1).build();
        Song song2 = Song.builder().songId(2).songName("Columbus Columbus").movieId(1).build();
        Song song3 = Song.builder().songId(3).songName("Enake Enaka").movieId(1).build();
        Song song4 = Song.builder().songId(3).songName("Kannalane").movieId(2).build();
        Song song5 = Song.builder().songId(4).songName("Uyire Uyire").movieId(2).build();

        insert(jeans, bombay);
        insert(song1, song2, song3, song4, song5);

        List<Movie> movies = dao.getAllMovies();

        assertEquals(2, movies.size());
        assertEquals("Jeans",movies.get(0).getMovieName());
        assertEquals(3, movies.get(0).getSongs().size());
        List<String> songNames = Lists.newArrayList();//movies.get(0).getSongs().stream().map(Song::getSongName).collect(toList());
        assertTrue(songNames.containsAll(Arrays.asList("Anbe Anbe", "Columbus Columbus", "Enake Enaka")));

        assertEquals("Bombay", movies.get(1).getMovieName());
        assertEquals(2, movies.get(1).getSongs().size());
        songNames = Lists.newArrayList();//movies.get(1).getSongs().stream().map(Song::getSongName).collect(toList());
        assertTrue(songNames.containsAll(Arrays.asList("Kannalane", "Uyire Uyire")));
    }


    @Test
    public void shouldGetMovieWithSongsAndActors() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").build();
        Song song1 = Song.builder().songId(1).songName("Anbe Anbe").movieId(1).build();
        Song song2 = Song.builder().songId(2).songName("Columbus Columbus").movieId(1).build();
        Actor prashanth = Actor.builder().actorId(1).actorName("Prashanth").movieId(1).build();
        Actor aishwarya = Actor.builder().actorId(2).actorName("Aishwarya").movieId(1).build();

        insert(jeans);
        insert(song1, song2);
        insert(prashanth, aishwarya);

        Movie movie = dao.getMovie(1);

        assertEquals(2, movie.getSongs().size());
        List<String> songNames = Lists.newArrayList();// movie.getSongs().stream().map(Song::getSongName).collect(toList());
        assertTrue(songNames.containsAll(Arrays.asList("Anbe Anbe", "Columbus Columbus")));
        assertEquals(2, movie.getActors().size());
        List<String> actorNames = Lists.newArrayList();// movie.getActors().stream().map(Actor::getActorName).collect(toList());
        assertTrue(actorNames.containsAll(Arrays.asList("Prashanth", "Aishwarya")));
    }

    @Test
    public void shouldGetMovieAlongWithDirector() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").build();
        Director shankar = Director.builder().directorId(1).directorName("Shankar").movieId(1).build();

        insert(jeans);
        insert(shankar);

        Movie movie = dao.getMovie(1);

        assertEquals("Jeans", movie.getMovieName());
        assertEquals(new Integer(1), movie.getDirector().getDirectorId());
        assertEquals("Shankar", movie.getDirector().getDirectorName());
    }

    @Test
    public void shouldGetMovieAlongWithDirectorAndAssistantDirector() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").build();
        Director shankar = Director.builder().directorId(1).directorName("Shankar").movieId(1).build();
        AssistantDirector assistantDirector1 = AssistantDirector.builder().id(1).name("Name1").directorId(1).build();
        AssistantDirector assistantDirector2 = AssistantDirector.builder().id(2).name("Name2").directorId(1).build();

        insert(jeans);
        insert(shankar);
        insert(assistantDirector1, assistantDirector2);

        List<Movie> movies = dao.getMovieDirector();

        assertEquals(1, movies.size());
        Director director = movies.get(0).getDirector();
        assertEquals("Shankar", director.getDirectorName());
        assertEquals(new Integer(1), director.getDirectorId());
        assertEquals(2, director.getAssistantDirectors().size());
    }

    @Test
    public void shouldTakeOverriddenMapperFactoryIfSpecified() throws Exception {
        Movie jeans = Movie.builder().movieId(1).movieName("Jeans").ratings(BigDecimal.ONE).build();
        insert(jeans);

        GenericFolder.register(new BigDecimalMapperFactory());

        Movie movie = dao.getMovie(1);

        assertEquals(BigDecimal.TEN, movie.getRatings());
    }
}
