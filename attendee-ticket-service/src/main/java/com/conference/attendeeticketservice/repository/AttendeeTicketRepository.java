package com.conference.attendeeticketservice.repository;

import com.conference.attendeeticketservice.entity.AttendeeTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendeeTicketRepository extends JpaRepository<AttendeeTicket, Long> {
}
