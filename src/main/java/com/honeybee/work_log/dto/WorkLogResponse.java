package com.honeybee.work_log.dto;

import com.honeybee.work_log.domain.WorkLog;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO for {@link WorkLog}
 */
@Value
public class WorkLogResponse implements Serializable {
    String log;
    String userName;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    List<String> tags;

    public WorkLogResponse(WorkLog workLog) {
        this.log = workLog.getLog();
        this.userName = workLog.getUserName();
        this.createAt = workLog.getCreateAt();
        this.updateAt = workLog.getUpdateAt();
        this.tags = workLog.getTags();
    }
}