package com.practice1.projava.studylist;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class StudyListDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    StudyListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(HomeController.StudyItem studyItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(studyItem);
        SimpleJdbcInsert insert =
                new SimpleJdbcInsert(jdbcTemplate)
                        .withTableName("studylist");
        insert.execute(param);
    }
    public List<HomeController.StudyItem> findAll() {
        String query = "SELECT * FROM studylist";

        List<Map<String,Object>> result = jdbcTemplate.queryForList(query);
        List<HomeController.StudyItem> studyItems = result.stream()
                .map((Map<String, Object> row) -> new HomeController.StudyItem(
                        row.get("id").toString(),
                        LocalDate.parse(row.get("date").toString()),
                        row.get("content").toString(),
                        Double.parseDouble(row.get("time").toString())
                        ))
                        .toList();
        return studyItems;
    }
    public int delete(String id) {
        int number = jdbcTemplate.update("DELETE FROM studylist WHERE id = ?", id);
        return number;
    }
    public int update(HomeController.StudyItem studyItem) {
        int number = jdbcTemplate.update(
                "UPDATE studylist SET date = ?, content = ?, time = ? WHERE id = ?",
                studyItem.date(),
                studyItem.content(),
                studyItem.time(),
                studyItem.id());
        return number;
    }
}