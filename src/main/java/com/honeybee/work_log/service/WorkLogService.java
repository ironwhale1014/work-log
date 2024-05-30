package com.honeybee.work_log.service;


import com.honeybee.work_log.dto.UpdateWorkLogRequest;
import com.honeybee.work_log.domain.WorkLog;
import com.honeybee.work_log.repository.WorkLogRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepo;

    public WorkLog saveLog(WorkLog workLog) {
        return workLogRepo.save(workLog);
    }

    public WorkLog findById(Long id) {
        Optional<WorkLog> workLog = workLogRepo.findById(id);
        return workLog.orElseThrow(() -> new IllegalArgumentException("not found" + id));
    }

    public List<WorkLog> findByKeyword(String keyword) {
        return workLogRepo.findAllByKeyword(keyword);
    }

    public List<WorkLog> findAll() {
        return workLogRepo.findAll();
    }

    @Transactional
    public WorkLog updateWorkLog(Long id, UpdateWorkLogRequest request) {
        WorkLog workLog = workLogRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("not found" + id));

        workLog.update(request.getLog(), request.getTags());

        return workLog;
    }

    public void deleteWorkLog(Long id) {
        workLogRepo.deleteById(id);
    }


}
