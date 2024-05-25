package com.ride.share.api;

/**
 * Contains the constants related to the API paths and parameters used in the Controllers.
 */
public class ApiConstants {

    private ApiConstants() {
    }

    /**
     * Vehicles reset endpoint path.
     */
    public static final String PATH_CARS = "/cars";

    /**
     * Server Status endpoint path.
     */
    public static final String PATH_CHECK = "/check";

    /**
     * Ride creation endpoint path.
     */
    public static final String PATH_RIDES = "/rides";

    /**
     * Ride Id parameter.
     */
    public static final String PARAM_ID = "id";

    /**
     * Ride Id endpoint path.
     */
    public static final String PATH_RIDES_ID = PATH_RIDES + "/{" + PARAM_ID + "}";

    /**
     * Ride termination endpoint path.
     */
    public static final String PATH_END_RIDE = PATH_RIDES_ID + "/end";

    /**
     * Ride find endpoint path.
     */
    public static final String PATH_FIND_RIDE = PATH_RIDES_ID + "/find";
}
