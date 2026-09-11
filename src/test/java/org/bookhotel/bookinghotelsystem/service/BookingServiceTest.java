package org.bookhotel.bookinghotelsystem.service;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.dto.BookingRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.*;
import org.bookhotel.bookinghotelsystem.enums.BookingStatus;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.repository.BookingRepository;
import org.bookhotel.bookinghotelsystem.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private UserService userService;
    @Mock
    private RoomService roomService;
    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private BookingRequestDTO bookingRequestDTO;
    private Room room;
    private User user;
    private Authority authority;
    private Role role;

    @BeforeEach
    public void init() {
        room = Room.builder().id(1).roomNumber("101").price(1L)
                .status(RoomStatus.AVAILABLE).capacity(1).build();

        authority = Authority.builder().authority("authority").build();
        role = Role.builder().id(1).role("role").authorities(Set.of(authority)).build();
        user = User.builder().id(1).username("username").password("password")
                .roles(Set.of(role)).email("example@email.com").build();

        booking = Booking.builder().id(1).bookingCode("bookingCode").startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(5)).createdAt(LocalDateTime.now()).
                user(user).room(room).status(BookingStatus.PENDING).build();
        bookingRequestDTO = BookingRequestDTO.builder().startDate(LocalDateTime.now().plusDays(1))
                .endDate(LocalDateTime.now().plusDays(5)).room(room).build();
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(user.getUsername(),null));

    }


    @Test
    public void addBooking_whenAdded_booking() {
        when(userService.getByUsername(anyString())).thenReturn(user);
        when(bookingRepository.existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter(
                any(), any(),any(), any()
        )).thenReturn(false);
        when(roomService.getRoomById(anyInt())).thenReturn(room);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        //Attention:
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn(user.getUsername());
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        var test = bookingService.addBooking(bookingRequestDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getId()).isEqualTo(booking.getId());
        Assertions.assertThat(test.getUser()).isEqualTo(booking.getUser());
        Assertions.assertThat(test.getStartDate()).isEqualTo(booking.getStartDate());
        Assertions.assertThat(test.getEndDate()).isEqualTo(booking.getEndDate());
        Assertions.assertThat(test.getCreatedAt()).isEqualTo(booking.getCreatedAt());
        Assertions.assertThat(test.getRoom()).isEqualTo(booking.getRoom());
        Assertions.assertThat(test.getBookingCode()).isEqualTo(booking.getBookingCode());
        Assertions.assertThat(test.getStatus()).isEqualTo(booking.getStatus());
    }

    @Test
    public void cancelBookingByBookingCode_whenCanceled_void(){

        when(bookingRepository.findBookingByBookingCode(anyString())).thenReturn(booking);
        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

          bookingService.cancelBookingByBookingCode(booking.getBookingCode());

          verify(bookingRepository).save(any(Booking.class));

    }
}
