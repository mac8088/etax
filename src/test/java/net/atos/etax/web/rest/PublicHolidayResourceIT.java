package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.PublicHoliday;
import net.atos.etax.repository.PublicHolidayRepository;
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
 * Integration tests for the {@Link PublicHolidayResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class PublicHolidayResourceIT {

    private static final String DEFAULT_CSTD_HOLIDAY_TYPES = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_HOLIDAY_TYPES = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_WORKING_FLAG = false;
    private static final Boolean UPDATED_WORKING_FLAG = true;

    private static final Boolean DEFAULT_COUNTRY_WIDE = false;
    private static final Boolean UPDATED_COUNTRY_WIDE = true;

    private static final Integer DEFAULT_CC_VERSION = 1;
    private static final Integer UPDATED_CC_VERSION = 2;

    @Autowired
    private PublicHolidayRepository publicHolidayRepository;

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

    private MockMvc restPublicHolidayMockMvc;

    private PublicHoliday publicHoliday;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PublicHolidayResource publicHolidayResource = new PublicHolidayResource(publicHolidayRepository);
        this.restPublicHolidayMockMvc = MockMvcBuilders.standaloneSetup(publicHolidayResource)
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
    public static PublicHoliday createEntity(EntityManager em) {
        PublicHoliday publicHoliday = new PublicHoliday()
            .cstdHolidayTypes(DEFAULT_CSTD_HOLIDAY_TYPES)
            .description(DEFAULT_DESCRIPTION)
            .date(DEFAULT_DATE)
            .workingFlag(DEFAULT_WORKING_FLAG)
            .countryWide(DEFAULT_COUNTRY_WIDE)
            .ccVersion(DEFAULT_CC_VERSION);
        return publicHoliday;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PublicHoliday createUpdatedEntity(EntityManager em) {
        PublicHoliday publicHoliday = new PublicHoliday()
            .cstdHolidayTypes(UPDATED_CSTD_HOLIDAY_TYPES)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .workingFlag(UPDATED_WORKING_FLAG)
            .countryWide(UPDATED_COUNTRY_WIDE)
            .ccVersion(UPDATED_CC_VERSION);
        return publicHoliday;
    }

    @BeforeEach
    public void initTest() {
        publicHoliday = createEntity(em);
    }

    @Test
    @Transactional
    public void createPublicHoliday() throws Exception {
        int databaseSizeBeforeCreate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday
        restPublicHolidayMockMvc.perform(post("/api/public-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicHoliday)))
            .andExpect(status().isCreated());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeCreate + 1);
        PublicHoliday testPublicHoliday = publicHolidayList.get(publicHolidayList.size() - 1);
        assertThat(testPublicHoliday.getCstdHolidayTypes()).isEqualTo(DEFAULT_CSTD_HOLIDAY_TYPES);
        assertThat(testPublicHoliday.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPublicHoliday.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPublicHoliday.isWorkingFlag()).isEqualTo(DEFAULT_WORKING_FLAG);
        assertThat(testPublicHoliday.isCountryWide()).isEqualTo(DEFAULT_COUNTRY_WIDE);
        assertThat(testPublicHoliday.getCcVersion()).isEqualTo(DEFAULT_CC_VERSION);
    }

    @Test
    @Transactional
    public void createPublicHolidayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday with an existing ID
        publicHoliday.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPublicHolidayMockMvc.perform(post("/api/public-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicHoliday)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = publicHolidayRepository.findAll().size();
        // set the field null
        publicHoliday.setDate(null);

        // Create the PublicHoliday, which fails.

        restPublicHolidayMockMvc.perform(post("/api/public-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicHoliday)))
            .andExpect(status().isBadRequest());

        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPublicHolidays() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdHolidayTypes").value(hasItem(DEFAULT_CSTD_HOLIDAY_TYPES.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].workingFlag").value(hasItem(DEFAULT_WORKING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].countryWide").value(hasItem(DEFAULT_COUNTRY_WIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));
    }
    
    @Test
    @Transactional
    public void getPublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get the publicHoliday
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/{id}", publicHoliday.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(publicHoliday.getId().intValue()))
            .andExpect(jsonPath("$.cstdHolidayTypes").value(DEFAULT_CSTD_HOLIDAY_TYPES.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.workingFlag").value(DEFAULT_WORKING_FLAG.booleanValue()))
            .andExpect(jsonPath("$.countryWide").value(DEFAULT_COUNTRY_WIDE.booleanValue()))
            .andExpect(jsonPath("$.ccVersion").value(DEFAULT_CC_VERSION));
    }

    @Test
    @Transactional
    public void getNonExistingPublicHoliday() throws Exception {
        // Get the publicHoliday
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        int databaseSizeBeforeUpdate = publicHolidayRepository.findAll().size();

        // Update the publicHoliday
        PublicHoliday updatedPublicHoliday = publicHolidayRepository.findById(publicHoliday.getId()).get();
        // Disconnect from session so that the updates on updatedPublicHoliday are not directly saved in db
        em.detach(updatedPublicHoliday);
        updatedPublicHoliday
            .cstdHolidayTypes(UPDATED_CSTD_HOLIDAY_TYPES)
            .description(UPDATED_DESCRIPTION)
            .date(UPDATED_DATE)
            .workingFlag(UPDATED_WORKING_FLAG)
            .countryWide(UPDATED_COUNTRY_WIDE)
            .ccVersion(UPDATED_CC_VERSION);

        restPublicHolidayMockMvc.perform(put("/api/public-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPublicHoliday)))
            .andExpect(status().isOk());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeUpdate);
        PublicHoliday testPublicHoliday = publicHolidayList.get(publicHolidayList.size() - 1);
        assertThat(testPublicHoliday.getCstdHolidayTypes()).isEqualTo(UPDATED_CSTD_HOLIDAY_TYPES);
        assertThat(testPublicHoliday.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPublicHoliday.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPublicHoliday.isWorkingFlag()).isEqualTo(UPDATED_WORKING_FLAG);
        assertThat(testPublicHoliday.isCountryWide()).isEqualTo(UPDATED_COUNTRY_WIDE);
        assertThat(testPublicHoliday.getCcVersion()).isEqualTo(UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingPublicHoliday() throws Exception {
        int databaseSizeBeforeUpdate = publicHolidayRepository.findAll().size();

        // Create the PublicHoliday

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPublicHolidayMockMvc.perform(put("/api/public-holidays")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(publicHoliday)))
            .andExpect(status().isBadRequest());

        // Validate the PublicHoliday in the database
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePublicHoliday() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        int databaseSizeBeforeDelete = publicHolidayRepository.findAll().size();

        // Delete the publicHoliday
        restPublicHolidayMockMvc.perform(delete("/api/public-holidays/{id}", publicHoliday.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PublicHoliday> publicHolidayList = publicHolidayRepository.findAll();
        assertThat(publicHolidayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PublicHoliday.class);
        PublicHoliday publicHoliday1 = new PublicHoliday();
        publicHoliday1.setId(1L);
        PublicHoliday publicHoliday2 = new PublicHoliday();
        publicHoliday2.setId(publicHoliday1.getId());
        assertThat(publicHoliday1).isEqualTo(publicHoliday2);
        publicHoliday2.setId(2L);
        assertThat(publicHoliday1).isNotEqualTo(publicHoliday2);
        publicHoliday1.setId(null);
        assertThat(publicHoliday1).isNotEqualTo(publicHoliday2);
    }
}
