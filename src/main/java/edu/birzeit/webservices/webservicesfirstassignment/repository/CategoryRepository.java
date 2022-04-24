package edu.birzeit.webservices.webservicesfirstassignment.repository;

import edu.birzeit.webservices.webservicesfirstassignment.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
