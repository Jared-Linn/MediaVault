package com.linqiumeng.mediavault.entity;

import jakarta.persistence.*;
import lombok.Data;
import nonapi.io.github.classgraph.json.Id;


@Entity
@Table(name = "files")
@Data
public class FileEntity {

    @jakarta.persistence.Id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String fileName;

    @Column(nullable = false, length = 255)
    private String fileKey;

    @Column(nullable = false)
    private Long fileSize;

    @Column(nullable = false, length = 255)
    private String contentType;

    @Column(nullable = false, updatable = false)
    private java.sql.Timestamp uploadTime;

    @Column(length = 50)
    private String status;

    @Column(columnDefinition = "JSON")
    private String metadata;

    @PrePersist
    protected void onCreate() {
        this.uploadTime = new java.sql.Timestamp(new java.util.Date().getTime());
    }
}
