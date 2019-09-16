package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesDesc;
import net.atos.etax.domain.StdCodes;
import net.atos.etax.repository.StdCodesDescRepository;
import net.atos.etax.service.StdCodesDescService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.StdCodesDescCriteria;
import net.atos.etax.service.StdCodesDescQueryService;

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
 * Integration tests for the {@Link StdCodesDescResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class StdCodesDescResourceIT {

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_INTERNAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_INTERNAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_LANG_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LANG_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_EXTERNAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EXTERNAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_DESC = "AAAAAAAAAA";
    private static final String UPDATED_CODE_DESC = "BBBBBBBBBB";

    @Autowired
    private StdCodesDescRepository stdCodesDescRepository;

    @Autowired
    private StdCodesDescService stdCodesDescService;

    @Autowired
    private StdCodesDescQueryService stdCodesDescQueryService;

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

    private MockMvc restStdCodesDescMockMvc;

    private StdCodesDesc stdCodesDesc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StdCodesDescResource stdCodesDescResource = new StdCodesDescResource(stdCodesDescService, stdCodesDescQueryService);
        this.restStdCodesDescMockMvc = MockMvcBuilders.standaloneSetup(stdCodesDescResource)
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
    public static StdCodesDesc createEntity(EntityManager em) {
        StdCodesDesc stdCodesDesc = new StdCodesDesc()
            .groupCode(DEFAULT_GROUP_CODE)
            .internalCode(DEFAULT_INTERNAL_CODE)
            .langCode(DEFAULT_LANG_CODE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .externalCode(DEFAULT_EXTERNAL_CODE)
            .codeDesc(DEFAULT_CODE_DESC);
        return stdCodesDesc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StdCodesDesc createUpdatedEntity(EntityManager em) {
        StdCodesDesc stdCodesDesc = new StdCodesDesc()
            .groupCode(UPDATED_GROUP_CODE)
            .internalCode(UPDATED_INTERNAL_CODE)
            .langCode(UPDATED_LANG_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .externalCode(UPDATED_EXTERNAL_CODE)
            .codeDesc(UPDATED_CODE_DESC);
        return stdCodesDesc;
    }

    @BeforeEach
    public void initTest() {
        stdCodesDesc = createEntity(em);
    }

    @Test
    @Transactional
    public void createStdCodesDesc() throws Exception {
        int databaseSizeBeforeCreate = stdCodesDescRepository.findAll().size();

        // Create the StdCodesDesc
        restStdCodesDescMockMvc.perform(post("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isCreated());

        // Validate the StdCodesDesc in the database
        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeCreate + 1);
        StdCodesDesc testStdCodesDesc = stdCodesDescList.get(stdCodesDescList.size() - 1);
        assertThat(testStdCodesDesc.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testStdCodesDesc.getInternalCode()).isEqualTo(DEFAULT_INTERNAL_CODE);
        assertThat(testStdCodesDesc.getLangCode()).isEqualTo(DEFAULT_LANG_CODE);
        assertThat(testStdCodesDesc.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStdCodesDesc.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStdCodesDesc.getExternalCode()).isEqualTo(DEFAULT_EXTERNAL_CODE);
        assertThat(testStdCodesDesc.getCodeDesc()).isEqualTo(DEFAULT_CODE_DESC);
    }

    @Test
    @Transactional
    public void createStdCodesDescWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stdCodesDescRepository.findAll().size();

        // Create the StdCodesDesc with an existing ID
        stdCodesDesc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStdCodesDescMockMvc.perform(post("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesDesc in the database
        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesDescRepository.findAll().size();
        // set the field null
        stdCodesDesc.setGroupCode(null);

        // Create the StdCodesDesc, which fails.

        restStdCodesDescMockMvc.perform(post("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isBadRequest());

        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkInternalCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesDescRepository.findAll().size();
        // set the field null
        stdCodesDesc.setInternalCode(null);

        // Create the StdCodesDesc, which fails.

        restStdCodesDescMockMvc.perform(post("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isBadRequest());

        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkLangCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesDescRepository.findAll().size();
        // set the field null
        stdCodesDesc.setLangCode(null);

        // Create the StdCodesDesc, which fails.

        restStdCodesDescMockMvc.perform(post("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isBadRequest());

        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesDescRepository.findAll().size();
        // set the field null
        stdCodesDesc.setStartDate(null);

        // Create the StdCodesDesc, which fails.

        restStdCodesDescMockMvc.perform(post("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isBadRequest());

        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescs() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesDesc.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].langCode").value(hasItem(DEFAULT_LANG_CODE.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].externalCode").value(hasItem(DEFAULT_EXTERNAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].codeDesc").value(hasItem(DEFAULT_CODE_DESC.toString())));
    }
    
    @Test
    @Transactional
    public void getStdCodesDesc() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get the stdCodesDesc
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs/{id}", stdCodesDesc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stdCodesDesc.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.internalCode").value(DEFAULT_INTERNAL_CODE.toString()))
            .andExpect(jsonPath("$.langCode").value(DEFAULT_LANG_CODE.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.externalCode").value(DEFAULT_EXTERNAL_CODE.toString()))
            .andExpect(jsonPath("$.codeDesc").value(DEFAULT_CODE_DESC.toString()));
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where groupCode equals to DEFAULT_GROUP_CODE
        defaultStdCodesDescShouldBeFound("groupCode.equals=" + DEFAULT_GROUP_CODE);

        // Get all the stdCodesDescList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesDescShouldNotBeFound("groupCode.equals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where groupCode in DEFAULT_GROUP_CODE or UPDATED_GROUP_CODE
        defaultStdCodesDescShouldBeFound("groupCode.in=" + DEFAULT_GROUP_CODE + "," + UPDATED_GROUP_CODE);

        // Get all the stdCodesDescList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesDescShouldNotBeFound("groupCode.in=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where groupCode is not null
        defaultStdCodesDescShouldBeFound("groupCode.specified=true");

        // Get all the stdCodesDescList where groupCode is null
        defaultStdCodesDescShouldNotBeFound("groupCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByInternalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where internalCode equals to DEFAULT_INTERNAL_CODE
        defaultStdCodesDescShouldBeFound("internalCode.equals=" + DEFAULT_INTERNAL_CODE);

        // Get all the stdCodesDescList where internalCode equals to UPDATED_INTERNAL_CODE
        defaultStdCodesDescShouldNotBeFound("internalCode.equals=" + UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByInternalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where internalCode in DEFAULT_INTERNAL_CODE or UPDATED_INTERNAL_CODE
        defaultStdCodesDescShouldBeFound("internalCode.in=" + DEFAULT_INTERNAL_CODE + "," + UPDATED_INTERNAL_CODE);

        // Get all the stdCodesDescList where internalCode equals to UPDATED_INTERNAL_CODE
        defaultStdCodesDescShouldNotBeFound("internalCode.in=" + UPDATED_INTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByInternalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where internalCode is not null
        defaultStdCodesDescShouldBeFound("internalCode.specified=true");

        // Get all the stdCodesDescList where internalCode is null
        defaultStdCodesDescShouldNotBeFound("internalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByLangCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where langCode equals to DEFAULT_LANG_CODE
        defaultStdCodesDescShouldBeFound("langCode.equals=" + DEFAULT_LANG_CODE);

        // Get all the stdCodesDescList where langCode equals to UPDATED_LANG_CODE
        defaultStdCodesDescShouldNotBeFound("langCode.equals=" + UPDATED_LANG_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByLangCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where langCode in DEFAULT_LANG_CODE or UPDATED_LANG_CODE
        defaultStdCodesDescShouldBeFound("langCode.in=" + DEFAULT_LANG_CODE + "," + UPDATED_LANG_CODE);

        // Get all the stdCodesDescList where langCode equals to UPDATED_LANG_CODE
        defaultStdCodesDescShouldNotBeFound("langCode.in=" + UPDATED_LANG_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByLangCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where langCode is not null
        defaultStdCodesDescShouldBeFound("langCode.specified=true");

        // Get all the stdCodesDescList where langCode is null
        defaultStdCodesDescShouldNotBeFound("langCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where startDate equals to DEFAULT_START_DATE
        defaultStdCodesDescShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the stdCodesDescList where startDate equals to UPDATED_START_DATE
        defaultStdCodesDescShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultStdCodesDescShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the stdCodesDescList where startDate equals to UPDATED_START_DATE
        defaultStdCodesDescShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where startDate is not null
        defaultStdCodesDescShouldBeFound("startDate.specified=true");

        // Get all the stdCodesDescList where startDate is null
        defaultStdCodesDescShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where startDate greater than or equals to DEFAULT_START_DATE
        defaultStdCodesDescShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesDescList where startDate greater than or equals to UPDATED_START_DATE
        defaultStdCodesDescShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where startDate less than or equals to DEFAULT_START_DATE
        defaultStdCodesDescShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesDescList where startDate less than or equals to UPDATED_START_DATE
        defaultStdCodesDescShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesDescsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where endDate equals to DEFAULT_END_DATE
        defaultStdCodesDescShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the stdCodesDescList where endDate equals to UPDATED_END_DATE
        defaultStdCodesDescShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultStdCodesDescShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the stdCodesDescList where endDate equals to UPDATED_END_DATE
        defaultStdCodesDescShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where endDate is not null
        defaultStdCodesDescShouldBeFound("endDate.specified=true");

        // Get all the stdCodesDescList where endDate is null
        defaultStdCodesDescShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where endDate greater than or equals to DEFAULT_END_DATE
        defaultStdCodesDescShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesDescList where endDate greater than or equals to UPDATED_END_DATE
        defaultStdCodesDescShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where endDate less than or equals to DEFAULT_END_DATE
        defaultStdCodesDescShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesDescList where endDate less than or equals to UPDATED_END_DATE
        defaultStdCodesDescShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesDescsByExternalCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where externalCode equals to DEFAULT_EXTERNAL_CODE
        defaultStdCodesDescShouldBeFound("externalCode.equals=" + DEFAULT_EXTERNAL_CODE);

        // Get all the stdCodesDescList where externalCode equals to UPDATED_EXTERNAL_CODE
        defaultStdCodesDescShouldNotBeFound("externalCode.equals=" + UPDATED_EXTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByExternalCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where externalCode in DEFAULT_EXTERNAL_CODE or UPDATED_EXTERNAL_CODE
        defaultStdCodesDescShouldBeFound("externalCode.in=" + DEFAULT_EXTERNAL_CODE + "," + UPDATED_EXTERNAL_CODE);

        // Get all the stdCodesDescList where externalCode equals to UPDATED_EXTERNAL_CODE
        defaultStdCodesDescShouldNotBeFound("externalCode.in=" + UPDATED_EXTERNAL_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByExternalCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where externalCode is not null
        defaultStdCodesDescShouldBeFound("externalCode.specified=true");

        // Get all the stdCodesDescList where externalCode is null
        defaultStdCodesDescShouldNotBeFound("externalCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByCodeDescIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where codeDesc equals to DEFAULT_CODE_DESC
        defaultStdCodesDescShouldBeFound("codeDesc.equals=" + DEFAULT_CODE_DESC);

        // Get all the stdCodesDescList where codeDesc equals to UPDATED_CODE_DESC
        defaultStdCodesDescShouldNotBeFound("codeDesc.equals=" + UPDATED_CODE_DESC);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByCodeDescIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where codeDesc in DEFAULT_CODE_DESC or UPDATED_CODE_DESC
        defaultStdCodesDescShouldBeFound("codeDesc.in=" + DEFAULT_CODE_DESC + "," + UPDATED_CODE_DESC);

        // Get all the stdCodesDescList where codeDesc equals to UPDATED_CODE_DESC
        defaultStdCodesDescShouldNotBeFound("codeDesc.in=" + UPDATED_CODE_DESC);
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByCodeDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

        // Get all the stdCodesDescList where codeDesc is not null
        defaultStdCodesDescShouldBeFound("codeDesc.specified=true");

        // Get all the stdCodesDescList where codeDesc is null
        defaultStdCodesDescShouldNotBeFound("codeDesc.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesDescsByStdCodesIsEqualToSomething() throws Exception {
        // Initialize the database
        StdCodes stdCodes = StdCodesResourceIT.createEntity(em);
        em.persist(stdCodes);
        em.flush();
        stdCodesDesc.setStdCodes(stdCodes);
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);
        Long stdCodesId = stdCodes.getId();

        // Get all the stdCodesDescList where stdCodes equals to stdCodesId
        defaultStdCodesDescShouldBeFound("stdCodesId.equals=" + stdCodesId);

        // Get all the stdCodesDescList where stdCodes equals to stdCodesId + 1
        defaultStdCodesDescShouldNotBeFound("stdCodesId.equals=" + (stdCodesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStdCodesDescShouldBeFound(String filter) throws Exception {
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesDesc.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].internalCode").value(hasItem(DEFAULT_INTERNAL_CODE)))
            .andExpect(jsonPath("$.[*].langCode").value(hasItem(DEFAULT_LANG_CODE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].externalCode").value(hasItem(DEFAULT_EXTERNAL_CODE)))
            .andExpect(jsonPath("$.[*].codeDesc").value(hasItem(DEFAULT_CODE_DESC)));

        // Check, that the count call also returns 1
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStdCodesDescShouldNotBeFound(String filter) throws Exception {
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStdCodesDesc() throws Exception {
        // Get the stdCodesDesc
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesDesc() throws Exception {
        // Initialize the database
        stdCodesDescService.save(stdCodesDesc);

        int databaseSizeBeforeUpdate = stdCodesDescRepository.findAll().size();

        // Update the stdCodesDesc
        StdCodesDesc updatedStdCodesDesc = stdCodesDescRepository.findById(stdCodesDesc.getId()).get();
        // Disconnect from session so that the updates on updatedStdCodesDesc are not directly saved in db
        em.detach(updatedStdCodesDesc);
        updatedStdCodesDesc
            .groupCode(UPDATED_GROUP_CODE)
            .internalCode(UPDATED_INTERNAL_CODE)
            .langCode(UPDATED_LANG_CODE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .externalCode(UPDATED_EXTERNAL_CODE)
            .codeDesc(UPDATED_CODE_DESC);

        restStdCodesDescMockMvc.perform(put("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStdCodesDesc)))
            .andExpect(status().isOk());

        // Validate the StdCodesDesc in the database
        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeUpdate);
        StdCodesDesc testStdCodesDesc = stdCodesDescList.get(stdCodesDescList.size() - 1);
        assertThat(testStdCodesDesc.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testStdCodesDesc.getInternalCode()).isEqualTo(UPDATED_INTERNAL_CODE);
        assertThat(testStdCodesDesc.getLangCode()).isEqualTo(UPDATED_LANG_CODE);
        assertThat(testStdCodesDesc.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStdCodesDesc.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStdCodesDesc.getExternalCode()).isEqualTo(UPDATED_EXTERNAL_CODE);
        assertThat(testStdCodesDesc.getCodeDesc()).isEqualTo(UPDATED_CODE_DESC);
    }

    @Test
    @Transactional
    public void updateNonExistingStdCodesDesc() throws Exception {
        int databaseSizeBeforeUpdate = stdCodesDescRepository.findAll().size();

        // Create the StdCodesDesc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdCodesDescMockMvc.perform(put("/api/std-codes-descs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesDesc)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesDesc in the database
        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStdCodesDesc() throws Exception {
        // Initialize the database
        stdCodesDescService.save(stdCodesDesc);

        int databaseSizeBeforeDelete = stdCodesDescRepository.findAll().size();

        // Delete the stdCodesDesc
        restStdCodesDescMockMvc.perform(delete("/api/std-codes-descs/{id}", stdCodesDesc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StdCodesDesc> stdCodesDescList = stdCodesDescRepository.findAll();
        assertThat(stdCodesDescList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StdCodesDesc.class);
        StdCodesDesc stdCodesDesc1 = new StdCodesDesc();
        stdCodesDesc1.setId(1L);
        StdCodesDesc stdCodesDesc2 = new StdCodesDesc();
        stdCodesDesc2.setId(stdCodesDesc1.getId());
        assertThat(stdCodesDesc1).isEqualTo(stdCodesDesc2);
        stdCodesDesc2.setId(2L);
        assertThat(stdCodesDesc1).isNotEqualTo(stdCodesDesc2);
        stdCodesDesc1.setId(null);
        assertThat(stdCodesDesc1).isNotEqualTo(stdCodesDesc2);
    }
}
