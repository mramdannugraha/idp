package com.ramdan.trainingkaryawan.controller;

import com.ramdan.trainingkaryawan.dto.CreateTrainingRequest;
import com.ramdan.trainingkaryawan.dto.UpdateTrainingRequest;
import com.ramdan.trainingkaryawan.model.Training;
import com.ramdan.trainingkaryawan.service.TrainingService;
import com.ramdan.trainingkaryawan.utils.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/v1/idstar/training")
public class TrainingController {
    @Autowired
    private TrainingService trainingService;

    @PostMapping(
            path = "/save",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Training> insert(@RequestBody CreateTrainingRequest request) {
        Training createdTraining = trainingService.createTraining(request);
        return GenerateResponse.<Training>builder()
                .code(HttpStatus.OK.value())
                .data(createdTraining)
                .status("sukses")
                .build();
    }

    @PutMapping(
            path = "/update",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Training> update(@RequestBody UpdateTrainingRequest request) {
        Training updatedTraining = trainingService.updateTraining(request);
        return GenerateResponse.<Training>builder()
                .code(HttpStatus.OK.value())
                .data(updatedTraining)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/list",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Page<Training>> getAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<Training> list = trainingService.getAll(page, size);
        return GenerateResponse.<Page<Training>>builder()
                .code(HttpStatus.OK.value())
                .data(list)
                .status("sukses")
                .build();
    }

    @GetMapping(
            path = "/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<Training> get(@PathVariable("id") Long id) {
        Training training = trainingService.getById(id);
        return GenerateResponse.<Training>builder()
                .code(HttpStatus.OK.value())
                .data(training)
                .status("sukses")
                .build();
    }

    @DeleteMapping(
            path = "/delete",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public GenerateResponse<String> delete(@RequestBody Map<String, Object> payload) {
        trainingService.delete(Long.parseLong(payload.get("id").toString()));
        return GenerateResponse.<String>builder()
                .code(HttpStatus.OK.value())
                .data("Sukses")
                .status("sukses")
                .build();
    }
}
