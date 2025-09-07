package com.movievault.repository;

import com.movievault.model.Booking;
// import com.movievault.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByShowTimeId(Long showTimeId);
    // List<Booking> findByUser(User user);
}
