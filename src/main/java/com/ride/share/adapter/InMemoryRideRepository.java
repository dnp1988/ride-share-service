package com.ride.share.adapter;

import com.ride.share.domain.dto.RideDTO;
import com.ride.share.domain.repository.RideRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Handles storage of Rides using {@link RideDTO}.
 * Saves the information locally using a {@link ConcurrentHashMap}.
 */
@Repository
public class InMemoryRideRepository implements RideRepository {

    /**
     * Map containing every Ride saved as {@link RideDTO} indexed by the External Ride ID.
     */
    private Map<Long, RideDTO> rideMap;

    /**
     * Creates a regular instance.
     */
    public InMemoryRideRepository() {
        this.rideMap = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RideDTO get(Long id) {
        return rideMap.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(RideDTO ride) {
        if (Objects.nonNull(rideMap.putIfAbsent(ride.getId(), ride))) {
            throw new IllegalArgumentException(String.format("ID(%s) is already in use", ride.getId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAll(Collection<RideDTO> rides) {
        rides.forEach(this::save);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RideDTO updateAssignment(Long rideId, Long carId) {
        Optional<RideDTO> rideOptional = Optional.ofNullable(get(rideId));
        if (rideOptional.isPresent()) {
            RideDTO ride = rideOptional.get();
            ride.setAssignedToCarId(carId);
            return ride;
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void remove(Long id) {
        rideMap.remove(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        rideMap.clear();
    }
}
