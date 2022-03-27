package com.recoder.capstone_admin.domain.repository.parkinglot;

import com.recoder.capstone_admin.domain.model.ParkingLot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParkingLotRepository extends JpaRepository<ParkingLot, Integer> {

    @Query("SELECT p FROM ParkingLot p WHERE p.no = :no")
    public ParkingLot findParkingLotByNo(@Param("no")int no);

    @Query("SELECT p FROM ParkingLot p WHERE p.name LIKE CONCAT('%',:search,'%')")
    public List<ParkingLot> findParkingLotListByString(@Param("search")String search);
}
