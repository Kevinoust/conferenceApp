package com.conference.attendeeticketservice.controller;

import com.conference.attendeeticketservice.dto.AttendeeTicketDTO;
import com.conference.attendeeticketservice.dto.AttendeeTicketDtlVO;
import com.conference.attendeeticketservice.dto.AttendeeTicketSumVO;
import com.conference.attendeeticketservice.dto.SuccessResponse;
import com.conference.attendeeticketservice.entity.AttendeeTicket;
import com.conference.attendeeticketservice.service.AttendeeTicketService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Version;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attendeeTickets")
public class AttendeeTicketController {
    @Autowired
    private AttendeeTicketService service;

    @GetMapping
    public SuccessResponse<?> getAll() {
        List<AttendeeTicket> attendeeTickets = service.getAllAttendeeTickets();
        return new SuccessResponse<>(HttpStatus.OK, attendeeTickets.stream().map(this::entityToSumVO).collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public SuccessResponse<?> getOne(@PathVariable Long id) {
        AttendeeTicket attendTicket = service.getAttendTicket(id);
        return new SuccessResponse<>(HttpStatus.OK, attendTicket);
    }

    @PostMapping
    public SuccessResponse<?> create(@RequestBody @Valid AttendeeTicketDTO attendeeTicketDTO) {
        return new SuccessResponse<>(HttpStatus.OK, service.createAttendTicket(attendeeTicketDTO));
    }

    @PutMapping("{id}")
    public SuccessResponse<?> update(@PathVariable Long id, @RequestBody @Valid AttendeeTicketDTO attendeeTicketDTO) {
        return new SuccessResponse<>(HttpStatus.OK, service.updateAttendTicket(id, attendeeTicketDTO));
    }

    @DeleteMapping("{id}")
    public SuccessResponse<?> delete(@PathVariable Long id) {
        service.deleteAttendTicket(id);
        return new SuccessResponse<>(HttpStatus.NO_CONTENT,"");
    }

    private AttendeeTicketSumVO entityToSumVO(AttendeeTicket attendeeTicket) {
        AttendeeTicketSumVO attendeeTicketSumVO = new AttendeeTicketSumVO();
        attendeeTicketSumVO.setAttendeeId(attendeeTicket.getAttendee().getAttendeeId());
        attendeeTicketSumVO.setAttendeeTicketId(attendeeTicket.getAttendeeTicketId());
        attendeeTicketSumVO.setDiscountCodeId(attendeeTicket.getDiscountCode().getDiscountCodeId());
        attendeeTicketSumVO.setNetPrice(attendeeTicket.getNetPrice());
        attendeeTicketSumVO.setTicketPriceId(attendeeTicket.getTicketPrice().getTicketPriceId());
        return attendeeTicketSumVO;
    }

    private AttendeeTicketDtlVO entityToDtlVO(AttendeeTicket attendeeTicket) {
        AttendeeTicketDtlVO attendeeTicketDtlVO = new AttendeeTicketDtlVO();
        BeanUtils.copyProperties(attendeeTicket, attendeeTicketDtlVO);
        BeanUtils.copyProperties(attendeeTicket.getAttendee(), attendeeTicketDtlVO.getAttendee());
        BeanUtils.copyProperties(attendeeTicket.getDiscountCode(), attendeeTicketDtlVO.getDiscountCode());
        BeanUtils.copyProperties(attendeeTicket.getTicketPrice(), attendeeTicketDtlVO.getTicketPrice());
        BeanUtils.copyProperties(attendeeTicket.getTicketPrice().getPricingCategory(), attendeeTicketDtlVO.getTicketPrice().getPricingCategory());
        BeanUtils.copyProperties(attendeeTicket.getTicketPrice().getTicketType(), attendeeTicketDtlVO.getTicketPrice().getTicketType());
        return attendeeTicketDtlVO;
    }
}
