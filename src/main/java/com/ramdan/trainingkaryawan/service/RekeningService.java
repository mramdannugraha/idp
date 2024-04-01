package com.ramdan.trainingkaryawan.service;

import com.ramdan.trainingkaryawan.dto.CreateRekeningRequest;
import com.ramdan.trainingkaryawan.dto.RekeningResponse;
import com.ramdan.trainingkaryawan.dto.UpdateRekeningRequest;
import org.springframework.data.domain.Page;

public interface RekeningService {
    RekeningResponse insertRekening(CreateRekeningRequest request);
    RekeningResponse updateRekening(UpdateRekeningRequest request);
    Page<RekeningResponse> getAll(int page, int size);
    RekeningResponse getById(Long id);
    void deleteRekening(Long id);
}
