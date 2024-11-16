package com.linqiumeng.mediavault.repository;

import com.linqiumeng.mediavault.entity.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileEntityRepository extends JpaRepository<FileEntity, Integer> {
}