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
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.List;


@RequestMapping("/worklog")
@RequiredArgsConstructor
@Controller
public class WorkLogViewController {
    final private WorkLogService workLogService;

    final private RestTemplate restTemplate;


    @GetMapping("")
    public String getWorkLog(Model model, Principal principal, @RequestParam(required = false) String token) {

        if(token != null) {

        }


        String username = "test";
        if (principal != null) {
            username = principal.getName();
            System.out.println("username = " + username);
        }

        List<WorkLogsViewResponse> workLogs = new java.util.ArrayList<>(workLogService.findAll(username).stream().map(WorkLogsViewResponse::new).toList());

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
