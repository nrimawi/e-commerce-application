package edu.birzeit.webservices.webservicesfirstassignment.repository;

import edu.birzeit.webservices.webservicesfirstassignment.entity.Product;
import edu.birzeit.webservices.webservicesfirstassignment.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring recognises the repositories by the fact that they extend one of the predefined Repository interfaces
 * Author: Mohammed Kharma
 */
public interface ProductRepository extends JpaRepository<Product, Long> {

}
