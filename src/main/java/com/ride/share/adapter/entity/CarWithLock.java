package com.ride.share.adapter.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.Semaphore;

/**
 * Car Model used in {@link com.ride.share.adapter.InMemoryCarRepository}.
 * It is adapted to handle seat locking using a {@link Semaphore}.
 */
@Getter
@AllArgsConstructor
public class CarWithLock {

    /**
     * External ID of the Car.
     */
    private Long id;

    /**
     * Number of total seats in the Car.
     */
    private Integer maxSeats;

    /**
     * Car semaphore used to handle locking of Car seats.
     */
    private Semaphore semaphore;
}
