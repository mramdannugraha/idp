package com.ramdan.trainingkaryawan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "training")
public class Training extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pengajar", length = 50)
    private String pengajar;

    @Column(name = "tema", length = 50)
    private String tema;

    @JsonIgnore
    @OneToMany(mappedBy = "training")
    List<KaryawanTraining> karyawanTrainingList;

    @JsonIgnore
    @ManyToOne(targetEntity = Karyawan.class)
    @JoinColumn(name = "id_karyawan")
    private Karyawan karyawan;
}
