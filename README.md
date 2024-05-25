# Ride Share Service
This service provides a system for organizing shared rides and connecting passengers with drivers.

The system implements a REST API to manage the allocation of cars to rides based on the seating capacity of the vehicles and the number of passengers per trip.

Passengers request trips in groups. Members of a group prefer to travel together in the same car. Any group can be assigned to a car with enough available seats, regardless of their current location. If no suitable car is immediately available, groups are willing to wait until one becomes free. Once a car is assigned to a waiting group, they will travel to their destination without being reassigned to another car during the ride.

Regarding trip order, groups are served as promptly as possible, adhering to their arrival sequence when feasible. If a later-arriving group can be accommodated before an earlier-arriving group due to seat availability, they may be served first.
