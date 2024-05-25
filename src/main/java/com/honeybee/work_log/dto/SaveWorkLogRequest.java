package com.honeybee.work_log.dto;

import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.honeybee.work_log.domain.WorkLog}
 */
@Value
public class SaveWorkLogRequest implements Serializable {
    String log;
    String userName;
    List<String> tags;
}