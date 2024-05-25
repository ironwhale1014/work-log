package com.honeybee.work_log.dto;

import com.honeybee.work_log.domain.WorkLog;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.honeybee.work_log.domain.WorkLog}
 */

@Getter
@NoArgsConstructor
public class WorkLogsViewResponse implements Serializable {
    @NotEmpty(message = "내용은 필수 입니다.")
    String log;
    String userName;
    LocalDateTime createAt;
    LocalDateTime updateAt;
    List<String> tags;
    Long id;

    public WorkLogsViewResponse(WorkLog workLog) {
        this.log = workLog.getLog();
        this.userName = workLog.getUserName();
        this.createAt = workLog.getCreateAt();
        this.updateAt = workLog.getUpdateAt();
        this.tags = workLog.getTags();
        this.id = workLog.getId();
    }

}