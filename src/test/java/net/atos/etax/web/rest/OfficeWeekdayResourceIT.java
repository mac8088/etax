package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.OfficeWeekday;
import net.atos.etax.repository.OfficeWeekdayRepository;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static net.atos.etax.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OfficeWeekdayResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class OfficeWeekdayResourceIT {

    private static final Integer DEFAULT_OFFICE_ID = 1;
    private static final Integer UPDATED_OFFICE_ID = 2;

    private static final String DEFAULT_CSTD_WEEKWORKING_DAY = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_WEEKWORKING_DAY = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OfficeWeekdayRepository officeWeekdayRepository;

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

    private MockMvc restOfficeWeekdayMockMvc;

    private OfficeWeekday officeWeekday;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeWeekdayResource officeWeekdayResource = new OfficeWeekdayResource(officeWeekdayRepository);
        this.restOfficeWeekdayMockMvc = MockMvcBuilders.standaloneSetup(officeWeekdayResource)
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
    public static OfficeWeekday createEntity(EntityManager em) {
        OfficeWeekday officeWeekday = new OfficeWeekday()
            .officeId(DEFAULT_OFFICE_ID)
            .cstdWeekworkingDay(DEFAULT_CSTD_WEEKWORKING_DAY)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return officeWeekday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeWeekday createUpdatedEntity(EntityManager em) {
        OfficeWeekday officeWeekday = new OfficeWeekday()
            .officeId(UPDATED_OFFICE_ID)
            .cstdWeekworkingDay(UPDATED_CSTD_WEEKWORKING_DAY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return officeWeekday;
    }

    @BeforeEach
    public void initTest() {
        officeWeekday = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfficeWeekday() throws Exception {
        int databaseSizeBeforeCreate = officeWeekdayRepository.findAll().size();

        // Create the OfficeWeekday
        restOfficeWeekdayMockMvc.perform(post("/api/office-weekdays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeWeekday)))
            .andExpect(status().isCreated());

        // Validate the OfficeWeekday in the database
        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeCreate + 1);
        OfficeWeekday testOfficeWeekday = officeWeekdayList.get(officeWeekdayList.size() - 1);
        assertThat(testOfficeWeekday.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testOfficeWeekday.getCstdWeekworkingDay()).isEqualTo(DEFAULT_CSTD_WEEKWORKING_DAY);
        assertThat(testOfficeWeekday.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOfficeWeekday.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createOfficeWeekdayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeWeekdayRepository.findAll().size();

        // Create the OfficeWeekday with an existing ID
        officeWeekday.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeWeekdayMockMvc.perform(post("/api/office-weekdays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeWeekday)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeWeekday in the database
        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeWeekdayRepository.findAll().size();
        // set the field null
        officeWeekday.setOfficeId(null);

        // Create the OfficeWeekday, which fails.

        restOfficeWeekdayMockMvc.perform(post("/api/office-weekdays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeWeekday)))
            .andExpect(status().isBadRequest());

        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdWeekworkingDayIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeWeekdayRepository.findAll().size();
        // set the field null
        officeWeekday.setCstdWeekworkingDay(null);

        // Create the OfficeWeekday, which fails.

        restOfficeWeekdayMockMvc.perform(post("/api/office-weekdays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeWeekday)))
            .andExpect(status().isBadRequest());

        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfficeWeekdays() throws Exception {
        // Initialize the database
        officeWeekdayRepository.saveAndFlush(officeWeekday);

        // Get all the officeWeekdayList
        restOfficeWeekdayMockMvc.perform(get("/api/office-weekdays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeWeekday.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeId").value(hasItem(DEFAULT_OFFICE_ID)))
            .andExpect(jsonPath("$.[*].cstdWeekworkingDay").value(hasItem(DEFAULT_CSTD_WEEKWORKING_DAY.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOfficeWeekday() throws Exception {
        // Initialize the database
        officeWeekdayRepository.saveAndFlush(officeWeekday);

        // Get the officeWeekday
        restOfficeWeekdayMockMvc.perform(get("/api/office-weekdays/{id}", officeWeekday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(officeWeekday.getId().intValue()))
            .andExpect(jsonPath("$.officeId").value(DEFAULT_OFFICE_ID))
            .andExpect(jsonPath("$.cstdWeekworkingDay").value(DEFAULT_CSTD_WEEKWORKING_DAY.toString()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfficeWeekday() throws Exception {
        // Get the officeWeekday
        restOfficeWeekdayMockMvc.perform(get("/api/office-weekdays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfficeWeekday() throws Exception {
        // Initialize the database
        officeWeekdayRepository.saveAndFlush(officeWeekday);

        int databaseSizeBeforeUpdate = officeWeekdayRepository.findAll().size();

        // Update the officeWeekday
        OfficeWeekday updatedOfficeWeekday = officeWeekdayRepository.findById(officeWeekday.getId()).get();
        // Disconnect from session so that the updates on updatedOfficeWeekday are not directly saved in db
        em.detach(updatedOfficeWeekday);
        updatedOfficeWeekday
            .officeId(UPDATED_OFFICE_ID)
            .cstdWeekworkingDay(UPDATED_CSTD_WEEKWORKING_DAY)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restOfficeWeekdayMockMvc.perform(put("/api/office-weekdays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfficeWeekday)))
            .andExpect(status().isOk());

        // Validate the OfficeWeekday in the database
        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeUpdate);
        OfficeWeekday testOfficeWeekday = officeWeekdayList.get(officeWeekdayList.size() - 1);
        assertThat(testOfficeWeekday.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testOfficeWeekday.getCstdWeekworkingDay()).isEqualTo(UPDATED_CSTD_WEEKWORKING_DAY);
        assertThat(testOfficeWeekday.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOfficeWeekday.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOfficeWeekday() throws Exception {
        int databaseSizeBeforeUpdate = officeWeekdayRepository.findAll().size();

        // Create the OfficeWeekday

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeWeekdayMockMvc.perform(put("/api/office-weekdays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeWeekday)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeWeekday in the database
        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfficeWeekday() throws Exception {
        // Initialize the database
        officeWeekdayRepository.saveAndFlush(officeWeekday);

        int databaseSizeBeforeDelete = officeWeekdayRepository.findAll().size();

        // Delete the officeWeekday
        restOfficeWeekdayMockMvc.perform(delete("/api/office-weekdays/{id}", officeWeekday.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfficeWeekday> officeWeekdayList = officeWeekdayRepository.findAll();
        assertThat(officeWeekdayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeWeekday.class);
        OfficeWeekday officeWeekday1 = new OfficeWeekday();
        officeWeekday1.setId(1L);
        OfficeWeekday officeWeekday2 = new OfficeWeekday();
        officeWeekday2.setId(officeWeekday1.getId());
        assertThat(officeWeekday1).isEqualTo(officeWeekday2);
        officeWeekday2.setId(2L);
        assertThat(officeWeekday1).isNotEqualTo(officeWeekday2);
        officeWeekday1.setId(null);
        assertThat(officeWeekday1).isNotEqualTo(officeWeekday2);
    }
}
