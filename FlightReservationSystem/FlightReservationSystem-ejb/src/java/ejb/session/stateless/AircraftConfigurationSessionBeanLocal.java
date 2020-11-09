/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.AircraftConfiguration;
import java.util.List;
import javax.ejb.Local;
import util.exception.AircraftConfigurationNotFoundException;

/**
 *
 * @author Administrator
 */
@Local
public interface AircraftConfigurationSessionBeanLocal {

    public List<AircraftConfiguration> retrieveAllAircraftConfigurations();

    public AircraftConfiguration retrieveAircraftConfigurationByName(String name) throws AircraftConfigurationNotFoundException;
    
}
