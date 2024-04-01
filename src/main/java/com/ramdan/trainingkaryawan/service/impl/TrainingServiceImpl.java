package com.ramdan.trainingkaryawan.service.impl;

import com.ramdan.trainingkaryawan.dto.CreateTrainingRequest;
import com.ramdan.trainingkaryawan.dto.UpdateTrainingRequest;
import com.ramdan.trainingkaryawan.model.Training;
import com.ramdan.trainingkaryawan.repository.TrainingRepository;
import com.ramdan.trainingkaryawan.service.TrainingService;
import com.ramdan.trainingkaryawan.service.ValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Objects;

@Service
public class TrainingServiceImpl implements TrainingService {
    public static final Logger logger = LoggerFactory.getLogger(KaryawanServiceImpl.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private TrainingRepository trainingRepository;

    @Transactional
    @Override
    public Training createTraining(CreateTrainingRequest request) {
        validationService.validate(request);

        Training training = new Training();
        training.setTema(request.getTema());
        training.setPengajar(request.getPengajar());

        Training createdTraining = trainingRepository.save(training);
        return createdTraining;
    }

    @Transactional
    @Override
    public Training updateTraining(UpdateTrainingRequest request) {
        validationService.validate(request);

        Training training = trainingRepository.findById(Long.parseLong(request.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data training tidak ditemukan"));

        if (!Objects.equals(request.getTema(), "")) {
            training.setTema(request.getTema());
        }

        if (!Objects.equals(request.getPengajar(), "")) {
            training.setPengajar(request.getPengajar());
        }

        Date date = new Date(System.currentTimeMillis());
        training.setUpdated_date(date);

        return trainingRepository.save(training);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Training> getAll(int page, int size) {
        Pageable data = PageRequest.of(page,size, Sort.by(Sort.Order.asc("id")));
        Page<Training> list = trainingRepository.findAll(data);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public Training getById(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data training tidak ditemukan"));

        return training;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        Training training = trainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data training tidak ditemukan"));

        trainingRepository.deleteById(training.getId());
    }
}
