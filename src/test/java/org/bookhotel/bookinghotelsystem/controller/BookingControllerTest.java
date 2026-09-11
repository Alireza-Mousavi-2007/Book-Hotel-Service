package org.bookhotel.bookinghotelsystem.controller;

import org.bookhotel.bookinghotelsystem.dto.BookingRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.*;
import org.bookhotel.bookinghotelsystem.enums.BookingStatus;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.security.jwt.JwtToken;
import org.bookhotel.bookinghotelsystem.service.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookingController.class)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private BookingService bookingService;
    @MockitoBean
    private JwtToken jwtToken;
    @MockitoBean
    private UserDetailsService userDetailsService;


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
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getAllBookings_whenGet_responseEntityListOfBookings() throws Exception {
        when(bookingService.getAllBookings()).thenReturn(List.of(booking));

        mockMvc.perform(get("/api/bookings/admin"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].bookingCode").value(booking.getBookingCode()));

    }



    @Test
    @WithMockUser(authorities = {"READ"})
    public void getBookingByBookingCode_whenGet_responseEntityBookingRequestDTO() throws Exception {
        when(bookingService.getBookingByBookingCode(anyString())).thenReturn(bookingRequestDTO);

        mockMvc.perform(get("/api/bookings/{bookingCode}", booking.getBookingCode()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.startDate").value(booking.getStartDate().toString().replaceAll("0+$", "")))
                .andExpect(jsonPath("$.endDate").value(booking.getEndDate().toString().replaceAll("0+$", "")));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getBookingById_whenGet_responseEntityBooking() throws Exception {
        when(bookingService.getBookingById(anyInt())).thenReturn(booking);

        mockMvc.perform(get("/api/bookings/admin/{id}", booking.getId()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.bookingCode").value(booking.getBookingCode()));

    }


    @Test
    @WithMockUser(authorities = {"CREATE"})
    public void addBooking_whenGet_responseEntityBooking() throws Exception {
        when(bookingService.addBooking(any(BookingRequestDTO.class))).thenReturn(booking);

        mockMvc.perform(post("/api/bookings").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookingRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.bookingCode").value(booking.getBookingCode()));
    }

    @Test
    @WithMockUser(authorities = {"CANCEL"})
    public void cancelBookingByBookingCode_whenCancel_responseEntityString() throws Exception {
        mockMvc.perform(patch("/api/bookings/{bookingCode}/cancel", booking.getBookingCode())
                        .with(csrf()))
                .andExpect(status().isOk());
        verify(bookingService).cancelBookingByBookingCode(anyString());
    }


}
