package BusinessLogicLayer;

import DataAccessLayer.Instrument;
import java.util.List;

/**
 * Interface for defining service operations related to musical instruments.
 */
public interface IInstrumentService {

    /**
     * Retrieves an instrument by its unique identification number.
     *
     * @param instrId the unique ID of the instrument
     * @return the Instrument object
     */
    Instrument findInstrumentById(String instrId);

    /**
     * Lists all instruments in the database.
     *
     * @return a list of all instruments
     */
    List<Instrument> listAllInstruments();

    /**
     * Adds a new instrument to the database.
     *
     * @param instrument the Instrument object to be added
     * @return 
     */
    boolean addInstrument(Instrument instrument);

    /**
     * Updates an existing instrument's information in the database.
     *
     * @param instrument the Instrument object to be updated
     * @return 
     */
    boolean updateInstrument(Instrument instrument);

    /**
     * Deletes an instrument from the database using its ID.
     *
     * @param instrId the unique ID of the instrument to be deleted
     * @return 
     */
    boolean deleteInstrumentById(String instrId);

    // Other instrument-related business operations can be defined here.
}
