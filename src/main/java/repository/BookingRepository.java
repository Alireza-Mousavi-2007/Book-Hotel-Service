package repository;

import entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    public Booking findBookingByBookingCode(String bookingCode);
}
