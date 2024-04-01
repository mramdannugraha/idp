package com.ramdan.trainingkaryawan.service.impl;

import com.ramdan.trainingkaryawan.dto.CreateKaryawanTrainingRequest;
import com.ramdan.trainingkaryawan.dto.UpdateKaryawanTrainingRequest;
import com.ramdan.trainingkaryawan.model.Karyawan;
import com.ramdan.trainingkaryawan.model.KaryawanTraining;
import com.ramdan.trainingkaryawan.model.Training;
import com.ramdan.trainingkaryawan.repository.KaryawanRepository;
import com.ramdan.trainingkaryawan.repository.KaryawanTrainingRepository;
import com.ramdan.trainingkaryawan.repository.TrainingRepository;
import com.ramdan.trainingkaryawan.service.KaryawanTrainingService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

@Service
public class KaryawanTrainingServiceImpl implements KaryawanTrainingService {
    public static final Logger logger = LoggerFactory.getLogger(KaryawanServiceImpl.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private KaryawanTrainingRepository karyawanTrainingRepository;

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private TrainingRepository trainingRepository;

    @Transactional
    @Override
    public KaryawanTraining create(CreateKaryawanTrainingRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());
        validationService.validate(request.getTraining());

        Karyawan karyawan = karyawanRepository.findById(Long.parseLong(request.getKaryawan().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data karyawan tidak ditemukan"));

        Training training = trainingRepository.findById(Long.parseLong(request.getTraining().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data training tidak ditemukan"));

        String sourcePattern = "yyyy-MM-dd HH:mm:ss";
        Date date;

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(sourcePattern);
            date = sourceFormat.parse(request.getTanggal());
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, gagal parsing tanggal");
        }

        KaryawanTraining karyawanTraining = new KaryawanTraining();
        karyawanTraining.setTanggal(date);
        karyawanTraining.setKaryawan(karyawan);
        karyawanTraining.setTraining(training);

        return karyawanTrainingRepository.save(karyawanTraining);
    }

    @Transactional
    @Override
    public KaryawanTraining update(UpdateKaryawanTrainingRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());
        validationService.validate(request.getTraining());

        Karyawan karyawan = karyawanRepository.findById(Long.parseLong(request.getKaryawan().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data karyawan tidak ditemukan"));

        Training training = trainingRepository.findById(Long.parseLong(request.getTraining().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data training tidak ditemukan"));

        KaryawanTraining karyawanTraining = karyawanTrainingRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data karyawan training tidak ditemukan"));


        if (!Objects.equals(request.getTanggal(), "")) {
            Date date = parseDate(request.getTanggal());
            karyawanTraining.setTanggal(date);
        }

        return karyawanTrainingRepository.save(karyawanTraining);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<KaryawanTraining> getAll(int page, int size) {
        Pageable data = PageRequest.of(page,size, Sort.by(Sort.Order.asc("id")));
        Page<KaryawanTraining> list = karyawanTrainingRepository.findAll(data);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public KaryawanTraining getById(Long id) {
        KaryawanTraining karyawanTraining = karyawanTrainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data karyawan training tidak ditemukan"));

        return karyawanTraining;
    }

    @Transactional
    @Override
    public void delete(Long id) {
        KaryawanTraining karyawanTraining = karyawanTrainingRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "data karyawan training tidak ditemukan"));

        karyawanTrainingRepository.deleteById(karyawanTraining.getId());
    }

    private Date parseDate(String tanggal) {
        String sourcePattern = "yyyy-MM-dd HH:mm:ss";
        Date date;

        try {
            SimpleDateFormat sourceFormat = new SimpleDateFormat(sourcePattern);
            date = sourceFormat.parse(tanggal);
            return date;
        } catch (ParseException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error, gagal parsing tanggal");
        }
    }
}
