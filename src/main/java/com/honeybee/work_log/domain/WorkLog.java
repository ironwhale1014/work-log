package com.honeybee.work_log.domain;


import com.honeybee.work_log.util.StringListConvert;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "log")
    private String log;

    private String userName;


    @CreatedDate
    private LocalDateTime createAt;
    @LastModifiedDate
    private LocalDateTime updateAt;


    @Convert(converter = StringListConvert.class)
    private List<String> tags;

    @Builder
    public WorkLog(String log, String userName, List<String> tags, LocalDateTime createAt, LocalDateTime updateAt) {
        this.log = log;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.userName = userName;
        this.tags = tags;
    }

    public void update(String log,List<String> tags) {
        this.log = log;
        this.tags = tags;
    }

}
