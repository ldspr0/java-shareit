package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
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

    @Query("SELECT b FROM Booking b WHERE b.item.ownerId = :ownerId ORDER BY b.dateStart DESC")
    Collection<Booking> findByOwnerIdOrderByDateStartDesc(@Param("ownerId") Long ownerId);

    // Для State.CURRENT
    @Query("SELECT b FROM Booking b " +
            "WHERE b.bookedTo  = :bookedTo " +
            "AND b.dateStart <= :now AND b.dateEnd >= :now " +
            "ORDER BY b.dateStart DESC")
    Collection<Booking> findCurrentByBookedTo(@Param("bookedTo") Long bookedTo,
                                              @Param("now") Instant now);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.ownerId = :ownerId " +
            "AND b.dateStart <= :now AND b.dateEnd >= :now " +
            "ORDER BY b.dateStart DESC")
    Collection<Booking> findCurrentByOwnerId(@Param("ownerId") Long ownerId,
                                             @Param("now") Instant now);

    // Для State.PAST
    Collection<Booking> findByBookedToIdAndDateEndBeforeOrderByDateStartDesc(Long bookerId, Instant now);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.ownerId = :ownerId AND b.dateEnd < :now " +
            "ORDER BY b.dateStart DESC")
    Collection<Booking> findPastByOwnerId(@Param("ownerId") Long ownerId,
                                          @Param("now") Instant now);

    // Для State.FUTURE
    Collection<Booking> findByBookedToIdAndDateStartAfterOrderByDateStartDesc(Long bookerId, Instant now);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.ownerId = :ownerId AND b.dateStart > :now " +
            "ORDER BY b.dateStart DESC")
    Collection<Booking> findFutureByOwnerId(@Param("ownerId") Long ownerId,
                                            @Param("now") Instant now);

    // Для State.WAITING и REJECTED
    Collection<Booking> findByBookedToIdAndStatusOrderByDateStartDesc(Long bookerId, Status status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.ownerId = :ownerId AND b.status = :status " +
            "ORDER BY b.dateStart DESC")
    Collection<Booking> findByOwnerIdAndStatus(@Param("ownerId") Long ownerId,
                                               @Param("status") Status status);

    @Query("SELECT b FROM Booking b " +
            "WHERE b.item.id = :itemId AND b.bookedTo.id = :userId")
    Optional<Booking> findByItemIdAndBookerId(
            @Param("itemId") Long itemId,
            @Param("userId") Long userId
    );

    Optional<Booking> findFirstByItemIdAndDateStartAfterOrderByDateStartAsc(long id, LocalDateTime now);

    Optional<Booking> findFirstByItemIdAndDateEndBeforeOrderByDateEndDesc(long id, LocalDateTime now);
}
