package com.ramdan.trainingkaryawan.controller;

import com.ramdan.trainingkaryawan.dto.CreateKaryawanTrainingRequest;
import com.ramdan.trainingkaryawan.dto.UpdateKaryawanTrainingRequest;
import com.ramdan.trainingkaryawan.model.KaryawanTraining;
import com.ramdan.trainingkaryawan.service.KaryawanTrainingService;
import com.ramdan.trainingkaryawan.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/v1/idstar/karyawan-training")
public class KaryawanTrainingController {
    @Autowired
    private KaryawanTrainingService karyawanTrainingService;

    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<KaryawanTraining> insert(@RequestBody CreateKaryawanTrainingRequest request) {
        KaryawanTraining karyawanTraining = karyawanTrainingService.create(request);
        return GenerateResponse.<KaryawanTraining>builder()
                .code(HttpStatus.OK.value())
                .data(karyawanTraining)
                .status("sukses")
                .build();
    }

    @PutMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<KaryawanTraining> update(@RequestBody UpdateKaryawanTrainingRequest request) {
        KaryawanTraining karyawanTraining = karyawanTrainingService.update(request);
        return GenerateResponse.<KaryawanTraining>builder()
                .code(HttpStatus.OK.value())
                .data(karyawanTraining)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Page<KaryawanTraining>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<KaryawanTraining> list = karyawanTrainingService.getAll(page, size);
        return GenerateResponse.<Page<KaryawanTraining>>builder()
                .code(HttpStatus.OK.value())
                .data(list)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<KaryawanTraining> get(@PathVariable("id") Long id) {
        KaryawanTraining karyawanTraining = karyawanTrainingService.getById(id);
        return GenerateResponse.<KaryawanTraining>builder()
                .code(HttpStatus.OK.value())
                .data(karyawanTraining)
                .status("sukses")
                .build();
    }

    @DeleteMapping(
            path = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<String> delete(@RequestBody Map<String, Object> payload) {
        karyawanTrainingService.delete(Long.parseLong(payload.get("id").toString()));
        return GenerateResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .data("Sukses")
                .status("sukses")
                .build();
    }
}
