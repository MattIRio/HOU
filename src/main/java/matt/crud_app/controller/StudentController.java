package matt.crud_app.controller;

import matt.crud_app.model.Student;
import matt.crud_app.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
    @RequestMapping("/student")
    public class StudentController {
        @Autowired
        private StudentService studentService;

        @PostMapping("/add")
        public String add(@RequestBody Student student){
            studentService.saveStudent(student);
            return "student added";
        }

    @GetMapping("")
    public String page(){
        return "student_form.html";
    }

        @GetMapping("/getAll")
    public List<Student> getAllStudents(){
            return studentService.getAllStudents();
        }

        @DeleteMapping("/delete/{id}")
        public String deleteByID(@PathVariable Integer id){
                studentService.deleteStudent(id);
                return "student deleted";
    }
}
