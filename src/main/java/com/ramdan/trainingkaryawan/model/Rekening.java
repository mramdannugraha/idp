package com.ramdan.trainingkaryawan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rekening")
public class Rekening extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nama", length = 50)
    private String nama;

    @Column(name = "jenis", length = 15)
    private String jenis;

    @Column(name = "rekening", length = 15)
    private String rekening;

    @Column(name = "alamat")
    private String alamat;

    @JsonIgnore
    @ManyToOne(targetEntity = Karyawan.class)
    @JoinColumn(name = "id_karyawan")
    private Karyawan karyawan;
}
