package com.buffet.hrmanagement.service;

import com.buffet.hrmanagement.model.LeaveRequest;
import com.buffet.hrmanagement.repository.LeaveRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Autowired
    private LeaveRequestRepository leaveRequestRepository;

    @Override
    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findByStatus("Pending");
    }

    @Override
    public LeaveRequest getLeaveRequestById(Long id) {
        return leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found for id :: " + id));
    }

    @Override
    public LeaveRequest updateLeaveRequestStatus(Long id, String status, String comments) {
        LeaveRequest leaveRequest = getLeaveRequestById(id);
        leaveRequest.setStatus(status);
        leaveRequest.setComments(comments);
        return leaveRequestRepository.save(leaveRequest);
    }

    @Override
    public void saveLeaveRequest(LeaveRequest leaveRequest) {
        leaveRequestRepository.save(leaveRequest);
    }
}
