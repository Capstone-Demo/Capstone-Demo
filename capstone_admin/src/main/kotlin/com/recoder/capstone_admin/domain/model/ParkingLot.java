package com.recoder.capstone_admin.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParkingLot {
    private final int no;
    private final String name;
    private final int currentUserCnt;
    private final int totalSectorCnt;

    public ParkingLot() {
        this.no = -1;
        this.name = "dummy";
        this.currentUserCnt = -1;
        this.totalSectorCnt = -1;
    }
}
