package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesGroupProp;
import net.atos.etax.repository.StdCodesGroupPropRepository;
import net.atos.etax.service.StdCodesGroupPropService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.StdCodesGroupPropCriteria;
import net.atos.etax.service.StdCodesGroupPropQueryService;

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

import net.atos.etax.domain.enumeration.ValueTypeIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
/**
 * Integration tests for the {@Link StdCodesGroupPropResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class StdCodesGroupPropResourceIT {

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PROP_DESC = "AAAAAAAAAA";
    private static final String UPDATED_PROP_DESC = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ValueTypeIndicator DEFAULT_PROP_TYPE = ValueTypeIndicator.D;
    private static final ValueTypeIndicator UPDATED_PROP_TYPE = ValueTypeIndicator.S;

    private static final OptionIndicator DEFAULT_PROP_MDTR = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_PROP_MDTR = OptionIndicator.N;

    private static final LocalDate DEFAULT_DFLT_VALUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DFLT_VALUE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_DFLT_VALUE_STRING = "AAAAAAAAAA";
    private static final String UPDATED_DFLT_VALUE_STRING = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DFLT_VALUE_BOOL = false;
    private static final Boolean UPDATED_DFLT_VALUE_BOOL = true;

    private static final Double DEFAULT_DFLT_VALUE_NUMBER = 1D;
    private static final Double UPDATED_DFLT_VALUE_NUMBER = 2D;

    @Autowired
    private StdCodesGroupPropRepository stdCodesGroupPropRepository;

    @Autowired
    private StdCodesGroupPropService stdCodesGroupPropService;

    @Autowired
    private StdCodesGroupPropQueryService stdCodesGroupPropQueryService;

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

    private MockMvc restStdCodesGroupPropMockMvc;

    private StdCodesGroupProp stdCodesGroupProp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StdCodesGroupPropResource stdCodesGroupPropResource = new StdCodesGroupPropResource(stdCodesGroupPropService, stdCodesGroupPropQueryService);
        this.restStdCodesGroupPropMockMvc = MockMvcBuilders.standaloneSetup(stdCodesGroupPropResource)
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
    public static StdCodesGroupProp createEntity(EntityManager em) {
        StdCodesGroupProp stdCodesGroupProp = new StdCodesGroupProp()
            .groupCode(DEFAULT_GROUP_CODE)
            .propCode(DEFAULT_PROP_CODE)
            .propDesc(DEFAULT_PROP_DESC)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .propType(DEFAULT_PROP_TYPE)
            .propMdtr(DEFAULT_PROP_MDTR)
            .dfltValueDate(DEFAULT_DFLT_VALUE_DATE)
            .dfltValueString(DEFAULT_DFLT_VALUE_STRING)
            .dfltValueBool(DEFAULT_DFLT_VALUE_BOOL)
            .dfltValueNumber(DEFAULT_DFLT_VALUE_NUMBER);
        return stdCodesGroupProp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StdCodesGroupProp createUpdatedEntity(EntityManager em) {
        StdCodesGroupProp stdCodesGroupProp = new StdCodesGroupProp()
            .groupCode(UPDATED_GROUP_CODE)
            .propCode(UPDATED_PROP_CODE)
            .propDesc(UPDATED_PROP_DESC)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .propType(UPDATED_PROP_TYPE)
            .propMdtr(UPDATED_PROP_MDTR)
            .dfltValueDate(UPDATED_DFLT_VALUE_DATE)
            .dfltValueString(UPDATED_DFLT_VALUE_STRING)
            .dfltValueBool(UPDATED_DFLT_VALUE_BOOL)
            .dfltValueNumber(UPDATED_DFLT_VALUE_NUMBER);
        return stdCodesGroupProp;
    }

    @BeforeEach
    public void initTest() {
        stdCodesGroupProp = createEntity(em);
    }

    @Test
    @Transactional
    public void createStdCodesGroupProp() throws Exception {
        int databaseSizeBeforeCreate = stdCodesGroupPropRepository.findAll().size();

        // Create the StdCodesGroupProp
        restStdCodesGroupPropMockMvc.perform(post("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isCreated());

        // Validate the StdCodesGroupProp in the database
        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeCreate + 1);
        StdCodesGroupProp testStdCodesGroupProp = stdCodesGroupPropList.get(stdCodesGroupPropList.size() - 1);
        assertThat(testStdCodesGroupProp.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testStdCodesGroupProp.getPropCode()).isEqualTo(DEFAULT_PROP_CODE);
        assertThat(testStdCodesGroupProp.getPropDesc()).isEqualTo(DEFAULT_PROP_DESC);
        assertThat(testStdCodesGroupProp.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testStdCodesGroupProp.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testStdCodesGroupProp.getPropType()).isEqualTo(DEFAULT_PROP_TYPE);
        assertThat(testStdCodesGroupProp.getPropMdtr()).isEqualTo(DEFAULT_PROP_MDTR);
        assertThat(testStdCodesGroupProp.getDfltValueDate()).isEqualTo(DEFAULT_DFLT_VALUE_DATE);
        assertThat(testStdCodesGroupProp.getDfltValueString()).isEqualTo(DEFAULT_DFLT_VALUE_STRING);
        assertThat(testStdCodesGroupProp.isDfltValueBool()).isEqualTo(DEFAULT_DFLT_VALUE_BOOL);
        assertThat(testStdCodesGroupProp.getDfltValueNumber()).isEqualTo(DEFAULT_DFLT_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void createStdCodesGroupPropWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stdCodesGroupPropRepository.findAll().size();

        // Create the StdCodesGroupProp with an existing ID
        stdCodesGroupProp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStdCodesGroupPropMockMvc.perform(post("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesGroupProp in the database
        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupPropRepository.findAll().size();
        // set the field null
        stdCodesGroupProp.setGroupCode(null);

        // Create the StdCodesGroupProp, which fails.

        restStdCodesGroupPropMockMvc.perform(post("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupPropRepository.findAll().size();
        // set the field null
        stdCodesGroupProp.setPropCode(null);

        // Create the StdCodesGroupProp, which fails.

        restStdCodesGroupPropMockMvc.perform(post("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPropDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupPropRepository.findAll().size();
        // set the field null
        stdCodesGroupProp.setPropDesc(null);

        // Create the StdCodesGroupProp, which fails.

        restStdCodesGroupPropMockMvc.perform(post("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupPropRepository.findAll().size();
        // set the field null
        stdCodesGroupProp.setStartDate(null);

        // Create the StdCodesGroupProp, which fails.

        restStdCodesGroupPropMockMvc.perform(post("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupProps() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesGroupProp.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].propCode").value(hasItem(DEFAULT_PROP_CODE.toString())))
            .andExpect(jsonPath("$.[*].propDesc").value(hasItem(DEFAULT_PROP_DESC.toString())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].propType").value(hasItem(DEFAULT_PROP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].propMdtr").value(hasItem(DEFAULT_PROP_MDTR.toString())))
            .andExpect(jsonPath("$.[*].dfltValueDate").value(hasItem(DEFAULT_DFLT_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dfltValueString").value(hasItem(DEFAULT_DFLT_VALUE_STRING.toString())))
            .andExpect(jsonPath("$.[*].dfltValueBool").value(hasItem(DEFAULT_DFLT_VALUE_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].dfltValueNumber").value(hasItem(DEFAULT_DFLT_VALUE_NUMBER.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getStdCodesGroupProp() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get the stdCodesGroupProp
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props/{id}", stdCodesGroupProp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stdCodesGroupProp.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.propCode").value(DEFAULT_PROP_CODE.toString()))
            .andExpect(jsonPath("$.propDesc").value(DEFAULT_PROP_DESC.toString()))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.propType").value(DEFAULT_PROP_TYPE.toString()))
            .andExpect(jsonPath("$.propMdtr").value(DEFAULT_PROP_MDTR.toString()))
            .andExpect(jsonPath("$.dfltValueDate").value(DEFAULT_DFLT_VALUE_DATE.toString()))
            .andExpect(jsonPath("$.dfltValueString").value(DEFAULT_DFLT_VALUE_STRING.toString()))
            .andExpect(jsonPath("$.dfltValueBool").value(DEFAULT_DFLT_VALUE_BOOL.booleanValue()))
            .andExpect(jsonPath("$.dfltValueNumber").value(DEFAULT_DFLT_VALUE_NUMBER.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where groupCode equals to DEFAULT_GROUP_CODE
        defaultStdCodesGroupPropShouldBeFound("groupCode.equals=" + DEFAULT_GROUP_CODE);

        // Get all the stdCodesGroupPropList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesGroupPropShouldNotBeFound("groupCode.equals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where groupCode in DEFAULT_GROUP_CODE or UPDATED_GROUP_CODE
        defaultStdCodesGroupPropShouldBeFound("groupCode.in=" + DEFAULT_GROUP_CODE + "," + UPDATED_GROUP_CODE);

        // Get all the stdCodesGroupPropList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesGroupPropShouldNotBeFound("groupCode.in=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where groupCode is not null
        defaultStdCodesGroupPropShouldBeFound("groupCode.specified=true");

        // Get all the stdCodesGroupPropList where groupCode is null
        defaultStdCodesGroupPropShouldNotBeFound("groupCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propCode equals to DEFAULT_PROP_CODE
        defaultStdCodesGroupPropShouldBeFound("propCode.equals=" + DEFAULT_PROP_CODE);

        // Get all the stdCodesGroupPropList where propCode equals to UPDATED_PROP_CODE
        defaultStdCodesGroupPropShouldNotBeFound("propCode.equals=" + UPDATED_PROP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propCode in DEFAULT_PROP_CODE or UPDATED_PROP_CODE
        defaultStdCodesGroupPropShouldBeFound("propCode.in=" + DEFAULT_PROP_CODE + "," + UPDATED_PROP_CODE);

        // Get all the stdCodesGroupPropList where propCode equals to UPDATED_PROP_CODE
        defaultStdCodesGroupPropShouldNotBeFound("propCode.in=" + UPDATED_PROP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propCode is not null
        defaultStdCodesGroupPropShouldBeFound("propCode.specified=true");

        // Get all the stdCodesGroupPropList where propCode is null
        defaultStdCodesGroupPropShouldNotBeFound("propCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropDescIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propDesc equals to DEFAULT_PROP_DESC
        defaultStdCodesGroupPropShouldBeFound("propDesc.equals=" + DEFAULT_PROP_DESC);

        // Get all the stdCodesGroupPropList where propDesc equals to UPDATED_PROP_DESC
        defaultStdCodesGroupPropShouldNotBeFound("propDesc.equals=" + UPDATED_PROP_DESC);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropDescIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propDesc in DEFAULT_PROP_DESC or UPDATED_PROP_DESC
        defaultStdCodesGroupPropShouldBeFound("propDesc.in=" + DEFAULT_PROP_DESC + "," + UPDATED_PROP_DESC);

        // Get all the stdCodesGroupPropList where propDesc equals to UPDATED_PROP_DESC
        defaultStdCodesGroupPropShouldNotBeFound("propDesc.in=" + UPDATED_PROP_DESC);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propDesc is not null
        defaultStdCodesGroupPropShouldBeFound("propDesc.specified=true");

        // Get all the stdCodesGroupPropList where propDesc is null
        defaultStdCodesGroupPropShouldNotBeFound("propDesc.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where startDate equals to DEFAULT_START_DATE
        defaultStdCodesGroupPropShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the stdCodesGroupPropList where startDate equals to UPDATED_START_DATE
        defaultStdCodesGroupPropShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultStdCodesGroupPropShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the stdCodesGroupPropList where startDate equals to UPDATED_START_DATE
        defaultStdCodesGroupPropShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where startDate is not null
        defaultStdCodesGroupPropShouldBeFound("startDate.specified=true");

        // Get all the stdCodesGroupPropList where startDate is null
        defaultStdCodesGroupPropShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where startDate greater than or equals to DEFAULT_START_DATE
        defaultStdCodesGroupPropShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesGroupPropList where startDate greater than or equals to UPDATED_START_DATE
        defaultStdCodesGroupPropShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where startDate less than or equals to DEFAULT_START_DATE
        defaultStdCodesGroupPropShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the stdCodesGroupPropList where startDate less than or equals to UPDATED_START_DATE
        defaultStdCodesGroupPropShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where endDate equals to DEFAULT_END_DATE
        defaultStdCodesGroupPropShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the stdCodesGroupPropList where endDate equals to UPDATED_END_DATE
        defaultStdCodesGroupPropShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultStdCodesGroupPropShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the stdCodesGroupPropList where endDate equals to UPDATED_END_DATE
        defaultStdCodesGroupPropShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where endDate is not null
        defaultStdCodesGroupPropShouldBeFound("endDate.specified=true");

        // Get all the stdCodesGroupPropList where endDate is null
        defaultStdCodesGroupPropShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where endDate greater than or equals to DEFAULT_END_DATE
        defaultStdCodesGroupPropShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesGroupPropList where endDate greater than or equals to UPDATED_END_DATE
        defaultStdCodesGroupPropShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where endDate less than or equals to DEFAULT_END_DATE
        defaultStdCodesGroupPropShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the stdCodesGroupPropList where endDate less than or equals to UPDATED_END_DATE
        defaultStdCodesGroupPropShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propType equals to DEFAULT_PROP_TYPE
        defaultStdCodesGroupPropShouldBeFound("propType.equals=" + DEFAULT_PROP_TYPE);

        // Get all the stdCodesGroupPropList where propType equals to UPDATED_PROP_TYPE
        defaultStdCodesGroupPropShouldNotBeFound("propType.equals=" + UPDATED_PROP_TYPE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropTypeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propType in DEFAULT_PROP_TYPE or UPDATED_PROP_TYPE
        defaultStdCodesGroupPropShouldBeFound("propType.in=" + DEFAULT_PROP_TYPE + "," + UPDATED_PROP_TYPE);

        // Get all the stdCodesGroupPropList where propType equals to UPDATED_PROP_TYPE
        defaultStdCodesGroupPropShouldNotBeFound("propType.in=" + UPDATED_PROP_TYPE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propType is not null
        defaultStdCodesGroupPropShouldBeFound("propType.specified=true");

        // Get all the stdCodesGroupPropList where propType is null
        defaultStdCodesGroupPropShouldNotBeFound("propType.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropMdtrIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propMdtr equals to DEFAULT_PROP_MDTR
        defaultStdCodesGroupPropShouldBeFound("propMdtr.equals=" + DEFAULT_PROP_MDTR);

        // Get all the stdCodesGroupPropList where propMdtr equals to UPDATED_PROP_MDTR
        defaultStdCodesGroupPropShouldNotBeFound("propMdtr.equals=" + UPDATED_PROP_MDTR);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropMdtrIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propMdtr in DEFAULT_PROP_MDTR or UPDATED_PROP_MDTR
        defaultStdCodesGroupPropShouldBeFound("propMdtr.in=" + DEFAULT_PROP_MDTR + "," + UPDATED_PROP_MDTR);

        // Get all the stdCodesGroupPropList where propMdtr equals to UPDATED_PROP_MDTR
        defaultStdCodesGroupPropShouldNotBeFound("propMdtr.in=" + UPDATED_PROP_MDTR);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByPropMdtrIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where propMdtr is not null
        defaultStdCodesGroupPropShouldBeFound("propMdtr.specified=true");

        // Get all the stdCodesGroupPropList where propMdtr is null
        defaultStdCodesGroupPropShouldNotBeFound("propMdtr.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueDate equals to DEFAULT_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldBeFound("dfltValueDate.equals=" + DEFAULT_DFLT_VALUE_DATE);

        // Get all the stdCodesGroupPropList where dfltValueDate equals to UPDATED_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueDate.equals=" + UPDATED_DFLT_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueDateIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueDate in DEFAULT_DFLT_VALUE_DATE or UPDATED_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldBeFound("dfltValueDate.in=" + DEFAULT_DFLT_VALUE_DATE + "," + UPDATED_DFLT_VALUE_DATE);

        // Get all the stdCodesGroupPropList where dfltValueDate equals to UPDATED_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueDate.in=" + UPDATED_DFLT_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueDate is not null
        defaultStdCodesGroupPropShouldBeFound("dfltValueDate.specified=true");

        // Get all the stdCodesGroupPropList where dfltValueDate is null
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueDate greater than or equals to DEFAULT_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldBeFound("dfltValueDate.greaterOrEqualThan=" + DEFAULT_DFLT_VALUE_DATE);

        // Get all the stdCodesGroupPropList where dfltValueDate greater than or equals to UPDATED_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueDate.greaterOrEqualThan=" + UPDATED_DFLT_VALUE_DATE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueDate less than or equals to DEFAULT_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueDate.lessThan=" + DEFAULT_DFLT_VALUE_DATE);

        // Get all the stdCodesGroupPropList where dfltValueDate less than or equals to UPDATED_DFLT_VALUE_DATE
        defaultStdCodesGroupPropShouldBeFound("dfltValueDate.lessThan=" + UPDATED_DFLT_VALUE_DATE);
    }


    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueStringIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueString equals to DEFAULT_DFLT_VALUE_STRING
        defaultStdCodesGroupPropShouldBeFound("dfltValueString.equals=" + DEFAULT_DFLT_VALUE_STRING);

        // Get all the stdCodesGroupPropList where dfltValueString equals to UPDATED_DFLT_VALUE_STRING
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueString.equals=" + UPDATED_DFLT_VALUE_STRING);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueStringIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueString in DEFAULT_DFLT_VALUE_STRING or UPDATED_DFLT_VALUE_STRING
        defaultStdCodesGroupPropShouldBeFound("dfltValueString.in=" + DEFAULT_DFLT_VALUE_STRING + "," + UPDATED_DFLT_VALUE_STRING);

        // Get all the stdCodesGroupPropList where dfltValueString equals to UPDATED_DFLT_VALUE_STRING
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueString.in=" + UPDATED_DFLT_VALUE_STRING);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueStringIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueString is not null
        defaultStdCodesGroupPropShouldBeFound("dfltValueString.specified=true");

        // Get all the stdCodesGroupPropList where dfltValueString is null
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueString.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueBoolIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueBool equals to DEFAULT_DFLT_VALUE_BOOL
        defaultStdCodesGroupPropShouldBeFound("dfltValueBool.equals=" + DEFAULT_DFLT_VALUE_BOOL);

        // Get all the stdCodesGroupPropList where dfltValueBool equals to UPDATED_DFLT_VALUE_BOOL
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueBool.equals=" + UPDATED_DFLT_VALUE_BOOL);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueBoolIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueBool in DEFAULT_DFLT_VALUE_BOOL or UPDATED_DFLT_VALUE_BOOL
        defaultStdCodesGroupPropShouldBeFound("dfltValueBool.in=" + DEFAULT_DFLT_VALUE_BOOL + "," + UPDATED_DFLT_VALUE_BOOL);

        // Get all the stdCodesGroupPropList where dfltValueBool equals to UPDATED_DFLT_VALUE_BOOL
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueBool.in=" + UPDATED_DFLT_VALUE_BOOL);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueBoolIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueBool is not null
        defaultStdCodesGroupPropShouldBeFound("dfltValueBool.specified=true");

        // Get all the stdCodesGroupPropList where dfltValueBool is null
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueBool.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueNumber equals to DEFAULT_DFLT_VALUE_NUMBER
        defaultStdCodesGroupPropShouldBeFound("dfltValueNumber.equals=" + DEFAULT_DFLT_VALUE_NUMBER);

        // Get all the stdCodesGroupPropList where dfltValueNumber equals to UPDATED_DFLT_VALUE_NUMBER
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueNumber.equals=" + UPDATED_DFLT_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueNumberIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueNumber in DEFAULT_DFLT_VALUE_NUMBER or UPDATED_DFLT_VALUE_NUMBER
        defaultStdCodesGroupPropShouldBeFound("dfltValueNumber.in=" + DEFAULT_DFLT_VALUE_NUMBER + "," + UPDATED_DFLT_VALUE_NUMBER);

        // Get all the stdCodesGroupPropList where dfltValueNumber equals to UPDATED_DFLT_VALUE_NUMBER
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueNumber.in=" + UPDATED_DFLT_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupPropsByDfltValueNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

        // Get all the stdCodesGroupPropList where dfltValueNumber is not null
        defaultStdCodesGroupPropShouldBeFound("dfltValueNumber.specified=true");

        // Get all the stdCodesGroupPropList where dfltValueNumber is null
        defaultStdCodesGroupPropShouldNotBeFound("dfltValueNumber.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStdCodesGroupPropShouldBeFound(String filter) throws Exception {
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesGroupProp.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].propCode").value(hasItem(DEFAULT_PROP_CODE)))
            .andExpect(jsonPath("$.[*].propDesc").value(hasItem(DEFAULT_PROP_DESC)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].propType").value(hasItem(DEFAULT_PROP_TYPE.toString())))
            .andExpect(jsonPath("$.[*].propMdtr").value(hasItem(DEFAULT_PROP_MDTR.toString())))
            .andExpect(jsonPath("$.[*].dfltValueDate").value(hasItem(DEFAULT_DFLT_VALUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].dfltValueString").value(hasItem(DEFAULT_DFLT_VALUE_STRING)))
            .andExpect(jsonPath("$.[*].dfltValueBool").value(hasItem(DEFAULT_DFLT_VALUE_BOOL.booleanValue())))
            .andExpect(jsonPath("$.[*].dfltValueNumber").value(hasItem(DEFAULT_DFLT_VALUE_NUMBER.doubleValue())));

        // Check, that the count call also returns 1
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStdCodesGroupPropShouldNotBeFound(String filter) throws Exception {
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingStdCodesGroupProp() throws Exception {
        // Get the stdCodesGroupProp
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesGroupProp() throws Exception {
        // Initialize the database
        stdCodesGroupPropService.save(stdCodesGroupProp);

        int databaseSizeBeforeUpdate = stdCodesGroupPropRepository.findAll().size();

        // Update the stdCodesGroupProp
        StdCodesGroupProp updatedStdCodesGroupProp = stdCodesGroupPropRepository.findById(stdCodesGroupProp.getId()).get();
        // Disconnect from session so that the updates on updatedStdCodesGroupProp are not directly saved in db
        em.detach(updatedStdCodesGroupProp);
        updatedStdCodesGroupProp
            .groupCode(UPDATED_GROUP_CODE)
            .propCode(UPDATED_PROP_CODE)
            .propDesc(UPDATED_PROP_DESC)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .propType(UPDATED_PROP_TYPE)
            .propMdtr(UPDATED_PROP_MDTR)
            .dfltValueDate(UPDATED_DFLT_VALUE_DATE)
            .dfltValueString(UPDATED_DFLT_VALUE_STRING)
            .dfltValueBool(UPDATED_DFLT_VALUE_BOOL)
            .dfltValueNumber(UPDATED_DFLT_VALUE_NUMBER);

        restStdCodesGroupPropMockMvc.perform(put("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStdCodesGroupProp)))
            .andExpect(status().isOk());

        // Validate the StdCodesGroupProp in the database
        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeUpdate);
        StdCodesGroupProp testStdCodesGroupProp = stdCodesGroupPropList.get(stdCodesGroupPropList.size() - 1);
        assertThat(testStdCodesGroupProp.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testStdCodesGroupProp.getPropCode()).isEqualTo(UPDATED_PROP_CODE);
        assertThat(testStdCodesGroupProp.getPropDesc()).isEqualTo(UPDATED_PROP_DESC);
        assertThat(testStdCodesGroupProp.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testStdCodesGroupProp.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testStdCodesGroupProp.getPropType()).isEqualTo(UPDATED_PROP_TYPE);
        assertThat(testStdCodesGroupProp.getPropMdtr()).isEqualTo(UPDATED_PROP_MDTR);
        assertThat(testStdCodesGroupProp.getDfltValueDate()).isEqualTo(UPDATED_DFLT_VALUE_DATE);
        assertThat(testStdCodesGroupProp.getDfltValueString()).isEqualTo(UPDATED_DFLT_VALUE_STRING);
        assertThat(testStdCodesGroupProp.isDfltValueBool()).isEqualTo(UPDATED_DFLT_VALUE_BOOL);
        assertThat(testStdCodesGroupProp.getDfltValueNumber()).isEqualTo(UPDATED_DFLT_VALUE_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingStdCodesGroupProp() throws Exception {
        int databaseSizeBeforeUpdate = stdCodesGroupPropRepository.findAll().size();

        // Create the StdCodesGroupProp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdCodesGroupPropMockMvc.perform(put("/api/std-codes-group-props")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroupProp)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesGroupProp in the database
        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStdCodesGroupProp() throws Exception {
        // Initialize the database
        stdCodesGroupPropService.save(stdCodesGroupProp);

        int databaseSizeBeforeDelete = stdCodesGroupPropRepository.findAll().size();

        // Delete the stdCodesGroupProp
        restStdCodesGroupPropMockMvc.perform(delete("/api/std-codes-group-props/{id}", stdCodesGroupProp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StdCodesGroupProp> stdCodesGroupPropList = stdCodesGroupPropRepository.findAll();
        assertThat(stdCodesGroupPropList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StdCodesGroupProp.class);
        StdCodesGroupProp stdCodesGroupProp1 = new StdCodesGroupProp();
        stdCodesGroupProp1.setId(1L);
        StdCodesGroupProp stdCodesGroupProp2 = new StdCodesGroupProp();
        stdCodesGroupProp2.setId(stdCodesGroupProp1.getId());
        assertThat(stdCodesGroupProp1).isEqualTo(stdCodesGroupProp2);
        stdCodesGroupProp2.setId(2L);
        assertThat(stdCodesGroupProp1).isNotEqualTo(stdCodesGroupProp2);
        stdCodesGroupProp1.setId(null);
        assertThat(stdCodesGroupProp1).isNotEqualTo(stdCodesGroupProp2);
    }
}
