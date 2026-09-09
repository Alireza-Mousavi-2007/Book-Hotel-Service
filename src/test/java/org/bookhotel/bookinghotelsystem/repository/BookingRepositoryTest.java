package org.bookhotel.bookinghotelsystem.repository;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.entity.*;
import org.bookhotel.bookinghotelsystem.enums.BookingStatus;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.Set;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class BookingRepositoryTest {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoomRepository roomRepository;


    private Booking booking;
    private Authority authority;
    private Role role;
    private User user;
    private Room room;

    @BeforeEach
    public void init() {
        authority = Authority.builder()
                .authority("authority")
                .build();
        authority = authorityRepository.save(authority);

        role = Role.builder().role("role").authorities(Set.of(authority)).build();
        role = roleRepository.save(role);

        user = User.builder().username("username")
                .email("example@email.com")
                .password("password")
                .roles(Set.of(role)).build();
        user = userRepository.save(user);

        room = Room.builder().roomNumber("101").capacity(1).price(1L).status(RoomStatus.AVAILABLE).build();
        room = roomRepository.save(room);

        booking = Booking.builder().bookingCode("100").room(room).startDate(LocalDateTime.now().plusDays(1)).createdAt(LocalDateTime.now())
                .endDate(LocalDateTime.now().plusDays(5)).status(BookingStatus.PENDING).user(user).build();

        booking = bookingRepository.save(booking);
    }

    @Test
    public void findBookingByBookingCode_whenFound_booking() {
        var tested = bookingRepository.findBookingByBookingCode(booking.getBookingCode());

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getBookingCode()).isEqualTo(booking.getBookingCode());
        Assertions.assertThat(tested.getUser()).isEqualTo(booking.getUser());
        Assertions.assertThat(tested.getEndDate()).isEqualTo(booking.getEndDate());
        Assertions.assertThat(tested.getRoom()).isEqualTo(booking.getRoom());
        Assertions.assertThat(tested.getStatus()).isEqualTo(booking.getStatus());
        Assertions.assertThat(tested.getStartDate()).isEqualTo(booking.getStartDate());
        Assertions.assertThat(tested.getCreatedAt()).isEqualTo(booking.getCreatedAt());
        Assertions.assertThat(tested.getId()).isEqualTo(booking.getId());
    }

    @Test
    public void existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter_ifExists_boolean() {
        var tested = bookingRepository.existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter(
                booking.getRoom(),
                booking.getStatus(),
                booking.getEndDate().minusDays(1),
                booking.getStartDate().plusDays(1)
        );
        Assertions.assertThat(tested).isTrue();
    }

    @Test
    public void existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter_ifNotExists_boolean() {
        var tested = bookingRepository.existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter(
                booking.getRoom(),
                booking.getStatus(),
                booking.getEndDate().minusDays(51),
                booking.getStartDate().minusDays(50)
        );

        Assertions.assertThat(tested).isFalse();
    }

}
