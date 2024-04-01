package com.ramdan.trainingkaryawan.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "detail_karyawan")
public class DetailKaryawan extends AbstractDate implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nik", length = 30)
    private String nik;

    @Column(name = "npwp", length = 30)
    private String npwp;

    @JsonIgnore
    @OneToOne(mappedBy = "detailKaryawan")
    private Karyawan karyawan;
}
