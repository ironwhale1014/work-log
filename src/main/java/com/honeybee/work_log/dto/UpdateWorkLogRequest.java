package com.honeybee.work_log.dto;

import com.honeybee.work_log.domain.WorkLog;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link WorkLog}
 */
@Value
public class UpdateWorkLogRequest implements Serializable {
    String log;
    String userName;
    List<String> tags;
}