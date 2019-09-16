package net.atos.etax.service.impl;

import net.atos.etax.service.PublicHolidayService;
import net.atos.etax.domain.PublicHoliday;
import net.atos.etax.repository.PublicHolidayRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PublicHoliday}.
 */
@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayServiceImpl.class);

    private final PublicHolidayRepository publicHolidayRepository;

    public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
    }

    /**
     * Save a publicHoliday.
     *
     * @param publicHoliday the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PublicHoliday save(PublicHoliday publicHoliday) {
        log.debug("Request to save PublicHoliday : {}", publicHoliday);
        return publicHolidayRepository.save(publicHoliday);
    }

    /**
     * Get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PublicHoliday> findAll(Pageable pageable) {
        log.debug("Request to get all PublicHolidays");
        return publicHolidayRepository.findAll(pageable);
    }


    /**
     * Get one publicHoliday by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PublicHoliday> findOne(Long id) {
        log.debug("Request to get PublicHoliday : {}", id);
        return publicHolidayRepository.findById(id);
    }

    /**
     * Delete the publicHoliday by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicHoliday : {}", id);
        publicHolidayRepository.deleteById(id);
    }
}
