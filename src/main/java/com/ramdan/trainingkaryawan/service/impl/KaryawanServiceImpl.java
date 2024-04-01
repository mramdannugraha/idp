package com.ramdan.trainingkaryawan.service.impl;

import com.ramdan.trainingkaryawan.dto.CreateKaryawanRequest;
import com.ramdan.trainingkaryawan.dto.UpdateKaryawanRequest;
import com.ramdan.trainingkaryawan.model.DetailKaryawan;
import com.ramdan.trainingkaryawan.model.Karyawan;
import com.ramdan.trainingkaryawan.repository.DetailKaryawanRepository;
import com.ramdan.trainingkaryawan.repository.KaryawanRepository;
import com.ramdan.trainingkaryawan.service.KaryawanService;
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
public class KaryawanServiceImpl implements KaryawanService {

    public static final Logger logger = LoggerFactory.getLogger(KaryawanServiceImpl.class);

    @Autowired
    private ValidationService validationService;

    @Autowired
    private KaryawanRepository karyawanRepository;

    @Autowired
    private DetailKaryawanRepository detailKaryawanRepository;

    @Transactional
    @Override
    public Karyawan insertKaryawanAndDetail(CreateKaryawanRequest request) {
        validationService.validate(request);
        validationService.validate(request.getDetailKaryawan());

        Karyawan karyawan = new Karyawan();
        karyawan.setNama(request.getNama());
        karyawan.setDob(request.getDob());
        karyawan.setAlamat(request.getAlamat());
        karyawan.setStatus(request.getStatus());

        DetailKaryawan detailKaryawan = new DetailKaryawan();
        detailKaryawan.setNik(request.getDetailKaryawan().getNik());
        detailKaryawan.setNpwp(request.getDetailKaryawan().getNpwp());

        karyawan.setDetailKaryawan(detailKaryawan);

        Karyawan newKaryawan = karyawanRepository.save(karyawan);
        DetailKaryawan newDetailKaryawan = detailKaryawanRepository.save(detailKaryawan);
        newKaryawan.setDetailKaryawan(newDetailKaryawan);

        return newKaryawan;
    }

    @Transactional
    @Override
    public Karyawan updateKaryawanAndDetail(UpdateKaryawanRequest request) {
        validationService.validate(request);
        validationService.validate(request.getDetailKaryawan());

        Karyawan karyawan = karyawanRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_karyawan: not found"));
        DetailKaryawan detailKaryawan = detailKaryawanRepository.findById(request.getDetailKaryawan().getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "detail_karyawan: not found"));

        if (!Objects.equals(request.getNama(), "")) {
            karyawan.setNama(request.getNama());
        }

        if (!Objects.equals(request.getDob(), "")) {
            karyawan.setDob(request.getDob());
        }

        if (!Objects.equals(request.getAlamat(), "")) {
            karyawan.setAlamat(request.getAlamat());
        }

        if (!Objects.equals(request.getStatus(), "")) {
            karyawan.setStatus(request.getStatus());
        }

        if (!Objects.equals(request.getDetailKaryawan().getNik(), "")) {
            detailKaryawan.setNik(request.getDetailKaryawan().getNik());
        }

        if (!Objects.equals(request.getDetailKaryawan().getNpwp(), "")) {
            detailKaryawan.setNpwp(request.getDetailKaryawan().getNpwp());
        }

        Date date = new Date(System.currentTimeMillis());
        karyawan.setUpdated_date(date);
        detailKaryawan.setUpdated_date(date);

        Karyawan updatedKaryawan = karyawanRepository.save(karyawan);
        DetailKaryawan updatedDetailKaryawan = detailKaryawanRepository.save(detailKaryawan);
        updatedKaryawan.setDetailKaryawan(updatedDetailKaryawan);

        return updatedKaryawan;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Karyawan> getAll(int page, int size) {
        Pageable data = PageRequest.of(page,size, Sort.by(Sort.Order.asc("id")));
        Page<Karyawan> list = karyawanRepository.findAll(data);
        return list;
    }

    @Transactional(readOnly = true)
    @Override
    public Karyawan getById(Long id) {
        Karyawan karyawan = karyawanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_karyawan: not found"));

        return karyawan;
    }

    @Transactional
    @Override
    public void deleteKaryawan(Long id) {
        Karyawan karyawan = karyawanRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "id_karyawan: not found"));

        karyawanRepository.deleteById(karyawan.getId());
    }
}
