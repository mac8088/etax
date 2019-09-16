package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.ExchangeRate;
import net.atos.etax.repository.ExchangeRateRepository;
import net.atos.etax.service.ExchangeRateService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.ExchangeRateCriteria;
import net.atos.etax.service.ExchangeRateQueryService;

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
 * Integration tests for the {@Link ExchangeRateResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class ExchangeRateResourceIT {

    private static final String DEFAULT_CSTD_CURRENCY_A = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_CURRENCY_A = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_CURRENCY_B = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_CURRENCY_B = "BBBBBBBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_CC_VERSION = 1;
    private static final Integer UPDATED_CC_VERSION = 2;

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    @Autowired
    private ExchangeRateService exchangeRateService;

    @Autowired
    private ExchangeRateQueryService exchangeRateQueryService;

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

    private MockMvc restExchangeRateMockMvc;

    private ExchangeRate exchangeRate;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExchangeRateResource exchangeRateResource = new ExchangeRateResource(exchangeRateService, exchangeRateQueryService);
        this.restExchangeRateMockMvc = MockMvcBuilders.standaloneSetup(exchangeRateResource)
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
    public static ExchangeRate createEntity(EntityManager em) {
        ExchangeRate exchangeRate = new ExchangeRate()
            .cstdCurrencyA(DEFAULT_CSTD_CURRENCY_A)
            .cstdCurrencyB(DEFAULT_CSTD_CURRENCY_B)
            .rate(DEFAULT_RATE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .ccVersion(DEFAULT_CC_VERSION);
        return exchangeRate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExchangeRate createUpdatedEntity(EntityManager em) {
        ExchangeRate exchangeRate = new ExchangeRate()
            .cstdCurrencyA(UPDATED_CSTD_CURRENCY_A)
            .cstdCurrencyB(UPDATED_CSTD_CURRENCY_B)
            .rate(UPDATED_RATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .ccVersion(UPDATED_CC_VERSION);
        return exchangeRate;
    }

    @BeforeEach
    public void initTest() {
        exchangeRate = createEntity(em);
    }

    @Test
    @Transactional
    public void createExchangeRate() throws Exception {
        int databaseSizeBeforeCreate = exchangeRateRepository.findAll().size();

        // Create the ExchangeRate
        restExchangeRateMockMvc.perform(post("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isCreated());

        // Validate the ExchangeRate in the database
        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeCreate + 1);
        ExchangeRate testExchangeRate = exchangeRateList.get(exchangeRateList.size() - 1);
        assertThat(testExchangeRate.getCstdCurrencyA()).isEqualTo(DEFAULT_CSTD_CURRENCY_A);
        assertThat(testExchangeRate.getCstdCurrencyB()).isEqualTo(DEFAULT_CSTD_CURRENCY_B);
        assertThat(testExchangeRate.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testExchangeRate.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testExchangeRate.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testExchangeRate.getCcVersion()).isEqualTo(DEFAULT_CC_VERSION);
    }

    @Test
    @Transactional
    public void createExchangeRateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = exchangeRateRepository.findAll().size();

        // Create the ExchangeRate with an existing ID
        exchangeRate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExchangeRateMockMvc.perform(post("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isBadRequest());

        // Validate the ExchangeRate in the database
        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCstdCurrencyAIsRequired() throws Exception {
        int databaseSizeBeforeTest = exchangeRateRepository.findAll().size();
        // set the field null
        exchangeRate.setCstdCurrencyA(null);

        // Create the ExchangeRate, which fails.

        restExchangeRateMockMvc.perform(post("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isBadRequest());

        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdCurrencyBIsRequired() throws Exception {
        int databaseSizeBeforeTest = exchangeRateRepository.findAll().size();
        // set the field null
        exchangeRate.setCstdCurrencyB(null);

        // Create the ExchangeRate, which fails.

        restExchangeRateMockMvc.perform(post("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isBadRequest());

        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exchangeRateRepository.findAll().size();
        // set the field null
        exchangeRate.setRate(null);

        // Create the ExchangeRate, which fails.

        restExchangeRateMockMvc.perform(post("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isBadRequest());

        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = exchangeRateRepository.findAll().size();
        // set the field null
        exchangeRate.setStartDate(null);

        // Create the ExchangeRate, which fails.

        restExchangeRateMockMvc.perform(post("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isBadRequest());

        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllExchangeRates() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList
        restExchangeRateMockMvc.perform(get("/api/exchange-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exchangeRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdCurrencyA").value(hasItem(DEFAULT_CSTD_CURRENCY_A.toString())))
            .andExpect(jsonPath("$.[*].cstdCurrencyB").value(hasItem(DEFAULT_CSTD_CURRENCY_B.toString())))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));
    }
    
    @Test
    @Transactional
    public void getExchangeRate() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get the exchangeRate
        restExchangeRateMockMvc.perform(get("/api/exchange-rates/{id}", exchangeRate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(exchangeRate.getId().intValue()))
            .andExpect(jsonPath("$.cstdCurrencyA").value(DEFAULT_CSTD_CURRENCY_A.toString()))
            .andExpect(jsonPath("$.cstdCurrencyB").value(DEFAULT_CSTD_CURRENCY_B.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()))
            .andExpect(jsonPath("$.ccVersion").value(DEFAULT_CC_VERSION));
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCstdCurrencyAIsEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where cstdCurrencyA equals to DEFAULT_CSTD_CURRENCY_A
        defaultExchangeRateShouldBeFound("cstdCurrencyA.equals=" + DEFAULT_CSTD_CURRENCY_A);

        // Get all the exchangeRateList where cstdCurrencyA equals to UPDATED_CSTD_CURRENCY_A
        defaultExchangeRateShouldNotBeFound("cstdCurrencyA.equals=" + UPDATED_CSTD_CURRENCY_A);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCstdCurrencyAIsInShouldWork() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where cstdCurrencyA in DEFAULT_CSTD_CURRENCY_A or UPDATED_CSTD_CURRENCY_A
        defaultExchangeRateShouldBeFound("cstdCurrencyA.in=" + DEFAULT_CSTD_CURRENCY_A + "," + UPDATED_CSTD_CURRENCY_A);

        // Get all the exchangeRateList where cstdCurrencyA equals to UPDATED_CSTD_CURRENCY_A
        defaultExchangeRateShouldNotBeFound("cstdCurrencyA.in=" + UPDATED_CSTD_CURRENCY_A);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCstdCurrencyAIsNullOrNotNull() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where cstdCurrencyA is not null
        defaultExchangeRateShouldBeFound("cstdCurrencyA.specified=true");

        // Get all the exchangeRateList where cstdCurrencyA is null
        defaultExchangeRateShouldNotBeFound("cstdCurrencyA.specified=false");
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCstdCurrencyBIsEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where cstdCurrencyB equals to DEFAULT_CSTD_CURRENCY_B
        defaultExchangeRateShouldBeFound("cstdCurrencyB.equals=" + DEFAULT_CSTD_CURRENCY_B);

        // Get all the exchangeRateList where cstdCurrencyB equals to UPDATED_CSTD_CURRENCY_B
        defaultExchangeRateShouldNotBeFound("cstdCurrencyB.equals=" + UPDATED_CSTD_CURRENCY_B);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCstdCurrencyBIsInShouldWork() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where cstdCurrencyB in DEFAULT_CSTD_CURRENCY_B or UPDATED_CSTD_CURRENCY_B
        defaultExchangeRateShouldBeFound("cstdCurrencyB.in=" + DEFAULT_CSTD_CURRENCY_B + "," + UPDATED_CSTD_CURRENCY_B);

        // Get all the exchangeRateList where cstdCurrencyB equals to UPDATED_CSTD_CURRENCY_B
        defaultExchangeRateShouldNotBeFound("cstdCurrencyB.in=" + UPDATED_CSTD_CURRENCY_B);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCstdCurrencyBIsNullOrNotNull() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where cstdCurrencyB is not null
        defaultExchangeRateShouldBeFound("cstdCurrencyB.specified=true");

        // Get all the exchangeRateList where cstdCurrencyB is null
        defaultExchangeRateShouldNotBeFound("cstdCurrencyB.specified=false");
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByRateIsEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where rate equals to DEFAULT_RATE
        defaultExchangeRateShouldBeFound("rate.equals=" + DEFAULT_RATE);

        // Get all the exchangeRateList where rate equals to UPDATED_RATE
        defaultExchangeRateShouldNotBeFound("rate.equals=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByRateIsInShouldWork() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where rate in DEFAULT_RATE or UPDATED_RATE
        defaultExchangeRateShouldBeFound("rate.in=" + DEFAULT_RATE + "," + UPDATED_RATE);

        // Get all the exchangeRateList where rate equals to UPDATED_RATE
        defaultExchangeRateShouldNotBeFound("rate.in=" + UPDATED_RATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where rate is not null
        defaultExchangeRateShouldBeFound("rate.specified=true");

        // Get all the exchangeRateList where rate is null
        defaultExchangeRateShouldNotBeFound("rate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByStartDateIsEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where startDate equals to DEFAULT_START_DATE
        defaultExchangeRateShouldBeFound("startDate.equals=" + DEFAULT_START_DATE);

        // Get all the exchangeRateList where startDate equals to UPDATED_START_DATE
        defaultExchangeRateShouldNotBeFound("startDate.equals=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByStartDateIsInShouldWork() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where startDate in DEFAULT_START_DATE or UPDATED_START_DATE
        defaultExchangeRateShouldBeFound("startDate.in=" + DEFAULT_START_DATE + "," + UPDATED_START_DATE);

        // Get all the exchangeRateList where startDate equals to UPDATED_START_DATE
        defaultExchangeRateShouldNotBeFound("startDate.in=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByStartDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where startDate is not null
        defaultExchangeRateShouldBeFound("startDate.specified=true");

        // Get all the exchangeRateList where startDate is null
        defaultExchangeRateShouldNotBeFound("startDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByStartDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where startDate greater than or equals to DEFAULT_START_DATE
        defaultExchangeRateShouldBeFound("startDate.greaterOrEqualThan=" + DEFAULT_START_DATE);

        // Get all the exchangeRateList where startDate greater than or equals to UPDATED_START_DATE
        defaultExchangeRateShouldNotBeFound("startDate.greaterOrEqualThan=" + UPDATED_START_DATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByStartDateIsLessThanSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where startDate less than or equals to DEFAULT_START_DATE
        defaultExchangeRateShouldNotBeFound("startDate.lessThan=" + DEFAULT_START_DATE);

        // Get all the exchangeRateList where startDate less than or equals to UPDATED_START_DATE
        defaultExchangeRateShouldBeFound("startDate.lessThan=" + UPDATED_START_DATE);
    }


    @Test
    @Transactional
    public void getAllExchangeRatesByEndDateIsEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where endDate equals to DEFAULT_END_DATE
        defaultExchangeRateShouldBeFound("endDate.equals=" + DEFAULT_END_DATE);

        // Get all the exchangeRateList where endDate equals to UPDATED_END_DATE
        defaultExchangeRateShouldNotBeFound("endDate.equals=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByEndDateIsInShouldWork() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where endDate in DEFAULT_END_DATE or UPDATED_END_DATE
        defaultExchangeRateShouldBeFound("endDate.in=" + DEFAULT_END_DATE + "," + UPDATED_END_DATE);

        // Get all the exchangeRateList where endDate equals to UPDATED_END_DATE
        defaultExchangeRateShouldNotBeFound("endDate.in=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByEndDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where endDate is not null
        defaultExchangeRateShouldBeFound("endDate.specified=true");

        // Get all the exchangeRateList where endDate is null
        defaultExchangeRateShouldNotBeFound("endDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByEndDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where endDate greater than or equals to DEFAULT_END_DATE
        defaultExchangeRateShouldBeFound("endDate.greaterOrEqualThan=" + DEFAULT_END_DATE);

        // Get all the exchangeRateList where endDate greater than or equals to UPDATED_END_DATE
        defaultExchangeRateShouldNotBeFound("endDate.greaterOrEqualThan=" + UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByEndDateIsLessThanSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where endDate less than or equals to DEFAULT_END_DATE
        defaultExchangeRateShouldNotBeFound("endDate.lessThan=" + DEFAULT_END_DATE);

        // Get all the exchangeRateList where endDate less than or equals to UPDATED_END_DATE
        defaultExchangeRateShouldBeFound("endDate.lessThan=" + UPDATED_END_DATE);
    }


    @Test
    @Transactional
    public void getAllExchangeRatesByCcVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where ccVersion equals to DEFAULT_CC_VERSION
        defaultExchangeRateShouldBeFound("ccVersion.equals=" + DEFAULT_CC_VERSION);

        // Get all the exchangeRateList where ccVersion equals to UPDATED_CC_VERSION
        defaultExchangeRateShouldNotBeFound("ccVersion.equals=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCcVersionIsInShouldWork() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where ccVersion in DEFAULT_CC_VERSION or UPDATED_CC_VERSION
        defaultExchangeRateShouldBeFound("ccVersion.in=" + DEFAULT_CC_VERSION + "," + UPDATED_CC_VERSION);

        // Get all the exchangeRateList where ccVersion equals to UPDATED_CC_VERSION
        defaultExchangeRateShouldNotBeFound("ccVersion.in=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCcVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where ccVersion is not null
        defaultExchangeRateShouldBeFound("ccVersion.specified=true");

        // Get all the exchangeRateList where ccVersion is null
        defaultExchangeRateShouldNotBeFound("ccVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCcVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where ccVersion greater than or equals to DEFAULT_CC_VERSION
        defaultExchangeRateShouldBeFound("ccVersion.greaterOrEqualThan=" + DEFAULT_CC_VERSION);

        // Get all the exchangeRateList where ccVersion greater than or equals to UPDATED_CC_VERSION
        defaultExchangeRateShouldNotBeFound("ccVersion.greaterOrEqualThan=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllExchangeRatesByCcVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

        // Get all the exchangeRateList where ccVersion less than or equals to DEFAULT_CC_VERSION
        defaultExchangeRateShouldNotBeFound("ccVersion.lessThan=" + DEFAULT_CC_VERSION);

        // Get all the exchangeRateList where ccVersion less than or equals to UPDATED_CC_VERSION
        defaultExchangeRateShouldBeFound("ccVersion.lessThan=" + UPDATED_CC_VERSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultExchangeRateShouldBeFound(String filter) throws Exception {
        restExchangeRateMockMvc.perform(get("/api/exchange-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(exchangeRate.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdCurrencyA").value(hasItem(DEFAULT_CSTD_CURRENCY_A)))
            .andExpect(jsonPath("$.[*].cstdCurrencyB").value(hasItem(DEFAULT_CSTD_CURRENCY_B)))
            .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));

        // Check, that the count call also returns 1
        restExchangeRateMockMvc.perform(get("/api/exchange-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultExchangeRateShouldNotBeFound(String filter) throws Exception {
        restExchangeRateMockMvc.perform(get("/api/exchange-rates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restExchangeRateMockMvc.perform(get("/api/exchange-rates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingExchangeRate() throws Exception {
        // Get the exchangeRate
        restExchangeRateMockMvc.perform(get("/api/exchange-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExchangeRate() throws Exception {
        // Initialize the database
        exchangeRateService.save(exchangeRate);

        int databaseSizeBeforeUpdate = exchangeRateRepository.findAll().size();

        // Update the exchangeRate
        ExchangeRate updatedExchangeRate = exchangeRateRepository.findById(exchangeRate.getId()).get();
        // Disconnect from session so that the updates on updatedExchangeRate are not directly saved in db
        em.detach(updatedExchangeRate);
        updatedExchangeRate
            .cstdCurrencyA(UPDATED_CSTD_CURRENCY_A)
            .cstdCurrencyB(UPDATED_CSTD_CURRENCY_B)
            .rate(UPDATED_RATE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .ccVersion(UPDATED_CC_VERSION);

        restExchangeRateMockMvc.perform(put("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedExchangeRate)))
            .andExpect(status().isOk());

        // Validate the ExchangeRate in the database
        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeUpdate);
        ExchangeRate testExchangeRate = exchangeRateList.get(exchangeRateList.size() - 1);
        assertThat(testExchangeRate.getCstdCurrencyA()).isEqualTo(UPDATED_CSTD_CURRENCY_A);
        assertThat(testExchangeRate.getCstdCurrencyB()).isEqualTo(UPDATED_CSTD_CURRENCY_B);
        assertThat(testExchangeRate.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testExchangeRate.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testExchangeRate.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testExchangeRate.getCcVersion()).isEqualTo(UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingExchangeRate() throws Exception {
        int databaseSizeBeforeUpdate = exchangeRateRepository.findAll().size();

        // Create the ExchangeRate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExchangeRateMockMvc.perform(put("/api/exchange-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(exchangeRate)))
            .andExpect(status().isBadRequest());

        // Validate the ExchangeRate in the database
        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExchangeRate() throws Exception {
        // Initialize the database
        exchangeRateService.save(exchangeRate);

        int databaseSizeBeforeDelete = exchangeRateRepository.findAll().size();

        // Delete the exchangeRate
        restExchangeRateMockMvc.perform(delete("/api/exchange-rates/{id}", exchangeRate.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExchangeRate> exchangeRateList = exchangeRateRepository.findAll();
        assertThat(exchangeRateList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExchangeRate.class);
        ExchangeRate exchangeRate1 = new ExchangeRate();
        exchangeRate1.setId(1L);
        ExchangeRate exchangeRate2 = new ExchangeRate();
        exchangeRate2.setId(exchangeRate1.getId());
        assertThat(exchangeRate1).isEqualTo(exchangeRate2);
        exchangeRate2.setId(2L);
        assertThat(exchangeRate1).isNotEqualTo(exchangeRate2);
        exchangeRate1.setId(null);
        assertThat(exchangeRate1).isNotEqualTo(exchangeRate2);
    }
}
