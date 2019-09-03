package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.Office;
import net.atos.etax.repository.OfficeRepository;
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
        final OfficeResource officeResource = new OfficeResource(officeRepository);
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
    public void getNonExistingOffice() throws Exception {
        // Get the office
        restOfficeMockMvc.perform(get("/api/offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffice() throws Exception {
        // Initialize the database
        officeRepository.saveAndFlush(office);

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
        officeRepository.saveAndFlush(office);

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
