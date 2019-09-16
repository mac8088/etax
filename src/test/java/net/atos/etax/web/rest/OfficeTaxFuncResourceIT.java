package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.OfficeTaxFunc;
import net.atos.etax.repository.OfficeTaxFuncRepository;
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
 * Integration tests for the {@Link OfficeTaxFuncResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class OfficeTaxFuncResourceIT {

    private static final Integer DEFAULT_TAX_OFFICE_ID = 1;
    private static final Integer UPDATED_TAX_OFFICE_ID = 2;

    private static final Integer DEFAULT_FUNC_OFFICE_ID = 1;
    private static final Integer UPDATED_FUNC_OFFICE_ID = 2;

    private static final LocalDate DEFAULT_START_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_START_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_END_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_END_DATE = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private OfficeTaxFuncRepository officeTaxFuncRepository;

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

    private MockMvc restOfficeTaxFuncMockMvc;

    private OfficeTaxFunc officeTaxFunc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeTaxFuncResource officeTaxFuncResource = new OfficeTaxFuncResource(officeTaxFuncRepository);
        this.restOfficeTaxFuncMockMvc = MockMvcBuilders.standaloneSetup(officeTaxFuncResource)
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
    public static OfficeTaxFunc createEntity(EntityManager em) {
        OfficeTaxFunc officeTaxFunc = new OfficeTaxFunc()
            .taxOfficeId(DEFAULT_TAX_OFFICE_ID)
            .funcOfficeId(DEFAULT_FUNC_OFFICE_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE);
        return officeTaxFunc;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeTaxFunc createUpdatedEntity(EntityManager em) {
        OfficeTaxFunc officeTaxFunc = new OfficeTaxFunc()
            .taxOfficeId(UPDATED_TAX_OFFICE_ID)
            .funcOfficeId(UPDATED_FUNC_OFFICE_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);
        return officeTaxFunc;
    }

    @BeforeEach
    public void initTest() {
        officeTaxFunc = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfficeTaxFunc() throws Exception {
        int databaseSizeBeforeCreate = officeTaxFuncRepository.findAll().size();

        // Create the OfficeTaxFunc
        restOfficeTaxFuncMockMvc.perform(post("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeTaxFunc)))
            .andExpect(status().isCreated());

        // Validate the OfficeTaxFunc in the database
        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeCreate + 1);
        OfficeTaxFunc testOfficeTaxFunc = officeTaxFuncList.get(officeTaxFuncList.size() - 1);
        assertThat(testOfficeTaxFunc.getTaxOfficeId()).isEqualTo(DEFAULT_TAX_OFFICE_ID);
        assertThat(testOfficeTaxFunc.getFuncOfficeId()).isEqualTo(DEFAULT_FUNC_OFFICE_ID);
        assertThat(testOfficeTaxFunc.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOfficeTaxFunc.getEndDate()).isEqualTo(DEFAULT_END_DATE);
    }

    @Test
    @Transactional
    public void createOfficeTaxFuncWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeTaxFuncRepository.findAll().size();

        // Create the OfficeTaxFunc with an existing ID
        officeTaxFunc.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeTaxFuncMockMvc.perform(post("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeTaxFunc)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeTaxFunc in the database
        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkTaxOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeTaxFuncRepository.findAll().size();
        // set the field null
        officeTaxFunc.setTaxOfficeId(null);

        // Create the OfficeTaxFunc, which fails.

        restOfficeTaxFuncMockMvc.perform(post("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeTaxFunc)))
            .andExpect(status().isBadRequest());

        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFuncOfficeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeTaxFuncRepository.findAll().size();
        // set the field null
        officeTaxFunc.setFuncOfficeId(null);

        // Create the OfficeTaxFunc, which fails.

        restOfficeTaxFuncMockMvc.perform(post("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeTaxFunc)))
            .andExpect(status().isBadRequest());

        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeTaxFuncRepository.findAll().size();
        // set the field null
        officeTaxFunc.setStartDate(null);

        // Create the OfficeTaxFunc, which fails.

        restOfficeTaxFuncMockMvc.perform(post("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeTaxFunc)))
            .andExpect(status().isBadRequest());

        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfficeTaxFuncs() throws Exception {
        // Initialize the database
        officeTaxFuncRepository.saveAndFlush(officeTaxFunc);

        // Get all the officeTaxFuncList
        restOfficeTaxFuncMockMvc.perform(get("/api/office-tax-funcs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeTaxFunc.getId().intValue())))
            .andExpect(jsonPath("$.[*].taxOfficeId").value(hasItem(DEFAULT_TAX_OFFICE_ID)))
            .andExpect(jsonPath("$.[*].funcOfficeId").value(hasItem(DEFAULT_FUNC_OFFICE_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(DEFAULT_START_DATE.toString())))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(DEFAULT_END_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getOfficeTaxFunc() throws Exception {
        // Initialize the database
        officeTaxFuncRepository.saveAndFlush(officeTaxFunc);

        // Get the officeTaxFunc
        restOfficeTaxFuncMockMvc.perform(get("/api/office-tax-funcs/{id}", officeTaxFunc.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(officeTaxFunc.getId().intValue()))
            .andExpect(jsonPath("$.taxOfficeId").value(DEFAULT_TAX_OFFICE_ID))
            .andExpect(jsonPath("$.funcOfficeId").value(DEFAULT_FUNC_OFFICE_ID))
            .andExpect(jsonPath("$.startDate").value(DEFAULT_START_DATE.toString()))
            .andExpect(jsonPath("$.endDate").value(DEFAULT_END_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingOfficeTaxFunc() throws Exception {
        // Get the officeTaxFunc
        restOfficeTaxFuncMockMvc.perform(get("/api/office-tax-funcs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfficeTaxFunc() throws Exception {
        // Initialize the database
        officeTaxFuncRepository.saveAndFlush(officeTaxFunc);

        int databaseSizeBeforeUpdate = officeTaxFuncRepository.findAll().size();

        // Update the officeTaxFunc
        OfficeTaxFunc updatedOfficeTaxFunc = officeTaxFuncRepository.findById(officeTaxFunc.getId()).get();
        // Disconnect from session so that the updates on updatedOfficeTaxFunc are not directly saved in db
        em.detach(updatedOfficeTaxFunc);
        updatedOfficeTaxFunc
            .taxOfficeId(UPDATED_TAX_OFFICE_ID)
            .funcOfficeId(UPDATED_FUNC_OFFICE_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE);

        restOfficeTaxFuncMockMvc.perform(put("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfficeTaxFunc)))
            .andExpect(status().isOk());

        // Validate the OfficeTaxFunc in the database
        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeUpdate);
        OfficeTaxFunc testOfficeTaxFunc = officeTaxFuncList.get(officeTaxFuncList.size() - 1);
        assertThat(testOfficeTaxFunc.getTaxOfficeId()).isEqualTo(UPDATED_TAX_OFFICE_ID);
        assertThat(testOfficeTaxFunc.getFuncOfficeId()).isEqualTo(UPDATED_FUNC_OFFICE_ID);
        assertThat(testOfficeTaxFunc.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOfficeTaxFunc.getEndDate()).isEqualTo(UPDATED_END_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingOfficeTaxFunc() throws Exception {
        int databaseSizeBeforeUpdate = officeTaxFuncRepository.findAll().size();

        // Create the OfficeTaxFunc

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeTaxFuncMockMvc.perform(put("/api/office-tax-funcs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeTaxFunc)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeTaxFunc in the database
        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfficeTaxFunc() throws Exception {
        // Initialize the database
        officeTaxFuncRepository.saveAndFlush(officeTaxFunc);

        int databaseSizeBeforeDelete = officeTaxFuncRepository.findAll().size();

        // Delete the officeTaxFunc
        restOfficeTaxFuncMockMvc.perform(delete("/api/office-tax-funcs/{id}", officeTaxFunc.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfficeTaxFunc> officeTaxFuncList = officeTaxFuncRepository.findAll();
        assertThat(officeTaxFuncList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeTaxFunc.class);
        OfficeTaxFunc officeTaxFunc1 = new OfficeTaxFunc();
        officeTaxFunc1.setId(1L);
        OfficeTaxFunc officeTaxFunc2 = new OfficeTaxFunc();
        officeTaxFunc2.setId(officeTaxFunc1.getId());
        assertThat(officeTaxFunc1).isEqualTo(officeTaxFunc2);
        officeTaxFunc2.setId(2L);
        assertThat(officeTaxFunc1).isNotEqualTo(officeTaxFunc2);
        officeTaxFunc1.setId(null);
        assertThat(officeTaxFunc1).isNotEqualTo(officeTaxFunc2);
    }
}
