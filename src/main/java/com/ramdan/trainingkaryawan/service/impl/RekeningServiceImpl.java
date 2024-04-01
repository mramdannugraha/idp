package com.ramdan.trainingkaryawan.service.impl;

import com.ramdan.trainingkaryawan.dto.CreateRekeningRequest;
import com.ramdan.trainingkaryawan.dto.RekeningResponse;
import com.ramdan.trainingkaryawan.dto.UpdateRekeningRequest;
import com.ramdan.trainingkaryawan.model.Karyawan;
import com.ramdan.trainingkaryawan.model.Rekening;
import com.ramdan.trainingkaryawan.repository.KaryawanRepository;
import com.ramdan.trainingkaryawan.repository.RekeningRepository;
import com.ramdan.trainingkaryawan.service.RekeningService;
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
public class RekeningServiceImpl implements RekeningService {
    public static final Logger logger = LoggerFactory.getLogger(KaryawanServiceImpl.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private RekeningRepository rekeningRepository;

    @Transactional
    @Override
    public RekeningResponse insertRekening(CreateRekeningRequest request) {
        validationService.validate(request);

        Karyawan karyawan = karyawanRepository.findById(Long.parseLong(request.getKaryawan().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_karyawan: not found"));

        Rekening rekening = new Rekening();
        rekening.setNama(request.getNama());
        rekening.setJenis(request.getJenis());
        rekening.setRekening(request.getRekening());
        rekening.setAlamat(request.getAlamat());
        rekening.setKaryawan(karyawan);

        Rekening createdRekening = rekeningRepository.save(rekening);
        return toResponse(createdRekening);
    }

    @Transactional
    @Override
    public RekeningResponse updateRekening(UpdateRekeningRequest request) {
        validationService.validate(request);
        validationService.validate(request.getKaryawan());

        Karyawan karyawan = karyawanRepository.findById(Long.parseLong(request.getKaryawan().getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_karyawan: not found"));

        Rekening rekening = rekeningRepository.findById(Long.parseLong(request.getId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_rekening: not found"));

        if (Objects.equals(karyawan.getId(), rekening.getKaryawan().getId())) {
            if (!Objects.equals(request.getNama(), "")) {
                rekening.setNama(request.getNama());
            }

            if (!Objects.equals(request.getJenis(), "")) {
                rekening.setJenis(request.getJenis());
            }

            if (!Objects.equals(request.getRekening(), "")) {
                rekening.setRekening(request.getRekening());
            }

            if (!Objects.equals(request.getAlamat(), "")) {
                rekening.setAlamat(request.getAlamat());
            }

            Date date = new Date(System.currentTimeMillis());
            rekening.setUpdated_date(date);

            Rekening updatedRekening = rekeningRepository.save(rekening);
            return toResponse(updatedRekening);
        } else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "data tidak sesuai");
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Page<RekeningResponse> getAll(int page, int size) {
        Pageable data = PageRequest.of(page,size, Sort.by(Sort.Order.asc("id")));
        Page<Rekening> list = rekeningRepository.findAll(data);
        return list.map(this::toResponse);
    }

    @Transactional(readOnly = true)
    @Override
    public RekeningResponse getById(Long id) {
        Rekening rekening = rekeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_rekening: not found"));

        return toResponse(rekening);
    }

    @Transactional
    @Override
    public void deleteRekening(Long id) {
        Rekening rekening = rekeningRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_rekening: not found"));

        rekeningRepository.deleteById(rekening.getId());
    }

    private RekeningResponse toResponse(Rekening rekening) {
        RekeningResponse response = new RekeningResponse();
        response.setCreated_date(rekening.getCreated_date());
        response.setDeleted_date(rekening.getDeleted_date());
        response.setUpdated_date(rekening.getUpdated_date());
        response.setId(rekening.getId());
        response.setNama(rekening.getNama());
        response.setJenis(rekening.getJenis());
        response.setRekening(rekening.getRekening());
        response.setAlamat(rekening.getAlamat());

        RekeningResponse.DetailKaryawanResponse detail = new RekeningResponse.DetailKaryawanResponse();
        detail.setId(rekening.getKaryawan().getId());
        detail.setNama(rekening.getKaryawan().getNama());
        response.setKaryawan(detail);

        return response;
    }
}
