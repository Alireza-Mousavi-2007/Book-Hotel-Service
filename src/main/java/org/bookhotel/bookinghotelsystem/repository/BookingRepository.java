package org.bookhotel.bookinghotelsystem.repository;

import org.bookhotel.bookinghotelsystem.entity.Booking;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface BookingRepository extends JpaRepository<Booking, Integer> {

    public Booking findBookingByBookingCode(String bookingCode);

    //متد تداخل داشتن
    public boolean existsByRoomAndStatusAndStartDateBeforeAndEndDateAfter(
            Room room,// این اتاق وجود داره ؟
            BookingStatus status,// وضعیت اتاق چی هست ؟نباید دسترس باشه
            LocalDateTime endDate, // شرط تداخل زمان
            LocalDateTime startDate // شرط تداخل زمان

    );


}
