package com.tickets.repository;

import com.tickets.entity.ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ticketRepository extends JpaRepository<ticket,Long> {
    List<ticket> findByticketState(int ticketState);


    @Query("SELECT t FROM ticket t WHERE t.ticketState = 1")
    List<ticket> findByTicketState();

    @Query("SELECT t FROM ticket t WHERE t.ticketState = 0")
    List<ticket> findClosedtickets();
}