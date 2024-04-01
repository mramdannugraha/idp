package com.ramdan.trainingkaryawan;

import com.ramdan.trainingkaryawan.controller.fileupload.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
@EnableScheduling
public class TrainingKaryawanApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrainingKaryawanApplication.class, args);
    }

}
