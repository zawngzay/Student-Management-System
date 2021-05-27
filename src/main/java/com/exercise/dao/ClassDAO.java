package com.exercise.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.exercise.dto.ClassRequestDTO;
import com.exercise.dto.ClassResponseDTO;

@Repository
public class ClassDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(ClassRequestDTO dto) {
		int result = 0;
		String sql = "insert into class (id,name) values(?,?)";
		result = jdbcTemplate.update(sql, dto.getId(), dto.getName());
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ClassResponseDTO> select(ClassRequestDTO dto) {

		String sql = "select * from class";
		if (!dto.getId().equals("") || !dto.getName().equals("")) {
			String sql1 = "select * from class where id=? or name=?";
			return  (List<ClassResponseDTO>) jdbcTemplate.query(sql1,
					(rs, rowNum) -> new ClassResponseDTO(rs.getString("id"), rs.getString("name")), dto.getId() ,dto.getName());
		}
		return (List<ClassResponseDTO>) jdbcTemplate.query(sql,
				(rs, rowNum) -> new ClassResponseDTO(rs.getString("id"), rs.getString("name")));

	}
}
