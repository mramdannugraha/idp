package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateKaryawanTrainingRequest {
    private Long id;
    private String tanggal;
    private KaryawanDetailRequest karyawan;
    private TrainingDetailRequest training;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class KaryawanDetailRequest {
        @NotBlank
        private String id;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TrainingDetailRequest {
        @NotBlank
        private String id;
    }
}
