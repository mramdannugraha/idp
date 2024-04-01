package com.ramdan.trainingkaryawan.repository;

import com.ramdan.trainingkaryawan.model.DetailKaryawan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetailKaryawanRepository extends JpaRepository<DetailKaryawan, Long> {
}
