package com.buffet.hrmanagement.repository;

import com.buffet.hrmanagement.model.PayrollRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PayrollRecordRepository extends JpaRepository<PayrollRecord, Long> {
    List<PayrollRecord> findByPayrollPeriodId(Long payrollPeriodId);
}
