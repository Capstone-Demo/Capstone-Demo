package com.recoder.capstone_admin.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

@Data
@AllArgsConstructor
@Entity
@Table(name="reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="no")
    private Integer no;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="lot_no")
    private ParkingLot parkingLot;

    @Column(name="sector")
    private Integer sector;

}
