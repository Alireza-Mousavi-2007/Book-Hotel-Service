package service;

import dto.RoomRequestDTO;
import entity.Room;
import enums.RoomStatus;

import java.util.List;

public interface RoomService {

    public List<Room> getAllRoom();

    public Room getRoomById(Integer id);

    public Room getRoomByRoomNumber(String roomNumber);

    public Room addRoom(RoomRequestDTO roomRequestDTO);

    public Room updateRoomStatus(String roomNumber, RoomStatus status);

    public void deleteRoomByRoomNumber(String roomNumber);

}
