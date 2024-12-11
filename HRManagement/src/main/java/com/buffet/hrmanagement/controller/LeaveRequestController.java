package com.buffet.hrmanagement.controller;

import com.buffet.hrmanagement.model.Employee;
import com.buffet.hrmanagement.model.LeaveRequest;
import com.buffet.hrmanagement.controller.EmployeeController;
import com.buffet.hrmanagement.repository.EmployeeRepository;
import com.buffet.hrmanagement.service.EmployeeService;
import com.buffet.hrmanagement.service.EmployeeServiceImpl;
import com.buffet.hrmanagement.service.LeaveRequestService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@Controller
@RequestMapping("/leaves")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("")
    public String viewLeaveRequests(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        List<LeaveRequest> pendingRequests = leaveRequestService.getPendingLeaveRequests();

        // Create a map to hold employee details for each leave request
        Map<Long, Employee> employeeMap = new HashMap<>();
        for (LeaveRequest request : pendingRequests) {
            Employee employee = employeeService.getEmployeeById(request.getEmployeeId());
            if (employee != null) {
                employeeMap.put(request.getEmployeeId(), employee);
            }
        }

        model.addAttribute("pendingRequests", pendingRequests);
        model.addAttribute("employeeMap", employeeMap);

        return "leave_requests";
    }



    @GetMapping("/approve/{id}")
    public String approveLeaveRequest(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        leaveRequestService.updateLeaveRequestStatus(id, "Approved", "Approved by HR");
        return "redirect:/leaves";
    }

    @GetMapping("/deny/{id}")
    public String denyLeaveRequest(@PathVariable("id") Long id, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        leaveRequestService.updateLeaveRequestStatus(id, "Denied", "Denied by HR");
        return "redirect:/leaves";
    }

    @PostMapping("/comment/{id}")
    public String commentOnLeaveRequest(@PathVariable("id") Long id,
                                        @RequestParam("comments") String comments,
                                        HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        LeaveRequest leaveRequest = leaveRequestService.getLeaveRequestById(id);
        leaveRequest.setComments(comments);
        leaveRequestService.updateLeaveRequestStatus(id, leaveRequest.getStatus(), comments);
        return "redirect:/leaves";
    }

    @GetMapping("/submit")
    public String showLeaveRequestForm(Model model, HttpSession session) {
        if (session.getAttribute("loggedInUser") == null) {
            return "redirect:/login";
        }

        LeaveRequest leaveRequest = new LeaveRequest();
        model.addAttribute("leaveRequest", leaveRequest);
        return "submit_leave_request";
    }

    @PostMapping("/submit")
    public String submitLeaveRequest(@ModelAttribute("leaveRequest") LeaveRequest leaveRequest) {
        // Fetch the employee using EmployeeService
        Employee employee = employeeService.getEmployeeById(leaveRequest.getEmployeeId());
        if (employee == null) {
            throw new RuntimeException("Employee not found for ID: " + leaveRequest.getEmployeeId());
        }

        // Log the employee ID for debugging
        System.out.println("Submitting leave request for employee ID: " + employee.getId());

        // Set the employee ID in the leave request
        leaveRequest.setEmployeeId(employee.getId());

        // Set the status to "Pending"
        leaveRequest.setStatus("Pending");

        // Save the leave request
        leaveRequestService.saveLeaveRequest(leaveRequest);

        return "redirect:/leaves";
    }


}
