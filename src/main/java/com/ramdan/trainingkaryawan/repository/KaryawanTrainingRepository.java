package com.ramdan.trainingkaryawan.repository;

import com.ramdan.trainingkaryawan.model.KaryawanTraining;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanTrainingRepository extends PagingAndSortingRepository<KaryawanTraining, Long> {
}
