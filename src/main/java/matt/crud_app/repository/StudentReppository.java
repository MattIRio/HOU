package matt.crud_app.repository;

import matt.crud_app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentReppository extends JpaRepository<Student, Integer>{


}
