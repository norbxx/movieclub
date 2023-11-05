package lnp.movieclub.movie;

import lnp.movieclub.genre.Genre;
import lnp.movieclub.genre.GenreRepository;
import lnp.movieclub.movie.dto.MovieDto;
import lnp.movieclub.movie.dto.MovieSaveDto;
import lnp.movieclub.storage.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final GenreRepository genreRepository;
    private final FileStorageService fileStorageService;

    public List<MovieDto> findAllPromotedMovies(){
        return movieRepository.findAllByPromotedIsTrue()
                .stream()
                .map(MovieDtoMapper::mapper)
                .toList();
    }

    public Optional<MovieDto> findMovieById(long id){
        return movieRepository.findById(id).map(MovieDtoMapper::mapper);
    }

    public List<MovieDto> findMoviesByGenreName(String genre){
        return movieRepository.findAllByGenre_NameIgnoreCase(genre)
                .stream()
                .map(MovieDtoMapper::mapper)
                .toList();
    }

    public void addMovie(MovieSaveDto movieToSave){
        Movie movie = new Movie();
        movie.setTitle(movieToSave.getTitle());
        movie.setOriginalTitle(movieToSave.getOriginalTitle());
        movie.setPromoted(movieToSave.isPromoted());
        movie.setReleaseYear(movieToSave.getReleaseYear());
        movie.setShortDescription(movieToSave.getShortDescription());
        movie.setYoutubeTrailerId(movieToSave.getYoutubeTrailerId());
        Genre genre = genreRepository.findByNameIgnoreCase(movieToSave.getGenre()).orElseThrow();
        movie.setGenre(genre);
        if (movieToSave.getPoster() != null) {
            String savedFileName = fileStorageService.saveImage(movieToSave.getPoster());
            movie.setPoster(savedFileName);
        }
        movieRepository.save(movie);
    }
}
