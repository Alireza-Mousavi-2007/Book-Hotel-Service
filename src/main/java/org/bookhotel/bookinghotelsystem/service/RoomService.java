package org.bookhotel.bookinghotelsystem.service;

import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;

import java.util.List;

public interface RoomService {

    public List<Room> getAllRoom();

    public Room getRoomById(Integer id);

    public Room getRoomByRoomNumber(String roomNumber);

    public Room addRoom(RoomRequestDTO roomRequestDTO);

    public Room updateRoomStatus(String roomNumber, RoomStatus status);

    public void deleteRoomByRoomNumber(String roomNumber);

}
