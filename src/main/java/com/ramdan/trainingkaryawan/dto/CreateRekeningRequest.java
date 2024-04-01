package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRekeningRequest {
    @NotBlank
    @Size(max = 50)
    private String nama;

    @Size(max = 15)
    private String jenis;

    @NotBlank
    @Size(max = 15)
    private String rekening;

    private String alamat;

    private DetailRekeningKaryawanRequest karyawan;

}
