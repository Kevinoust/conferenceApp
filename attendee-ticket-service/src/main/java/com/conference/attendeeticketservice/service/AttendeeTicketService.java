package com.conference.attendeeticketservice.service;

import com.conference.attendeeticketservice.dto.AttendeeTicketDTO;
import com.conference.attendeeticketservice.entity.Attendee;
import com.conference.attendeeticketservice.entity.AttendeeTicket;
import com.conference.attendeeticketservice.entity.DiscountCode;
import com.conference.attendeeticketservice.entity.TicketPrice;
import com.conference.attendeeticketservice.exception.ResourceNotFoundException;
import com.conference.attendeeticketservice.repository.AttendeeRepository;
import com.conference.attendeeticketservice.repository.AttendeeTicketRepository;
import com.conference.attendeeticketservice.repository.DiscountCodeRepository;
import com.conference.attendeeticketservice.repository.TicketPriceRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AttendeeTicketService {
    @Autowired
    private AttendeeTicketRepository repository;

    @Autowired
    private AttendeeRepository attendeeRepository;

    @Autowired
    private TicketPriceRepository ticketPriceRepository;

    @Autowired
    private DiscountCodeRepository discountCodeRepository;

    @Transactional(readOnly = true)
    public List<AttendeeTicket> getAllAttendeeTickets() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public AttendeeTicket getAttendTicket(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendee Ticket id=" + id));
    }

    public AttendeeTicket createAttendTicket(AttendeeTicketDTO attendeeTicketDTO) {
        AttendeeTicket attendeeTicket = new AttendeeTicket();
        attendeeTicket.setAttendeeTicketId(attendeeTicketDTO.getAttendeeTicketId());

        Attendee attendee = getAttendee(attendeeTicketDTO.getAttendeeId());
        DiscountCode discountCode = attendeeTicketDTO.getDiscountCodeId() != null ? getDiscountCode(attendeeTicketDTO.getDiscountCodeId()) : null;
        TicketPrice ticketPrice = getTicketPrice(attendeeTicketDTO.getTicketPriceId());

        attendeeTicket.setAttendee(attendee);
        attendeeTicket.setDiscountCode(discountCode);
        attendeeTicket.setTicketPrice(ticketPrice);
        attendeeTicket.setNetPrice(ticketPrice.getBasePrice() - (discountCode != null ? discountCode.getDiscountAmount() : 0.0));

        return repository.save(attendeeTicket);
    }

    public AttendeeTicket updateAttendTicket(Long id, AttendeeTicketDTO attendeeTicketDTO) {
        AttendeeTicket existingAttendTicket = getAttendTicket(id);

        Attendee attendee = getAttendee(attendeeTicketDTO.getAttendeeId());
        DiscountCode discountCode = attendeeTicketDTO.getDiscountCodeId() != null ? getDiscountCode(attendeeTicketDTO.getDiscountCodeId()) : null;
        TicketPrice ticketPrice = getTicketPrice(attendeeTicketDTO.getTicketPriceId());

        existingAttendTicket.setAttendee(attendee);
        existingAttendTicket.setDiscountCode(discountCode);
        existingAttendTicket.setTicketPrice(ticketPrice);
        existingAttendTicket.setNetPrice(ticketPrice.getBasePrice() - (discountCode != null ? discountCode.getDiscountAmount() : 0.0));

        return repository.save(existingAttendTicket);
    }

    public void deleteAttendTicket(Long id) {
        getAttendTicket(id);
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Attendee getAttendee(Long id) {
        return attendeeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Attendee id=" + id));
    }

    @Transactional(readOnly = true)
    public TicketPrice getTicketPrice(Long id) {
        return ticketPriceRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("TicketPrice id=" + id));
    }

    @Transactional(readOnly = true)
    public DiscountCode getDiscountCode(Long id) {
        return discountCodeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("DiscountCode id=" + id));
    }
}
