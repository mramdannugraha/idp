package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateKaryawanRequest {

    @NotBlank
    @Size(max = 50)
    private String nama;

    @DateTimeFormat(pattern = "yyMMdd")
    private Date dob;

    @Size(max = 255)
    private String alamat;

    @Size(max = 15)
    private String status;

    private CreateDetailKaryawanRequest detailKaryawan;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CreateDetailKaryawanRequest {
        @NotBlank
        @Size(max = 30)
        private String nik;

        @Size(max = 30)
        private String npwp;
    }
}
