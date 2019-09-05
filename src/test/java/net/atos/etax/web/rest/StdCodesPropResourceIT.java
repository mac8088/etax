package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesProp;
import net.atos.etax.repository.StdCodesPropRepository;
import net.atos.etax.service.StdCodesPropService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.StdCodesPropCriteria;
import net.atos.etax.service.StdCodesPropQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static net.atos.etax.web.rest.TestUtil.sameInstant;
import static net.atos.etax.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link StdCodesPropResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class StdCodesPropResourceIT {

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROP_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final LocalDate DEFAULT_VALUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_VALUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_VALUE_STRING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_VALUE_BOOL = false;
    private static final Boolean UPDATED_VALUE_BOOL = true;

    private static final Double DEFAULT_VALUE_NUMBER = 1D;
    private static final Double UPDATED_VALUE_NUMBER = 2D;

    @Autowired
    private StdCodesPropRepository stdCodesPropRepository;

    @Autowired
    private StdCodesPropService stdCodesPropService;

    @Autowired
    private StdCodesPropQueryService stdCodesPropQueryService;

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

    private MockMvc restStdCodesPropMockMvc;

    private StdCodesProp stdCodesProp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StdCodesPropResource stdCodesPropResource = new StdCodesPropResource(stdCodesPropService, stdCodesPropQueryService);
        this.restStdCodesPropMockMvc = MockMvcBuilders.standaloneSetup(stdCodesPropResource)
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
    public static StdCodesProp createEntity(EntityManager em) {
        StdCodesProp stdCodesProp = new StdCodesProp()
            .groupCode(DEFAULT_GROUP_CODE)
            .internalCode(DEFAULT_INTERNAL_CODE)
            .propCode(DEFAULT_PROP_CODE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .valueDate(DEFAULT_VALUE_DATE)
            .valueString(DEFAULT_VALUE_STRING)
            .valueBool(DEFAULT_VALUE_BOOL)
            .valueNumber(DEFAULT_VALUE_NUMBER);
        return stdCodesProp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StdCodesProp createUpdatedEntity(EntityManager em) {
        StdCodesProp stdCodesProp = new StdCodesProp()
            .groupCode(UPDATED_GROUP_CODE)
            .internalCode(UPDATED_INTERNAL_CODE)
            .propCode(UPDATED_PROP_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .valueDate(UPDATED_VALUE_DATE)
            .valueString(UPDATED_VALUE_STRING)
            .valueBool(UPDATED_VALUE_BOOL)
            .valueNumber(UPDATED_VALUE_NUMBER);
        return stdCodesProp;
    }

    @BeforeEach
    public void initTest() {
        stdCodesProp = createEntity(em);
    }

    @Test
    @Transactional
    public void createStdCodesProp() throws Exception {
        int databaseSizeBeforeCreate = stdCodesPropRepository.findAll().size();

        // Create the StdCodesProp
        restStdCodesPropMockMvc.perform(post("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isCreated());

        // Validate the StdCodesProp in the database
        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeCreate + 1);
        StdCodesProp testStdCodesProp = stdCodesPropList.get(stdCodesPropList.size() - 1);
        assertThat(testStdCodesProp.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testStdCodesProp.getInternalCode()).isEqualTo(DEFAULT_INTERNAL_CODE);
        assertThat(testStdCodesProp.getPropCode()).isEqualTo(DEFAULT_PROP_CODE);
        assertThat(testStdCodesProp.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStdCodesProp.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStdCodesProp.getValueDate()).isEqualTo(DEFAULT_VALUE_DATE);
        assertThat(testStdCodesProp.getValueString()).isEqualTo(DEFAULT_VALUE_STRING);
        assertThat(testStdCodesProp.isValueBool()).isEqualTo(DEFAULT_VALUE_BOOL);
        assertThat(testStdCodesProp.getValueNumber()).isEqualTo(DEFAULT_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void createStdCodesPropWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stdCodesPropRepository.findAll().size();

        // Create the StdCodesProp with an existing ID
        stdCodesProp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStdCodesPropMockMvc.perform(post("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesProp in the database
        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesPropRepository.findAll().size();
        // set the field null
        stdCodesProp.setGroupCode(null);

        // Create the StdCodesProp, which fails.

        restStdCodesPropMockMvc.perform(post("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesPropRepository.findAll().size();
        // set the field null
        stdCodesProp.setInternalCode(null);

        // Create the StdCodesProp, which fails.

        restStdCodesPropMockMvc.perform(post("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesPropRepository.findAll().size();
        // set the field null
        stdCodesProp.setPropCode(null);

        // Create the StdCodesProp, which fails.

        restStdCodesPropMockMvc.perform(post("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesPropRepository.findAll().size();
        // set the field null
        stdCodesProp.setStartDate(null);

        // Create the StdCodesProp, which fails.

        restStdCodesPropMockMvc.perform(post("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStdCodesProps() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesProp.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].propCode").value(hasItem(DEFAULT_PROP_CODE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].valueDate").value(hasItem(DEFAULT_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING.toString())))
            .andExpect(jsonPath("$.[*].valueBool").value(hasItem(DEFAULT_VALUE_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].valueNumber").value(hasItem(DEFAULT_VALUE_NUMBER.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStdCodesProp() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get the stdCodesProp
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props/{id}", stdCodesProp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stdCodesProp.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.internalCode").value(DEFAULT_INTERNAL_CODE.toString()))
            .andExpect(jsonPath("$.propCode").value(DEFAULT_PROP_CODE.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.valueDate").value(DEFAULT_VALUE_DATE.toString()))
            .andExpect(jsonPath("$.valueString").value(DEFAULT_VALUE_STRING.toString()))
            .andExpect(jsonPath("$.valueBool").value(DEFAULT_VALUE_BOOL.booleanValue()))
            .andExpect(jsonPath("$.valueNumber").value(DEFAULT_VALUE_NUMBER.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where groupCode equals to DEFAULT_GROUP_CODE
        defaultStdCodesPropShouldBeFound("groupCode.equals=" + DEFAULT_GROUP_CODE);

        // Get all the stdCodesPropList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesPropShouldNotBeFound("groupCode.equals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where groupCode in DEFAULT_GROUP_CODE or UPDATED_GROUP_CODE
        defaultStdCodesPropShouldBeFound("groupCode.in=" + DEFAULT_GROUP_CODE + "," + UPDATED_GROUP_CODE);

        // Get all the stdCodesPropList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesPropShouldNotBeFound("groupCode.in=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where groupCode is not null
        defaultStdCodesPropShouldBeFound("groupCode.specified=true");

        // Get all the stdCodesPropList where groupCode is null
        defaultStdCodesPropShouldNotBeFound("groupCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByInternalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where internalCode equals to DEFAULT_INTERNAL_CODE
        defaultStdCodesPropShouldBeFound("internalCode.equals=" + DEFAULT_INTERNAL_CODE);

        // Get all the stdCodesPropList where internalCode equals to UPDATED_INTERNAL_CODE
        defaultStdCodesPropShouldNotBeFound("internalCode.equals=" + UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByInternalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where internalCode in DEFAULT_INTERNAL_CODE or UPDATED_INTERNAL_CODE
        defaultStdCodesPropShouldBeFound("internalCode.in=" + DEFAULT_INTERNAL_CODE + "," + UPDATED_INTERNAL_CODE);

        // Get all the stdCodesPropList where internalCode equals to UPDATED_INTERNAL_CODE
        defaultStdCodesPropShouldNotBeFound("internalCode.in=" + UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByInternalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where internalCode is not null
        defaultStdCodesPropShouldBeFound("internalCode.specified=true");

        // Get all the stdCodesPropList where internalCode is null
        defaultStdCodesPropShouldNotBeFound("internalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByPropCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where propCode equals to DEFAULT_PROP_CODE
        defaultStdCodesPropShouldBeFound("propCode.equals=" + DEFAULT_PROP_CODE);

        // Get all the stdCodesPropList where propCode equals to UPDATED_PROP_CODE
        defaultStdCodesPropShouldNotBeFound("propCode.equals=" + UPDATED_PROP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByPropCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where propCode in DEFAULT_PROP_CODE or UPDATED_PROP_CODE
        defaultStdCodesPropShouldBeFound("propCode.in=" + DEFAULT_PROP_CODE + "," + UPDATED_PROP_CODE);

        // Get all the stdCodesPropList where propCode equals to UPDATED_PROP_CODE
        defaultStdCodesPropShouldNotBeFound("propCode.in=" + UPDATED_PROP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByPropCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where propCode is not null
        defaultStdCodesPropShouldBeFound("propCode.specified=true");

        // Get all the stdCodesPropList where propCode is null
        defaultStdCodesPropShouldNotBeFound("propCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where startDate equals to DEFAULT_START_DATE
        defaultStdCodesPropShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the stdCodesPropList where startDate equals to UPDATED_START_DATE
        defaultStdCodesPropShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultStdCodesPropShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the stdCodesPropList where startDate equals to UPDATED_START_DATE
        defaultStdCodesPropShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where startDate is not null
        defaultStdCodesPropShouldBeFound("startDate.specified=true");

        // Get all the stdCodesPropList where startDate is null
        defaultStdCodesPropShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where startDate greater than or equals to DEFAULT_START_DATE
        defaultStdCodesPropShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesPropList where startDate greater than or equals to UPDATED_START_DATE
        defaultStdCodesPropShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where startDate less than or equals to DEFAULT_START_DATE
        defaultStdCodesPropShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesPropList where startDate less than or equals to UPDATED_START_DATE
        defaultStdCodesPropShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesPropsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where endDate equals to DEFAULT_END_DATE
        defaultStdCodesPropShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the stdCodesPropList where endDate equals to UPDATED_END_DATE
        defaultStdCodesPropShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultStdCodesPropShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the stdCodesPropList where endDate equals to UPDATED_END_DATE
        defaultStdCodesPropShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where endDate is not null
        defaultStdCodesPropShouldBeFound("endDate.specified=true");

        // Get all the stdCodesPropList where endDate is null
        defaultStdCodesPropShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where endDate greater than or equals to DEFAULT_END_DATE
        defaultStdCodesPropShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesPropList where endDate greater than or equals to UPDATED_END_DATE
        defaultStdCodesPropShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where endDate less than or equals to DEFAULT_END_DATE
        defaultStdCodesPropShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesPropList where endDate less than or equals to UPDATED_END_DATE
        defaultStdCodesPropShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesPropsByValueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueDate equals to DEFAULT_VALUE_DATE
        defaultStdCodesPropShouldBeFound("valueDate.equals=" + DEFAULT_VALUE_DATE);

        // Get all the stdCodesPropList where valueDate equals to UPDATED_VALUE_DATE
        defaultStdCodesPropShouldNotBeFound("valueDate.equals=" + UPDATED_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueDate in DEFAULT_VALUE_DATE or UPDATED_VALUE_DATE
        defaultStdCodesPropShouldBeFound("valueDate.in=" + DEFAULT_VALUE_DATE + "," + UPDATED_VALUE_DATE);

        // Get all the stdCodesPropList where valueDate equals to UPDATED_VALUE_DATE
        defaultStdCodesPropShouldNotBeFound("valueDate.in=" + UPDATED_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueDate is not null
        defaultStdCodesPropShouldBeFound("valueDate.specified=true");

        // Get all the stdCodesPropList where valueDate is null
        defaultStdCodesPropShouldNotBeFound("valueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueDate greater than or equals to DEFAULT_VALUE_DATE
        defaultStdCodesPropShouldBeFound("valueDate.greaterOrEqualThan=" + DEFAULT_VALUE_DATE);

        // Get all the stdCodesPropList where valueDate greater than or equals to UPDATED_VALUE_DATE
        defaultStdCodesPropShouldNotBeFound("valueDate.greaterOrEqualThan=" + UPDATED_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueDate less than or equals to DEFAULT_VALUE_DATE
        defaultStdCodesPropShouldNotBeFound("valueDate.lessThan=" + DEFAULT_VALUE_DATE);

        // Get all the stdCodesPropList where valueDate less than or equals to UPDATED_VALUE_DATE
        defaultStdCodesPropShouldBeFound("valueDate.lessThan=" + UPDATED_VALUE_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesPropsByValueStringIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueString equals to DEFAULT_VALUE_STRING
        defaultStdCodesPropShouldBeFound("valueString.equals=" + DEFAULT_VALUE_STRING);

        // Get all the stdCodesPropList where valueString equals to UPDATED_VALUE_STRING
        defaultStdCodesPropShouldNotBeFound("valueString.equals=" + UPDATED_VALUE_STRING);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueStringIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueString in DEFAULT_VALUE_STRING or UPDATED_VALUE_STRING
        defaultStdCodesPropShouldBeFound("valueString.in=" + DEFAULT_VALUE_STRING + "," + UPDATED_VALUE_STRING);

        // Get all the stdCodesPropList where valueString equals to UPDATED_VALUE_STRING
        defaultStdCodesPropShouldNotBeFound("valueString.in=" + UPDATED_VALUE_STRING);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueString is not null
        defaultStdCodesPropShouldBeFound("valueString.specified=true");

        // Get all the stdCodesPropList where valueString is null
        defaultStdCodesPropShouldNotBeFound("valueString.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueBoolIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueBool equals to DEFAULT_VALUE_BOOL
        defaultStdCodesPropShouldBeFound("valueBool.equals=" + DEFAULT_VALUE_BOOL);

        // Get all the stdCodesPropList where valueBool equals to UPDATED_VALUE_BOOL
        defaultStdCodesPropShouldNotBeFound("valueBool.equals=" + UPDATED_VALUE_BOOL);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueBoolIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueBool in DEFAULT_VALUE_BOOL or UPDATED_VALUE_BOOL
        defaultStdCodesPropShouldBeFound("valueBool.in=" + DEFAULT_VALUE_BOOL + "," + UPDATED_VALUE_BOOL);

        // Get all the stdCodesPropList where valueBool equals to UPDATED_VALUE_BOOL
        defaultStdCodesPropShouldNotBeFound("valueBool.in=" + UPDATED_VALUE_BOOL);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueBoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueBool is not null
        defaultStdCodesPropShouldBeFound("valueBool.specified=true");

        // Get all the stdCodesPropList where valueBool is null
        defaultStdCodesPropShouldNotBeFound("valueBool.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueNumber equals to DEFAULT_VALUE_NUMBER
        defaultStdCodesPropShouldBeFound("valueNumber.equals=" + DEFAULT_VALUE_NUMBER);

        // Get all the stdCodesPropList where valueNumber equals to UPDATED_VALUE_NUMBER
        defaultStdCodesPropShouldNotBeFound("valueNumber.equals=" + UPDATED_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueNumberIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueNumber in DEFAULT_VALUE_NUMBER or UPDATED_VALUE_NUMBER
        defaultStdCodesPropShouldBeFound("valueNumber.in=" + DEFAULT_VALUE_NUMBER + "," + UPDATED_VALUE_NUMBER);

        // Get all the stdCodesPropList where valueNumber equals to UPDATED_VALUE_NUMBER
        defaultStdCodesPropShouldNotBeFound("valueNumber.in=" + UPDATED_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesPropsByValueNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

        // Get all the stdCodesPropList where valueNumber is not null
        defaultStdCodesPropShouldBeFound("valueNumber.specified=true");

        // Get all the stdCodesPropList where valueNumber is null
        defaultStdCodesPropShouldNotBeFound("valueNumber.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStdCodesPropShouldBeFound(String filter) throws Exception {
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesProp.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE)))
            .andExpect(jsonPath("$.[*].propCode").value(hasItem(DEFAULT_PROP_CODE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].valueDate").value(hasItem(DEFAULT_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].valueString").value(hasItem(DEFAULT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].valueBool").value(hasItem(DEFAULT_VALUE_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].valueNumber").value(hasItem(DEFAULT_VALUE_NUMBER.doubleValue())));

        // Check, that the count call also returns 1
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStdCodesPropShouldNotBeFound(String filter) throws Exception {
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStdCodesProp() throws Exception {
        // Get the stdCodesProp
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesProp() throws Exception {
        // Initialize the database
        stdCodesPropService.save(stdCodesProp);

        int databaseSizeBeforeUpdate = stdCodesPropRepository.findAll().size();

        // Update the stdCodesProp
        StdCodesProp updatedStdCodesProp = stdCodesPropRepository.findById(stdCodesProp.getId()).get();
        // Disconnect from session so that the updates on updatedStdCodesProp are not directly saved in db
        em.detach(updatedStdCodesProp);
        updatedStdCodesProp
            .groupCode(UPDATED_GROUP_CODE)
            .internalCode(UPDATED_INTERNAL_CODE)
            .propCode(UPDATED_PROP_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .valueDate(UPDATED_VALUE_DATE)
            .valueString(UPDATED_VALUE_STRING)
            .valueBool(UPDATED_VALUE_BOOL)
            .valueNumber(UPDATED_VALUE_NUMBER);

        restStdCodesPropMockMvc.perform(put("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStdCodesProp)))
            .andExpect(status().isOk());

        // Validate the StdCodesProp in the database
        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeUpdate);
        StdCodesProp testStdCodesProp = stdCodesPropList.get(stdCodesPropList.size() - 1);
        assertThat(testStdCodesProp.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testStdCodesProp.getInternalCode()).isEqualTo(UPDATED_INTERNAL_CODE);
        assertThat(testStdCodesProp.getPropCode()).isEqualTo(UPDATED_PROP_CODE);
        assertThat(testStdCodesProp.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStdCodesProp.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStdCodesProp.getValueDate()).isEqualTo(UPDATED_VALUE_DATE);
        assertThat(testStdCodesProp.getValueString()).isEqualTo(UPDATED_VALUE_STRING);
        assertThat(testStdCodesProp.isValueBool()).isEqualTo(UPDATED_VALUE_BOOL);
        assertThat(testStdCodesProp.getValueNumber()).isEqualTo(UPDATED_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingStdCodesProp() throws Exception {
        int databaseSizeBeforeUpdate = stdCodesPropRepository.findAll().size();

        // Create the StdCodesProp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdCodesPropMockMvc.perform(put("/api/std-codes-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesProp)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesProp in the database
        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStdCodesProp() throws Exception {
        // Initialize the database
        stdCodesPropService.save(stdCodesProp);

        int databaseSizeBeforeDelete = stdCodesPropRepository.findAll().size();

        // Delete the stdCodesProp
        restStdCodesPropMockMvc.perform(delete("/api/std-codes-props/{id}", stdCodesProp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StdCodesProp> stdCodesPropList = stdCodesPropRepository.findAll();
        assertThat(stdCodesPropList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StdCodesProp.class);
        StdCodesProp stdCodesProp1 = new StdCodesProp();
        stdCodesProp1.setId(1L);
        StdCodesProp stdCodesProp2 = new StdCodesProp();
        stdCodesProp2.setId(stdCodesProp1.getId());
        assertThat(stdCodesProp1).isEqualTo(stdCodesProp2);
        stdCodesProp2.setId(2L);
        assertThat(stdCodesProp1).isNotEqualTo(stdCodesProp2);
        stdCodesProp1.setId(null);
        assertThat(stdCodesProp1).isNotEqualTo(stdCodesProp2);
    }
}
