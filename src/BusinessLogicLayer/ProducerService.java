package BusinessLogicLayer;

import DataAccessLayer.ProducerDAO;
import DataAccessLayer.Producer;
import java.util.List;

/**
 * Service class for producer-related business logic.
 */
public class ProducerService implements IProducerService {

    private ProducerDAO producerDao;

    /**
     * Constructor that injects a ProducerDAO object.
     */
    public ProducerService() {
    	producerDao= new ProducerDAO();
    }

    @Override
    public Producer findProducerBySSN(String ssn) {
        // Logic to find a producer by SSN
        return producerDao.getProducerBySSN(ssn);
    }

    @Override
    public List<Producer> listAllProducers() {
        // Logic to retrieve all producers
        return producerDao.getAllProducers();
    }

    @Override
    public void addProducer(Producer producer) {
        // Logic to save a producer, which could be an insert or an update
        if (producer != null) {
            if (producerDao.getProducerBySSN(producer.getSsn()) != null) {
                producerDao.updateProducer(producer);
            } else {
                producerDao.addProducer(producer);
            }
        }
    }

    @Override
    public void deleteProducerBySSN(String ssn) {
        // Logic to delete a producer by SSN
        producerDao.deleteProducer(ssn);
    }

    @Override
    public void updateProducer(Producer producer) {
        // Logic to update an existing producer
        if (producer != null && producerDao.getProducerBySSN(producer.getSsn()) != null) {
            producerDao.updateProducer(producer);
        } else {
            // Handle the case where the producer does not exist
            // This could be an exception or simply a no-operation, based on your design choice
            // throw new ProducerNotFoundException("Producer with SSN " + producer.getSsn() + " not found.");
        }
    }

    // Additional methods and business logic for producers can be implemented here.
}
