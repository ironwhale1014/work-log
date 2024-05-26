package com.honeybee.work_log.controller;


import com.honeybee.work_log.domain.WorkLog;
import com.honeybee.work_log.dto.SaveWorkLogRequest;
import com.honeybee.work_log.dto.UpdateWorkLogRequest;
import com.honeybee.work_log.dto.WorkLogResponse;
import com.honeybee.work_log.service.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class WorkLogApiController {

    private final WorkLogService workLogService;

    @GetMapping("/logs")
    public ResponseEntity<List<WorkLogResponse>> findAllLogs() {
        List<WorkLogResponse> workLogs = workLogService.findAll().stream().map(WorkLogResponse::new).toList();
        return ResponseEntity.ok().body(workLogs);
    }

    @PostMapping("/logs")
    public ResponseEntity<WorkLog> saveWorkLog(@Validated @RequestBody SaveWorkLogRequest request) {
        System.out.println("request = " + request.getLog());
        WorkLog workLog = WorkLog.builder().tags(request.getTags())
                .log(request.getLog())
                .userName(request.getUserName()).build();
        workLogService.saveLog(workLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(workLog);
    }

    @PutMapping("/logs/{id}")
    public ResponseEntity<WorkLog> updateWorkLog(@PathVariable Long id, @RequestBody UpdateWorkLogRequest request) {
        System.out.println("request = " + request.getLog());
        WorkLog workLog = workLogService.updateWorkLog(id, request);

        return ResponseEntity.ok().body(workLog);
    }

    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
        return ResponseEntity.ok().build();
    }


}
