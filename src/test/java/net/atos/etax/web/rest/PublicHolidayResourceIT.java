package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.PublicHoliday;
import net.atos.etax.repository.PublicHolidayRepository;
import net.atos.etax.service.PublicHolidayService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.PublicHolidayCriteria;
import net.atos.etax.service.PublicHolidayQueryService;

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
    private PublicHolidayService publicHolidayService;

    @Autowired
    private PublicHolidayQueryService publicHolidayQueryService;

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
        final PublicHolidayResource publicHolidayResource = new PublicHolidayResource(publicHolidayService, publicHolidayQueryService);
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
    public void getAllPublicHolidaysByCstdHolidayTypesIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where cstdHolidayTypes equals to DEFAULT_CSTD_HOLIDAY_TYPES
        defaultPublicHolidayShouldBeFound("cstdHolidayTypes.equals=" + DEFAULT_CSTD_HOLIDAY_TYPES);

        // Get all the publicHolidayList where cstdHolidayTypes equals to UPDATED_CSTD_HOLIDAY_TYPES
        defaultPublicHolidayShouldNotBeFound("cstdHolidayTypes.equals=" + UPDATED_CSTD_HOLIDAY_TYPES);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCstdHolidayTypesIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where cstdHolidayTypes in DEFAULT_CSTD_HOLIDAY_TYPES or UPDATED_CSTD_HOLIDAY_TYPES
        defaultPublicHolidayShouldBeFound("cstdHolidayTypes.in=" + DEFAULT_CSTD_HOLIDAY_TYPES + "," + UPDATED_CSTD_HOLIDAY_TYPES);

        // Get all the publicHolidayList where cstdHolidayTypes equals to UPDATED_CSTD_HOLIDAY_TYPES
        defaultPublicHolidayShouldNotBeFound("cstdHolidayTypes.in=" + UPDATED_CSTD_HOLIDAY_TYPES);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCstdHolidayTypesIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where cstdHolidayTypes is not null
        defaultPublicHolidayShouldBeFound("cstdHolidayTypes.specified=true");

        // Get all the publicHolidayList where cstdHolidayTypes is null
        defaultPublicHolidayShouldNotBeFound("cstdHolidayTypes.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description equals to DEFAULT_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the publicHolidayList where description equals to UPDATED_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPublicHolidayShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the publicHolidayList where description equals to UPDATED_DESCRIPTION
        defaultPublicHolidayShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where description is not null
        defaultPublicHolidayShouldBeFound("description.specified=true");

        // Get all the publicHolidayList where description is null
        defaultPublicHolidayShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where date equals to DEFAULT_DATE
        defaultPublicHolidayShouldBeFound("date.equals=" + DEFAULT_DATE);

        // Get all the publicHolidayList where date equals to UPDATED_DATE
        defaultPublicHolidayShouldNotBeFound("date.equals=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where date in DEFAULT_DATE or UPDATED_DATE
        defaultPublicHolidayShouldBeFound("date.in=" + DEFAULT_DATE + "," + UPDATED_DATE);

        // Get all the publicHolidayList where date equals to UPDATED_DATE
        defaultPublicHolidayShouldNotBeFound("date.in=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where date is not null
        defaultPublicHolidayShouldBeFound("date.specified=true");

        // Get all the publicHolidayList where date is null
        defaultPublicHolidayShouldNotBeFound("date.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where date greater than or equals to DEFAULT_DATE
        defaultPublicHolidayShouldBeFound("date.greaterOrEqualThan=" + DEFAULT_DATE);

        // Get all the publicHolidayList where date greater than or equals to UPDATED_DATE
        defaultPublicHolidayShouldNotBeFound("date.greaterOrEqualThan=" + UPDATED_DATE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByDateIsLessThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where date less than or equals to DEFAULT_DATE
        defaultPublicHolidayShouldNotBeFound("date.lessThan=" + DEFAULT_DATE);

        // Get all the publicHolidayList where date less than or equals to UPDATED_DATE
        defaultPublicHolidayShouldBeFound("date.lessThan=" + UPDATED_DATE);
    }


    @Test
    @Transactional
    public void getAllPublicHolidaysByWorkingFlagIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where workingFlag equals to DEFAULT_WORKING_FLAG
        defaultPublicHolidayShouldBeFound("workingFlag.equals=" + DEFAULT_WORKING_FLAG);

        // Get all the publicHolidayList where workingFlag equals to UPDATED_WORKING_FLAG
        defaultPublicHolidayShouldNotBeFound("workingFlag.equals=" + UPDATED_WORKING_FLAG);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByWorkingFlagIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where workingFlag in DEFAULT_WORKING_FLAG or UPDATED_WORKING_FLAG
        defaultPublicHolidayShouldBeFound("workingFlag.in=" + DEFAULT_WORKING_FLAG + "," + UPDATED_WORKING_FLAG);

        // Get all the publicHolidayList where workingFlag equals to UPDATED_WORKING_FLAG
        defaultPublicHolidayShouldNotBeFound("workingFlag.in=" + UPDATED_WORKING_FLAG);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByWorkingFlagIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where workingFlag is not null
        defaultPublicHolidayShouldBeFound("workingFlag.specified=true");

        // Get all the publicHolidayList where workingFlag is null
        defaultPublicHolidayShouldNotBeFound("workingFlag.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCountryWideIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where countryWide equals to DEFAULT_COUNTRY_WIDE
        defaultPublicHolidayShouldBeFound("countryWide.equals=" + DEFAULT_COUNTRY_WIDE);

        // Get all the publicHolidayList where countryWide equals to UPDATED_COUNTRY_WIDE
        defaultPublicHolidayShouldNotBeFound("countryWide.equals=" + UPDATED_COUNTRY_WIDE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCountryWideIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where countryWide in DEFAULT_COUNTRY_WIDE or UPDATED_COUNTRY_WIDE
        defaultPublicHolidayShouldBeFound("countryWide.in=" + DEFAULT_COUNTRY_WIDE + "," + UPDATED_COUNTRY_WIDE);

        // Get all the publicHolidayList where countryWide equals to UPDATED_COUNTRY_WIDE
        defaultPublicHolidayShouldNotBeFound("countryWide.in=" + UPDATED_COUNTRY_WIDE);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCountryWideIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where countryWide is not null
        defaultPublicHolidayShouldBeFound("countryWide.specified=true");

        // Get all the publicHolidayList where countryWide is null
        defaultPublicHolidayShouldNotBeFound("countryWide.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCcVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where ccVersion equals to DEFAULT_CC_VERSION
        defaultPublicHolidayShouldBeFound("ccVersion.equals=" + DEFAULT_CC_VERSION);

        // Get all the publicHolidayList where ccVersion equals to UPDATED_CC_VERSION
        defaultPublicHolidayShouldNotBeFound("ccVersion.equals=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCcVersionIsInShouldWork() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where ccVersion in DEFAULT_CC_VERSION or UPDATED_CC_VERSION
        defaultPublicHolidayShouldBeFound("ccVersion.in=" + DEFAULT_CC_VERSION + "," + UPDATED_CC_VERSION);

        // Get all the publicHolidayList where ccVersion equals to UPDATED_CC_VERSION
        defaultPublicHolidayShouldNotBeFound("ccVersion.in=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCcVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where ccVersion is not null
        defaultPublicHolidayShouldBeFound("ccVersion.specified=true");

        // Get all the publicHolidayList where ccVersion is null
        defaultPublicHolidayShouldNotBeFound("ccVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCcVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where ccVersion greater than or equals to DEFAULT_CC_VERSION
        defaultPublicHolidayShouldBeFound("ccVersion.greaterOrEqualThan=" + DEFAULT_CC_VERSION);

        // Get all the publicHolidayList where ccVersion greater than or equals to UPDATED_CC_VERSION
        defaultPublicHolidayShouldNotBeFound("ccVersion.greaterOrEqualThan=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllPublicHolidaysByCcVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        publicHolidayRepository.saveAndFlush(publicHoliday);

        // Get all the publicHolidayList where ccVersion less than or equals to DEFAULT_CC_VERSION
        defaultPublicHolidayShouldNotBeFound("ccVersion.lessThan=" + DEFAULT_CC_VERSION);

        // Get all the publicHolidayList where ccVersion less than or equals to UPDATED_CC_VERSION
        defaultPublicHolidayShouldBeFound("ccVersion.lessThan=" + UPDATED_CC_VERSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPublicHolidayShouldBeFound(String filter) throws Exception {
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(publicHoliday.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdHolidayTypes").value(hasItem(DEFAULT_CSTD_HOLIDAY_TYPES)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].workingFlag").value(hasItem(DEFAULT_WORKING_FLAG.booleanValue())))
            .andExpect(jsonPath("$.[*].countryWide").value(hasItem(DEFAULT_COUNTRY_WIDE.booleanValue())))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));

        // Check, that the count call also returns 1
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPublicHolidayShouldNotBeFound(String filter) throws Exception {
        restPublicHolidayMockMvc.perform(get("/api/public-holidays?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPublicHolidayMockMvc.perform(get("/api/public-holidays/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        publicHolidayService.save(publicHoliday);

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
        publicHolidayService.save(publicHoliday);

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
