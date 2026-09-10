package org.bookhotel.bookinghotelsystem.service;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.repository.RoomRepository;
import org.bookhotel.bookinghotelsystem.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class RoomServiceTest {

    @Mock
    private RoomRepository roomRepository;
    @InjectMocks
    private RoomServiceImpl roomService;

    private Room room;
    private RoomRequestDTO roomRequestDTO;

    @BeforeEach
    public void init() {
        room = Room.builder().id(1).roomNumber("101").price(1L)
                .status(RoomStatus.AVAILABLE).capacity(1).build();

        roomRequestDTO = RoomRequestDTO.builder().roomNumber("101").price(1L)
                .status(RoomStatus.AVAILABLE).capacity(1).build();
    }

    @Test
    public void addRoom_whenAdded_room() {
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        var test = roomService.addRoom(roomRequestDTO);

        Assertions.assertThat(test).isNotNull();
        Assertions.assertThat(test.getId()).isEqualTo(room.getId());
        Assertions.assertThat(test.getStatus()).isEqualTo(room.getStatus());
        Assertions.assertThat(test.getRoomNumber()).isEqualTo(room.getRoomNumber());
        Assertions.assertThat(test.getPrice()).isEqualTo(room.getPrice());
        Assertions.assertThat(test.getCapacity()).isEqualTo(room.getCapacity());

    }

    @Test
    public void updateRoomStatus_whenUpdated_room() {

        when(roomRepository.findRoomByRoomNumber(room.getRoomNumber())).thenReturn(room);
        when(roomRepository.save(any(Room.class))).thenReturn(room);

        var test = roomService.updateRoomStatus(room.getRoomNumber(), room.getStatus());

        Assertions.assertThat(test.getStatus()).isEqualTo(room.getStatus());

    }

    @Test
    public void deleteRoomByRoomNumber_whenDeleted_void(){

        when(roomRepository.findRoomByRoomNumber(anyString())).thenReturn(room);

        roomService.deleteRoomByRoomNumber(room.getRoomNumber());

        verify(roomRepository).delete(any(Room.class));

    }


}
