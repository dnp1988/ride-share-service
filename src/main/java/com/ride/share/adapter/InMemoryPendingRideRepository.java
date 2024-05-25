package com.ride.share.adapter;

import com.ride.share.domain.dto.PendingRideDTO;
import com.ride.share.domain.repository.PendingRideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.PriorityBlockingQueue;


/**
 * Handles a queue of Pending Rides using {@link PendingRideDTO}.
 * Saves the information locally using a {@link PriorityBlockingQueue}.
 * <p>
 * The priority order of the queue is defined by the time when each Pending Ride was first inserted.
 * This serves the purpose of keeping the arrival order of Rides if they are removed and reinserted in the queue.
 * The first insertion time is saved as a milliseconds timestamp in the Pending Ride.
 */
@Repository
public class InMemoryPendingRideRepository implements PendingRideRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryPendingRideRepository.class);

    /**
     * Queue containing every Pending Ride saved as {@link PendingRideDTO}.
     * ordered by the External Ride ID.
     */
    private Queue<PendingRideDTO> pendingRideQueue;

    /**
     * Creates a regular instance.
     *
     * @param initialCapacity initial size of the queue
     */
    public InMemoryPendingRideRepository(
            @Value("${ride-share.pending-ride.queue-init-size:10}") Integer initialCapacity) {
        LOGGER.debug("Initializing Queue with initialCapacity({})", initialCapacity);
        this.pendingRideQueue = new PriorityBlockingQueue<>(initialCapacity, queueComparator());
    }

    /**
     * Creates the comparator that defines the priority order of the Pending Rides queue.
     * The order is firstly defined by first insertion time and by ID in case of a collision.
     *
     * @return comparator with priority order criteria for the queue
     */
    private Comparator<PendingRideDTO> queueComparator() {
        return Comparator.comparing(PendingRideDTO::getEntryTime)
                .thenComparing(PendingRideDTO::getId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PendingRideDTO poll() {
        return pendingRideQueue.poll();
    }

    /**
     * Inserts the given Pending Ride in the queue.
     * If the Pending Ride has no entry time, the current timestamp is saved in it using {@link Instant}.
     *
     * @param pendingRide Pending Ride to insert
     */
    @Override
    public void add(PendingRideDTO pendingRide) {
        if (Objects.isNull(pendingRide.getEntryTime())) {
            Long timestamp = Instant.now().toEpochMilli();
            pendingRide.setEntryTime(timestamp);
        }
        pendingRideQueue.add(pendingRide);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addAll(Collection<PendingRideDTO> pendingRides) {
        pendingRides.forEach(this::add);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        pendingRideQueue.clear();
    }
}
