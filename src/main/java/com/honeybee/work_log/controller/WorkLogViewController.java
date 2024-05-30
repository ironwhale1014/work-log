package com.honeybee.work_log.controller;


import com.honeybee.work_log.domain.WorkLog;
import com.honeybee.work_log.dto.WorkLogsViewResponse;
import com.honeybee.work_log.service.WorkLogService;
import com.honeybee.work_log.util.WorkLogsViewResponseComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


@RequestMapping("/worklog")
@RequiredArgsConstructor
@Controller
public class WorkLogViewController {
    final private WorkLogService workLogService;

    @GetMapping("")
    public String getWorkLog(Model model) {
        List<WorkLogsViewResponse> workLogs = new java.util.ArrayList<>(workLogService.findAll().stream().map(WorkLogsViewResponse::new).toList());

        workLogs.sort(new WorkLogsViewResponseComparator());

        model.addAttribute("workLogs", workLogs);
        return "work_log";
    }

    @GetMapping("/create")
    public String createWorkLog(@RequestParam(required = false) Long id, Model model) {
        System.out.println("id = " + id);
        if (id == null) {
            model.addAttribute("workLog", new WorkLogsViewResponse());
        } else {
            WorkLog byId = workLogService.findById(id);
            model.addAttribute("workLog", new WorkLogsViewResponse(byId));
        }

        return "log_form";

    }

}
