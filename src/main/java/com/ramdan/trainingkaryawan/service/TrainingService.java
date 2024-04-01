package com.ramdan.trainingkaryawan.service;

import com.ramdan.trainingkaryawan.dto.CreateTrainingRequest;
import com.ramdan.trainingkaryawan.dto.UpdateTrainingRequest;
import com.ramdan.trainingkaryawan.model.Training;
import org.springframework.data.domain.Page;

public interface TrainingService {
    Training createTraining(CreateTrainingRequest request);
    Training updateTraining(UpdateTrainingRequest request);
    Page<Training> getAll(int page, int size);
    Training getById(Long id);
    void delete(Long id);
}
