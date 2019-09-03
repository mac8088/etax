package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.ExchangeRate;
import net.atos.etax.repository.ExchangeRateRepository;
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
        final ExchangeRateResource exchangeRateResource = new ExchangeRateResource(exchangeRateRepository);
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
    public void getNonExistingExchangeRate() throws Exception {
        // Get the exchangeRate
        restExchangeRateMockMvc.perform(get("/api/exchange-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExchangeRate() throws Exception {
        // Initialize the database
        exchangeRateRepository.saveAndFlush(exchangeRate);

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
        exchangeRateRepository.saveAndFlush(exchangeRate);

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
