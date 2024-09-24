package com.supershy.moviepedia.like.service;

import com.supershy.moviepedia.common.exception.NotFoundException;
import com.supershy.moviepedia.like.dto.LikedMovieDto;
import com.supershy.moviepedia.like.dto.LikedMovieListDto;
import com.supershy.moviepedia.like.entity.Like;
import com.supershy.moviepedia.like.repository.LikeRepository;
import com.supershy.moviepedia.member.entity.Member;
import com.supershy.moviepedia.movie.entity.Movie;
import com.supershy.moviepedia.movie.repository.MovieRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;
    private final MovieRepository movieRepository;

    @Override
    public String insertLike(Member member, Long movieId) {
        Movie movie = movieRepository.findById(movieId).orElseThrow(() -> new NotFoundException("찾으시는 영화가 없습니다."));
        Long memberId = member.getMemberId();
        if (!likeRepository.existsByMember_MemberIdAndMovie_MovieId(memberId, movieId)) {
            Like like = new Like(member, movie);
            likeRepository.save(like);
            return "좋아요가 등록되었습니다.";
        } else {
            deleteLike(memberId, movieId);
            return "좋아요가 취소되었습니다.";
        }
    }

    private void deleteLike(Long memberId, Long movieId) {
        likeRepository.deleteByMember_MemberIdAndMovie_MovieId(memberId, movieId);
    }

    @Override
    public LikedMovieListDto getLikedMovies(int page, int size, Long memberId) {
        Sort sortObj = Sort.by(Sort.Direction.ASC, "title");
        Pageable pageable = PageRequest.of(page, size, sortObj);
        Page<Movie> foundMovieList = likeRepository.findAllByMember_MemberId(memberId, pageable);

        List<LikedMovieDto> likedMovieDtoList = foundMovieList.stream()
                .map(LikedMovieDto::new)
                .collect(Collectors.toList());
        return new LikedMovieListDto(likedMovieDtoList,(int) foundMovieList.getTotalElements());
    }

    @Override
    public String getLike(Member member, Long movieId) {
        Long memberId = member.getMemberId();
        if (likeRepository.existsByMember_MemberIdAndMovie_MovieId(memberId, movieId)) {
            return "좋아요한 영화입니다.";
        } else {
            return "좋아요하지 않은 영화입니다..";
        }
    }
}

