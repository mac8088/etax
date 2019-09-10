package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesGroupProp;
import net.atos.etax.repository.StdCodesGroupPropRepository;
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
        final StdCodesGroupPropResource stdCodesGroupPropResource = new StdCodesGroupPropResource(stdCodesGroupPropRepository);
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
    public void getNonExistingStdCodesGroupProp() throws Exception {
        // Get the stdCodesGroupProp
        restStdCodesGroupPropMockMvc.perform(get("/api/std-codes-group-props/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesGroupProp() throws Exception {
        // Initialize the database
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

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
        stdCodesGroupPropRepository.saveAndFlush(stdCodesGroupProp);

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
