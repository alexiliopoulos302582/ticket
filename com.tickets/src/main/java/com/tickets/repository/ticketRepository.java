package com.tickets.repository;

import com.tickets.entity.ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ticketRepository extends JpaRepository<ticket,Long> {
    List<ticket> findByticketState(int ticketState);

    @Query("SELECT t FROM ticket t WHERE "+
            "(t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.fullName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.userName LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.department LIKE %?1% OR " +
            "t.helpTopic LIKE %?1% OR " +
            "t.priority LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.assignedTo LIKE %?1%) AND t.ticketState = 0 ")
    List<ticket> findAllByKeyword(String keyword);
    @Query("SELECT t FROM ticket t WHERE t.ticketState = 0")
    List<ticket> findClosedtickets();
    Optional<ticket> findById(Long id);

    @Query("SELECT t FROM ticket t WHERE "+
        "(t.phoneNumber LIKE %?1% OR " +
        "t.city LIKE %?1% OR " +
        "t.fullName LIKE %?1% OR " +
        "t.email LIKE %?1% OR " +
        "t.serialNumber LIKE %?1% OR " +
        "t.userName LIKE %?1% OR " +
        "t.subject LIKE %?1% OR " +
        "t.department LIKE %?1% OR " +
        "t.helpTopic LIKE %?1% OR " +
        "t.priority LIKE %?1% OR " +
        "t.deviceModel LIKE %?1% OR " +
        "t.category LIKE %?1% OR " +
        "t.assignedTo LIKE %?1%) AND t.ticketState = 0 ")
    List<ticket> findClosedticketsBySearch(String search);


    @Query("SELECT t FROM ticket t WHERE "+
            "(t.phoneNumber LIKE %?1% OR " +
            "t.city LIKE %?1% OR " +
            "t.fullName LIKE %?1% OR " +
            "t.email LIKE %?1% OR " +
            "t.serialNumber LIKE %?1% OR " +
            "t.userName LIKE %?1% OR " +
            "t.subject LIKE %?1% OR " +
            "t.department LIKE %?1% OR " +
            "t.helpTopic LIKE %?1% OR " +
            "t.priority LIKE %?1% OR " +
            "t.deviceModel LIKE %?1% OR " +
            "t.category LIKE %?1% OR " +
            "t.assignedTo LIKE %?1%) AND t.ticketState = 1 ")
    List<ticket> findOpenticketsBySearch(String search);
}


//("SELECT t FROM ticket t WHERE "+
//        "(t.phoneNumber LIKE %?1% OR " +
//        "t.city LIKE %?1% OR " +
//        "t.fullName LIKE %?1% OR " +
//        "t.email LIKE %?1% OR " +
//        "t.serialNumber LIKE %?1% OR " +
//        "t.userName LIKE %?1% OR " +
//        "t.subject LIKE %?1% OR " +
//        "t.department LIKE %?1% OR " +
//        "t.helpTopic LIKE %?1% OR " +
//        "t.priority LIKE %?1% OR " +
//        "t.deviceModel LIKE %?1% OR " +
//        "t.category LIKE %?1% OR " +
//        "t.assignedTo LIKE %?1%) AND t.ticketState = 0 ")