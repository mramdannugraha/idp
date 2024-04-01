package com.ramdan.trainingkaryawan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "karyawan")
public class Karyawan extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", nullable = false, length = 50)
    private String nama;

    @Column(name = "dob")
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyMMdd")
    private Date dob;

    @Column(name = "alamat")
    private String alamat;

    @Column(name = "status", length = 15)
    private String status;

    @OneToOne(targetEntity = DetailKaryawan.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "detail_karyawan", referencedColumnName = "id")
    private DetailKaryawan detailKaryawan;

    @JsonIgnore
    @OneToMany(mappedBy = "karyawan", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Rekening> rekeningList;

    @JsonIgnore
    @OneToMany(mappedBy = "karyawan")
    List<KaryawanTraining> karyawanTrainingList;

    @JsonIgnore
    @OneToMany(mappedBy = "karyawan")
    List<Training> trainingList;
}
