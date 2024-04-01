package com.ramdan.trainingkaryawan.service;

import com.ramdan.trainingkaryawan.dto.CreateKaryawanTrainingRequest;
import com.ramdan.trainingkaryawan.dto.UpdateKaryawanTrainingRequest;
import com.ramdan.trainingkaryawan.model.KaryawanTraining;
import org.springframework.data.domain.Page;

public interface KaryawanTrainingService {
    KaryawanTraining create(CreateKaryawanTrainingRequest request);
    KaryawanTraining update(UpdateKaryawanTrainingRequest request);
    Page<KaryawanTraining> getAll(int page, int size);
    KaryawanTraining getById(Long id);
    void delete(Long id);
}
