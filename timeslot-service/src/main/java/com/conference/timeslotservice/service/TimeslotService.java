package com.conference.timeslotservice.service;

import com.conference.timeslotservice.dto.TimeslotDTO;
import com.conference.timeslotservice.entity.Timeslot;
import com.conference.timeslotservice.exception.ResourceNotFoundException;
import com.conference.timeslotservice.repository.TimeslotRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TimeslotService {
    @Autowired
    private TimeslotRepository timeslotRepository;

    @Transactional(readOnly = true)
    public List<TimeslotDTO> getAll() {
        return timeslotRepository.findAllByProjection();
    }

    @Transactional(readOnly = true)
    public Timeslot getTimeSlot(Long id) {
        return timeslotRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Timeslot id=" + id));
    }

    public Timeslot createTimeSlot(Timeslot timeslot) {
        return timeslotRepository.save(timeslot);
    }

    public Timeslot updateTimeSlot(Long id, Timeslot timeslot) {
        Timeslot existingTimeslot = getTimeSlot(id);
        BeanUtils.copyProperties(timeslot, existingTimeslot, "timeSlotId");
        return timeslotRepository.save(existingTimeslot);
    }

    public void deleteTimeSlot(Long id) {
        getTimeSlot(id);
        timeslotRepository.deleteById(id);
    }
}
