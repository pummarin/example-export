package com.prior.training.export.repository;

import com.prior.training.export.entity.FilmEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FilmRepository {

    private JdbcTemplate jdbcTemplate;

    public FilmRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<FilmEntity> getOnly20Film(){

        StringBuilder sb = new StringBuilder();
        sb.append("  select film_id,  ");
        sb.append("         title,  ");
        sb.append("         description,  ");
        sb.append("         release_year,  ");
        sb.append("         rental_rate,  ");
        sb.append("         rental_duration,  ");
        sb.append("         length,  ");
        sb.append("         rating  ");
        sb.append("  from film where film_id < 20  ");

        return this.jdbcTemplate.query(sb.toString(), new RowMapper<FilmEntity>() {
            @Override
            public FilmEntity mapRow(ResultSet resultSet, int i) throws SQLException {
                FilmEntity r = new FilmEntity();
                int cols = 1 ;
                r.setFilmID(resultSet.getLong(cols++));
                r.setTitle(resultSet.getString(cols++));
                r.setDescription(resultSet.getString(cols++));
                r.setReleaseYear(resultSet.getLong(cols++));
                r.setRentalRate(resultSet.getDouble(cols++));
                r.setRentalDuration(resultSet.getLong(cols++));
                r.setLength(resultSet.getLong(cols++));
                r.setRating(resultSet.getString(cols++));
                return r;
            }
        });

    }
}
