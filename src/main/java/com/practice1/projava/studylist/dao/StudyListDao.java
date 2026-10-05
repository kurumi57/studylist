package com.practice1.projava.studylist.dao;

import com.practice1.projava.studylist.model.StudyItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 学習記録の登録・取得・更新・削除など、
 * studylistテーブルへのデータベース操作を担当するクラス
 */
@Service
public class StudyListDao {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    StudyListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * 学習記録をstudylistテーブルに登録する
     *
     * @param studyItem　登録する学習記録
     */
    public void add(StudyItem studyItem) {
        SqlParameterSource param = new BeanPropertySqlParameterSource(studyItem);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbcTemplate).withTableName("studylist");
        insert.execute(param);
    }

    /**
     * 指定されたユーザーの学習記録をstudylistテーブルから取得する
     *
     * @param username　学習記録を取得するユーザー名
     * @return　指定されたユーザーの学習記録一覧
     */
    public List<StudyItem> findByUsername(String username) {
        String query = "SELECT * FROM studylist WHERE username = ?";
        List<Map<String, Object>> result = jdbcTemplate.queryForList(query, username);
        List<StudyItem> studyItems = result.stream().map(
                (Map<String, Object> row) -> new StudyItem(row.get("id").toString(),
                        LocalDate.parse(row.get("date").toString()), row.get("content").toString(),
                        Double.parseDouble(row.get("time").toString()),
                        row.get("username") == null ? null : row.get("username").toString())).toList();

        return studyItems;
    }

    /**
     * 指定されたIDとユーザー名に一致する学習記録をstudylistテーブルから削除する
     *
     * @param id　削除する学習記録のID
     * @param username　学習記録を所有するユーザー名
     * @return　削除されたレコード数
     */
    public int delete(String id, String username) {
        int number = jdbcTemplate.update("DELETE FROM studylist WHERE id = ? AND username = ?", id, username);

        return number;
    }

    /**
     * 指定された学習記録をstudylistテーブルで更新する
     *
     * @param studyItem　更新する学習記録
     * @return　更新されたレコード数
     */
    public int update(StudyItem studyItem) {
        int number = jdbcTemplate.update(
                "UPDATE studylist SET date = ?, content = ?, time = ? WHERE id = ? AND username = ?", studyItem.date(),
                studyItem.content(), studyItem.time(), studyItem.id(), studyItem.username());

        return number;
    }
}