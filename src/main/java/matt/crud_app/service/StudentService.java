package matt.crud_app.service;

import matt.crud_app.model.Student;

import java.util.List;

public interface StudentService {
    public Student saveStudent(Student student);
    public List<Student> getAllStudents();
    public void deleteStudent(int studentID);
}
