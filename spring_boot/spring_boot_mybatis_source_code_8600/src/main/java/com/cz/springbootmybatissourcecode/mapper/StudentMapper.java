package com.cz.springbootmybatissourcecode.mapper;

import com.cz.springbootmybatissourcecode.entity.Student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper {

    int insertStudent(Student student);
}
