package com.score.backend.models.dtos;

import com.score.backend.models.School;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Schema(description = "새로운 학교 정보 저장을 위한 DTO")
@Getter
public class SchoolDto {

    @Schema(description = "힉교명")
    private final String schoolName;

    @Schema(description = "학교가 속해 있는 행정 구역")
    private final String schoolLocation;

    @Schema(description = "힉교 주소")
    private final String schoolAddress;

    @Schema(description = "학교의 행정 표준 코드")
    private final String schoolCode;

    public SchoolDto(String schoolName, String schoolLocation, String schoolAddress, String schoolCode) {
        this.schoolName = schoolName;
        this.schoolLocation = schoolLocation;
        this.schoolAddress = schoolAddress;
        this.schoolCode = schoolCode;
    }

    public School toEntity() {
        return School.builder()
                .schoolName(schoolName)
                .schoolLocation(schoolLocation)
                .schoolCode(schoolCode)
                .build();
    }
}
