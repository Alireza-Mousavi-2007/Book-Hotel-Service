package org.bookhotel.bookinghotelsystem.controller;

import org.bookhotel.bookinghotelsystem.dto.RoomRequestDTO;
import org.bookhotel.bookinghotelsystem.entity.Room;
import org.bookhotel.bookinghotelsystem.enums.RoomStatus;
import org.bookhotel.bookinghotelsystem.security.jwt.JwtToken;
import org.bookhotel.bookinghotelsystem.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RoomController.class)
public class RoomControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private RoomService roomService;
    @MockitoBean
    private JwtToken jwtToken;
    @MockitoBean
    private UserDetailsService userDetailsService;


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
    @WithMockUser(roles = "ADMIN")
    public void getAllRoom_whenGet_responseEntity() throws Exception {
        when(roomService.getAllRoom()).thenReturn(List.of(room));

        mockMvc.perform(get("/api/rooms/admin").with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$[0].roomNumber").value(room.getRoomNumber()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getRoomById_whenGet_responseEntity() throws Exception {
        when(roomService.getRoomById(anyInt())).thenReturn(room);
        mockMvc.perform(get("/api/rooms/admin/{id}", 1).with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.roomNumber").value(room.getRoomNumber()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void getRoomByRoomNumber_whenGet_responseEntity() throws Exception {
        when(roomService.getRoomByRoomNumber(anyString())).thenReturn(room);
        mockMvc.perform(get("/api/rooms/{roomNumber}", room.getRoomNumber()).with(csrf()))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.roomNumber").value(room.getRoomNumber()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void addRoom_whenAdded_responseEntity() throws Exception {

        when(roomService.addRoom(any(RoomRequestDTO.class))).thenReturn(room);
        mockMvc.perform(post("/api/rooms/admin").with(csrf()).
                        contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRequestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomNumber").value(room.getRoomNumber()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void updateRoomStatus_whenUpdate_responseEntity() throws Exception {
        when(roomService.updateRoomStatus(anyString(), any(RoomStatus.class))).thenReturn(room);
        mockMvc.perform(put("/api/rooms/admin/{roomNumber}", room.getRoomNumber()).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(room.getStatus())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(RoomStatus.AVAILABLE.toString()));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void deleteRoomByRoomNumber_whenGet_responseEntity() throws Exception {
        mockMvc.perform(delete("/api/rooms/admin/{roomNumber}", room.getRoomNumber()).with(csrf()))
                .andExpect(status().isOk());

        verify(roomService).deleteRoomByRoomNumber(anyString());
    }

}
