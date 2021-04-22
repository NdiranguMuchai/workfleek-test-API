package com.comulynx.wallet.rest.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.comulynx.wallet.rest.api.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Optional<Customer> findByCustomerId(String customerId);

	// TODO : Implement the query and function below to delete a customer using Customer Id
	@Query("delete from Customer c where c.customerId =: customerId")
	void deleteCustomerByCustomerId(@Param("customerId") String customerId);

	// TODO : Implement the query and function below to update customer firstName using Customer Id
	@Modifying
	@Query("update Customer c set c.firstName =:firstName where  c.customerId= :customerId")
	int updateCustomerByCustomerId(@Param("firstName") String firstName, @Param("customerId") String customerId);

	// TODO : Implement the query and function below and to return all customers whose Email contains  'gmail'
	@Query("select c from Customer c where c.email like %:gmail%")
	List<Customer> findAllCustomersWhoseEmailContainsGmail(@Param("gmail") String gmail);

	//performs same function as method above
	List<Customer> findByEmailContains(String email);

	Boolean existsByCustomerIdOrEmail(String customerId, String email);
}
