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
    }
}