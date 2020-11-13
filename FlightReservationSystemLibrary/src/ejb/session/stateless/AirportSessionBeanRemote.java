/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Airport;
import javax.ejb.Remote;
import util.exception.AirportExistException;
import util.exception.AirportNotFoundException;
import util.exception.GeneralException;

/**
 *
 * @author Administrator
 */
@Remote
public interface AirportSessionBeanRemote {
    
    public Airport createNewAirport(Airport airport) throws AirportExistException, GeneralException;
    
    public Airport retrieveAirportByIataCode(String iataCode) throws AirportNotFoundException;
    
    
}
