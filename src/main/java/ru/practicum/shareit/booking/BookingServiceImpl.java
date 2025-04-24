package ru.practicum.shareit.booking;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.booking.dto.BookingDto;
import ru.practicum.shareit.booking.dto.NewBookingRequest;
import ru.practicum.shareit.booking.mapper.BookingMapper;
import ru.practicum.shareit.exception.ConditionsNotMetException;
import ru.practicum.shareit.exception.NotFoundException;
import ru.practicum.shareit.item.Item;
import ru.practicum.shareit.item.ItemRepository;
import ru.practicum.shareit.user.User;
import ru.practicum.shareit.user.UserRepository;
import ru.practicum.shareit.validations.ServiceValidations;

import java.time.Instant;
import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final ServiceValidations serviceValidations;
    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public Collection<BookingDto> getAllBookingsByOwner(long userId, String stateStr) {
        serviceValidations.checkIfUserExistsOrThrowError(userId);

        State state = parseState(stateStr);
        Instant now = Instant.now();

        Collection<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.findByOwnerIdOrderByDateStartDesc(userId);
            case CURRENT -> bookingRepository.findCurrentByOwnerId(userId, now);
            case PAST -> bookingRepository.findPastByOwnerId(userId, now);
            case FUTURE -> bookingRepository.findFutureByOwnerId(userId, now);
            case WAITING -> bookingRepository.findByOwnerIdAndStatus(userId, Status.WAITING);
            case REJECTED -> bookingRepository.findByOwnerIdAndStatus(userId, Status.REJECTED);
            default -> throw new ConditionsNotMetException("Неизвестный параметр: " + state);
        };

        return bookings.stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<BookingDto> getAllBookingsByUser(long userId, String stateStr) {
        State state = parseState(stateStr);
        Instant now = Instant.now();

        Collection<Booking> bookings = switch (state) {
            case ALL -> bookingRepository.findByBookedToIdOrderByDateStartDesc(userId);
            case CURRENT -> bookingRepository.findCurrentByBookedTo(userId, now);
            case PAST -> bookingRepository.findByBookedToIdAndDateEndBeforeOrderByDateStartDesc(userId, now);
            case FUTURE -> bookingRepository.findByBookedToIdAndDateStartAfterOrderByDateStartDesc(userId, now);
            case WAITING -> bookingRepository.findByBookedToIdAndStatusOrderByDateStartDesc(userId, Status.WAITING);
            case REJECTED -> bookingRepository.findByBookedToIdAndStatusOrderByDateStartDesc(userId, Status.REJECTED);
            default -> throw new ConditionsNotMetException("Неизвестный параметр: " + state);
        };

        return bookings.stream()
                .map(BookingMapper::mapToBookingDto)
                .collect(Collectors.toList());
    }

    @Override
    public BookingDto getBooking(long userId, long id) {
        Booking booking = bookingRepository.findByIdAndUserId(id, userId).orElseThrow(() -> new NotFoundException("Резервирование не найдено"));
        return BookingMapper.mapToBookingDto(booking);
    }

    @Override
    public BookingDto setApproval(long userId, long id, boolean approved) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Бронирование не найдено"));
        Item item = itemRepository.findById(booking.getItem().getId())
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        if (item.getOwnerId() != userId) {
            throw new ConditionsNotMetException("Предмет вам не принадлежит");
        }

        booking.setStatus(approved ? Status.APPROVED : Status.REJECTED);

        return BookingMapper.mapToBookingDto(booking);

    }

    @Override
    public BookingDto createBooking(long userId, NewBookingRequest request) {

        serviceValidations.checkIfUserExistsOrThrowError(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Пользователь не найден"));
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new NotFoundException("Предмет не найден"));
        if (!item.isAvailable()) {
            throw new ConditionsNotMetException("Предмет недоступен");
        }

        Booking booking = BookingMapper.mapToBooking(request);
        booking.setStatus(Status.WAITING);
        booking.setBookedTo(user);
        booking.setItem(item);
        booking = bookingRepository.save(booking);

        return BookingMapper.mapToBookingDto(booking);
    }

    private State parseState(String stateStr) {
        try {
            return State.valueOf(stateStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConditionsNotMetException("Неизвестный параметр: " + stateStr);
        }
    }
}
