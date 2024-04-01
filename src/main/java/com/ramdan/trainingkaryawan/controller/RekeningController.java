package com.ramdan.trainingkaryawan.controller;

import com.ramdan.trainingkaryawan.dto.CreateRekeningRequest;
import com.ramdan.trainingkaryawan.dto.RekeningResponse;
import com.ramdan.trainingkaryawan.dto.UpdateRekeningRequest;
import com.ramdan.trainingkaryawan.service.RekeningService;
import com.ramdan.trainingkaryawan.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/v1/idstar/rekening")
public class RekeningController {
    @Autowired
    private RekeningService rekeningService;

    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<RekeningResponse> insert(@RequestBody CreateRekeningRequest request) {
        RekeningResponse response = rekeningService.insertRekening(request);
        return GenerateResponse.<RekeningResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .status("sukses")
                .build();
    }

    @PutMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<RekeningResponse> update(@RequestBody UpdateRekeningRequest request) {
        RekeningResponse response = rekeningService.updateRekening(request);
        return GenerateResponse.<RekeningResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Page<RekeningResponse>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<RekeningResponse> list = rekeningService.getAll(page, size);
        return GenerateResponse.<Page<RekeningResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(list)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<RekeningResponse> getById(@PathVariable("id") Long id) {
        RekeningResponse response = rekeningService.getById(id);
        return GenerateResponse.<RekeningResponse>builder()
                .code(HttpStatus.OK.value())
                .data(response)
                .status("sukses")
                .build();
    }

    @DeleteMapping(
            path = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<String> delete(@RequestBody Map<String, Object> payload) {
        rekeningService.deleteRekening(Long.parseLong(payload.get("id").toString()));
        return GenerateResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .data("Sukses")
                .status("sukses")
                .build();
    }
}
