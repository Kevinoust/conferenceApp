package com.conference.timeslotservice.controller;

import com.conference.timeslotservice.dto.ResponseDTO;
import com.conference.timeslotservice.dto.SuccessResponse;
import com.conference.timeslotservice.dto.TimeslotDTO;
import com.conference.timeslotservice.entity.Timeslot;
import com.conference.timeslotservice.service.TimeslotService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/timeslot")
@Slf4j
public class TimeslotController {
    @Autowired
    private TimeslotService timeslotService;

    @GetMapping
    public ResponseDTO getAll() {
        return new SuccessResponse<>(OK, timeslotService.getAll());
    }

    @GetMapping("{id}")
    public ResponseDTO get(@PathVariable Long id) {
        return new SuccessResponse<>(OK, timeslotService.getTimeSlot(id));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public ResponseDTO create(@Valid @RequestBody TimeslotDTO timeslotDTO) {
        return new SuccessResponse<>(CREATED, timeslotService.createTimeSlot(dtoToEntity(timeslotDTO)));
    }

    @PutMapping("{id}")
    public ResponseDTO update(@PathVariable Long id, @Valid @RequestBody TimeslotDTO timeslotDTO) {
        return new SuccessResponse<>(OK, timeslotService.updateTimeSlot(id, dtoToEntity(timeslotDTO)));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public ResponseDTO delete(@PathVariable Long id) {
        timeslotService.deleteTimeSlot(id);
        return new SuccessResponse<>(NO_CONTENT, "");
    }

    private Timeslot dtoToEntity(TimeslotDTO timeslotDTO) {
        Timeslot timeslot = new Timeslot();
        timeslot.setTimeSlotId(timeslotDTO.getTimeSlotId());
        timeslot.setTimeSlotDate(LocalDate.parse(timeslotDTO.getTimeSlotDate(), DateTimeFormatter.ISO_DATE));
        timeslot.setStartTime(LocalTime.parse(timeslotDTO.getStartTime(), DateTimeFormatter.ISO_TIME));
        timeslot.setEndTime(LocalTime.parse(timeslotDTO.getEndTime(), DateTimeFormatter.ISO_TIME));
        timeslot.setIsKeynoteTimeSlot(timeslotDTO.getIsKeynoteTimeSlot());
        return timeslot;
    }
}
