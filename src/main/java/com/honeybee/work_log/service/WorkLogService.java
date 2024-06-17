package com.honeybee.work_log.service;


import com.honeybee.work_log.domain.WorkLog;
import com.honeybee.work_log.dto.SaveWorkLogRequest;
import com.honeybee.work_log.dto.UpdateWorkLogRequest;
import com.honeybee.work_log.repository.WorkLogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepo;

    public WorkLog saveLog(SaveWorkLogRequest req, String userName) {

        return workLogRepo.save(req.toEntity(userName));
    }

    public WorkLog findById(Long id) {
        Optional<WorkLog> workLog = workLogRepo.findById(id);
        return workLog.orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }

    public List<WorkLog> findByKeyword(String keyword, String userName) {
        return workLogRepo.findAllByKeyword(keyword, userName);
    }

    public List<WorkLog> findAll(String userName) {
        return workLogRepo.findByUserName(userName);
    }

    @Transactional
    public WorkLog updateWorkLog(Long id, UpdateWorkLogRequest request) {
        WorkLog workLog = workLogRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));


        authorizeWorkLogAuthor(workLog);

        workLog.update(request.getLog(), request.getTags());

        return workLog;
    }

    public void deleteWorkLog(Long id) {
        WorkLog workLog = workLogRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));


        authorizeWorkLogAuthor(workLog);
        workLogRepo.deleteById(id);
    }


    private static void authorizeWorkLogAuthor(WorkLog workLog) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!workLog.getUserName().equals(userName)) {
            throw new SecurityException("You are not allowed to update this log");
        }
    }



}
