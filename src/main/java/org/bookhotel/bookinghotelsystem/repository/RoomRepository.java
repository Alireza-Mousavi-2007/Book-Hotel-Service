package org.bookhotel.bookinghotelsystem.repository;

import org.bookhotel.bookinghotelsystem.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Integer> {

    public Room findRoomByRoomNumber(String roomNumber);

}
