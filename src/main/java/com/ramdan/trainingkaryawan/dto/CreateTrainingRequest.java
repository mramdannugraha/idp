package com.ramdan.trainingkaryawan.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainingRequest {
    @NotBlank
    @Size(max = 50)
    private String tema;

    @Size(max = 50)
    private String pengajar;
}
