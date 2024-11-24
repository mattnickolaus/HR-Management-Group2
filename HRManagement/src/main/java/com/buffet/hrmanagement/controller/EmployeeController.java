package com.buffet.hrmanagement.controller;

import com.buffet.hrmanagement.model.Employee;
import com.buffet.hrmanagement.service.EmployeeService;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Page;

import java.util.List;

@Controller
@RequestMapping("/employees") // All URLs in this controller will be prefixed with /employees
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("") // List all employees
    public String viewEmployeesPage(Model model) {
        model.addAttribute("listEmployees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/showNewEmployeeForm") // Shows new employee form
    public String showNewEmployeeForm(Model model) {
        Employee employee = new Employee();
        model.addAttribute("employee", employee);
        return "new_employee";
    }

    @PostMapping("/saveEmployee") // Saves a new or updated employee
    public String saveEmployee(@ModelAttribute("employee") Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

    @GetMapping("/showFormForUpdate/{id}") // Shows form to update employee
    public String showFormForUpdate(@PathVariable(value = "id") Long id, Model model) {
        Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "update_employee";
    }

    @GetMapping("/deleteEmployee/{id}") // Delete employee by ID
    public String deleteEmployee(@PathVariable(value = "id") Long id) {
        this.employeeService.deleteEmployeeById(id);
        return "redirect:/employees";
    }
}