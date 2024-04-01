package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateRekeningRequest {
    @NotBlank
    private String id;

    @Size(max = 50)
    private String nama;

    @Size(max = 15)
    private String jenis;

    @Size(max = 15)
    private String rekening;

    private String alamat;

    private DetailRekeningKaryawanRequest karyawan;
}
