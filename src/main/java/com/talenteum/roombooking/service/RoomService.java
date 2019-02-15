package com.talenteum.roombooking.service;

import com.talenteum.roombooking.domain.Room;
import com.talenteum.roombooking.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Room.
 */
@Service
@Transactional
public class RoomService {

    private final Logger log = LoggerFactory.getLogger(RoomService.class);

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    /**
     * Save a room.
     *
     * @param room the entity to save
     * @return the persisted entity
     */
    public Room save(Room room) {
        log.debug("Request to save Room : {}", room);
        return roomRepository.save(room);
    }

    /**
     * Get all the rooms.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Room> findAll() {
        log.debug("Request to get all Rooms");
        return roomRepository.findAll();
    }


    /**
     * Get one room by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Room> findOne(Long id) {
        log.debug("Request to get Room : {}", id);
        return roomRepository.findById(id);
    }

    /**
     * Delete the room by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Room : {}", id);        roomRepository.deleteById(id);
    }
}
