package com.honeybee.work_log.repository;

import com.honeybee.work_log.domain.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    @Query(
            "select distinct w from WorkLog w " +
                    "where w.log like %:kw% AND w.userName = :email"
    )
    List<WorkLog> findAllByKeyword(@Param("kw") String kw,@Param("email") String email);

    List<WorkLog> findByUserName(String email);
}
