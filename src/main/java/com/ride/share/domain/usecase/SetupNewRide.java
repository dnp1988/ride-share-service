package com.ride.share.domain.usecase;

import com.ride.share.domain.entity.Ride;
import org.springframework.stereotype.Component;

/**
 * Creates a new Ride and sets it up to be processed.
 * Saves the Ride in its Repository and also in the Pending Rides queue.
 * Additionally, it triggers the Revise Pending Rides Job.
 */
@Component
public class SetupNewRide {

    private CreateRide createRide;
    private QueueNewRide queueNewRide;
    private RevisePendingRides revisePendingRides;

    /**
     * Creates a regular use case instance.
     *
     * @param createRide         Use Case of CreateRide
     * @param queueNewRide       Use Case of QueueRide
     * @param revisePendingRides Use Case of RevisePendingRides
     */
    public SetupNewRide(CreateRide createRide,
                        QueueNewRide queueNewRide,
                        RevisePendingRides revisePendingRides) {
        this.createRide = createRide;
        this.queueNewRide = queueNewRide;
        this.revisePendingRides = revisePendingRides;
    }

    /**
     * Creates a new Ride and sets it up to be processed.
     * Saves the Ride in its Repository and also in the Pending Rides queue.
     * Additionally, it triggers the Revise Pending Rides Job.
     *
     * @param ride Ride to create and setup
     */
    public void setup(Ride ride) {
        createRide.create(ride);

        queueNewRide.queue(ride);

        //Triggers the Job to Process the Pending Ride Queue
        revisePendingRides.revise();
    }
}
