package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodes;
import net.atos.etax.repository.StdCodesRepository;
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
        final StdCodesResource stdCodesResource = new StdCodesResource(stdCodesRepository);
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
    public void getNonExistingStdCodes() throws Exception {
        // Get the stdCodes
        restStdCodesMockMvc.perform(get("/api/std-codes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodes() throws Exception {
        // Initialize the database
        stdCodesRepository.saveAndFlush(stdCodes);

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
        stdCodesRepository.saveAndFlush(stdCodes);

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
