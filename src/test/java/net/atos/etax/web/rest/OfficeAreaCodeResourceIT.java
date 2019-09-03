package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.OfficeAreaCode;
import net.atos.etax.repository.OfficeAreaCodeRepository;
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
import java.util.List;

import static net.atos.etax.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link OfficeAreaCodeResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class OfficeAreaCodeResourceIT {

    private static final Integer DEFAULT_OFFICE_ID = 1;
    private static final Integer UPDATED_OFFICE_ID = 2;

    private static final String DEFAULT_FROM_PIN = "AAAAAAAAAA";
    private static final String UPDATED_FROM_PIN = "BBBBBBBBBB";

    private static final String DEFAULT_TO_PIN = "AAAAAAAAAA";
    private static final String UPDATED_TO_PIN = "BBBBBBBBBB";

    @Autowired
    private OfficeAreaCodeRepository officeAreaCodeRepository;

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

    private MockMvc restOfficeAreaCodeMockMvc;

    private OfficeAreaCode officeAreaCode;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeAreaCodeResource officeAreaCodeResource = new OfficeAreaCodeResource(officeAreaCodeRepository);
        this.restOfficeAreaCodeMockMvc = MockMvcBuilders.standaloneSetup(officeAreaCodeResource)
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
    public static OfficeAreaCode createEntity(EntityManager em) {
        OfficeAreaCode officeAreaCode = new OfficeAreaCode()
            .officeId(DEFAULT_OFFICE_ID)
            .fromPin(DEFAULT_FROM_PIN)
            .toPin(DEFAULT_TO_PIN);
        return officeAreaCode;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeAreaCode createUpdatedEntity(EntityManager em) {
        OfficeAreaCode officeAreaCode = new OfficeAreaCode()
            .officeId(UPDATED_OFFICE_ID)
            .fromPin(UPDATED_FROM_PIN)
            .toPin(UPDATED_TO_PIN);
        return officeAreaCode;
    }

    @BeforeEach
    public void initTest() {
        officeAreaCode = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfficeAreaCode() throws Exception {
        int databaseSizeBeforeCreate = officeAreaCodeRepository.findAll().size();

        // Create the OfficeAreaCode
        restOfficeAreaCodeMockMvc.perform(post("/api/office-area-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeAreaCode)))
            .andExpect(status().isCreated());

        // Validate the OfficeAreaCode in the database
        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeCreate + 1);
        OfficeAreaCode testOfficeAreaCode = officeAreaCodeList.get(officeAreaCodeList.size() - 1);
        assertThat(testOfficeAreaCode.getOfficeId()).isEqualTo(DEFAULT_OFFICE_ID);
        assertThat(testOfficeAreaCode.getFromPin()).isEqualTo(DEFAULT_FROM_PIN);
        assertThat(testOfficeAreaCode.getToPin()).isEqualTo(DEFAULT_TO_PIN);
    }

    @Test
    @Transactional
    public void createOfficeAreaCodeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeAreaCodeRepository.findAll().size();

        // Create the OfficeAreaCode with an existing ID
        officeAreaCode.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeAreaCodeMockMvc.perform(post("/api/office-area-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeAreaCode)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeAreaCode in the database
        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeAreaCodeRepository.findAll().size();
        // set the field null
        officeAreaCode.setOfficeId(null);

        // Create the OfficeAreaCode, which fails.

        restOfficeAreaCodeMockMvc.perform(post("/api/office-area-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeAreaCode)))
            .andExpect(status().isBadRequest());

        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFromPinIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeAreaCodeRepository.findAll().size();
        // set the field null
        officeAreaCode.setFromPin(null);

        // Create the OfficeAreaCode, which fails.

        restOfficeAreaCodeMockMvc.perform(post("/api/office-area-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeAreaCode)))
            .andExpect(status().isBadRequest());

        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfficeAreaCodes() throws Exception {
        // Initialize the database
        officeAreaCodeRepository.saveAndFlush(officeAreaCode);

        // Get all the officeAreaCodeList
        restOfficeAreaCodeMockMvc.perform(get("/api/office-area-codes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeAreaCode.getId().intValue())))
            .andExpect(jsonPath("$.[*].officeId").value(hasItem(DEFAULT_OFFICE_ID)))
            .andExpect(jsonPath("$.[*].fromPin").value(hasItem(DEFAULT_FROM_PIN.toString())))
            .andExpect(jsonPath("$.[*].toPin").value(hasItem(DEFAULT_TO_PIN.toString())));
    }
    
    @Test
    @Transactional
    public void getOfficeAreaCode() throws Exception {
        // Initialize the database
        officeAreaCodeRepository.saveAndFlush(officeAreaCode);

        // Get the officeAreaCode
        restOfficeAreaCodeMockMvc.perform(get("/api/office-area-codes/{id}", officeAreaCode.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(officeAreaCode.getId().intValue()))
            .andExpect(jsonPath("$.officeId").value(DEFAULT_OFFICE_ID))
            .andExpect(jsonPath("$.fromPin").value(DEFAULT_FROM_PIN.toString()))
            .andExpect(jsonPath("$.toPin").value(DEFAULT_TO_PIN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfficeAreaCode() throws Exception {
        // Get the officeAreaCode
        restOfficeAreaCodeMockMvc.perform(get("/api/office-area-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfficeAreaCode() throws Exception {
        // Initialize the database
        officeAreaCodeRepository.saveAndFlush(officeAreaCode);

        int databaseSizeBeforeUpdate = officeAreaCodeRepository.findAll().size();

        // Update the officeAreaCode
        OfficeAreaCode updatedOfficeAreaCode = officeAreaCodeRepository.findById(officeAreaCode.getId()).get();
        // Disconnect from session so that the updates on updatedOfficeAreaCode are not directly saved in db
        em.detach(updatedOfficeAreaCode);
        updatedOfficeAreaCode
            .officeId(UPDATED_OFFICE_ID)
            .fromPin(UPDATED_FROM_PIN)
            .toPin(UPDATED_TO_PIN);

        restOfficeAreaCodeMockMvc.perform(put("/api/office-area-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfficeAreaCode)))
            .andExpect(status().isOk());

        // Validate the OfficeAreaCode in the database
        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeUpdate);
        OfficeAreaCode testOfficeAreaCode = officeAreaCodeList.get(officeAreaCodeList.size() - 1);
        assertThat(testOfficeAreaCode.getOfficeId()).isEqualTo(UPDATED_OFFICE_ID);
        assertThat(testOfficeAreaCode.getFromPin()).isEqualTo(UPDATED_FROM_PIN);
        assertThat(testOfficeAreaCode.getToPin()).isEqualTo(UPDATED_TO_PIN);
    }

    @Test
    @Transactional
    public void updateNonExistingOfficeAreaCode() throws Exception {
        int databaseSizeBeforeUpdate = officeAreaCodeRepository.findAll().size();

        // Create the OfficeAreaCode

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeAreaCodeMockMvc.perform(put("/api/office-area-codes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeAreaCode)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeAreaCode in the database
        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfficeAreaCode() throws Exception {
        // Initialize the database
        officeAreaCodeRepository.saveAndFlush(officeAreaCode);

        int databaseSizeBeforeDelete = officeAreaCodeRepository.findAll().size();

        // Delete the officeAreaCode
        restOfficeAreaCodeMockMvc.perform(delete("/api/office-area-codes/{id}", officeAreaCode.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfficeAreaCode> officeAreaCodeList = officeAreaCodeRepository.findAll();
        assertThat(officeAreaCodeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeAreaCode.class);
        OfficeAreaCode officeAreaCode1 = new OfficeAreaCode();
        officeAreaCode1.setId(1L);
        OfficeAreaCode officeAreaCode2 = new OfficeAreaCode();
        officeAreaCode2.setId(officeAreaCode1.getId());
        assertThat(officeAreaCode1).isEqualTo(officeAreaCode2);
        officeAreaCode2.setId(2L);
        assertThat(officeAreaCode1).isNotEqualTo(officeAreaCode2);
        officeAreaCode1.setId(null);
        assertThat(officeAreaCode1).isNotEqualTo(officeAreaCode2);
    }
}
