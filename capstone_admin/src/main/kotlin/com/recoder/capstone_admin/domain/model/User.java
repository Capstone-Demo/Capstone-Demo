package com.recoder.capstone_admin.domain.model;

import com.recoder.capstone_admin.domain.repository.reservation.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@Entity
@Table(name="user")
public class User implements Serializable {

    @Id
    @Column(name="id")
    private final String id;

    @Column(name="pw")
    private final String pw;

    @Column(name="phone")
    private final String phone;

    @Column(name="carno")
    private final String carNo;

    @Column(name="is_master")
    private final boolean isMaster;

    @OneToOne(fetch=FetchType.LAZY,
            mappedBy="user",
            cascade=CascadeType.ALL)
    private Reservation reservation;

    public User() {
        this.id="dummy";
        this.pw="dummy";
        this.phone="dummy";
        this.carNo="dummy";
        this.isMaster = false;
    }

}
