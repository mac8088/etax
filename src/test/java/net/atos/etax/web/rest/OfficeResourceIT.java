package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.Office;
import net.atos.etax.repository.OfficeRepository;
import net.atos.etax.service.OfficeService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.OfficeCriteria;
import net.atos.etax.service.OfficeQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@Link OfficeResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class OfficeResourceIT {

    private static final String DEFAULT_CSTD_OFFICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_OFFICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_CLASSIFIER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_CLASSIFIER_CODE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EFFECTIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EFFECTIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_FAX = "AAAAAAAAAA";
    private static final String UPDATED_FAX = "BBBBBBBBBB";

    private static final String DEFAULT_STL = "AAAAAAAAAA";
    private static final String UPDATED_STL = "BBBBBBBBBB";

    private static final Integer DEFAULT_MNG_OFFICE = 1;
    private static final Integer UPDATED_MNG_OFFICE = 2;

    private static final String DEFAULT_PHYSICAL_ADR = "AAAAAAAAAA";
    private static final String UPDATED_PHYSICAL_ADR = "BBBBBBBBBB";

    private static final String DEFAULT_POSTAL_AADR = "AAAAAAAAAA";
    private static final String UPDATED_POSTAL_AADR = "BBBBBBBBBB";

    private static final String DEFAULT_PIN_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PIN_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_WEEK_WORKING_DAY = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_WEEK_WORKING_DAY = "BBBBBBBBBB";

    private static final String DEFAULT_OFFICE_CODE = "AAAAAAA";
    private static final String UPDATED_OFFICE_CODE = "BBBBBBB";

    private static final String DEFAULT_CSTD_OFFICE_SUB_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_OFFICE_SUB_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_OFFICE_FUNC_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_OFFICE_FUNC_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_CC_VERSION = 1;
    private static final Integer UPDATED_CC_VERSION = 2;

    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private OfficeQueryService officeQueryService;

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

    private MockMvc restOfficeMockMvc;

    private Office office;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeResource officeResource = new OfficeResource(officeService, officeQueryService);
        this.restOfficeMockMvc = MockMvcBuilders.standaloneSetup(officeResource)
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
    public static Office createEntity(EntityManager em) {
        Office office = new Office()
            .cstdOfficeType(DEFAULT_CSTD_OFFICE_TYPE)
            .name(DEFAULT_NAME)
            .cstdClassifierCode(DEFAULT_CSTD_CLASSIFIER_CODE)
            .effectiveDate(DEFAULT_EFFECTIVE_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .phone(DEFAULT_PHONE)
            .fax(DEFAULT_FAX)
            .stl(DEFAULT_STL)
            .mngOffice(DEFAULT_MNG_OFFICE)
            .physicalAdr(DEFAULT_PHYSICAL_ADR)
            .postalAadr(DEFAULT_POSTAL_AADR)
            .pinCode(DEFAULT_PIN_CODE)
            .cstdWeekWorkingDay(DEFAULT_CSTD_WEEK_WORKING_DAY)
            .officeCode(DEFAULT_OFFICE_CODE)
            .cstdOfficeSubType(DEFAULT_CSTD_OFFICE_SUB_TYPE)
            .cstdOfficeFuncType(DEFAULT_CSTD_OFFICE_FUNC_TYPE)
            .ccVersion(DEFAULT_CC_VERSION);
        return office;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Office createUpdatedEntity(EntityManager em) {
        Office office = new Office()
            .cstdOfficeType(UPDATED_CSTD_OFFICE_TYPE)
            .name(UPDATED_NAME)
            .cstdClassifierCode(UPDATED_CSTD_CLASSIFIER_CODE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .stl(UPDATED_STL)
            .mngOffice(UPDATED_MNG_OFFICE)
            .physicalAdr(UPDATED_PHYSICAL_ADR)
            .postalAadr(UPDATED_POSTAL_AADR)
            .pinCode(UPDATED_PIN_CODE)
            .cstdWeekWorkingDay(UPDATED_CSTD_WEEK_WORKING_DAY)
            .officeCode(UPDATED_OFFICE_CODE)
            .cstdOfficeSubType(UPDATED_CSTD_OFFICE_SUB_TYPE)
            .cstdOfficeFuncType(UPDATED_CSTD_OFFICE_FUNC_TYPE)
            .ccVersion(UPDATED_CC_VERSION);
        return office;
    }

    @BeforeEach
    public void initTest() {
        office = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffice() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // Create the Office
        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isCreated());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate + 1);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getCstdOfficeType()).isEqualTo(DEFAULT_CSTD_OFFICE_TYPE);
        assertThat(testOffice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffice.getCstdClassifierCode()).isEqualTo(DEFAULT_CSTD_CLASSIFIER_CODE);
        assertThat(testOffice.getEffectiveDate()).isEqualTo(DEFAULT_EFFECTIVE_DATE);
        assertThat(testOffice.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testOffice.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOffice.getFax()).isEqualTo(DEFAULT_FAX);
        assertThat(testOffice.getStl()).isEqualTo(DEFAULT_STL);
        assertThat(testOffice.getMngOffice()).isEqualTo(DEFAULT_MNG_OFFICE);
        assertThat(testOffice.getPhysicalAdr()).isEqualTo(DEFAULT_PHYSICAL_ADR);
        assertThat(testOffice.getPostalAadr()).isEqualTo(DEFAULT_POSTAL_AADR);
        assertThat(testOffice.getPinCode()).isEqualTo(DEFAULT_PIN_CODE);
        assertThat(testOffice.getCstdWeekWorkingDay()).isEqualTo(DEFAULT_CSTD_WEEK_WORKING_DAY);
        assertThat(testOffice.getOfficeCode()).isEqualTo(DEFAULT_OFFICE_CODE);
        assertThat(testOffice.getCstdOfficeSubType()).isEqualTo(DEFAULT_CSTD_OFFICE_SUB_TYPE);
        assertThat(testOffice.getCstdOfficeFuncType()).isEqualTo(DEFAULT_CSTD_OFFICE_FUNC_TYPE);
        assertThat(testOffice.getCcVersion()).isEqualTo(DEFAULT_CC_VERSION);
    }

    @Test
    @Transactional
    public void createOfficeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeRepository.findAll().size();

        // Create the Office with an existing ID
        office.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCstdOfficeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRepository.findAll().size();
        // set the field null
        office.setCstdOfficeType(null);

        // Create the Office, which fails.

        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRepository.findAll().size();
        // set the field null
        office.setName(null);

        // Create the Office, which fails.

        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkEffectiveDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRepository.findAll().size();
        // set the field null
        office.setEffectiveDate(null);

        // Create the Office, which fails.

        restOfficeMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOffices() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdOfficeType").value(hasItem(DEFAULT_CSTD_OFFICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].cstdClassifierCode").value(hasItem(DEFAULT_CSTD_CLASSIFIER_CODE.toString())))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(sameInstant(DEFAULT_EFFECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX.toString())))
            .andExpect(jsonPath("$.[*].stl").value(hasItem(DEFAULT_STL.toString())))
            .andExpect(jsonPath("$.[*].mngOffice").value(hasItem(DEFAULT_MNG_OFFICE)))
            .andExpect(jsonPath("$.[*].physicalAdr").value(hasItem(DEFAULT_PHYSICAL_ADR.toString())))
            .andExpect(jsonPath("$.[*].postalAadr").value(hasItem(DEFAULT_POSTAL_AADR.toString())))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE.toString())))
            .andExpect(jsonPath("$.[*].cstdWeekWorkingDay").value(hasItem(DEFAULT_CSTD_WEEK_WORKING_DAY.toString())))
            .andExpect(jsonPath("$.[*].officeCode").value(hasItem(DEFAULT_OFFICE_CODE.toString())))
            .andExpect(jsonPath("$.[*].cstdOfficeSubType").value(hasItem(DEFAULT_CSTD_OFFICE_SUB_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cstdOfficeFuncType").value(hasItem(DEFAULT_CSTD_OFFICE_FUNC_TYPE.toString())))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));
    }
    
    @Test
    @Transactional
    public void getOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", office.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(office.getId().intValue()))
            .andExpect(jsonPath("$.cstdOfficeType").value(DEFAULT_CSTD_OFFICE_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.cstdClassifierCode").value(DEFAULT_CSTD_CLASSIFIER_CODE.toString()))
            .andExpect(jsonPath("$.effectiveDate").value(sameInstant(DEFAULT_EFFECTIVE_DATE)))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.fax").value(DEFAULT_FAX.toString()))
            .andExpect(jsonPath("$.stl").value(DEFAULT_STL.toString()))
            .andExpect(jsonPath("$.mngOffice").value(DEFAULT_MNG_OFFICE))
            .andExpect(jsonPath("$.physicalAdr").value(DEFAULT_PHYSICAL_ADR.toString()))
            .andExpect(jsonPath("$.postalAadr").value(DEFAULT_POSTAL_AADR.toString()))
            .andExpect(jsonPath("$.pinCode").value(DEFAULT_PIN_CODE.toString()))
            .andExpect(jsonPath("$.cstdWeekWorkingDay").value(DEFAULT_CSTD_WEEK_WORKING_DAY.toString()))
            .andExpect(jsonPath("$.officeCode").value(DEFAULT_OFFICE_CODE.toString()))
            .andExpect(jsonPath("$.cstdOfficeSubType").value(DEFAULT_CSTD_OFFICE_SUB_TYPE.toString()))
            .andExpect(jsonPath("$.cstdOfficeFuncType").value(DEFAULT_CSTD_OFFICE_FUNC_TYPE.toString()))
            .andExpect(jsonPath("$.ccVersion").value(DEFAULT_CC_VERSION));
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeType equals to DEFAULT_CSTD_OFFICE_TYPE
        defaultOfficeShouldBeFound("cstdOfficeType.equals=" + DEFAULT_CSTD_OFFICE_TYPE);

        // Get all the officeList where cstdOfficeType equals to UPDATED_CSTD_OFFICE_TYPE
        defaultOfficeShouldNotBeFound("cstdOfficeType.equals=" + UPDATED_CSTD_OFFICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeTypeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeType in DEFAULT_CSTD_OFFICE_TYPE or UPDATED_CSTD_OFFICE_TYPE
        defaultOfficeShouldBeFound("cstdOfficeType.in=" + DEFAULT_CSTD_OFFICE_TYPE + "," + UPDATED_CSTD_OFFICE_TYPE);

        // Get all the officeList where cstdOfficeType equals to UPDATED_CSTD_OFFICE_TYPE
        defaultOfficeShouldNotBeFound("cstdOfficeType.in=" + UPDATED_CSTD_OFFICE_TYPE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeType is not null
        defaultOfficeShouldBeFound("cstdOfficeType.specified=true");

        // Get all the officeList where cstdOfficeType is null
        defaultOfficeShouldNotBeFound("cstdOfficeType.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where name equals to DEFAULT_NAME
        defaultOfficeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the officeList where name equals to UPDATED_NAME
        defaultOfficeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOfficesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultOfficeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the officeList where name equals to UPDATED_NAME
        defaultOfficeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllOfficesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where name is not null
        defaultOfficeShouldBeFound("name.specified=true");

        // Get all the officeList where name is null
        defaultOfficeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdClassifierCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdClassifierCode equals to DEFAULT_CSTD_CLASSIFIER_CODE
        defaultOfficeShouldBeFound("cstdClassifierCode.equals=" + DEFAULT_CSTD_CLASSIFIER_CODE);

        // Get all the officeList where cstdClassifierCode equals to UPDATED_CSTD_CLASSIFIER_CODE
        defaultOfficeShouldNotBeFound("cstdClassifierCode.equals=" + UPDATED_CSTD_CLASSIFIER_CODE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdClassifierCodeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdClassifierCode in DEFAULT_CSTD_CLASSIFIER_CODE or UPDATED_CSTD_CLASSIFIER_CODE
        defaultOfficeShouldBeFound("cstdClassifierCode.in=" + DEFAULT_CSTD_CLASSIFIER_CODE + "," + UPDATED_CSTD_CLASSIFIER_CODE);

        // Get all the officeList where cstdClassifierCode equals to UPDATED_CSTD_CLASSIFIER_CODE
        defaultOfficeShouldNotBeFound("cstdClassifierCode.in=" + UPDATED_CSTD_CLASSIFIER_CODE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdClassifierCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdClassifierCode is not null
        defaultOfficeShouldBeFound("cstdClassifierCode.specified=true");

        // Get all the officeList where cstdClassifierCode is null
        defaultOfficeShouldNotBeFound("cstdClassifierCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByEffectiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where effectiveDate equals to DEFAULT_EFFECTIVE_DATE
        defaultOfficeShouldBeFound("effectiveDate.equals=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the officeList where effectiveDate equals to UPDATED_EFFECTIVE_DATE
        defaultOfficeShouldNotBeFound("effectiveDate.equals=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllOfficesByEffectiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where effectiveDate in DEFAULT_EFFECTIVE_DATE or UPDATED_EFFECTIVE_DATE
        defaultOfficeShouldBeFound("effectiveDate.in=" + DEFAULT_EFFECTIVE_DATE + "," + UPDATED_EFFECTIVE_DATE);

        // Get all the officeList where effectiveDate equals to UPDATED_EFFECTIVE_DATE
        defaultOfficeShouldNotBeFound("effectiveDate.in=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllOfficesByEffectiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where effectiveDate is not null
        defaultOfficeShouldBeFound("effectiveDate.specified=true");

        // Get all the officeList where effectiveDate is null
        defaultOfficeShouldNotBeFound("effectiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByEffectiveDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where effectiveDate greater than or equals to DEFAULT_EFFECTIVE_DATE
        defaultOfficeShouldBeFound("effectiveDate.greaterOrEqualThan=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the officeList where effectiveDate greater than or equals to UPDATED_EFFECTIVE_DATE
        defaultOfficeShouldNotBeFound("effectiveDate.greaterOrEqualThan=" + UPDATED_EFFECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllOfficesByEffectiveDateIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where effectiveDate less than or equals to DEFAULT_EFFECTIVE_DATE
        defaultOfficeShouldNotBeFound("effectiveDate.lessThan=" + DEFAULT_EFFECTIVE_DATE);

        // Get all the officeList where effectiveDate less than or equals to UPDATED_EFFECTIVE_DATE
        defaultOfficeShouldBeFound("effectiveDate.lessThan=" + UPDATED_EFFECTIVE_DATE);
    }


    @Test
    @Transactional
    public void getAllOfficesByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultOfficeShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the officeList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultOfficeShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOfficesByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultOfficeShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the officeList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultOfficeShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOfficesByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where expiryDate is not null
        defaultOfficeShouldBeFound("expiryDate.specified=true");

        // Get all the officeList where expiryDate is null
        defaultOfficeShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where expiryDate greater than or equals to DEFAULT_EXPIRY_DATE
        defaultOfficeShouldBeFound("expiryDate.greaterOrEqualThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the officeList where expiryDate greater than or equals to UPDATED_EXPIRY_DATE
        defaultOfficeShouldNotBeFound("expiryDate.greaterOrEqualThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllOfficesByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where expiryDate less than or equals to DEFAULT_EXPIRY_DATE
        defaultOfficeShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the officeList where expiryDate less than or equals to UPDATED_EXPIRY_DATE
        defaultOfficeShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllOfficesByPhoneIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where phone equals to DEFAULT_PHONE
        defaultOfficeShouldBeFound("phone.equals=" + DEFAULT_PHONE);

        // Get all the officeList where phone equals to UPDATED_PHONE
        defaultOfficeShouldNotBeFound("phone.equals=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllOfficesByPhoneIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where phone in DEFAULT_PHONE or UPDATED_PHONE
        defaultOfficeShouldBeFound("phone.in=" + DEFAULT_PHONE + "," + UPDATED_PHONE);

        // Get all the officeList where phone equals to UPDATED_PHONE
        defaultOfficeShouldNotBeFound("phone.in=" + UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void getAllOfficesByPhoneIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where phone is not null
        defaultOfficeShouldBeFound("phone.specified=true");

        // Get all the officeList where phone is null
        defaultOfficeShouldNotBeFound("phone.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByFaxIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where fax equals to DEFAULT_FAX
        defaultOfficeShouldBeFound("fax.equals=" + DEFAULT_FAX);

        // Get all the officeList where fax equals to UPDATED_FAX
        defaultOfficeShouldNotBeFound("fax.equals=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllOfficesByFaxIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where fax in DEFAULT_FAX or UPDATED_FAX
        defaultOfficeShouldBeFound("fax.in=" + DEFAULT_FAX + "," + UPDATED_FAX);

        // Get all the officeList where fax equals to UPDATED_FAX
        defaultOfficeShouldNotBeFound("fax.in=" + UPDATED_FAX);
    }

    @Test
    @Transactional
    public void getAllOfficesByFaxIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where fax is not null
        defaultOfficeShouldBeFound("fax.specified=true");

        // Get all the officeList where fax is null
        defaultOfficeShouldNotBeFound("fax.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByStlIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where stl equals to DEFAULT_STL
        defaultOfficeShouldBeFound("stl.equals=" + DEFAULT_STL);

        // Get all the officeList where stl equals to UPDATED_STL
        defaultOfficeShouldNotBeFound("stl.equals=" + UPDATED_STL);
    }

    @Test
    @Transactional
    public void getAllOfficesByStlIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where stl in DEFAULT_STL or UPDATED_STL
        defaultOfficeShouldBeFound("stl.in=" + DEFAULT_STL + "," + UPDATED_STL);

        // Get all the officeList where stl equals to UPDATED_STL
        defaultOfficeShouldNotBeFound("stl.in=" + UPDATED_STL);
    }

    @Test
    @Transactional
    public void getAllOfficesByStlIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where stl is not null
        defaultOfficeShouldBeFound("stl.specified=true");

        // Get all the officeList where stl is null
        defaultOfficeShouldNotBeFound("stl.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByMngOfficeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where mngOffice equals to DEFAULT_MNG_OFFICE
        defaultOfficeShouldBeFound("mngOffice.equals=" + DEFAULT_MNG_OFFICE);

        // Get all the officeList where mngOffice equals to UPDATED_MNG_OFFICE
        defaultOfficeShouldNotBeFound("mngOffice.equals=" + UPDATED_MNG_OFFICE);
    }

    @Test
    @Transactional
    public void getAllOfficesByMngOfficeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where mngOffice in DEFAULT_MNG_OFFICE or UPDATED_MNG_OFFICE
        defaultOfficeShouldBeFound("mngOffice.in=" + DEFAULT_MNG_OFFICE + "," + UPDATED_MNG_OFFICE);

        // Get all the officeList where mngOffice equals to UPDATED_MNG_OFFICE
        defaultOfficeShouldNotBeFound("mngOffice.in=" + UPDATED_MNG_OFFICE);
    }

    @Test
    @Transactional
    public void getAllOfficesByMngOfficeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where mngOffice is not null
        defaultOfficeShouldBeFound("mngOffice.specified=true");

        // Get all the officeList where mngOffice is null
        defaultOfficeShouldNotBeFound("mngOffice.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByMngOfficeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where mngOffice greater than or equals to DEFAULT_MNG_OFFICE
        defaultOfficeShouldBeFound("mngOffice.greaterOrEqualThan=" + DEFAULT_MNG_OFFICE);

        // Get all the officeList where mngOffice greater than or equals to UPDATED_MNG_OFFICE
        defaultOfficeShouldNotBeFound("mngOffice.greaterOrEqualThan=" + UPDATED_MNG_OFFICE);
    }

    @Test
    @Transactional
    public void getAllOfficesByMngOfficeIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where mngOffice less than or equals to DEFAULT_MNG_OFFICE
        defaultOfficeShouldNotBeFound("mngOffice.lessThan=" + DEFAULT_MNG_OFFICE);

        // Get all the officeList where mngOffice less than or equals to UPDATED_MNG_OFFICE
        defaultOfficeShouldBeFound("mngOffice.lessThan=" + UPDATED_MNG_OFFICE);
    }


    @Test
    @Transactional
    public void getAllOfficesByPinCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where pinCode equals to DEFAULT_PIN_CODE
        defaultOfficeShouldBeFound("pinCode.equals=" + DEFAULT_PIN_CODE);

        // Get all the officeList where pinCode equals to UPDATED_PIN_CODE
        defaultOfficeShouldNotBeFound("pinCode.equals=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllOfficesByPinCodeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where pinCode in DEFAULT_PIN_CODE or UPDATED_PIN_CODE
        defaultOfficeShouldBeFound("pinCode.in=" + DEFAULT_PIN_CODE + "," + UPDATED_PIN_CODE);

        // Get all the officeList where pinCode equals to UPDATED_PIN_CODE
        defaultOfficeShouldNotBeFound("pinCode.in=" + UPDATED_PIN_CODE);
    }

    @Test
    @Transactional
    public void getAllOfficesByPinCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where pinCode is not null
        defaultOfficeShouldBeFound("pinCode.specified=true");

        // Get all the officeList where pinCode is null
        defaultOfficeShouldNotBeFound("pinCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdWeekWorkingDayIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdWeekWorkingDay equals to DEFAULT_CSTD_WEEK_WORKING_DAY
        defaultOfficeShouldBeFound("cstdWeekWorkingDay.equals=" + DEFAULT_CSTD_WEEK_WORKING_DAY);

        // Get all the officeList where cstdWeekWorkingDay equals to UPDATED_CSTD_WEEK_WORKING_DAY
        defaultOfficeShouldNotBeFound("cstdWeekWorkingDay.equals=" + UPDATED_CSTD_WEEK_WORKING_DAY);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdWeekWorkingDayIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdWeekWorkingDay in DEFAULT_CSTD_WEEK_WORKING_DAY or UPDATED_CSTD_WEEK_WORKING_DAY
        defaultOfficeShouldBeFound("cstdWeekWorkingDay.in=" + DEFAULT_CSTD_WEEK_WORKING_DAY + "," + UPDATED_CSTD_WEEK_WORKING_DAY);

        // Get all the officeList where cstdWeekWorkingDay equals to UPDATED_CSTD_WEEK_WORKING_DAY
        defaultOfficeShouldNotBeFound("cstdWeekWorkingDay.in=" + UPDATED_CSTD_WEEK_WORKING_DAY);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdWeekWorkingDayIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdWeekWorkingDay is not null
        defaultOfficeShouldBeFound("cstdWeekWorkingDay.specified=true");

        // Get all the officeList where cstdWeekWorkingDay is null
        defaultOfficeShouldNotBeFound("cstdWeekWorkingDay.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficeCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officeCode equals to DEFAULT_OFFICE_CODE
        defaultOfficeShouldBeFound("officeCode.equals=" + DEFAULT_OFFICE_CODE);

        // Get all the officeList where officeCode equals to UPDATED_OFFICE_CODE
        defaultOfficeShouldNotBeFound("officeCode.equals=" + UPDATED_OFFICE_CODE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficeCodeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officeCode in DEFAULT_OFFICE_CODE or UPDATED_OFFICE_CODE
        defaultOfficeShouldBeFound("officeCode.in=" + DEFAULT_OFFICE_CODE + "," + UPDATED_OFFICE_CODE);

        // Get all the officeList where officeCode equals to UPDATED_OFFICE_CODE
        defaultOfficeShouldNotBeFound("officeCode.in=" + UPDATED_OFFICE_CODE);
    }

    @Test
    @Transactional
    public void getAllOfficesByOfficeCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where officeCode is not null
        defaultOfficeShouldBeFound("officeCode.specified=true");

        // Get all the officeList where officeCode is null
        defaultOfficeShouldNotBeFound("officeCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeSubTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeSubType equals to DEFAULT_CSTD_OFFICE_SUB_TYPE
        defaultOfficeShouldBeFound("cstdOfficeSubType.equals=" + DEFAULT_CSTD_OFFICE_SUB_TYPE);

        // Get all the officeList where cstdOfficeSubType equals to UPDATED_CSTD_OFFICE_SUB_TYPE
        defaultOfficeShouldNotBeFound("cstdOfficeSubType.equals=" + UPDATED_CSTD_OFFICE_SUB_TYPE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeSubTypeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeSubType in DEFAULT_CSTD_OFFICE_SUB_TYPE or UPDATED_CSTD_OFFICE_SUB_TYPE
        defaultOfficeShouldBeFound("cstdOfficeSubType.in=" + DEFAULT_CSTD_OFFICE_SUB_TYPE + "," + UPDATED_CSTD_OFFICE_SUB_TYPE);

        // Get all the officeList where cstdOfficeSubType equals to UPDATED_CSTD_OFFICE_SUB_TYPE
        defaultOfficeShouldNotBeFound("cstdOfficeSubType.in=" + UPDATED_CSTD_OFFICE_SUB_TYPE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeSubTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeSubType is not null
        defaultOfficeShouldBeFound("cstdOfficeSubType.specified=true");

        // Get all the officeList where cstdOfficeSubType is null
        defaultOfficeShouldNotBeFound("cstdOfficeSubType.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeFuncTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeFuncType equals to DEFAULT_CSTD_OFFICE_FUNC_TYPE
        defaultOfficeShouldBeFound("cstdOfficeFuncType.equals=" + DEFAULT_CSTD_OFFICE_FUNC_TYPE);

        // Get all the officeList where cstdOfficeFuncType equals to UPDATED_CSTD_OFFICE_FUNC_TYPE
        defaultOfficeShouldNotBeFound("cstdOfficeFuncType.equals=" + UPDATED_CSTD_OFFICE_FUNC_TYPE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeFuncTypeIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeFuncType in DEFAULT_CSTD_OFFICE_FUNC_TYPE or UPDATED_CSTD_OFFICE_FUNC_TYPE
        defaultOfficeShouldBeFound("cstdOfficeFuncType.in=" + DEFAULT_CSTD_OFFICE_FUNC_TYPE + "," + UPDATED_CSTD_OFFICE_FUNC_TYPE);

        // Get all the officeList where cstdOfficeFuncType equals to UPDATED_CSTD_OFFICE_FUNC_TYPE
        defaultOfficeShouldNotBeFound("cstdOfficeFuncType.in=" + UPDATED_CSTD_OFFICE_FUNC_TYPE);
    }

    @Test
    @Transactional
    public void getAllOfficesByCstdOfficeFuncTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where cstdOfficeFuncType is not null
        defaultOfficeShouldBeFound("cstdOfficeFuncType.specified=true");

        // Get all the officeList where cstdOfficeFuncType is null
        defaultOfficeShouldNotBeFound("cstdOfficeFuncType.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByCcVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ccVersion equals to DEFAULT_CC_VERSION
        defaultOfficeShouldBeFound("ccVersion.equals=" + DEFAULT_CC_VERSION);

        // Get all the officeList where ccVersion equals to UPDATED_CC_VERSION
        defaultOfficeShouldNotBeFound("ccVersion.equals=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllOfficesByCcVersionIsInShouldWork() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ccVersion in DEFAULT_CC_VERSION or UPDATED_CC_VERSION
        defaultOfficeShouldBeFound("ccVersion.in=" + DEFAULT_CC_VERSION + "," + UPDATED_CC_VERSION);

        // Get all the officeList where ccVersion equals to UPDATED_CC_VERSION
        defaultOfficeShouldNotBeFound("ccVersion.in=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllOfficesByCcVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ccVersion is not null
        defaultOfficeShouldBeFound("ccVersion.specified=true");

        // Get all the officeList where ccVersion is null
        defaultOfficeShouldNotBeFound("ccVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllOfficesByCcVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ccVersion greater than or equals to DEFAULT_CC_VERSION
        defaultOfficeShouldBeFound("ccVersion.greaterOrEqualThan=" + DEFAULT_CC_VERSION);

        // Get all the officeList where ccVersion greater than or equals to UPDATED_CC_VERSION
        defaultOfficeShouldNotBeFound("ccVersion.greaterOrEqualThan=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllOfficesByCcVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

        // Get all the officeList where ccVersion less than or equals to DEFAULT_CC_VERSION
        defaultOfficeShouldNotBeFound("ccVersion.lessThan=" + DEFAULT_CC_VERSION);

        // Get all the officeList where ccVersion less than or equals to UPDATED_CC_VERSION
        defaultOfficeShouldBeFound("ccVersion.lessThan=" + UPDATED_CC_VERSION);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOfficeShouldBeFound(String filter) throws Exception {
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(office.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdOfficeType").value(hasItem(DEFAULT_CSTD_OFFICE_TYPE)))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cstdClassifierCode").value(hasItem(DEFAULT_CSTD_CLASSIFIER_CODE)))
            .andExpect(jsonPath("$.[*].effectiveDate").value(hasItem(sameInstant(DEFAULT_EFFECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].fax").value(hasItem(DEFAULT_FAX)))
            .andExpect(jsonPath("$.[*].stl").value(hasItem(DEFAULT_STL)))
            .andExpect(jsonPath("$.[*].mngOffice").value(hasItem(DEFAULT_MNG_OFFICE)))
            .andExpect(jsonPath("$.[*].physicalAdr").value(hasItem(DEFAULT_PHYSICAL_ADR.toString())))
            .andExpect(jsonPath("$.[*].postalAadr").value(hasItem(DEFAULT_POSTAL_AADR.toString())))
            .andExpect(jsonPath("$.[*].pinCode").value(hasItem(DEFAULT_PIN_CODE)))
            .andExpect(jsonPath("$.[*].cstdWeekWorkingDay").value(hasItem(DEFAULT_CSTD_WEEK_WORKING_DAY)))
            .andExpect(jsonPath("$.[*].officeCode").value(hasItem(DEFAULT_OFFICE_CODE)))
            .andExpect(jsonPath("$.[*].cstdOfficeSubType").value(hasItem(DEFAULT_CSTD_OFFICE_SUB_TYPE)))
            .andExpect(jsonPath("$.[*].cstdOfficeFuncType").value(hasItem(DEFAULT_CSTD_OFFICE_FUNC_TYPE)))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));

        // Check, that the count call also returns 1
        restOfficeMockMvc.perform(get("/api/offices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOfficeShouldNotBeFound(String filter) throws Exception {
        restOfficeMockMvc.perform(get("/api/offices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOfficeMockMvc.perform(get("/api/offices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingOffice() throws Exception {
        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffice() throws Exception {
        // Initialize the database
        officeService.save(office);

        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Update the office
        Office updatedOffice = officeRepository.findById(office.getId()).get();
        // Disconnect from session so that the updates on updatedOffice are not directly saved in db
        em.detach(updatedOffice);
        updatedOffice
            .cstdOfficeType(UPDATED_CSTD_OFFICE_TYPE)
            .name(UPDATED_NAME)
            .cstdClassifierCode(UPDATED_CSTD_CLASSIFIER_CODE)
            .effectiveDate(UPDATED_EFFECTIVE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .phone(UPDATED_PHONE)
            .fax(UPDATED_FAX)
            .stl(UPDATED_STL)
            .mngOffice(UPDATED_MNG_OFFICE)
            .physicalAdr(UPDATED_PHYSICAL_ADR)
            .postalAadr(UPDATED_POSTAL_AADR)
            .pinCode(UPDATED_PIN_CODE)
            .cstdWeekWorkingDay(UPDATED_CSTD_WEEK_WORKING_DAY)
            .officeCode(UPDATED_OFFICE_CODE)
            .cstdOfficeSubType(UPDATED_CSTD_OFFICE_SUB_TYPE)
            .cstdOfficeFuncType(UPDATED_CSTD_OFFICE_FUNC_TYPE)
            .ccVersion(UPDATED_CC_VERSION);

        restOfficeMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffice)))
            .andExpect(status().isOk());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
        Office testOffice = officeList.get(officeList.size() - 1);
        assertThat(testOffice.getCstdOfficeType()).isEqualTo(UPDATED_CSTD_OFFICE_TYPE);
        assertThat(testOffice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffice.getCstdClassifierCode()).isEqualTo(UPDATED_CSTD_CLASSIFIER_CODE);
        assertThat(testOffice.getEffectiveDate()).isEqualTo(UPDATED_EFFECTIVE_DATE);
        assertThat(testOffice.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testOffice.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOffice.getFax()).isEqualTo(UPDATED_FAX);
        assertThat(testOffice.getStl()).isEqualTo(UPDATED_STL);
        assertThat(testOffice.getMngOffice()).isEqualTo(UPDATED_MNG_OFFICE);
        assertThat(testOffice.getPhysicalAdr()).isEqualTo(UPDATED_PHYSICAL_ADR);
        assertThat(testOffice.getPostalAadr()).isEqualTo(UPDATED_POSTAL_AADR);
        assertThat(testOffice.getPinCode()).isEqualTo(UPDATED_PIN_CODE);
        assertThat(testOffice.getCstdWeekWorkingDay()).isEqualTo(UPDATED_CSTD_WEEK_WORKING_DAY);
        assertThat(testOffice.getOfficeCode()).isEqualTo(UPDATED_OFFICE_CODE);
        assertThat(testOffice.getCstdOfficeSubType()).isEqualTo(UPDATED_CSTD_OFFICE_SUB_TYPE);
        assertThat(testOffice.getCstdOfficeFuncType()).isEqualTo(UPDATED_CSTD_OFFICE_FUNC_TYPE);
        assertThat(testOffice.getCcVersion()).isEqualTo(UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingOffice() throws Exception {
        int databaseSizeBeforeUpdate = officeRepository.findAll().size();

        // Create the Office

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(office)))
            .andExpect(status().isBadRequest());

        // Validate the Office in the database
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffice() throws Exception {
        // Initialize the database
        officeService.save(office);

        int databaseSizeBeforeDelete = officeRepository.findAll().size();

        // Delete the office
        restOfficeMockMvc.perform(delete("/api/offices/{id}", office.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Office> officeList = officeRepository.findAll();
        assertThat(officeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Office.class);
        Office office1 = new Office();
        office1.setId(1L);
        Office office2 = new Office();
        office2.setId(office1.getId());
        assertThat(office1).isEqualTo(office2);
        office2.setId(2L);
        assertThat(office1).isNotEqualTo(office2);
        office1.setId(null);
        assertThat(office1).isNotEqualTo(office2);
    }
}
