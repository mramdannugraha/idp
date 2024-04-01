package com.ramdan.trainingkaryawan.repository;

import com.ramdan.trainingkaryawan.model.Rekening;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RekeningRepository extends PagingAndSortingRepository<Rekening, Long> {
}
