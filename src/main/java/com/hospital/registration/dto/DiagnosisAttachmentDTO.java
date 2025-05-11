package com.hospital.registration.dto;

import lombok.Data;
import java.util.Date;

@Data
public class DiagnosisAttachmentDTO {
    private Long attachmentId;
    private Long diagnosisId;
    private String fileName;
    private String fileType;
    private String fileUrl;
    private Date uploadTime;
}
