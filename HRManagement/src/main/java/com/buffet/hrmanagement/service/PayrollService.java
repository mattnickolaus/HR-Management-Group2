package com.buffet.hrmanagement.service;

import com.buffet.hrmanagement.model.PayrollPeriod;
import com.buffet.hrmanagement.model.PayrollRecord;
import java.util.List;

public interface PayrollService {
    List<PayrollPeriod> getAllPayrollPeriods();
    PayrollPeriod createPayrollPeriod(PayrollPeriod payrollPeriod);
    PayrollPeriod getPayrollPeriodById(Long id);
    void processPayroll(Long payrollPeriodId);
    List<PayrollRecord> getPayrollRecordsByPeriod(Long payrollPeriodId);
    PayrollRecord getPayrollRecordById(Long id);
    PayrollRecord savePayrollRecord(PayrollRecord payrollRecord);
}
