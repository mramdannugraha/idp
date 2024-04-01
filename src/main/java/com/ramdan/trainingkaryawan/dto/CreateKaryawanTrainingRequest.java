package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKaryawanTrainingRequest implements Serializable {
    private KaryawanDetailRequest karyawan;

    private TrainingDetailRequest training;

    private String tanggal;

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
