package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

import java.util.Collection;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.id = :id " +
            "AND (b.item.ownerId = :userId OR b.bookedTo.id = :userId)")
    Optional<Booking> findByIdAndUserId(
            @Param("id") Long bookingId,
            @Param("userId") Long userId
    );

    // Для State.ALL
    Collection<Booking> findByBookedToIdOrderByDateStartDesc(Long bookerId);

    // Для State.CURRENT
    @Query("SELECT b FROM Booking b " +
            "WHERE b.bookedTo  = :bookedTo " +
            "AND b.dateStart <= :now AND b.dateEnd >= :now " +
            "ORDER BY b.dateStart DESC")
    Collection<Booking> findCurrentByBookedTo(@Param("bookedTo") Long bookedTo,
                                              @Param("now") Instant now);

    // Для State.PAST
    Collection<Booking> findByBookedToIdAndDateEndBeforeOrderByDateStartDesc(Long bookerId, Instant now);

    // Для State.FUTURE
    Collection<Booking> findByBookedToIdAndDateStartAfterOrderByDateStartDesc(Long bookerId, Instant now);

    // Для State.WAITING и REJECTED
    Collection<Booking> findByBookedToIdAndStatusOrderByDateStartDesc(Long bookerId, Status status);
}
