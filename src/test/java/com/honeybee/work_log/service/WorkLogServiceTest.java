package com.honeybee.work_log.service;

import com.honeybee.work_log.dto.UpdateWorkLogRequest;
import com.honeybee.work_log.domain.WorkLog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class WorkLogServiceTest {

    @Autowired
    private WorkLogService workLogService;

    @Test
    void saveLog() {


        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");


        WorkLog firstLog = WorkLog.builder().log("tag log").createAt(LocalDateTime.now()).tags(tags).build();


        workLogService.saveLog(firstLog);

        List<WorkLog> logList = workLogService.findAll();
        WorkLog last = logList.getLast();

        assertThat(last.getLog()).isEqualTo("tag log");

        System.out.println("last.getTags().toString() = " + last.getTags().toString());

        UpdateWorkLogRequest updateWorkLogRequest = new UpdateWorkLogRequest("update_log", "lee", tags);
        WorkLog workLog = workLogService.updateWorkLog(last.getId(), updateWorkLogRequest);
        WorkLog findWorkLog = workLogService.findById(last.getId());
        assertThat(findWorkLog.getLog()).isEqualTo("update_log");


    }
}