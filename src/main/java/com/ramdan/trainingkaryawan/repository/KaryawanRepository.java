package com.ramdan.trainingkaryawan.repository;

import com.ramdan.trainingkaryawan.model.Karyawan;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KaryawanRepository extends PagingAndSortingRepository<Karyawan, Long> {
}
