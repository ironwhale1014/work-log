package com.honeybee.work_log.controller;


import com.honeybee.work_log.domain.WorkLog;
import com.honeybee.work_log.dto.WorkLogsViewResponse;
import com.honeybee.work_log.service.WorkLogService;
import com.honeybee.work_log.util.WorkLogsViewResponseComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;


@RequestMapping("/worklog")
@RequiredArgsConstructor
@Controller
public class WorkLogViewController {
    final private WorkLogService workLogService;

    @GetMapping("")
    public String getWorkLog(Model model, Principal principal) {
        System.out.println("principal = " + principal.getName());
        List<WorkLogsViewResponse> workLogs = new java.util.ArrayList<>(workLogService.findAll(principal.getName()).stream().map(WorkLogsViewResponse::new).toList());

        workLogs.sort(new WorkLogsViewResponseComparator());

        model.addAttribute("workLogs", workLogs);
        return "work_log";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String createWorkLog(@RequestParam(required = false) Long id, Model model, Principal principal) {


        if (id == null) {
            model.addAttribute("workLog", new WorkLogsViewResponse());
        } else {
            WorkLog findWorkLog = workLogService.findById(id);

            if (findWorkLog.getUserName().equals(principal.getName())) {
                model.addAttribute("workLog", new WorkLogsViewResponse(findWorkLog));
                if (findWorkLog.getTags() != null) {
                    model.addAttribute("tagToString", String.join(",", findWorkLog.getTags()));
                }
            } else {
                throw new SecurityException("You are not allowed to update this log");
            }
        }
        return "log_form";

    }

}
