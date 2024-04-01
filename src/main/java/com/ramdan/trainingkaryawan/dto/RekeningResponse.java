package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RekeningResponse {
    private Date created_date;
    private Date deleted_date;
    private Date updated_date;
    private Long id;
    private String nama;
    private String jenis;
    private String rekening;
    private String alamat;
    private DetailKaryawanResponse karyawan;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DetailKaryawanResponse {
        private Long id;
        private String nama;
    }
}
