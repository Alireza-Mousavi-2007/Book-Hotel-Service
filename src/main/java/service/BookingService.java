package service;

import dto.BookingRequestDTO;
import entity.Booking;

import java.util.List;

public interface BookingService {

    public List<Booking> getAllBookings();

    public Booking getBookingByBookingCode(String bookingCode);

    public Booking getBookingById(Integer id);

    public Booking addBooking(BookingRequestDTO bookingRequestDTO);

    public void cancelBookingByBookingCode(String bookingCode); // کد رندوم تولید کن موقع اضافه کردند رزرو تو کنترلر بهش بده

    public String getAuthenticationName();

    public String makeBookingCode();
}
