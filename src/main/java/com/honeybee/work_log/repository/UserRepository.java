package com.honeybee.work_log.repository;

import com.honeybee.work_log.domain.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
}
