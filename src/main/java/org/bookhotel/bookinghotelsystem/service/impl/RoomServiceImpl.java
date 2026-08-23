package org.bookhotel.bookinghotelsystem.service.impl;

import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.exception.RoomNotFoundException;
import org.bookhotel.bookinghotelsystem.repository.RoomRepository;
import org.bookhotel.bookinghotelsystem.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository repo;

    public RoomServiceImpl(RoomRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Room> getAllRoom() {
        return repo.findAll();
    }

    @Override
    public Room getRoomById(Integer id) {
        var room = repo.findById(id);
        if (room.isEmpty()) throw new RoomNotFoundException("There's no room with = " + id);
        else
            return room.get();
    }

    @Override
    public Room getRoomByRoomNumber(String roomNumber) {
        var room = repo.findRoomByRoomNumber(roomNumber);
        if (room == null) throw new RoomNotFoundException("There's no room with = " + roomNumber);
        else {
            room.setId(null);
            return room;
        }
    }

    @Override
    public Room addRoom(RoomRequestDTO roomRequestDTO) {
        var room = new Room();

        room.setRoomNumber(roomRequestDTO.getRoomNumber());
        room.setPrice(roomRequestDTO.getPrice());
        room.setCapacity(roomRequestDTO.getCapacity());
        room.setStatus(roomRequestDTO.getStatus());

        return repo.save(room);

    }

    @Override
    public Room updateRoomStatus(String roomNumber, RoomStatus status) {
        var room = repo.findRoomByRoomNumber(roomNumber);
        if (room == null) throw new RoomNotFoundException("There's no room with = " + roomNumber);
        room.setStatus(status);
        return repo.save(room);
    }

    @Override
    public void deleteRoomByRoomNumber(String roomNumber) {
        var room = repo.findRoomByRoomNumber(roomNumber);
        if (room == null) throw new RoomNotFoundException("There's no room with = " + roomNumber);
        repo.delete(room);

    }
}
