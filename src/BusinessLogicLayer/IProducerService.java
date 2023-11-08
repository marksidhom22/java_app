package BusinessLogicLayer;

import DataAccessLayer.Producer;
import java.util.List;

/**
 * Interface for defining operations related to producers.
 */
public interface IProducerService {

    /**
     * Retrieves a producer by their SSN.
     *
     * @param ssn the SSN of the producer
     * @return the Producer object
     */
    Producer findProducerBySSN(String ssn);

    /**
     * Lists all producers in the database.
     *
     * @return a list of all producers
     */
    List<Producer> listAllProducers();

    /**
     * Adds a producer to the database.
     *
     * @param producer the Producer object to add
     */
    void addProducer(Producer producer);

    /**
     * Updates a producer's information in the database.
     *
     * @param producer the Producer object to update
     */
    void updateProducer(Producer producer);

    /**
     * Deletes a producer from the database by their SSN.
     *
     * @param ssn the SSN of the producer to delete
     */
    void deleteProducerBySSN(String ssn);

    // Additional methods for producer-related operations can be defined here
}
