package com.buffet.hrmanagement.service;

import com.buffet.hrmanagement.model.*;
import com.buffet.hrmanagement.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PayrollServiceImpl implements PayrollService {

    @Autowired
    private PayrollPeriodRepository payrollPeriodRepository;

    @Autowired
    private PayrollRecordRepository payrollRecordRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public List<PayrollPeriod> getAllPayrollPeriods() {
        return payrollPeriodRepository.findAll();
    }

    @Override
    public PayrollPeriod createPayrollPeriod(PayrollPeriod payrollPeriod) {
        return payrollPeriodRepository.save(payrollPeriod);
    }

    @Override
    public PayrollPeriod getPayrollPeriodById(Long id) {
        return payrollPeriodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll Period not found for id :: " + id));
    }

    @Override
    public void processPayroll(Long payrollPeriodId) {
        PayrollPeriod payrollPeriod = getPayrollPeriodById(payrollPeriodId);
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            PayrollRecord payrollRecord = new PayrollRecord();
            payrollRecord.setEmployee(employee);
            payrollRecord.setPayrollPeriod(payrollPeriod);

            // Calculate salaries and deductions
            BigDecimal grossSalary = calculateGrossSalary(employee);
            BigDecimal taxDeductions = calculateTaxDeductions(grossSalary);
            BigDecimal benefitDeductions = calculateBenefitDeductions(employee);
            BigDecimal netSalary = grossSalary.subtract(taxDeductions).subtract(benefitDeductions);

            payrollRecord.setGrossSalary(grossSalary);
            payrollRecord.setTaxDeductions(taxDeductions);
            payrollRecord.setBenefitsDeductions(benefitDeductions);
            payrollRecord.setNetSalary(netSalary);

            payrollRecordRepository.save(payrollRecord);
        }
    }

    @Override
    public List<PayrollRecord> getPayrollRecordsByPeriod(Long payrollPeriodId) {
        return payrollRecordRepository.findByPayrollPeriodId(payrollPeriodId);
    }

    @Override
    public PayrollRecord getPayrollRecordById(Long id) {
        return payrollRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payroll Record not found for id :: " + id));
    }

    @Override
    public PayrollRecord savePayrollRecord(PayrollRecord payrollRecord) {
        return payrollRecordRepository.save(payrollRecord);
    }

    // Helper methods for calculations
    private BigDecimal calculateGrossSalary(Employee employee) {
        // Base salary calculation, including overtime and bonuses
        return employee.getSalary(); // Simplified for example
    }

    private BigDecimal calculateTaxDeductions(BigDecimal grossSalary) {
        // Implement tax calculation logic
        BigDecimal taxRate = new BigDecimal("0.20"); // Example: 20% tax rate
        return grossSalary.multiply(taxRate);
    }

    private BigDecimal calculateBenefitDeductions(Employee employee) {
        // Implement benefit deduction logic
        BigDecimal benefits = new BigDecimal("100"); // Simplified fixed amount
        return benefits;
    }

}
