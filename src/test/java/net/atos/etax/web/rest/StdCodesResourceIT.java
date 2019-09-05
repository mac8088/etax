package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodes;
import net.atos.etax.domain.StdCodesDesc;
import net.atos.etax.domain.StdCodesGroup;
import net.atos.etax.repository.StdCodesRepository;
import net.atos.etax.service.StdCodesService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.StdCodesCriteria;
import net.atos.etax.service.StdCodesQueryService;

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
 * Integration tests for the {@Link StdCodesResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class StdCodesResourceIT {

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PARENT_INTERNAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_INTERNAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEC_LEVEL = 99;
    private static final Integer UPDATED_SEC_LEVEL = 98;

    private static final LocalDate DEFAULT_CODE_VALUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CODE_VALUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_CODE_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_CODE_VALUE_STRING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_CODE_VALUE_BOOL = false;
    private static final Boolean UPDATED_CODE_VALUE_BOOL = true;

    private static final Integer DEFAULT_CODE_VALUE_NUMBER = 1;
    private static final Integer UPDATED_CODE_VALUE_NUMBER = 2;

    @Autowired
    private StdCodesRepository stdCodesRepository;

    @Autowired
    private StdCodesService stdCodesService;

    @Autowired
    private StdCodesQueryService stdCodesQueryService;

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

    private MockMvc restStdCodesMockMvc;

    private StdCodes stdCodes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StdCodesResource stdCodesResource = new StdCodesResource(stdCodesService, stdCodesQueryService);
        this.restStdCodesMockMvc = MockMvcBuilders.standaloneSetup(stdCodesResource)
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
    public static StdCodes createEntity(EntityManager em) {
        StdCodes stdCodes = new StdCodes()
            .groupCode(DEFAULT_GROUP_CODE)
            .internalCode(DEFAULT_INTERNAL_CODE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .parentInternalCode(DEFAULT_PARENT_INTERNAL_CODE)
            .comments(DEFAULT_COMMENTS)
            .secLevel(DEFAULT_SEC_LEVEL)
            .codeValueDate(DEFAULT_CODE_VALUE_DATE)
            .codeValueString(DEFAULT_CODE_VALUE_STRING)
            .codeValueBool(DEFAULT_CODE_VALUE_BOOL)
            .codeValueNumber(DEFAULT_CODE_VALUE_NUMBER);
        return stdCodes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StdCodes createUpdatedEntity(EntityManager em) {
        StdCodes stdCodes = new StdCodes()
            .groupCode(UPDATED_GROUP_CODE)
            .internalCode(UPDATED_INTERNAL_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .parentInternalCode(UPDATED_PARENT_INTERNAL_CODE)
            .comments(UPDATED_COMMENTS)
            .secLevel(UPDATED_SEC_LEVEL)
            .codeValueDate(UPDATED_CODE_VALUE_DATE)
            .codeValueString(UPDATED_CODE_VALUE_STRING)
            .codeValueBool(UPDATED_CODE_VALUE_BOOL)
            .codeValueNumber(UPDATED_CODE_VALUE_NUMBER);
        return stdCodes;
    }

    @BeforeEach
    public void initTest() {
        stdCodes = createEntity(em);
    }

    @Test
    @Transactional
    public void createStdCodes() throws Exception {
        int databaseSizeBeforeCreate = stdCodesRepository.findAll().size();

        // Create the StdCodes
        restStdCodesMockMvc.perform(post("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isCreated());

        // Validate the StdCodes in the database
        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeCreate + 1);
        StdCodes testStdCodes = stdCodesList.get(stdCodesList.size() - 1);
        assertThat(testStdCodes.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testStdCodes.getInternalCode()).isEqualTo(DEFAULT_INTERNAL_CODE);
        assertThat(testStdCodes.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStdCodes.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStdCodes.getParentInternalCode()).isEqualTo(DEFAULT_PARENT_INTERNAL_CODE);
        assertThat(testStdCodes.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testStdCodes.getSecLevel()).isEqualTo(DEFAULT_SEC_LEVEL);
        assertThat(testStdCodes.getCodeValueDate()).isEqualTo(DEFAULT_CODE_VALUE_DATE);
        assertThat(testStdCodes.getCodeValueString()).isEqualTo(DEFAULT_CODE_VALUE_STRING);
        assertThat(testStdCodes.isCodeValueBool()).isEqualTo(DEFAULT_CODE_VALUE_BOOL);
        assertThat(testStdCodes.getCodeValueNumber()).isEqualTo(DEFAULT_CODE_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void createStdCodesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stdCodesRepository.findAll().size();

        // Create the StdCodes with an existing ID
        stdCodes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStdCodesMockMvc.perform(post("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodes in the database
        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesRepository.findAll().size();
        // set the field null
        stdCodes.setGroupCode(null);

        // Create the StdCodes, which fails.

        restStdCodesMockMvc.perform(post("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isBadRequest());

        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesRepository.findAll().size();
        // set the field null
        stdCodes.setInternalCode(null);

        // Create the StdCodes, which fails.

        restStdCodesMockMvc.perform(post("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isBadRequest());

        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesRepository.findAll().size();
        // set the field null
        stdCodes.setStartDate(null);

        // Create the StdCodes, which fails.

        restStdCodesMockMvc.perform(post("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isBadRequest());

        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesRepository.findAll().size();
        // set the field null
        stdCodes.setSecLevel(null);

        // Create the StdCodes, which fails.

        restStdCodesMockMvc.perform(post("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isBadRequest());

        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStdCodes() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList
        restStdCodesMockMvc.perform(get("/api/std-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodes.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].parentInternalCode").value(hasItem(DEFAULT_PARENT_INTERNAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS.toString())))
            .andExpect(jsonPath("$.[*].secLevel").value(hasItem(DEFAULT_SEC_LEVEL)))
            .andExpect(jsonPath("$.[*].codeValueDate").value(hasItem(DEFAULT_CODE_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].codeValueString").value(hasItem(DEFAULT_CODE_VALUE_STRING.toString())))
            .andExpect(jsonPath("$.[*].codeValueBool").value(hasItem(DEFAULT_CODE_VALUE_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].codeValueNumber").value(hasItem(DEFAULT_CODE_VALUE_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getStdCodes() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get the stdCodes
        restStdCodesMockMvc.perform(get("/api/std-codes/{id}", stdCodes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stdCodes.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.internalCode").value(DEFAULT_INTERNAL_CODE.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.parentInternalCode").value(DEFAULT_PARENT_INTERNAL_CODE.toString()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS.toString()))
            .andExpect(jsonPath("$.secLevel").value(DEFAULT_SEC_LEVEL))
            .andExpect(jsonPath("$.codeValueDate").value(DEFAULT_CODE_VALUE_DATE.toString()))
            .andExpect(jsonPath("$.codeValueString").value(DEFAULT_CODE_VALUE_STRING.toString()))
            .andExpect(jsonPath("$.codeValueBool").value(DEFAULT_CODE_VALUE_BOOL.booleanValue()))
            .andExpect(jsonPath("$.codeValueNumber").value(DEFAULT_CODE_VALUE_NUMBER));
    }

    @Test
    @Transactional
    public void getAllStdCodesByGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where groupCode equals to DEFAULT_GROUP_CODE
        defaultStdCodesShouldBeFound("groupCode.equals=" + DEFAULT_GROUP_CODE);

        // Get all the stdCodesList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesShouldNotBeFound("groupCode.equals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where groupCode in DEFAULT_GROUP_CODE or UPDATED_GROUP_CODE
        defaultStdCodesShouldBeFound("groupCode.in=" + DEFAULT_GROUP_CODE + "," + UPDATED_GROUP_CODE);

        // Get all the stdCodesList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesShouldNotBeFound("groupCode.in=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where groupCode is not null
        defaultStdCodesShouldBeFound("groupCode.specified=true");

        // Get all the stdCodesList where groupCode is null
        defaultStdCodesShouldNotBeFound("groupCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByInternalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where internalCode equals to DEFAULT_INTERNAL_CODE
        defaultStdCodesShouldBeFound("internalCode.equals=" + DEFAULT_INTERNAL_CODE);

        // Get all the stdCodesList where internalCode equals to UPDATED_INTERNAL_CODE
        defaultStdCodesShouldNotBeFound("internalCode.equals=" + UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByInternalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where internalCode in DEFAULT_INTERNAL_CODE or UPDATED_INTERNAL_CODE
        defaultStdCodesShouldBeFound("internalCode.in=" + DEFAULT_INTERNAL_CODE + "," + UPDATED_INTERNAL_CODE);

        // Get all the stdCodesList where internalCode equals to UPDATED_INTERNAL_CODE
        defaultStdCodesShouldNotBeFound("internalCode.in=" + UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByInternalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where internalCode is not null
        defaultStdCodesShouldBeFound("internalCode.specified=true");

        // Get all the stdCodesList where internalCode is null
        defaultStdCodesShouldNotBeFound("internalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where startDate equals to DEFAULT_START_DATE
        defaultStdCodesShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the stdCodesList where startDate equals to UPDATED_START_DATE
        defaultStdCodesShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultStdCodesShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the stdCodesList where startDate equals to UPDATED_START_DATE
        defaultStdCodesShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where startDate is not null
        defaultStdCodesShouldBeFound("startDate.specified=true");

        // Get all the stdCodesList where startDate is null
        defaultStdCodesShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where startDate greater than or equals to DEFAULT_START_DATE
        defaultStdCodesShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesList where startDate greater than or equals to UPDATED_START_DATE
        defaultStdCodesShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where startDate less than or equals to DEFAULT_START_DATE
        defaultStdCodesShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesList where startDate less than or equals to UPDATED_START_DATE
        defaultStdCodesShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where endDate equals to DEFAULT_END_DATE
        defaultStdCodesShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the stdCodesList where endDate equals to UPDATED_END_DATE
        defaultStdCodesShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultStdCodesShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the stdCodesList where endDate equals to UPDATED_END_DATE
        defaultStdCodesShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where endDate is not null
        defaultStdCodesShouldBeFound("endDate.specified=true");

        // Get all the stdCodesList where endDate is null
        defaultStdCodesShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where endDate greater than or equals to DEFAULT_END_DATE
        defaultStdCodesShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesList where endDate greater than or equals to UPDATED_END_DATE
        defaultStdCodesShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where endDate less than or equals to DEFAULT_END_DATE
        defaultStdCodesShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesList where endDate less than or equals to UPDATED_END_DATE
        defaultStdCodesShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesByParentInternalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where parentInternalCode equals to DEFAULT_PARENT_INTERNAL_CODE
        defaultStdCodesShouldBeFound("parentInternalCode.equals=" + DEFAULT_PARENT_INTERNAL_CODE);

        // Get all the stdCodesList where parentInternalCode equals to UPDATED_PARENT_INTERNAL_CODE
        defaultStdCodesShouldNotBeFound("parentInternalCode.equals=" + UPDATED_PARENT_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByParentInternalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where parentInternalCode in DEFAULT_PARENT_INTERNAL_CODE or UPDATED_PARENT_INTERNAL_CODE
        defaultStdCodesShouldBeFound("parentInternalCode.in=" + DEFAULT_PARENT_INTERNAL_CODE + "," + UPDATED_PARENT_INTERNAL_CODE);

        // Get all the stdCodesList where parentInternalCode equals to UPDATED_PARENT_INTERNAL_CODE
        defaultStdCodesShouldNotBeFound("parentInternalCode.in=" + UPDATED_PARENT_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByParentInternalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where parentInternalCode is not null
        defaultStdCodesShouldBeFound("parentInternalCode.specified=true");

        // Get all the stdCodesList where parentInternalCode is null
        defaultStdCodesShouldNotBeFound("parentInternalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where comments equals to DEFAULT_COMMENTS
        defaultStdCodesShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the stdCodesList where comments equals to UPDATED_COMMENTS
        defaultStdCodesShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultStdCodesShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the stdCodesList where comments equals to UPDATED_COMMENTS
        defaultStdCodesShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where comments is not null
        defaultStdCodesShouldBeFound("comments.specified=true");

        // Get all the stdCodesList where comments is null
        defaultStdCodesShouldNotBeFound("comments.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesBySecLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where secLevel equals to DEFAULT_SEC_LEVEL
        defaultStdCodesShouldBeFound("secLevel.equals=" + DEFAULT_SEC_LEVEL);

        // Get all the stdCodesList where secLevel equals to UPDATED_SEC_LEVEL
        defaultStdCodesShouldNotBeFound("secLevel.equals=" + UPDATED_SEC_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStdCodesBySecLevelIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where secLevel in DEFAULT_SEC_LEVEL or UPDATED_SEC_LEVEL
        defaultStdCodesShouldBeFound("secLevel.in=" + DEFAULT_SEC_LEVEL + "," + UPDATED_SEC_LEVEL);

        // Get all the stdCodesList where secLevel equals to UPDATED_SEC_LEVEL
        defaultStdCodesShouldNotBeFound("secLevel.in=" + UPDATED_SEC_LEVEL);
    }

    @Test
    @Transactional
    public void getAllStdCodesBySecLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where secLevel is not null
        defaultStdCodesShouldBeFound("secLevel.specified=true");

        // Get all the stdCodesList where secLevel is null
        defaultStdCodesShouldNotBeFound("secLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesBySecLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where secLevel greater than or equals to DEFAULT_SEC_LEVEL
        defaultStdCodesShouldBeFound("secLevel.greaterOrEqualThan=" + DEFAULT_SEC_LEVEL);

        // Get all the stdCodesList where secLevel greater than or equals to (DEFAULT_SEC_LEVEL + 1)
        defaultStdCodesShouldNotBeFound("secLevel.greaterOrEqualThan=" + (DEFAULT_SEC_LEVEL + 1));
    }

    @Test
    @Transactional
    public void getAllStdCodesBySecLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where secLevel less than or equals to DEFAULT_SEC_LEVEL
        defaultStdCodesShouldNotBeFound("secLevel.lessThan=" + DEFAULT_SEC_LEVEL);

        // Get all the stdCodesList where secLevel less than or equals to (DEFAULT_SEC_LEVEL + 1)
        defaultStdCodesShouldBeFound("secLevel.lessThan=" + (DEFAULT_SEC_LEVEL + 1));
    }


    @Test
    @Transactional
    public void getAllStdCodesByCodeValueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueDate equals to DEFAULT_CODE_VALUE_DATE
        defaultStdCodesShouldBeFound("codeValueDate.equals=" + DEFAULT_CODE_VALUE_DATE);

        // Get all the stdCodesList where codeValueDate equals to UPDATED_CODE_VALUE_DATE
        defaultStdCodesShouldNotBeFound("codeValueDate.equals=" + UPDATED_CODE_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueDate in DEFAULT_CODE_VALUE_DATE or UPDATED_CODE_VALUE_DATE
        defaultStdCodesShouldBeFound("codeValueDate.in=" + DEFAULT_CODE_VALUE_DATE + "," + UPDATED_CODE_VALUE_DATE);

        // Get all the stdCodesList where codeValueDate equals to UPDATED_CODE_VALUE_DATE
        defaultStdCodesShouldNotBeFound("codeValueDate.in=" + UPDATED_CODE_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueDate is not null
        defaultStdCodesShouldBeFound("codeValueDate.specified=true");

        // Get all the stdCodesList where codeValueDate is null
        defaultStdCodesShouldNotBeFound("codeValueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueDate greater than or equals to DEFAULT_CODE_VALUE_DATE
        defaultStdCodesShouldBeFound("codeValueDate.greaterOrEqualThan=" + DEFAULT_CODE_VALUE_DATE);

        // Get all the stdCodesList where codeValueDate greater than or equals to UPDATED_CODE_VALUE_DATE
        defaultStdCodesShouldNotBeFound("codeValueDate.greaterOrEqualThan=" + UPDATED_CODE_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueDate less than or equals to DEFAULT_CODE_VALUE_DATE
        defaultStdCodesShouldNotBeFound("codeValueDate.lessThan=" + DEFAULT_CODE_VALUE_DATE);

        // Get all the stdCodesList where codeValueDate less than or equals to UPDATED_CODE_VALUE_DATE
        defaultStdCodesShouldBeFound("codeValueDate.lessThan=" + UPDATED_CODE_VALUE_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesByCodeValueStringIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueString equals to DEFAULT_CODE_VALUE_STRING
        defaultStdCodesShouldBeFound("codeValueString.equals=" + DEFAULT_CODE_VALUE_STRING);

        // Get all the stdCodesList where codeValueString equals to UPDATED_CODE_VALUE_STRING
        defaultStdCodesShouldNotBeFound("codeValueString.equals=" + UPDATED_CODE_VALUE_STRING);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueStringIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueString in DEFAULT_CODE_VALUE_STRING or UPDATED_CODE_VALUE_STRING
        defaultStdCodesShouldBeFound("codeValueString.in=" + DEFAULT_CODE_VALUE_STRING + "," + UPDATED_CODE_VALUE_STRING);

        // Get all the stdCodesList where codeValueString equals to UPDATED_CODE_VALUE_STRING
        defaultStdCodesShouldNotBeFound("codeValueString.in=" + UPDATED_CODE_VALUE_STRING);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueString is not null
        defaultStdCodesShouldBeFound("codeValueString.specified=true");

        // Get all the stdCodesList where codeValueString is null
        defaultStdCodesShouldNotBeFound("codeValueString.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueBoolIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueBool equals to DEFAULT_CODE_VALUE_BOOL
        defaultStdCodesShouldBeFound("codeValueBool.equals=" + DEFAULT_CODE_VALUE_BOOL);

        // Get all the stdCodesList where codeValueBool equals to UPDATED_CODE_VALUE_BOOL
        defaultStdCodesShouldNotBeFound("codeValueBool.equals=" + UPDATED_CODE_VALUE_BOOL);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueBoolIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueBool in DEFAULT_CODE_VALUE_BOOL or UPDATED_CODE_VALUE_BOOL
        defaultStdCodesShouldBeFound("codeValueBool.in=" + DEFAULT_CODE_VALUE_BOOL + "," + UPDATED_CODE_VALUE_BOOL);

        // Get all the stdCodesList where codeValueBool equals to UPDATED_CODE_VALUE_BOOL
        defaultStdCodesShouldNotBeFound("codeValueBool.in=" + UPDATED_CODE_VALUE_BOOL);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueBoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueBool is not null
        defaultStdCodesShouldBeFound("codeValueBool.specified=true");

        // Get all the stdCodesList where codeValueBool is null
        defaultStdCodesShouldNotBeFound("codeValueBool.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueNumber equals to DEFAULT_CODE_VALUE_NUMBER
        defaultStdCodesShouldBeFound("codeValueNumber.equals=" + DEFAULT_CODE_VALUE_NUMBER);

        // Get all the stdCodesList where codeValueNumber equals to UPDATED_CODE_VALUE_NUMBER
        defaultStdCodesShouldNotBeFound("codeValueNumber.equals=" + UPDATED_CODE_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueNumberIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueNumber in DEFAULT_CODE_VALUE_NUMBER or UPDATED_CODE_VALUE_NUMBER
        defaultStdCodesShouldBeFound("codeValueNumber.in=" + DEFAULT_CODE_VALUE_NUMBER + "," + UPDATED_CODE_VALUE_NUMBER);

        // Get all the stdCodesList where codeValueNumber equals to UPDATED_CODE_VALUE_NUMBER
        defaultStdCodesShouldNotBeFound("codeValueNumber.in=" + UPDATED_CODE_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueNumber is not null
        defaultStdCodesShouldBeFound("codeValueNumber.specified=true");

        // Get all the stdCodesList where codeValueNumber is null
        defaultStdCodesShouldNotBeFound("codeValueNumber.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueNumberIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueNumber greater than or equals to DEFAULT_CODE_VALUE_NUMBER
        defaultStdCodesShouldBeFound("codeValueNumber.greaterOrEqualThan=" + DEFAULT_CODE_VALUE_NUMBER);

        // Get all the stdCodesList where codeValueNumber greater than or equals to UPDATED_CODE_VALUE_NUMBER
        defaultStdCodesShouldNotBeFound("codeValueNumber.greaterOrEqualThan=" + UPDATED_CODE_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesByCodeValueNumberIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

        // Get all the stdCodesList where codeValueNumber less than or equals to DEFAULT_CODE_VALUE_NUMBER
        defaultStdCodesShouldNotBeFound("codeValueNumber.lessThan=" + DEFAULT_CODE_VALUE_NUMBER);

        // Get all the stdCodesList where codeValueNumber less than or equals to UPDATED_CODE_VALUE_NUMBER
        defaultStdCodesShouldBeFound("codeValueNumber.lessThan=" + UPDATED_CODE_VALUE_NUMBER);
    }


    @Test
    @Transactional
    public void getAllStdCodesByStdCodesDescIsEqualToSomething() throws Exception {
        // Initialize the database
        StdCodesDesc stdCodesDesc = StdCodesDescResourceIT.createEntity(em);
        em.persist(stdCodesDesc);
        em.flush();
        stdCodes.addStdCodesDesc(stdCodesDesc);
        stdCodesRepository.saveAndFlush(stdCodes);
        Long stdCodesDescId = stdCodesDesc.getId();

        // Get all the stdCodesList where stdCodesDesc equals to stdCodesDescId
        defaultStdCodesShouldBeFound("stdCodesDescId.equals=" + stdCodesDescId);

        // Get all the stdCodesList where stdCodesDesc equals to stdCodesDescId + 1
        defaultStdCodesShouldNotBeFound("stdCodesDescId.equals=" + (stdCodesDescId + 1));
    }


    @Test
    @Transactional
    public void getAllStdCodesByStdCodesGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        StdCodesGroup stdCodesGroup = StdCodesGroupResourceIT.createEntity(em);
        em.persist(stdCodesGroup);
        em.flush();
        stdCodes.setStdCodesGroup(stdCodesGroup);
        stdCodesRepository.saveAndFlush(stdCodes);
        Long stdCodesGroupId = stdCodesGroup.getId();

        // Get all the stdCodesList where stdCodesGroup equals to stdCodesGroupId
        defaultStdCodesShouldBeFound("stdCodesGroupId.equals=" + stdCodesGroupId);

        // Get all the stdCodesList where stdCodesGroup equals to stdCodesGroupId + 1
        defaultStdCodesShouldNotBeFound("stdCodesGroupId.equals=" + (stdCodesGroupId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStdCodesShouldBeFound(String filter) throws Exception {
        restStdCodesMockMvc.perform(get("/api/std-codes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodes.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].parentInternalCode").value(hasItem(DEFAULT_PARENT_INTERNAL_CODE)))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].secLevel").value(hasItem(DEFAULT_SEC_LEVEL)))
            .andExpect(jsonPath("$.[*].codeValueDate").value(hasItem(DEFAULT_CODE_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].codeValueString").value(hasItem(DEFAULT_CODE_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].codeValueBool").value(hasItem(DEFAULT_CODE_VALUE_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].codeValueNumber").value(hasItem(DEFAULT_CODE_VALUE_NUMBER)));

        // Check, that the count call also returns 1
        restStdCodesMockMvc.perform(get("/api/std-codes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStdCodesShouldNotBeFound(String filter) throws Exception {
        restStdCodesMockMvc.perform(get("/api/std-codes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStdCodesMockMvc.perform(get("/api/std-codes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStdCodes() throws Exception {
        // Get the stdCodes
        restStdCodesMockMvc.perform(get("/api/std-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodes() throws Exception {
        // Initialize the database
        stdCodesService.save(stdCodes);

        int databaseSizeBeforeUpdate = stdCodesRepository.findAll().size();

        // Update the stdCodes
        StdCodes updatedStdCodes = stdCodesRepository.findById(stdCodes.getId()).get();
        // Disconnect from session so that the updates on updatedStdCodes are not directly saved in db
        em.detach(updatedStdCodes);
        updatedStdCodes
            .groupCode(UPDATED_GROUP_CODE)
            .internalCode(UPDATED_INTERNAL_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .parentInternalCode(UPDATED_PARENT_INTERNAL_CODE)
            .comments(UPDATED_COMMENTS)
            .secLevel(UPDATED_SEC_LEVEL)
            .codeValueDate(UPDATED_CODE_VALUE_DATE)
            .codeValueString(UPDATED_CODE_VALUE_STRING)
            .codeValueBool(UPDATED_CODE_VALUE_BOOL)
            .codeValueNumber(UPDATED_CODE_VALUE_NUMBER);

        restStdCodesMockMvc.perform(put("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStdCodes)))
            .andExpect(status().isOk());

        // Validate the StdCodes in the database
        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeUpdate);
        StdCodes testStdCodes = stdCodesList.get(stdCodesList.size() - 1);
        assertThat(testStdCodes.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testStdCodes.getInternalCode()).isEqualTo(UPDATED_INTERNAL_CODE);
        assertThat(testStdCodes.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStdCodes.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStdCodes.getParentInternalCode()).isEqualTo(UPDATED_PARENT_INTERNAL_CODE);
        assertThat(testStdCodes.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testStdCodes.getSecLevel()).isEqualTo(UPDATED_SEC_LEVEL);
        assertThat(testStdCodes.getCodeValueDate()).isEqualTo(UPDATED_CODE_VALUE_DATE);
        assertThat(testStdCodes.getCodeValueString()).isEqualTo(UPDATED_CODE_VALUE_STRING);
        assertThat(testStdCodes.isCodeValueBool()).isEqualTo(UPDATED_CODE_VALUE_BOOL);
        assertThat(testStdCodes.getCodeValueNumber()).isEqualTo(UPDATED_CODE_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingStdCodes() throws Exception {
        int databaseSizeBeforeUpdate = stdCodesRepository.findAll().size();

        // Create the StdCodes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdCodesMockMvc.perform(put("/api/std-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodes)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodes in the database
        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStdCodes() throws Exception {
        // Initialize the database
        stdCodesService.save(stdCodes);

        int databaseSizeBeforeDelete = stdCodesRepository.findAll().size();

        // Delete the stdCodes
        restStdCodesMockMvc.perform(delete("/api/std-codes/{id}", stdCodes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StdCodes> stdCodesList = stdCodesRepository.findAll();
        assertThat(stdCodesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StdCodes.class);
        StdCodes stdCodes1 = new StdCodes();
        stdCodes1.setId(1L);
        StdCodes stdCodes2 = new StdCodes();
        stdCodes2.setId(stdCodes1.getId());
        assertThat(stdCodes1).isEqualTo(stdCodes2);
        stdCodes2.setId(2L);
        assertThat(stdCodes1).isNotEqualTo(stdCodes2);
        stdCodes1.setId(null);
        assertThat(stdCodes1).isNotEqualTo(stdCodes2);
    }
}
