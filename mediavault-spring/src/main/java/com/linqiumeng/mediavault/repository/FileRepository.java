package com.linqiumeng.mediavault.repository;

import com.linqiumeng.mediavault.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
    List<FileEntity> findByUserId(int intExact);
}
