package com.recoder.capstone_admin.domain.repository.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Reservation {

    private final int reservationNo;
    private final String userId;
    private final String userCar;
    private final String parkingLotName;
    private final int parkingAtSectorNo;

    public Reservation() {
        this(-1, "dummy", "dummy", "dummy", -1);
    }

}
