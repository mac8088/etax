package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesDesc;
import net.atos.etax.repository.StdCodesDescRepository;
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
        final StdCodesDescResource stdCodesDescResource = new StdCodesDescResource(stdCodesDescRepository);
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
    public void getNonExistingStdCodesDesc() throws Exception {
        // Get the stdCodesDesc
        restStdCodesDescMockMvc.perform(get("/api/std-codes-descs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesDesc() throws Exception {
        // Initialize the database
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

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
        stdCodesDescRepository.saveAndFlush(stdCodesDesc);

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
