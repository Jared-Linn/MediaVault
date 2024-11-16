package com.linqiumeng.mediavault.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "files")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;


    private String fileName;

    @Size(max = 255)
    @Column(name = "file_key")
    private String fileKey;

    @Size(max = 255)
    @Column(name = "file_path")
    private String filePath;

    @Column(name = "file_size")
    private Long fileSize;

    @Size(max = 255)
    @Column(name = "content_type")
    private String contentType;

    @Column(name = "user_id")
    private Integer userId;

    @Size(max = 50)
    @ColumnDefault("'uploaded'")
    @Column(name = "status", length = 50)
    private String status;

    @Size(max = 100)
    @Column(name = "dataurl", length = 100)
    private String dataurl;

    @Lob
    @Column(name = "description")
    private String description;

    @Size(max = 255)
    @Column(name = "tags")
    private String tags;

    @ColumnDefault("0")
    @Column(name = "download_count")
    private Integer downloadCount;

    @Size(max = 255)
    @ColumnDefault("'public'")
    @Column(name = "permissions")
    private String permissions;

    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "upload_time", nullable = false)
    private Instant uploadTime;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "update_time")
    private Instant updateTime;

}
