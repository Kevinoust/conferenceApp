package com.conference.timeslotservice.repository;

import com.conference.timeslotservice.dto.TimeslotDTO;
import com.conference.timeslotservice.entity.Timeslot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeslotRepository extends JpaRepository<Timeslot, Long> {

    @Query("SELECT new com.conference.timeslotservice.dto.TimeslotDTO(t.timeSlotId, t.timeSlotDate, t.startTime, t.endTime, t.isKeynoteTimeSlot) FROM time_slots t")
    List<TimeslotDTO> findAllByProjection();
}
