package com.cz.springbootmybatissourcecode.service.impl;

import com.cz.springbootmybatissourcecode.entity.Student;
import com.cz.springbootmybatissourcecode.mapper.StudentMapper;
import com.cz.springbootmybatissourcecode.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public int insertStudent(Student student) {
        return this.studentMapper.insertStudent(student);
    }
}
