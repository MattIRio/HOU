package matt.crud_app.service;

import matt.crud_app.model.Student;
import matt.crud_app.repository.StudentReppository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImplementation implements StudentService{
    @Autowired
    private StudentReppository studentReppository;


    @Override
    public Student saveStudent(Student student) {
        return studentReppository.save(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentReppository.findAll();
    }

    @Override
    public void deleteStudent(int studentID) {
        Optional<Student> studentOptional = studentReppository.findById(studentID);
        studentReppository.delete(studentOptional.get());
    }


}
