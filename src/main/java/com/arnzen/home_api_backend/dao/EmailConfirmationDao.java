package com.arnzen.home_api_backend.dao;

import com.arnzen.home_api_backend.model.base.EmailConfirmationEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EmailConfirmationDao extends JpaRepository<EmailConfirmationEntity, Integer>  {
    Optional<EmailConfirmationEntity> findFirstByUserEntityIdOrderByIdDesc(int userId);
//    List<EmailConfirmationEntity> findByCreatedAtDateTimeAfter(LocalDateTime time);

    @Query("SELECT e FROM EmailConfirmationEntity e WHERE e.userEntity.id = :userId AND e.createdAtDateTime > :pastThirtyMinutes ORDER BY e.createdAtDateTime DESC")
    List<EmailConfirmationEntity> findAllCreatedEntitiesInPast30Minutes(
            @Param("userId") int userId,
            @Param("pastThirtyMinutes") LocalDateTime pastThirtyMinutes,
            Pageable pageable);

    @Modifying
    @Transactional
    @Query("UPDATE EmailConfirmationEntity e SET e.isActive = :isActive WHERE e.userEntity.id = :userId")
    int updateStatusByUserId(@Param("userId") int userId, @Param("isActive") boolean isActive);
}
