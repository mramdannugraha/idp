package com.ramdan.trainingkaryawan.repository;

import com.ramdan.trainingkaryawan.model.Training;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainingRepository extends PagingAndSortingRepository<Training, Long> {
}
