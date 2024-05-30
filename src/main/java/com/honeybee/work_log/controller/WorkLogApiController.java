package com.honeybee.work_log.controller;


import com.honeybee.work_log.domain.WorkLog;
import com.honeybee.work_log.dto.SaveWorkLogRequest;
import com.honeybee.work_log.dto.UpdateWorkLogRequest;
import com.honeybee.work_log.dto.WorkLogResponse;
import com.honeybee.work_log.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class WorkLogApiController {

    private final WorkLogService workLogService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/logs")
    public ResponseEntity<List<WorkLogResponse>> findAllLogs(@RequestParam(required = false) String keyword, Principal principal) {

        System.out.println("principal.getName() = " + principal.getName());
        if (keyword != null) {
            // 대소문자 구분 없이 검색 결과를 합치기 위해 Set 사용
            Set<WorkLogResponse> workLogs = new HashSet<>();

            workLogs.addAll(
                    workLogService.findByKeyword(keyword).stream()
                            .map(WorkLogResponse::new)
                            .toList()
            );

            workLogs.addAll(
                    workLogService.findByKeyword(keyword.toUpperCase()).stream()
                            .map(WorkLogResponse::new)
                            .toList()
            );
            return ResponseEntity.ok().body(new ArrayList<>(workLogs));
        } else {
            List<WorkLogResponse> workLogs = workLogService.findAll(principal.getName()).stream()
                    .map(WorkLogResponse::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(workLogs);
        }
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/logs")
    public ResponseEntity<WorkLog> saveWorkLog(@Validated @RequestBody SaveWorkLogRequest request, Principal principal) {

        WorkLog workLog = WorkLog.builder().tags(request.getTags())
                .log(request.getLog())
                .userName(principal.getName()).build();
        workLogService.saveLog(workLog);
        return ResponseEntity.status(HttpStatus.CREATED).body(workLog);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/logs/{id}")
    public ResponseEntity<WorkLog> updateWorkLog(@PathVariable Long id, @RequestBody UpdateWorkLogRequest request, Principal principal) {
        System.out.println("update principal = " + principal.getName());

        if (principal.getName() == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        try {
            WorkLog workLog = workLogService.updateWorkLog(id, request, principal.getName());
            return ResponseEntity.ok().body(workLog);
        } catch (SecurityException e) {
            System.out.println("e = " + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/logs/{id}")
    public ResponseEntity<Void> deleteWorkLog(@PathVariable Long id) {
        workLogService.deleteWorkLog(id);
        return ResponseEntity.ok().build();
    }


}
