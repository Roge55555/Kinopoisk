package com.itech.kinopoisk.repository.specification;

import com.itech.kinopoisk.entity.Film;
import com.itech.kinopoisk.entity.Film_;
import com.itech.kinopoisk.model.filter.FilmFilterRequest;
import org.springframework.data.jpa.domain.Specification;

import static org.springframework.data.jpa.domain.Specification.where;

public class FilmSpecification {

    private static Specification<Film> hasNameRu(String nameRu) {
        if (nameRu == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(Film_.NAME_RU)), nameRu);
    }

    private static Specification<Film> hasNameEn(String nameEn) {
        if (nameEn == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(Film_.NAME_EN)), nameEn);
    }

    private static Specification<Film> hasYear(String year) {
        if (year == null)
            return null;

        return (root, query, builder) ->
                builder.equal((root.get(Film_.YEAR)), year);
    }

    private static Specification<Film> hasRating(Double rating) {
        if (rating == null)
            return null;

        return (root, query, builder) ->
                builder.greaterThanOrEqualTo((root.get(Film_.RATING)), rating);
    }

    public static Specification<Film> getSpecification(final FilmFilterRequest request) {
        return (root, query, builder) -> {
            query.distinct(true);
            return where(hasNameRu(request.getNameRu()))
                    .and(hasNameEn(request.getNameEn()))
                    .and(hasYear(request.getYear()))
                    .and(hasRating(request.getRating()))
                    .toPredicate(root, query, builder);
        };
    }
}
