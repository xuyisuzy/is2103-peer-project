/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Customer;
import entity.FlightReservation;
import entity.FlightSchedule;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import util.enumeration.CabinClassType;
import util.exception.FlightReservationNotFoundException;

/**
 *
 * @author xuyis
 */
@Stateless
public class FlightReservationSessionBean implements FlightReservationSessionBeanRemote, FlightReservationSessionBeanLocal {

    @PersistenceContext(unitName = "FlightReservationSystem-ejbPU")
    private EntityManager em;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public FlightReservationSessionBean() {
    }
    
    public FlightReservation retrieveFlightReservationByID(Long flightReservationId) throws FlightReservationNotFoundException {
        FlightReservation flightReservation = em.find(FlightReservation.class, flightReservationId);
        
        if (flightReservation == null) {
            throw new FlightReservationNotFoundException("Flight Reservation with ID: " + flightReservationId + " does not exist!");
        }
        
        for (FlightSchedule flightSchedule: flightReservation.getFlightSchedules()) {
            flightSchedule.getDepartureAirport();
            flightSchedule.getDestinationAirport();
        }
        
        for (FlightSchedule returnFlightSchedule: flightReservation.getReturnFlightSchedules()) {
            returnFlightSchedule.getDepartureAirport();
            returnFlightSchedule.getDestinationAirport();
        }
        
        return flightReservation;
    }
    
    @Override
    public Long reserveFlight(Integer numOfPassengers, List<String[]> passengers, String[] creditCard, CabinClassType cabinClassType, List<Long> flightScheduleIds, List<Long> returnFlightScheduleIds, Customer customer) {
        
        FlightReservation flightReservation = new FlightReservation(numOfPassengers, passengers, creditCard, cabinClassType, customer);
        em.persist(flightReservation);
        
        for(Long flightScheduleId: flightScheduleIds) {
            FlightSchedule flightSchedule = em.find(FlightSchedule.class, flightScheduleId);
            flightReservation.getFlightSchedules().add(flightSchedule);
        }
        
        if (!returnFlightScheduleIds.isEmpty()) {
            for(Long returnFlightScheduleId: returnFlightScheduleIds) {
            FlightSchedule returnFlightSchedule = em.find(FlightSchedule.class, returnFlightScheduleId);
            flightReservation.getReturnFlightSchedules().add(returnFlightSchedule);
        }
        }
        
        em.flush();
        
        return flightReservation.getFlightReservationId();
    }

    
}
