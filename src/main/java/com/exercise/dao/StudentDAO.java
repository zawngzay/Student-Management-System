package com.exercise.dao;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import com.exercise.dto.StudentRequestDTO;
import com.exercise.dto.StudentResponseDTO;



@Repository
public class StudentDAO {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public int insert(StudentRequestDTO dto) {
		int result = 0;
		String sql = "insert into student (student_id,student_name,class_name,register_date,status) values(?,?,?,?,?)";
		result = jdbcTemplate.update(sql, dto.getStudentId(), dto.getStudentName(), dto.getClassName(),
				dto.getRegisterDate(), dto.getStatus());
		return result;
	}

	public int update(StudentRequestDTO dto) {
		int result = 0;
		String sql = "update student set student_name=?,class_name=?,register_date=?,status=? where student_id=?";
		result = jdbcTemplate.update(sql,  dto.getStudentName(),  dto.getClassName(), dto.getRegisterDate(),dto.getStatus(), dto.getStudentId());
		return result;
	}

	public int delete(StudentRequestDTO dto) {
		int result = 0;
		String sql = "delete from student where student_id=?";
		
		result = jdbcTemplate.update(sql,  dto.getStudentId());
		return result;
	}

	@SuppressWarnings("unchecked")
	public List<StudentResponseDTO> select(StudentRequestDTO dto) {
		List<StudentResponseDTO> list = new ArrayList<StudentResponseDTO>();

			String sql="select * from student";
			if (!dto.getStudentId().equals("")) {
				String sql1="select * from student where student_id=?";
				return (List<StudentResponseDTO>) jdbcTemplate.query(sql1, (rs, rowNum) -> new StudentResponseDTO(rs.getString("student_id"),
						rs.getString("student_name"), rs.getString("register_date"),rs.getString("status"),rs.getString("class_name")),
						dto.getStudentId());
				
			} else if (!dto.getStudentName().equals("") || !dto.getClassName().equals("")) {
				String sql2="select * from student where student_name=? or class_name=?";
				return (List<StudentResponseDTO>) jdbcTemplate.query(sql2, (rs, rowNum) -> new StudentResponseDTO(rs.getString("student_id"),
						rs.getString("student_name"), rs.getString("register_date"),rs.getString("status"),rs.getString("class_name")),
						dto.getStudentName(),dto.getClassName());
				
			}
			return (List<StudentResponseDTO>) jdbcTemplate.query(sql, (rs, rowNum) -> new StudentResponseDTO(rs.getString("student_id"),
					rs.getString("student_name"), rs.getString("register_date"),rs.getString("status"),rs.getString("class_name")));
			


}
}
