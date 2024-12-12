package com.buffet.hrmanagement.controller;

import com.buffet.hrmanagement.model.Employee;
import com.buffet.hrmanagement.model.LeaveRequest;
import com.buffet.hrmanagement.model.PayrollRecord;
import com.buffet.hrmanagement.service.EmployeeService;
import com.buffet.hrmanagement.service.LeaveRequestService;
import com.buffet.hrmanagement.service.PayrollService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private PayrollService payrollService;

    @Autowired
    private LeaveRequestService leaveRequestService;

    @GetMapping("")
    public String showReportForm(Model model) {
        // Just return the report.html page with a form
        return "report";
    }

    @PostMapping("/generate")
    public void generateReport(@RequestParam("dataType") String dataType,
                               @RequestParam(name="employeeId", required=false) Long employeeId,
                               HttpServletResponse response) throws Exception {

        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + dataType + "_report.csv\"");

        PrintWriter writer = response.getWriter();

        switch (dataType) {
            case "employees":
                if (employeeId != null) {
                    // Fetch single employee by ID
                    Employee emp = employeeService.getEmployeeById(employeeId);
                    // Write CSV header
                    writer.println("Id,Name,Email,DateOfBirth,Position,Department,Salary");
                    // Write the single employee row
                    writer.println(emp.getId() + ","
                            + emp.getName() + ","
                            + emp.getEmail() + ","
                            + emp.getDateOfBirth() + ","
                            + emp.getPosition() + ","
                            + emp.getDepartment() + ","
                            + emp.getSalary());
                } else {
                    // No employeeId provided, fetch all
                    List<Employee> employees = employeeService.getAllEmployees();
                    // Write CSV header
                    writer.println("Id,Name,Email,DateOfBirth,Position,Department,Salary");
                    for (Employee e : employees) {
                        writer.println(e.getId() + ","
                                + e.getName() + ","
                                + e.getEmail() + ","
                                + e.getDateOfBirth() + ","
                                + e.getPosition() + ","
                                + e.getDepartment() + ","
                                + e.getSalary());
                    }
                }
                break;

            case "payroll":
                // Get all payroll records
                List<PayrollRecord> payrollRecords = payrollService.getAllPayrollRecords();
                if (employeeId != null) {
                    // Confirm employee exists
                    Employee emp = employeeService.getEmployeeById(employeeId);
                    // Filter records by employee ID
                    payrollRecords = payrollRecords.stream()
                            .filter(p -> p.getEmployee().getId().equals(employeeId))
                            .collect(Collectors.toList());
                }

                // Write CSV header
                writer.println("Id,EmployeeId,GrossSalary,TaxDeductions,BenefitDeductions,NetSalary,Adjustments,Notes");
                for (PayrollRecord p : payrollRecords) {
                    // Replace commas in notes if needed
                    String notes = (p.getNotes() != null) ? p.getNotes().replace(",", ";") : "";
                    writer.println(p.getId() + ","
                            + p.getEmployee().getId() + ","
                            + p.getGrossSalary() + ","
                            + p.getTaxDeductions() + ","
                            + p.getBenefitsDeductions() + ","
                            + p.getNetSalary() + ","
                            + (p.getAdjustments() != null ? p.getAdjustments() : "") + ","
                            + notes);
                }
                break;

            case "leaves":
                // Get all leave requests
                List<LeaveRequest> leaveRequests = leaveRequestService.getAllLeaveRequests();
                if (employeeId != null) {
                    // Confirm employee exists
                    Employee emp = employeeService.getEmployeeById(employeeId);
                    // Filter leave requests by employee ID
                    leaveRequests = leaveRequests.stream()
                            .filter(l -> l.getEmployeeId().equals(employeeId))
                            .collect(Collectors.toList());
                }

                // Write CSV header
                writer.println("Id,EmployeeId,LeaveType,StartDate,EndDate,Status,Comments");
                for (LeaveRequest l : leaveRequests) {
                    String comments = (l.getComments() != null) ? l.getComments().replace(",", ";") : "";
                    writer.println(l.getId() + ","
                            + l.getEmployeeId() + ","
                            + l.getLeaveType() + ","
                            + l.getStartDate() + ","
                            + l.getEndDate() + ","
                            + l.getStatus() + ","
                            + comments);
                }
                break;

            default:
                // Invalid dataType
                writer.println("Invalid data type selected.");
                break;
        }

        writer.flush();
        writer.close();
    }
}
