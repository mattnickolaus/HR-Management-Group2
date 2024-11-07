package com.buffet.hrmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.buffet.hrmanagement.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
