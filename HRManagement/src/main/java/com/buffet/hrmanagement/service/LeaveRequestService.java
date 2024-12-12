package com.buffet.hrmanagement.service;

import com.buffet.hrmanagement.model.LeaveRequest;

import java.util.List;

public interface LeaveRequestService {
    List<LeaveRequest> getPendingLeaveRequests();
    LeaveRequest getLeaveRequestById(Long id);
    LeaveRequest updateLeaveRequestStatus(Long id, String status, String comments);
    void saveLeaveRequest(LeaveRequest leaveRequest);
    List<LeaveRequest> getAllLeaveRequests();
}
