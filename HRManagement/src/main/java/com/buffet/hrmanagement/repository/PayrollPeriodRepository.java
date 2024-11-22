package com.buffet.hrmanagement.repository;

import com.buffet.hrmanagement.model.PayrollPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PayrollPeriodRepository extends JpaRepository<PayrollPeriod, Long> {

}
