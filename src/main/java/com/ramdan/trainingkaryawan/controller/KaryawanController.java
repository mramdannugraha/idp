package com.ramdan.trainingkaryawan.controller;

import com.ramdan.trainingkaryawan.dto.CreateKaryawanRequest;
import com.ramdan.trainingkaryawan.dto.UpdateKaryawanRequest;
import com.ramdan.trainingkaryawan.model.Karyawan;
import com.ramdan.trainingkaryawan.service.KaryawanService;
import com.ramdan.trainingkaryawan.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/v1/idstar/karyawan")
public class KaryawanController {
    @Autowired
    private KaryawanService karyawanService;

    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Karyawan> insert(@RequestBody CreateKaryawanRequest request) {
        Karyawan karyawanResponse = karyawanService.insertKaryawanAndDetail(request);
        return GenerateResponse.<Karyawan>builder()
                .code(HttpStatus.OK.value())
                .data(karyawanResponse)
                .status("sukses")
                .build();
    }

    @PutMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Karyawan> update(@RequestBody UpdateKaryawanRequest request) {
        Karyawan karyawanResponse = karyawanService.updateKaryawanAndDetail(request);
        return GenerateResponse.<Karyawan>builder()
                .code(HttpStatus.OK.value())
                .data(karyawanResponse)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Page<Karyawan>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                             @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<Karyawan> list = karyawanService.getAll(page, size);
        return GenerateResponse.<Page<Karyawan>>builder()
                .code(HttpStatus.OK.value())
                .data(list)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Karyawan> getById(@PathVariable("id") Long id) {
        Karyawan karyawan = karyawanService.getById(id);
        return GenerateResponse.<Karyawan>builder()
                .code(HttpStatus.OK.value())
                .data(karyawan)
                .status("sukses")
                .build();
    }

    @DeleteMapping(
            path = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<String> delete(@RequestBody Map<String, Object> payload) {
        karyawanService.deleteKaryawan(Long.parseLong(payload.get("id").toString()));
        return GenerateResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .data("Sukses")
                .status("sukses")
                .build();
    }
}
