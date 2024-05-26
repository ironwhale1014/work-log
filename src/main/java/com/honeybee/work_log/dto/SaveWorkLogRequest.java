package com.honeybee.work_log.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.honeybee.work_log.domain.WorkLog}
 */
@Value
public class SaveWorkLogRequest implements Serializable {
    @NotEmpty(message = "필수 입력입니다.")
    String log;
    String userName;
    List<String> tags;
}