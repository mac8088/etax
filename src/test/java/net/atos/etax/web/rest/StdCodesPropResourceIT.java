package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesProp;
import net.atos.etax.repository.StdCodesPropRepository;
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
        final StdCodesPropResource stdCodesPropResource = new StdCodesPropResource(stdCodesPropRepository);
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
    public void getNonExistingStdCodesProp() throws Exception {
        // Get the stdCodesProp
        restStdCodesPropMockMvc.perform(get("/api/std-codes-props/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesProp() throws Exception {
        // Initialize the database
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

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
        stdCodesPropRepository.saveAndFlush(stdCodesProp);

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
