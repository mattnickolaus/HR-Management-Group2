package com.buffet.hrmanagement.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_records")
public class PayrollRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "payroll_period_id", nullable = false)
    private PayrollPeriod payrollPeriod;

    // Payroll Details
    @Column(name = "gross_salary", nullable = false)
    private BigDecimal grossSalary;

    @Column(name = "tax_deductions", nullable = false)
    private BigDecimal taxDeductions;

    @Column(name = "benefits_deductions", nullable = false)
    private BigDecimal benefitsDeductions;

    @Column(name = "net_salary", nullable = false)
    private BigDecimal netSalary;

    @Column(name = "adjustments")
    private BigDecimal adjustments;

    @Column(name = "notes")
    private String notes;

    public PayrollRecord() {}

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public PayrollPeriod getPayrollPeriod() {
        return payrollPeriod;
    }

    public void setPayrollPeriod(PayrollPeriod payrollPeriod) {
        this.payrollPeriod = payrollPeriod;
    }

    public BigDecimal getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
    }

    public BigDecimal getTaxDeductions() {
        return taxDeductions;
    }

    public void setTaxDeductions(BigDecimal taxDeductions) {
        this.taxDeductions = taxDeductions;
    }

    public BigDecimal getBenefitsDeductions() {
        return benefitsDeductions;
    }

    public void setBenefitsDeductions(BigDecimal benefitsDeductions) {
        this.benefitsDeductions = benefitsDeductions;
    }

    public BigDecimal getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(BigDecimal netSalary) {
        this.netSalary = netSalary;
    }

    public BigDecimal getAdjustments() {
        return adjustments;
    }

    public void setAdjustments(BigDecimal adjustments) {
        this.adjustments = adjustments;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
