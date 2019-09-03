package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesGroup;
import net.atos.etax.repository.StdCodesGroupRepository;
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

import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.ValueTypeIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
import net.atos.etax.domain.enumeration.OptionIndicator;
/**
 * Integration tests for the {@Link StdCodesGroupResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class StdCodesGroupResourceIT {

    private static final String DEFAULT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_GROUP_DESC = "AAAAAAAAAA";
    private static final String UPDATED_GROUP_DESC = "BBBBBBBBBB";

    private static final OptionIndicator DEFAULT_SYSTEM_IND = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_SYSTEM_IND = OptionIndicator.N;

    private static final OptionIndicator DEFAULT_SEC_LEVEL_REQUIRED = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_SEC_LEVEL_REQUIRED = OptionIndicator.N;

    private static final OptionIndicator DEFAULT_VALUE_REQUIRED = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_VALUE_REQUIRED = OptionIndicator.N;

    private static final ValueTypeIndicator DEFAULT_VALUE_TYPE = ValueTypeIndicator.D;
    private static final ValueTypeIndicator UPDATED_VALUE_TYPE = ValueTypeIndicator.S;

    private static final OptionIndicator DEFAULT_DESCRIPTION_REQUIRED = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_DESCRIPTION_REQUIRED = OptionIndicator.N;

    private static final OptionIndicator DEFAULT_EXTERNAL_CODE_REQUIRED = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_EXTERNAL_CODE_REQUIRED = OptionIndicator.N;

    private static final Integer DEFAULT_EXTERNAL_CODE_LENGTH = 1;
    private static final Integer UPDATED_EXTERNAL_CODE_LENGTH = 2;

    private static final OptionIndicator DEFAULT_PARENT_GROUP_REQUIRED = OptionIndicator.Y;
    private static final OptionIndicator UPDATED_PARENT_GROUP_REQUIRED = OptionIndicator.N;

    private static final String DEFAULT_PARENT_GROUP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PARENT_GROUP_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_TOUPPERCASE = false;
    private static final Boolean UPDATED_TOUPPERCASE = true;

    @Autowired
    private StdCodesGroupRepository stdCodesGroupRepository;

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

    private MockMvc restStdCodesGroupMockMvc;

    private StdCodesGroup stdCodesGroup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final StdCodesGroupResource stdCodesGroupResource = new StdCodesGroupResource(stdCodesGroupRepository);
        this.restStdCodesGroupMockMvc = MockMvcBuilders.standaloneSetup(stdCodesGroupResource)
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
    public static StdCodesGroup createEntity(EntityManager em) {
        StdCodesGroup stdCodesGroup = new StdCodesGroup()
            .groupCode(DEFAULT_GROUP_CODE)
            .groupDesc(DEFAULT_GROUP_DESC)
            .systemInd(DEFAULT_SYSTEM_IND)
            .secLevelRequired(DEFAULT_SEC_LEVEL_REQUIRED)
            .valueRequired(DEFAULT_VALUE_REQUIRED)
            .valueType(DEFAULT_VALUE_TYPE)
            .descriptionRequired(DEFAULT_DESCRIPTION_REQUIRED)
            .externalCodeRequired(DEFAULT_EXTERNAL_CODE_REQUIRED)
            .externalCodeLength(DEFAULT_EXTERNAL_CODE_LENGTH)
            .parentGroupRequired(DEFAULT_PARENT_GROUP_REQUIRED)
            .parentGroupCode(DEFAULT_PARENT_GROUP_CODE)
            .touppercase(DEFAULT_TOUPPERCASE);
        return stdCodesGroup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static StdCodesGroup createUpdatedEntity(EntityManager em) {
        StdCodesGroup stdCodesGroup = new StdCodesGroup()
            .groupCode(UPDATED_GROUP_CODE)
            .groupDesc(UPDATED_GROUP_DESC)
            .systemInd(UPDATED_SYSTEM_IND)
            .secLevelRequired(UPDATED_SEC_LEVEL_REQUIRED)
            .valueRequired(UPDATED_VALUE_REQUIRED)
            .valueType(UPDATED_VALUE_TYPE)
            .descriptionRequired(UPDATED_DESCRIPTION_REQUIRED)
            .externalCodeRequired(UPDATED_EXTERNAL_CODE_REQUIRED)
            .externalCodeLength(UPDATED_EXTERNAL_CODE_LENGTH)
            .parentGroupRequired(UPDATED_PARENT_GROUP_REQUIRED)
            .parentGroupCode(UPDATED_PARENT_GROUP_CODE)
            .touppercase(UPDATED_TOUPPERCASE);
        return stdCodesGroup;
    }

    @BeforeEach
    public void initTest() {
        stdCodesGroup = createEntity(em);
    }

    @Test
    @Transactional
    public void createStdCodesGroup() throws Exception {
        int databaseSizeBeforeCreate = stdCodesGroupRepository.findAll().size();

        // Create the StdCodesGroup
        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isCreated());

        // Validate the StdCodesGroup in the database
        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeCreate + 1);
        StdCodesGroup testStdCodesGroup = stdCodesGroupList.get(stdCodesGroupList.size() - 1);
        assertThat(testStdCodesGroup.getGroupCode()).isEqualTo(DEFAULT_GROUP_CODE);
        assertThat(testStdCodesGroup.getGroupDesc()).isEqualTo(DEFAULT_GROUP_DESC);
        assertThat(testStdCodesGroup.getSystemInd()).isEqualTo(DEFAULT_SYSTEM_IND);
        assertThat(testStdCodesGroup.getSecLevelRequired()).isEqualTo(DEFAULT_SEC_LEVEL_REQUIRED);
        assertThat(testStdCodesGroup.getValueRequired()).isEqualTo(DEFAULT_VALUE_REQUIRED);
        assertThat(testStdCodesGroup.getValueType()).isEqualTo(DEFAULT_VALUE_TYPE);
        assertThat(testStdCodesGroup.getDescriptionRequired()).isEqualTo(DEFAULT_DESCRIPTION_REQUIRED);
        assertThat(testStdCodesGroup.getExternalCodeRequired()).isEqualTo(DEFAULT_EXTERNAL_CODE_REQUIRED);
        assertThat(testStdCodesGroup.getExternalCodeLength()).isEqualTo(DEFAULT_EXTERNAL_CODE_LENGTH);
        assertThat(testStdCodesGroup.getParentGroupRequired()).isEqualTo(DEFAULT_PARENT_GROUP_REQUIRED);
        assertThat(testStdCodesGroup.getParentGroupCode()).isEqualTo(DEFAULT_PARENT_GROUP_CODE);
        assertThat(testStdCodesGroup.isTouppercase()).isEqualTo(DEFAULT_TOUPPERCASE);
    }

    @Test
    @Transactional
    public void createStdCodesGroupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = stdCodesGroupRepository.findAll().size();

        // Create the StdCodesGroup with an existing ID
        stdCodesGroup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesGroup in the database
        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkGroupCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setGroupCode(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGroupDescIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setGroupDesc(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSystemIndIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setSystemInd(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkSecLevelRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setSecLevelRequired(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValueRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setValueRequired(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDescriptionRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setDescriptionRequired(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkParentGroupRequiredIsRequired() throws Exception {
        int databaseSizeBeforeTest = stdCodesGroupRepository.findAll().size();
        // set the field null
        stdCodesGroup.setParentGroupRequired(null);

        // Create the StdCodesGroup, which fails.

        restStdCodesGroupMockMvc.perform(post("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroups() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].groupDesc").value(hasItem(DEFAULT_GROUP_DESC.toString())))
            .andExpect(jsonPath("$.[*].systemInd").value(hasItem(DEFAULT_SYSTEM_IND.toString())))
            .andExpect(jsonPath("$.[*].secLevelRequired").value(hasItem(DEFAULT_SEC_LEVEL_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].valueRequired").value(hasItem(DEFAULT_VALUE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].descriptionRequired").value(hasItem(DEFAULT_DESCRIPTION_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].externalCodeRequired").value(hasItem(DEFAULT_EXTERNAL_CODE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].externalCodeLength").value(hasItem(DEFAULT_EXTERNAL_CODE_LENGTH)))
            .andExpect(jsonPath("$.[*].parentGroupRequired").value(hasItem(DEFAULT_PARENT_GROUP_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].parentGroupCode").value(hasItem(DEFAULT_PARENT_GROUP_CODE.toString())))
            .andExpect(jsonPath("$.[*].touppercase").value(hasItem(DEFAULT_TOUPPERCASE.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getStdCodesGroup() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get the stdCodesGroup
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups/{id}", stdCodesGroup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(stdCodesGroup.getId().intValue()))
            .andExpect(jsonPath("$.groupCode").value(DEFAULT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.groupDesc").value(DEFAULT_GROUP_DESC.toString()))
            .andExpect(jsonPath("$.systemInd").value(DEFAULT_SYSTEM_IND.toString()))
            .andExpect(jsonPath("$.secLevelRequired").value(DEFAULT_SEC_LEVEL_REQUIRED.toString()))
            .andExpect(jsonPath("$.valueRequired").value(DEFAULT_VALUE_REQUIRED.toString()))
            .andExpect(jsonPath("$.valueType").value(DEFAULT_VALUE_TYPE.toString()))
            .andExpect(jsonPath("$.descriptionRequired").value(DEFAULT_DESCRIPTION_REQUIRED.toString()))
            .andExpect(jsonPath("$.externalCodeRequired").value(DEFAULT_EXTERNAL_CODE_REQUIRED.toString()))
            .andExpect(jsonPath("$.externalCodeLength").value(DEFAULT_EXTERNAL_CODE_LENGTH))
            .andExpect(jsonPath("$.parentGroupRequired").value(DEFAULT_PARENT_GROUP_REQUIRED.toString()))
            .andExpect(jsonPath("$.parentGroupCode").value(DEFAULT_PARENT_GROUP_CODE.toString()))
            .andExpect(jsonPath("$.touppercase").value(DEFAULT_TOUPPERCASE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingStdCodesGroup() throws Exception {
        // Get the stdCodesGroup
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateStdCodesGroup() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        int databaseSizeBeforeUpdate = stdCodesGroupRepository.findAll().size();

        // Update the stdCodesGroup
        StdCodesGroup updatedStdCodesGroup = stdCodesGroupRepository.findById(stdCodesGroup.getId()).get();
        // Disconnect from session so that the updates on updatedStdCodesGroup are not directly saved in db
        em.detach(updatedStdCodesGroup);
        updatedStdCodesGroup
            .groupCode(UPDATED_GROUP_CODE)
            .groupDesc(UPDATED_GROUP_DESC)
            .systemInd(UPDATED_SYSTEM_IND)
            .secLevelRequired(UPDATED_SEC_LEVEL_REQUIRED)
            .valueRequired(UPDATED_VALUE_REQUIRED)
            .valueType(UPDATED_VALUE_TYPE)
            .descriptionRequired(UPDATED_DESCRIPTION_REQUIRED)
            .externalCodeRequired(UPDATED_EXTERNAL_CODE_REQUIRED)
            .externalCodeLength(UPDATED_EXTERNAL_CODE_LENGTH)
            .parentGroupRequired(UPDATED_PARENT_GROUP_REQUIRED)
            .parentGroupCode(UPDATED_PARENT_GROUP_CODE)
            .touppercase(UPDATED_TOUPPERCASE);

        restStdCodesGroupMockMvc.perform(put("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedStdCodesGroup)))
            .andExpect(status().isOk());

        // Validate the StdCodesGroup in the database
        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeUpdate);
        StdCodesGroup testStdCodesGroup = stdCodesGroupList.get(stdCodesGroupList.size() - 1);
        assertThat(testStdCodesGroup.getGroupCode()).isEqualTo(UPDATED_GROUP_CODE);
        assertThat(testStdCodesGroup.getGroupDesc()).isEqualTo(UPDATED_GROUP_DESC);
        assertThat(testStdCodesGroup.getSystemInd()).isEqualTo(UPDATED_SYSTEM_IND);
        assertThat(testStdCodesGroup.getSecLevelRequired()).isEqualTo(UPDATED_SEC_LEVEL_REQUIRED);
        assertThat(testStdCodesGroup.getValueRequired()).isEqualTo(UPDATED_VALUE_REQUIRED);
        assertThat(testStdCodesGroup.getValueType()).isEqualTo(UPDATED_VALUE_TYPE);
        assertThat(testStdCodesGroup.getDescriptionRequired()).isEqualTo(UPDATED_DESCRIPTION_REQUIRED);
        assertThat(testStdCodesGroup.getExternalCodeRequired()).isEqualTo(UPDATED_EXTERNAL_CODE_REQUIRED);
        assertThat(testStdCodesGroup.getExternalCodeLength()).isEqualTo(UPDATED_EXTERNAL_CODE_LENGTH);
        assertThat(testStdCodesGroup.getParentGroupRequired()).isEqualTo(UPDATED_PARENT_GROUP_REQUIRED);
        assertThat(testStdCodesGroup.getParentGroupCode()).isEqualTo(UPDATED_PARENT_GROUP_CODE);
        assertThat(testStdCodesGroup.isTouppercase()).isEqualTo(UPDATED_TOUPPERCASE);
    }

    @Test
    @Transactional
    public void updateNonExistingStdCodesGroup() throws Exception {
        int databaseSizeBeforeUpdate = stdCodesGroupRepository.findAll().size();

        // Create the StdCodesGroup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStdCodesGroupMockMvc.perform(put("/api/std-codes-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(stdCodesGroup)))
            .andExpect(status().isBadRequest());

        // Validate the StdCodesGroup in the database
        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteStdCodesGroup() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        int databaseSizeBeforeDelete = stdCodesGroupRepository.findAll().size();

        // Delete the stdCodesGroup
        restStdCodesGroupMockMvc.perform(delete("/api/std-codes-groups/{id}", stdCodesGroup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<StdCodesGroup> stdCodesGroupList = stdCodesGroupRepository.findAll();
        assertThat(stdCodesGroupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(StdCodesGroup.class);
        StdCodesGroup stdCodesGroup1 = new StdCodesGroup();
        stdCodesGroup1.setId(1L);
        StdCodesGroup stdCodesGroup2 = new StdCodesGroup();
        stdCodesGroup2.setId(stdCodesGroup1.getId());
        assertThat(stdCodesGroup1).isEqualTo(stdCodesGroup2);
        stdCodesGroup2.setId(2L);
        assertThat(stdCodesGroup1).isNotEqualTo(stdCodesGroup2);
        stdCodesGroup1.setId(null);
        assertThat(stdCodesGroup1).isNotEqualTo(stdCodesGroup2);
    }
}
