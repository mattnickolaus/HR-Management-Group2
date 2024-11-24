package com.buffet.hrmanagement.controller;

import com.buffet.hrmanagement.model.*;
import com.buffet.hrmanagement.service.PayrollService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;

    // Show list of payroll periods
    @GetMapping("")
    public String viewPayrollPeriods(Model model) {
        List<PayrollPeriod> payrollPeriods = payrollService.getAllPayrollPeriods();
        model.addAttribute("payrollPeriods", payrollPeriods);
        return "payroll_periods";
    }

    // Show form to create a new payroll period
    @GetMapping("/newPeriod")
    public String showNewPayrollPeriodForm(Model model) {
        PayrollPeriod payrollPeriod = new PayrollPeriod();
        model.addAttribute("payrollPeriod", payrollPeriod);
        return "new_payroll_period";
    }

    // Save new payroll period
    @PostMapping("/savePeriod")
    public String savePayrollPeriod(@ModelAttribute("payrollPeriod") PayrollPeriod payrollPeriod) {
        payrollService.createPayrollPeriod(payrollPeriod);
        return "redirect:/payroll";
    }

    // Process payroll for a specific period
    @GetMapping("/process/{id}")
    public String processPayroll(@PathVariable("id") Long id) {
        payrollService.processPayroll(id);
        return "redirect:/payroll/records/" + id;
    }

    // View payroll records for a period
    @GetMapping("/records/{periodId}")
    public String viewPayrollRecords(@PathVariable("periodId") Long periodId, Model model) {
        PayrollPeriod payrollPeriod = payrollService.getPayrollPeriodById(periodId);
        List<PayrollRecord> payrollRecords = payrollService.getPayrollRecordsByPeriod(periodId);

        model.addAttribute("payrollPeriod", payrollPeriod);
        model.addAttribute("payrollRecords", payrollRecords);
        return "payroll_records";
    }

    // Adjust payroll record
    @GetMapping("/adjust/{id}")
    public String showAdjustPayrollRecordForm(@PathVariable("id") Long id, Model model) {
        PayrollRecord payrollRecord = payrollService.getPayrollRecordById(id);
        model.addAttribute("payrollRecord", payrollRecord);
        return "adjust_payroll_record";
    }

    @PostMapping("/saveAdjustment")
    public String savePayrollAdjustment(@ModelAttribute("payrollRecord") PayrollRecord payrollRecord) {
        // Recalculate net salary after adjustment
        BigDecimal grossSalary = payrollRecord.getGrossSalary().add(payrollRecord.getAdjustments());
        BigDecimal netSalary = grossSalary.subtract(payrollRecord.getTaxDeductions()).subtract(payrollRecord.getBenefitsDeductions());
        payrollRecord.setGrossSalary(grossSalary);
        payrollRecord.setNetSalary(netSalary);

        payrollService.savePayrollRecord(payrollRecord);
        return "redirect:/payroll/records/" + payrollRecord.getPayrollPeriod().getId();
    }
}
