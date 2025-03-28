package com.sku_sku.backend.dto.Request;

import com.sku_sku.backend.domain.lecture.JoinLectureFile;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

public class JoinLectureFilesDTO {

    @Data
    public static class CreateJoinLectureFilesRequest {
        @Schema(description = "강의 안내물 id", example = "1")
        private Long lectureId;
        @Schema(description = "강의자료 이름", example = "Spring.pdf")
        private String fileName;
        @Schema(description = "강의자료 유형", example = "application/pdf")
        private String fileType;
        @Schema(description = "강의자료 사이즈", example = "65362")
        private long size;
        @Schema(description = "강의자료", example = "base64 인코딩 값")
        private String file;

        public CreateJoinLectureFilesRequest(JoinLectureFile joinLectureFile) {
            this.lectureId = joinLectureFile.getId();
            this.fileName = joinLectureFile.getFileName();
            this.fileType = joinLectureFile.getFileType();
            this.size = joinLectureFile.getFileSize();
            this.file = joinLectureFile.arrayToFile();
        }
    }
}
