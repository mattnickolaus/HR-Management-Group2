package com.buffet.hrmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "payroll_periods")
public class PayrollPeriod {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="start_date", nullable = false)
    private LocalDate startDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

    @Column(name="period_name", nullable = false)
    private String periodName;

    @OneToMany(mappedBy = "payrollPeriod", cascade = CascadeType.ALL)
    private Set<PayrollRecord> payrollRecords;

    // Constructors, getter, and setter
    public PayrollPeriod() {}

    public PayrollPeriod(LocalDate startDate, LocalDate endDate, String periodName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.periodName = periodName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate stateDate) {
        this.startDate = stateDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getPeriodName() {
        return periodName;
    }

    public void setPeriodName(String periodName) {
        this.periodName = periodName;
    }

    public Set<PayrollRecord> getPayrollRecords() {
        return payrollRecords;
    }

    public void setPayrollRecords(Set<PayrollRecord> payrollRecords) {
        this.payrollRecords = payrollRecords;
    }
}
