package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.OfficeRelationship;
import net.atos.etax.repository.OfficeRelationshipRepository;
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
 * Integration tests for the {@Link OfficeRelationshipResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class OfficeRelationshipResourceIT {

    private static final Integer DEFAULT_PARENT_ID = 1;
    private static final Integer UPDATED_PARENT_ID = 2;

    private static final Integer DEFAULT_CHILE_ID = 1;
    private static final Integer UPDATED_CHILE_ID = 2;

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_CC_VERSION = 1;
    private static final Integer UPDATED_CC_VERSION = 2;

    @Autowired
    private OfficeRelationshipRepository officeRelationshipRepository;

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

    private MockMvc restOfficeRelationshipMockMvc;

    private OfficeRelationship officeRelationship;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficeRelationshipResource officeRelationshipResource = new OfficeRelationshipResource(officeRelationshipRepository);
        this.restOfficeRelationshipMockMvc = MockMvcBuilders.standaloneSetup(officeRelationshipResource)
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
    public static OfficeRelationship createEntity(EntityManager em) {
        OfficeRelationship officeRelationship = new OfficeRelationship()
            .parentId(DEFAULT_PARENT_ID)
            .chileId(DEFAULT_CHILE_ID)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .ccVersion(DEFAULT_CC_VERSION);
        return officeRelationship;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OfficeRelationship createUpdatedEntity(EntityManager em) {
        OfficeRelationship officeRelationship = new OfficeRelationship()
            .parentId(UPDATED_PARENT_ID)
            .chileId(UPDATED_CHILE_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .ccVersion(UPDATED_CC_VERSION);
        return officeRelationship;
    }

    @BeforeEach
    public void initTest() {
        officeRelationship = createEntity(em);
    }

    @Test
    @Transactional
    public void createOfficeRelationship() throws Exception {
        int databaseSizeBeforeCreate = officeRelationshipRepository.findAll().size();

        // Create the OfficeRelationship
        restOfficeRelationshipMockMvc.perform(post("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeRelationship)))
            .andExpect(status().isCreated());

        // Validate the OfficeRelationship in the database
        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeCreate + 1);
        OfficeRelationship testOfficeRelationship = officeRelationshipList.get(officeRelationshipList.size() - 1);
        assertThat(testOfficeRelationship.getParentId()).isEqualTo(DEFAULT_PARENT_ID);
        assertThat(testOfficeRelationship.getChileId()).isEqualTo(DEFAULT_CHILE_ID);
        assertThat(testOfficeRelationship.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testOfficeRelationship.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testOfficeRelationship.getCcVersion()).isEqualTo(DEFAULT_CC_VERSION);
    }

    @Test
    @Transactional
    public void createOfficeRelationshipWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officeRelationshipRepository.findAll().size();

        // Create the OfficeRelationship with an existing ID
        officeRelationship.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficeRelationshipMockMvc.perform(post("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeRelationship in the database
        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkParentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRelationshipRepository.findAll().size();
        // set the field null
        officeRelationship.setParentId(null);

        // Create the OfficeRelationship, which fails.

        restOfficeRelationshipMockMvc.perform(post("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeRelationship)))
            .andExpect(status().isBadRequest());

        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkChileIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRelationshipRepository.findAll().size();
        // set the field null
        officeRelationship.setChileId(null);

        // Create the OfficeRelationship, which fails.

        restOfficeRelationshipMockMvc.perform(post("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeRelationship)))
            .andExpect(status().isBadRequest());

        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkStartDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = officeRelationshipRepository.findAll().size();
        // set the field null
        officeRelationship.setStartDate(null);

        // Create the OfficeRelationship, which fails.

        restOfficeRelationshipMockMvc.perform(post("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeRelationship)))
            .andExpect(status().isBadRequest());

        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllOfficeRelationships() throws Exception {
        // Initialize the database
        officeRelationshipRepository.saveAndFlush(officeRelationship);

        // Get all the officeRelationshipList
        restOfficeRelationshipMockMvc.perform(get("/api/office-relationships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(officeRelationship.getId().intValue())))
            .andExpect(jsonPath("$.[*].parentId").value(hasItem(DEFAULT_PARENT_ID)))
            .andExpect(jsonPath("$.[*].chileId").value(hasItem(DEFAULT_CHILE_ID)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));
    }
    
    @Test
    @Transactional
    public void getOfficeRelationship() throws Exception {
        // Initialize the database
        officeRelationshipRepository.saveAndFlush(officeRelationship);

        // Get the officeRelationship
        restOfficeRelationshipMockMvc.perform(get("/api/office-relationships/{id}", officeRelationship.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(officeRelationship.getId().intValue()))
            .andExpect(jsonPath("$.parentId").value(DEFAULT_PARENT_ID))
            .andExpect(jsonPath("$.chileId").value(DEFAULT_CHILE_ID))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.ccVersion").value(DEFAULT_CC_VERSION));
    }

    @Test
    @Transactional
    public void getNonExistingOfficeRelationship() throws Exception {
        // Get the officeRelationship
        restOfficeRelationshipMockMvc.perform(get("/api/office-relationships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOfficeRelationship() throws Exception {
        // Initialize the database
        officeRelationshipRepository.saveAndFlush(officeRelationship);

        int databaseSizeBeforeUpdate = officeRelationshipRepository.findAll().size();

        // Update the officeRelationship
        OfficeRelationship updatedOfficeRelationship = officeRelationshipRepository.findById(officeRelationship.getId()).get();
        // Disconnect from session so that the updates on updatedOfficeRelationship are not directly saved in db
        em.detach(updatedOfficeRelationship);
        updatedOfficeRelationship
            .parentId(UPDATED_PARENT_ID)
            .chileId(UPDATED_CHILE_ID)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .ccVersion(UPDATED_CC_VERSION);

        restOfficeRelationshipMockMvc.perform(put("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOfficeRelationship)))
            .andExpect(status().isOk());

        // Validate the OfficeRelationship in the database
        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeUpdate);
        OfficeRelationship testOfficeRelationship = officeRelationshipList.get(officeRelationshipList.size() - 1);
        assertThat(testOfficeRelationship.getParentId()).isEqualTo(UPDATED_PARENT_ID);
        assertThat(testOfficeRelationship.getChileId()).isEqualTo(UPDATED_CHILE_ID);
        assertThat(testOfficeRelationship.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testOfficeRelationship.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testOfficeRelationship.getCcVersion()).isEqualTo(UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingOfficeRelationship() throws Exception {
        int databaseSizeBeforeUpdate = officeRelationshipRepository.findAll().size();

        // Create the OfficeRelationship

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficeRelationshipMockMvc.perform(put("/api/office-relationships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(officeRelationship)))
            .andExpect(status().isBadRequest());

        // Validate the OfficeRelationship in the database
        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOfficeRelationship() throws Exception {
        // Initialize the database
        officeRelationshipRepository.saveAndFlush(officeRelationship);

        int databaseSizeBeforeDelete = officeRelationshipRepository.findAll().size();

        // Delete the officeRelationship
        restOfficeRelationshipMockMvc.perform(delete("/api/office-relationships/{id}", officeRelationship.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OfficeRelationship> officeRelationshipList = officeRelationshipRepository.findAll();
        assertThat(officeRelationshipList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OfficeRelationship.class);
        OfficeRelationship officeRelationship1 = new OfficeRelationship();
        officeRelationship1.setId(1L);
        OfficeRelationship officeRelationship2 = new OfficeRelationship();
        officeRelationship2.setId(officeRelationship1.getId());
        assertThat(officeRelationship1).isEqualTo(officeRelationship2);
        officeRelationship2.setId(2L);
        assertThat(officeRelationship1).isNotEqualTo(officeRelationship2);
        officeRelationship1.setId(null);
        assertThat(officeRelationship1).isNotEqualTo(officeRelationship2);
    }
}
