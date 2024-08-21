package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.dto.MovieDTO;
import com.devsuperior.dsmovie.entities.MovieEntity;
import com.devsuperior.dsmovie.repositories.MovieRepository;
import com.devsuperior.dsmovie.services.exceptions.DatabaseException;
import com.devsuperior.dsmovie.services.exceptions.ResourceNotFoundException;
import com.devsuperior.dsmovie.tests.MovieFactory;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class MovieServiceTests {
	
	@InjectMocks
	private MovieService movieService;

	@Mock
	private MovieRepository movieRepository;

	private Long existingMovieId, nonExistingMovieId, dependedMovieId;
	private MovieEntity movie;
	private MovieDTO movieDTO;
	private PageImpl<MovieEntity> moviePage;

	@BeforeEach()
	void setUp() throws Exception {
		existingMovieId = 1L;
		nonExistingMovieId = 2L;
		dependedMovieId = 3L;

		movie = MovieFactory.createMovieEntity();
		movieDTO = new MovieDTO(movie);
		moviePage = new PageImpl<>(List.of(movie));

		Mockito.when(movieRepository.findById(existingMovieId)).thenReturn(Optional.of(movie));
		Mockito.when(movieRepository.findById(nonExistingMovieId)).thenReturn(Optional.empty());
		Mockito.when(movieRepository.searchByTitle(any(), any(Pageable.class))).thenReturn(moviePage);
		Mockito.when(movieRepository.getReferenceById(existingMovieId)).thenReturn(movie);
		Mockito.when(movieRepository.getReferenceById(dependedMovieId)).thenReturn(movie);
		Mockito.when(movieRepository.getReferenceById(nonExistingMovieId)).thenThrow(EntityNotFoundException.class);
		Mockito.when(movieRepository.save(any())).thenReturn(movie);
		Mockito.doNothing().when(movieRepository).deleteById(existingMovieId);
		Mockito.when(movieRepository.existsById(existingMovieId)).thenReturn(true);
		Mockito.when(movieRepository.existsById(nonExistingMovieId)).thenReturn(false);
		Mockito.when(movieRepository.existsById(dependedMovieId)).thenReturn(true);
		Mockito.doThrow(DataIntegrityViolationException.class).when(movieRepository).deleteById(dependedMovieId);

	}
	
	@Test
	public void findAllShouldReturnPagedMovieDTO() {
		Pageable pageable = PageRequest.of(0, 12);
		Page<MovieDTO> result = movieService.findAll(movie.getTitle(), pageable);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getSize(), 1);
		Assertions.assertEquals(result.getContent().getFirst().getTitle(), movie.getTitle());
	}
	
	@Test
	public void findByIdShouldReturnMovieDTOWhenIdExists() {
		MovieDTO result = movieService.findById(existingMovieId);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getId(), existingMovieId);
		Mockito.verify(movieRepository, Mockito.times(1)).findById(existingMovieId);
	}
	
	@Test
	public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			movieService.findById(nonExistingMovieId);
		});

		Mockito.verify(movieRepository, Mockito.times(1)).findById(nonExistingMovieId);
	}
	
	@Test
	public void insertShouldReturnMovieDTO() {
		MovieDTO result = movieService.insert(movieDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getTitle(), movieDTO.getTitle());
		Mockito.verify(movieRepository, Mockito.times(1)).save(any());
	}
	
	@Test
	public void updateShouldReturnMovieDTOWhenIdExists() {
		movie.setTitle("NEW_TITLE");
		MovieDTO updatedMovieDTO = new MovieDTO(movie);
		MovieDTO result = movieService.update(existingMovieId, updatedMovieDTO);

		Assertions.assertNotNull(result);
		Assertions.assertEquals(result.getTitle(), "NEW_TITLE");
		Mockito.verify(movieRepository, Mockito.times(1)).save(movie);
	}
	
	@Test
	public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			movieService.update(nonExistingMovieId, movieDTO);
		});
		Mockito.verify(movieRepository, Mockito.times(1)).getReferenceById(nonExistingMovieId);
	}
	
	@Test
	public void deleteShouldDoNothingWhenIdExists() {
		Assertions.assertDoesNotThrow(() -> {
			movieService.delete(existingMovieId);
		});
		Mockito.verify(movieRepository, Mockito.times(1)).existsById(existingMovieId);
		Mockito.verify(movieRepository, Mockito.times(1)).deleteById(existingMovieId);
	}
	
	@Test
	public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			movieService.delete(nonExistingMovieId);
		});
		Mockito.verify(movieRepository, Mockito.times(1)).existsById(nonExistingMovieId);
	}
	
	@Test
	public void deleteShouldThrowDatabaseExceptionWhenDependentId() {
		Assertions.assertThrows(DatabaseException.class, () -> {
			movieService.delete(dependedMovieId);
		});
		Mockito.verify(movieRepository, Mockito.times(1)).existsById(dependedMovieId);
		Mockito.verify(movieRepository, Mockito.times(1)).deleteById(dependedMovieId);
	}
}
