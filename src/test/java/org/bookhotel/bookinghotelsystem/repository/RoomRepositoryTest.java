package org.bookhotel.bookinghotelsystem.repository;

import org.assertj.core.api.Assertions;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    private Room room;

    @BeforeEach
    public void init() {
        room = Room.builder().roomNumber("101").capacity(1).price(1L).status(RoomStatus.AVAILABLE).build();
        room = roomRepository.save(room);
    }

    @Test
    public void findRoomByRoomNumber_whenFound_room() {
        var tested = roomRepository.findRoomByRoomNumber(room.getRoomNumber());

        Assertions.assertThat(tested).isNotNull();
        Assertions.assertThat(tested.getId()).isEqualTo(room.getId());
        Assertions.assertThat(tested.getRoomNumber()).isEqualTo(room.getRoomNumber());
        Assertions.assertThat(tested.getCapacity()).isEqualTo(room.getCapacity());
        Assertions.assertThat(tested.getPrice()).isEqualTo(room.getPrice());
        Assertions.assertThat(tested.getStatus()).isEqualTo(room.getStatus());
    }

}
