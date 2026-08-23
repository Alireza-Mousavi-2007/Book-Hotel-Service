package service.impl;

import dto.RoomRequestDTO;
import entity.Room;
import enums.RoomStatus;
import exception.RoomNotFoundException;
import org.springframework.stereotype.Service;
import repository.RoomRepository;
import service.RoomService;

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
        else
            return room;
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
