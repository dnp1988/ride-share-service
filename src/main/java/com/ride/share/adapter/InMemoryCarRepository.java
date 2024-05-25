package com.ride.share.adapter;

import com.ride.share.adapter.entity.CarWithLock;
import com.ride.share.domain.dto.CarDTO;
import com.ride.share.domain.repository.CarRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;


/**
 * Handles storage of Cars using {@link CarDTO} and Car seat locking.
 * Saves the information locally using a {@link ConcurrentHashMap} with a {@link CarWithLock} for each entry.
 * Seat locking is handled using a {@link Semaphore} for each Car.
 */
@Repository
public class InMemoryCarRepository implements CarRepository {

    /**
     * Map containing every Car saved as {@link CarWithLock} indexed by the External Car ID.
     */
    private Map<Long, CarWithLock> carMap;

    /**
     * Creates a regular instance.
     */
    public InMemoryCarRepository() {
        this.carMap = new ConcurrentHashMap<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarDTO get(Long id) {
        return Optional.ofNullable(carMap.get(id))
                .map(this::toCarDTO)
                .orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    private CarDTO toCarDTO(CarWithLock car) {
        return new CarDTO(car.getId(),
                car.getMaxSeats(),
                car.getSemaphore().availablePermits());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(CarDTO car) {
        CarWithLock newCar = new CarWithLock(car.getId(), car.getMaxSeats(), new Semaphore(car.getMaxSeats()));
        if (Objects.nonNull(carMap.putIfAbsent(newCar.getId(), newCar))) {
            throw new IllegalArgumentException(String.format("ID(%s) is already in use", car.getId()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveAll(Collection<CarDTO> cars) {
        cars.forEach(this::save);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarDTO takeSeats(Integer seats) {
        for (CarWithLock car : carMap.values()) {
            Boolean acquired = car.getSemaphore().tryAcquire(seats);
            if (acquired) {
                return toCarDTO(car);
            }
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CarDTO releaseSeats(Long id, Integer seats) {
        Optional<CarWithLock> carOptional = Optional.ofNullable(carMap.get(id));
        if (carOptional.isPresent()) {
            carOptional.get().getSemaphore().release(seats);
            return toCarDTO(carOptional.get());
        } else {
            return null;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        carMap.clear();
    }
}
