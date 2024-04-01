package com.ramdan.trainingkaryawan.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "karyawan_training")
public class KaryawanTraining extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tanggal")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Jakarta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date tanggal;

    @ManyToOne(targetEntity = Karyawan.class)
    @JoinColumn(name = "id_karyawan")
    private Karyawan karyawan;

    @ManyToOne(targetEntity = Training.class)
    @JoinColumn(name = "id_training")
    private Training training;
}
