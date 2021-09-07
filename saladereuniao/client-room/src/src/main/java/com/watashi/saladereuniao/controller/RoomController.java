package com.watashi.saladereuniao.controller;

import com.watashi.saladereuniao.exception.ResourceNotFoundException;
import com.watashi.saladereuniao.model.Room;
import com.watashi.saladereuniao.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1")
public class RoomController {

    @Autowired
    private RoomRepository roomRepository;

    @GetMapping("/rooms")
    public List<Room> getAllRoom(){
        return roomRepository.findAll();
    }

    @GetMapping("/rooms/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable(value = "id") long roomId)
            throws ResourceNotFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found:" + roomId));
        return  ResponseEntity.ok().body(room);
    }

    @PostMapping("/rooms")
    public Room createRoom(@Valid @RequestBody Room room){
        return roomRepository.save(room);
    }

    @PutMapping("/rooms/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable(value = "id") Long roomId,
                                           @Valid @RequestBody Room roomDetails)throws ResourceNotFoundException {
        Room updateRoom= roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found for this id: " + roomId));
        updateRoom.setName(roomDetails.getName());
        updateRoom.setDate(roomDetails.getDate());
        updateRoom.setStartHour(roomDetails.getStartHour());
        updateRoom.setEndHour(roomDetails.getEndHour());
        final Room updatedRoom = roomRepository.save(updateRoom);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/rooms/{id}")
     public Map<String, Boolean> deleteRoom(@PathVariable(value = "id") long roomId)
             throws  ResourceNotFoundException{
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()-> new ResourceNotFoundException("Room not found for this id: " + roomId));
        roomRepository.delete(room);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
     }




}
