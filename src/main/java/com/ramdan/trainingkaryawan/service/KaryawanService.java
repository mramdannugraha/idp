package com.ramdan.trainingkaryawan.service;

import com.ramdan.trainingkaryawan.dto.CreateKaryawanRequest;
import com.ramdan.trainingkaryawan.dto.UpdateKaryawanRequest;
import com.ramdan.trainingkaryawan.model.Karyawan;
import org.springframework.data.domain.Page;

public interface KaryawanService {
    Karyawan insertKaryawanAndDetail(CreateKaryawanRequest request);
    Karyawan updateKaryawanAndDetail(UpdateKaryawanRequest request);
    Page<Karyawan> getAll(int page, int size);
    Karyawan getById(Long id);
    void deleteKaryawan(Long id);
}
