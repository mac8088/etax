package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.OfficeHoliday;
import net.atos.etax.repository.OfficeHolidayRepository;
import net.atos.etax.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static net.atos.etax.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OfficeHolidayResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class OfficeHolidayResourceIT {

    private static final Integer DEFAULT_OFFICE_ID = 1;
    private static final Integer UPDATED_OFFICE_ID = 2;

    private static final String DEFAULT_HOLIDAY_ID = "AAAAAAA";
    private static final String UPDATED_HOLIDAY_ID = "BBBBBBB";

    @Autowired
    private OfficeHolidayRepository officeHolidayRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restOfficeHolidayMockMvc;

    private OfficeHoliday officeHoliday;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeHolidayResource officeHolidayResource = new OfficeHolidayResource(officeHolidayRepository);
        this.restOfficeHolidayMockMvc = MockMvcBuilders.standaloneSetup(officeHolidayResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeHoliday createEntity(EntityManager em) {
        OfficeHoliday officeHoliday = new OfficeHoliday()
            .officeId(DEFAULT_OFFICE_ID)
            .holidayId(DEFAULT_HOLIDAY_ID);
        return officeHoliday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeHoliday createUpdatedEntity(EntityManager em) {
        OfficeHoliday officeHoliday = new OfficeHoliday()
            .officeId(UPDATED_OFFICE_ID)
            .holidayId(UPDATED_HOLIDAY_ID);
        return officeHoliday;
    }

    @BeforeEach
    public void initTest() {
        officeHoliday = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfficeHoliday() throws Exception {
        int databaseSizeBeforeCreate = officeHolidayRepository.findAll().size();

        // Create the OfficeHoliday
        restOfficeHolidayMockMvc.perform(post("/api/office-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeHoliday)))
            .andExpect(status().isCreated());

        // Validate the OfficeHoliday in the database
        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeCreate + 1);
        OfficeHoliday testOfficeHoliday = officeHolidayList.get(officeHolidayList.size() - 1);
        assertThat(testOfficeHoliday.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testOfficeHoliday.getHolidayId()).isEqualTo(DEFAULT_HOLIDAY_ID);
    }

    @Test
    @Transactional
    public void createOfficeHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeHolidayRepository.findAll().size();

        // Create the OfficeHoliday with an existing ID
        officeHoliday.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeHolidayMockMvc.perform(post("/api/office-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeHoliday)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeHoliday in the database
        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeHolidayRepository.findAll().size();
        // set the field null
        officeHoliday.setOfficeId(null);

        // Create the OfficeHoliday, which fails.

        restOfficeHolidayMockMvc.perform(post("/api/office-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeHoliday)))
            .andExpect(status().isBadRequest());

        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkHolidayIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeHolidayRepository.findAll().size();
        // set the field null
        officeHoliday.setHolidayId(null);

        // Create the OfficeHoliday, which fails.

        restOfficeHolidayMockMvc.perform(post("/api/office-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeHoliday)))
            .andExpect(status().isBadRequest());

        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfficeHolidays() throws Exception {
        // Initialize the database
        officeHolidayRepository.saveAndFlush(officeHoliday);

        // Get all the officeHolidayList
        restOfficeHolidayMockMvc.perform(get("/api/office-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeId").value(hasItem(DEFAULT_OFFICE_ID)))
            .andExpect(jsonPath("$.[*].holidayId").value(hasItem(DEFAULT_HOLIDAY_ID.toString())));
    }
    
    @Test
    @Transactional
    public void getOfficeHoliday() throws Exception {
        // Initialize the database
        officeHolidayRepository.saveAndFlush(officeHoliday);

        // Get the officeHoliday
        restOfficeHolidayMockMvc.perform(get("/api/office-holidays/{id}", officeHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(officeHoliday.getId().intValue()))
            .andExpect(jsonPath("$.officeId").value(DEFAULT_OFFICE_ID))
            .andExpect(jsonPath("$.holidayId").value(DEFAULT_HOLIDAY_ID.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfficeHoliday() throws Exception {
        // Get the officeHoliday
        restOfficeHolidayMockMvc.perform(get("/api/office-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfficeHoliday() throws Exception {
        // Initialize the database
        officeHolidayRepository.saveAndFlush(officeHoliday);

        int databaseSizeBeforeUpdate = officeHolidayRepository.findAll().size();

        // Update the officeHoliday
        OfficeHoliday updatedOfficeHoliday = officeHolidayRepository.findById(officeHoliday.getId()).get();
        // Disconnect from session so that the updates on updatedOfficeHoliday are not directly saved in db
        em.detach(updatedOfficeHoliday);
        updatedOfficeHoliday
            .officeId(UPDATED_OFFICE_ID)
            .holidayId(UPDATED_HOLIDAY_ID);

        restOfficeHolidayMockMvc.perform(put("/api/office-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfficeHoliday)))
            .andExpect(status().isOk());

        // Validate the OfficeHoliday in the database
        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeUpdate);
        OfficeHoliday testOfficeHoliday = officeHolidayList.get(officeHolidayList.size() - 1);
        assertThat(testOfficeHoliday.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testOfficeHoliday.getHolidayId()).isEqualTo(UPDATED_HOLIDAY_ID);
    }

    @Test
    @Transactional
    public void updateNonExistingOfficeHoliday() throws Exception {
        int databaseSizeBeforeUpdate = officeHolidayRepository.findAll().size();

        // Create the OfficeHoliday

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeHolidayMockMvc.perform(put("/api/office-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeHoliday)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeHoliday in the database
        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfficeHoliday() throws Exception {
        // Initialize the database
        officeHolidayRepository.saveAndFlush(officeHoliday);

        int databaseSizeBeforeDelete = officeHolidayRepository.findAll().size();

        // Delete the officeHoliday
        restOfficeHolidayMockMvc.perform(delete("/api/office-holidays/{id}", officeHoliday.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfficeHoliday> officeHolidayList = officeHolidayRepository.findAll();
        assertThat(officeHolidayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeHoliday.class);
        OfficeHoliday officeHoliday1 = new OfficeHoliday();
        officeHoliday1.setId(1L);
        OfficeHoliday officeHoliday2 = new OfficeHoliday();
        officeHoliday2.setId(officeHoliday1.getId());
        assertThat(officeHoliday1).isEqualTo(officeHoliday2);
        officeHoliday2.setId(2L);
        assertThat(officeHoliday1).isNotEqualTo(officeHoliday2);
        officeHoliday1.setId(null);
        assertThat(officeHoliday1).isNotEqualTo(officeHoliday2);
    }
}
