package com.ureca.picky_be.base.business.movie.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GetMovieDetailResp(
        @JsonProperty("movie_info") MovieInfo movieInfo,
        @JsonProperty("trailer") String trailer,
        @JsonProperty("ost") String ost,
        @JsonProperty("movie_behind_videos") List<String> movieBehindVideos,
        @JsonProperty("like") boolean like,
        @JsonProperty("rating") double rating,
        @JsonProperty("streaming_platform") StreamingPlatform streamingPlatform,
        @JsonProperty("linereviewCount") Long linereviewCount
) {
    public record MovieInfo(
            @JsonProperty("id") Long id,
            @JsonProperty("title") String title,
            @JsonProperty("release_date") Date releaseDate,
            @JsonProperty("poster_path") String posterUrl,
            @JsonProperty("backdrop_path") String backdropUrl,
            @JsonProperty("overview") String plot,
            @JsonProperty("runtime") int runtime,
            @JsonProperty("genres") List<GenreInfo> genres,
            @JsonProperty("credits") Credits credits
    ){
        public record GenreInfo(
                @JsonProperty("id") Long id
        ) {}

        @JsonIgnoreProperties(ignoreUnknown = true)
        public record Credits(
                List<Cast> cast,
                List<Crew> crew
        ) {
            public record Cast(
                    @JsonProperty("id") Long id,
                    @JsonProperty("character") String role,
                    @JsonProperty("name") String name,
                    @JsonProperty("profile_path") String profileUrl
            ) {}

            public record Crew(
                    @JsonProperty("id") Long id,
                    @JsonProperty("job") String job,
                    @JsonProperty("name") String name,
                    @JsonProperty("profile_path") String profileUrl
            ) {}

            public List<Crew> getDirectingCrew() {
                return crew.stream()
                        .filter(c -> "Director".equals(c.job))
                        .toList();
            }
        }

    }

    public record StreamingPlatform(
            boolean netflix,
            boolean disney,
            boolean watcha,
            boolean wavve,
            boolean tving,
            boolean coupang
    ){}
}