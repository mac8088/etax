package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.StdCodesGroup;
import net.atos.etax.domain.StdCodes;
import net.atos.etax.repository.StdCodesGroupRepository;
import net.atos.etax.service.StdCodesGroupService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.StdCodesGroupCriteria;
import net.atos.etax.service.StdCodesGroupQueryService;

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
    private StdCodesGroupService stdCodesGroupService;

    @Autowired
    private StdCodesGroupQueryService stdCodesGroupQueryService;

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
        final StdCodesGroupResource stdCodesGroupResource = new StdCodesGroupResource(stdCodesGroupService, stdCodesGroupQueryService);
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
    public void getAllStdCodesGroupsByGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where groupCode equals to DEFAULT_GROUP_CODE
        defaultStdCodesGroupShouldBeFound("groupCode.equals=" + DEFAULT_GROUP_CODE);

        // Get all the stdCodesGroupList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesGroupShouldNotBeFound("groupCode.equals=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where groupCode in DEFAULT_GROUP_CODE or UPDATED_GROUP_CODE
        defaultStdCodesGroupShouldBeFound("groupCode.in=" + DEFAULT_GROUP_CODE + "," + UPDATED_GROUP_CODE);

        // Get all the stdCodesGroupList where groupCode equals to UPDATED_GROUP_CODE
        defaultStdCodesGroupShouldNotBeFound("groupCode.in=" + UPDATED_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where groupCode is not null
        defaultStdCodesGroupShouldBeFound("groupCode.specified=true");

        // Get all the stdCodesGroupList where groupCode is null
        defaultStdCodesGroupShouldNotBeFound("groupCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByGroupDescIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where groupDesc equals to DEFAULT_GROUP_DESC
        defaultStdCodesGroupShouldBeFound("groupDesc.equals=" + DEFAULT_GROUP_DESC);

        // Get all the stdCodesGroupList where groupDesc equals to UPDATED_GROUP_DESC
        defaultStdCodesGroupShouldNotBeFound("groupDesc.equals=" + UPDATED_GROUP_DESC);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByGroupDescIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where groupDesc in DEFAULT_GROUP_DESC or UPDATED_GROUP_DESC
        defaultStdCodesGroupShouldBeFound("groupDesc.in=" + DEFAULT_GROUP_DESC + "," + UPDATED_GROUP_DESC);

        // Get all the stdCodesGroupList where groupDesc equals to UPDATED_GROUP_DESC
        defaultStdCodesGroupShouldNotBeFound("groupDesc.in=" + UPDATED_GROUP_DESC);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByGroupDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where groupDesc is not null
        defaultStdCodesGroupShouldBeFound("groupDesc.specified=true");

        // Get all the stdCodesGroupList where groupDesc is null
        defaultStdCodesGroupShouldNotBeFound("groupDesc.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsBySystemIndIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where systemInd equals to DEFAULT_SYSTEM_IND
        defaultStdCodesGroupShouldBeFound("systemInd.equals=" + DEFAULT_SYSTEM_IND);

        // Get all the stdCodesGroupList where systemInd equals to UPDATED_SYSTEM_IND
        defaultStdCodesGroupShouldNotBeFound("systemInd.equals=" + UPDATED_SYSTEM_IND);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsBySystemIndIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where systemInd in DEFAULT_SYSTEM_IND or UPDATED_SYSTEM_IND
        defaultStdCodesGroupShouldBeFound("systemInd.in=" + DEFAULT_SYSTEM_IND + "," + UPDATED_SYSTEM_IND);

        // Get all the stdCodesGroupList where systemInd equals to UPDATED_SYSTEM_IND
        defaultStdCodesGroupShouldNotBeFound("systemInd.in=" + UPDATED_SYSTEM_IND);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsBySystemIndIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where systemInd is not null
        defaultStdCodesGroupShouldBeFound("systemInd.specified=true");

        // Get all the stdCodesGroupList where systemInd is null
        defaultStdCodesGroupShouldNotBeFound("systemInd.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsBySecLevelRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where secLevelRequired equals to DEFAULT_SEC_LEVEL_REQUIRED
        defaultStdCodesGroupShouldBeFound("secLevelRequired.equals=" + DEFAULT_SEC_LEVEL_REQUIRED);

        // Get all the stdCodesGroupList where secLevelRequired equals to UPDATED_SEC_LEVEL_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("secLevelRequired.equals=" + UPDATED_SEC_LEVEL_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsBySecLevelRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where secLevelRequired in DEFAULT_SEC_LEVEL_REQUIRED or UPDATED_SEC_LEVEL_REQUIRED
        defaultStdCodesGroupShouldBeFound("secLevelRequired.in=" + DEFAULT_SEC_LEVEL_REQUIRED + "," + UPDATED_SEC_LEVEL_REQUIRED);

        // Get all the stdCodesGroupList where secLevelRequired equals to UPDATED_SEC_LEVEL_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("secLevelRequired.in=" + UPDATED_SEC_LEVEL_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsBySecLevelRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where secLevelRequired is not null
        defaultStdCodesGroupShouldBeFound("secLevelRequired.specified=true");

        // Get all the stdCodesGroupList where secLevelRequired is null
        defaultStdCodesGroupShouldNotBeFound("secLevelRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByValueRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where valueRequired equals to DEFAULT_VALUE_REQUIRED
        defaultStdCodesGroupShouldBeFound("valueRequired.equals=" + DEFAULT_VALUE_REQUIRED);

        // Get all the stdCodesGroupList where valueRequired equals to UPDATED_VALUE_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("valueRequired.equals=" + UPDATED_VALUE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByValueRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where valueRequired in DEFAULT_VALUE_REQUIRED or UPDATED_VALUE_REQUIRED
        defaultStdCodesGroupShouldBeFound("valueRequired.in=" + DEFAULT_VALUE_REQUIRED + "," + UPDATED_VALUE_REQUIRED);

        // Get all the stdCodesGroupList where valueRequired equals to UPDATED_VALUE_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("valueRequired.in=" + UPDATED_VALUE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByValueRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where valueRequired is not null
        defaultStdCodesGroupShouldBeFound("valueRequired.specified=true");

        // Get all the stdCodesGroupList where valueRequired is null
        defaultStdCodesGroupShouldNotBeFound("valueRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByValueTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where valueType equals to DEFAULT_VALUE_TYPE
        defaultStdCodesGroupShouldBeFound("valueType.equals=" + DEFAULT_VALUE_TYPE);

        // Get all the stdCodesGroupList where valueType equals to UPDATED_VALUE_TYPE
        defaultStdCodesGroupShouldNotBeFound("valueType.equals=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByValueTypeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where valueType in DEFAULT_VALUE_TYPE or UPDATED_VALUE_TYPE
        defaultStdCodesGroupShouldBeFound("valueType.in=" + DEFAULT_VALUE_TYPE + "," + UPDATED_VALUE_TYPE);

        // Get all the stdCodesGroupList where valueType equals to UPDATED_VALUE_TYPE
        defaultStdCodesGroupShouldNotBeFound("valueType.in=" + UPDATED_VALUE_TYPE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByValueTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where valueType is not null
        defaultStdCodesGroupShouldBeFound("valueType.specified=true");

        // Get all the stdCodesGroupList where valueType is null
        defaultStdCodesGroupShouldNotBeFound("valueType.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByDescriptionRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where descriptionRequired equals to DEFAULT_DESCRIPTION_REQUIRED
        defaultStdCodesGroupShouldBeFound("descriptionRequired.equals=" + DEFAULT_DESCRIPTION_REQUIRED);

        // Get all the stdCodesGroupList where descriptionRequired equals to UPDATED_DESCRIPTION_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("descriptionRequired.equals=" + UPDATED_DESCRIPTION_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByDescriptionRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where descriptionRequired in DEFAULT_DESCRIPTION_REQUIRED or UPDATED_DESCRIPTION_REQUIRED
        defaultStdCodesGroupShouldBeFound("descriptionRequired.in=" + DEFAULT_DESCRIPTION_REQUIRED + "," + UPDATED_DESCRIPTION_REQUIRED);

        // Get all the stdCodesGroupList where descriptionRequired equals to UPDATED_DESCRIPTION_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("descriptionRequired.in=" + UPDATED_DESCRIPTION_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByDescriptionRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where descriptionRequired is not null
        defaultStdCodesGroupShouldBeFound("descriptionRequired.specified=true");

        // Get all the stdCodesGroupList where descriptionRequired is null
        defaultStdCodesGroupShouldNotBeFound("descriptionRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeRequired equals to DEFAULT_EXTERNAL_CODE_REQUIRED
        defaultStdCodesGroupShouldBeFound("externalCodeRequired.equals=" + DEFAULT_EXTERNAL_CODE_REQUIRED);

        // Get all the stdCodesGroupList where externalCodeRequired equals to UPDATED_EXTERNAL_CODE_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("externalCodeRequired.equals=" + UPDATED_EXTERNAL_CODE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeRequired in DEFAULT_EXTERNAL_CODE_REQUIRED or UPDATED_EXTERNAL_CODE_REQUIRED
        defaultStdCodesGroupShouldBeFound("externalCodeRequired.in=" + DEFAULT_EXTERNAL_CODE_REQUIRED + "," + UPDATED_EXTERNAL_CODE_REQUIRED);

        // Get all the stdCodesGroupList where externalCodeRequired equals to UPDATED_EXTERNAL_CODE_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("externalCodeRequired.in=" + UPDATED_EXTERNAL_CODE_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeRequired is not null
        defaultStdCodesGroupShouldBeFound("externalCodeRequired.specified=true");

        // Get all the stdCodesGroupList where externalCodeRequired is null
        defaultStdCodesGroupShouldNotBeFound("externalCodeRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeLengthIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeLength equals to DEFAULT_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldBeFound("externalCodeLength.equals=" + DEFAULT_EXTERNAL_CODE_LENGTH);

        // Get all the stdCodesGroupList where externalCodeLength equals to UPDATED_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldNotBeFound("externalCodeLength.equals=" + UPDATED_EXTERNAL_CODE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeLengthIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeLength in DEFAULT_EXTERNAL_CODE_LENGTH or UPDATED_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldBeFound("externalCodeLength.in=" + DEFAULT_EXTERNAL_CODE_LENGTH + "," + UPDATED_EXTERNAL_CODE_LENGTH);

        // Get all the stdCodesGroupList where externalCodeLength equals to UPDATED_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldNotBeFound("externalCodeLength.in=" + UPDATED_EXTERNAL_CODE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeLengthIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeLength is not null
        defaultStdCodesGroupShouldBeFound("externalCodeLength.specified=true");

        // Get all the stdCodesGroupList where externalCodeLength is null
        defaultStdCodesGroupShouldNotBeFound("externalCodeLength.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeLengthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeLength greater than or equals to DEFAULT_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldBeFound("externalCodeLength.greaterOrEqualThan=" + DEFAULT_EXTERNAL_CODE_LENGTH);

        // Get all the stdCodesGroupList where externalCodeLength greater than or equals to UPDATED_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldNotBeFound("externalCodeLength.greaterOrEqualThan=" + UPDATED_EXTERNAL_CODE_LENGTH);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByExternalCodeLengthIsLessThanSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where externalCodeLength less than or equals to DEFAULT_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldNotBeFound("externalCodeLength.lessThan=" + DEFAULT_EXTERNAL_CODE_LENGTH);

        // Get all the stdCodesGroupList where externalCodeLength less than or equals to UPDATED_EXTERNAL_CODE_LENGTH
        defaultStdCodesGroupShouldBeFound("externalCodeLength.lessThan=" + UPDATED_EXTERNAL_CODE_LENGTH);
    }


    @Test
    @Transactional
    public void getAllStdCodesGroupsByParentGroupRequiredIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where parentGroupRequired equals to DEFAULT_PARENT_GROUP_REQUIRED
        defaultStdCodesGroupShouldBeFound("parentGroupRequired.equals=" + DEFAULT_PARENT_GROUP_REQUIRED);

        // Get all the stdCodesGroupList where parentGroupRequired equals to UPDATED_PARENT_GROUP_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("parentGroupRequired.equals=" + UPDATED_PARENT_GROUP_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByParentGroupRequiredIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where parentGroupRequired in DEFAULT_PARENT_GROUP_REQUIRED or UPDATED_PARENT_GROUP_REQUIRED
        defaultStdCodesGroupShouldBeFound("parentGroupRequired.in=" + DEFAULT_PARENT_GROUP_REQUIRED + "," + UPDATED_PARENT_GROUP_REQUIRED);

        // Get all the stdCodesGroupList where parentGroupRequired equals to UPDATED_PARENT_GROUP_REQUIRED
        defaultStdCodesGroupShouldNotBeFound("parentGroupRequired.in=" + UPDATED_PARENT_GROUP_REQUIRED);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByParentGroupRequiredIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where parentGroupRequired is not null
        defaultStdCodesGroupShouldBeFound("parentGroupRequired.specified=true");

        // Get all the stdCodesGroupList where parentGroupRequired is null
        defaultStdCodesGroupShouldNotBeFound("parentGroupRequired.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByParentGroupCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where parentGroupCode equals to DEFAULT_PARENT_GROUP_CODE
        defaultStdCodesGroupShouldBeFound("parentGroupCode.equals=" + DEFAULT_PARENT_GROUP_CODE);

        // Get all the stdCodesGroupList where parentGroupCode equals to UPDATED_PARENT_GROUP_CODE
        defaultStdCodesGroupShouldNotBeFound("parentGroupCode.equals=" + UPDATED_PARENT_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByParentGroupCodeIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where parentGroupCode in DEFAULT_PARENT_GROUP_CODE or UPDATED_PARENT_GROUP_CODE
        defaultStdCodesGroupShouldBeFound("parentGroupCode.in=" + DEFAULT_PARENT_GROUP_CODE + "," + UPDATED_PARENT_GROUP_CODE);

        // Get all the stdCodesGroupList where parentGroupCode equals to UPDATED_PARENT_GROUP_CODE
        defaultStdCodesGroupShouldNotBeFound("parentGroupCode.in=" + UPDATED_PARENT_GROUP_CODE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByParentGroupCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where parentGroupCode is not null
        defaultStdCodesGroupShouldBeFound("parentGroupCode.specified=true");

        // Get all the stdCodesGroupList where parentGroupCode is null
        defaultStdCodesGroupShouldNotBeFound("parentGroupCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByTouppercaseIsEqualToSomething() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where touppercase equals to DEFAULT_TOUPPERCASE
        defaultStdCodesGroupShouldBeFound("touppercase.equals=" + DEFAULT_TOUPPERCASE);

        // Get all the stdCodesGroupList where touppercase equals to UPDATED_TOUPPERCASE
        defaultStdCodesGroupShouldNotBeFound("touppercase.equals=" + UPDATED_TOUPPERCASE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByTouppercaseIsInShouldWork() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where touppercase in DEFAULT_TOUPPERCASE or UPDATED_TOUPPERCASE
        defaultStdCodesGroupShouldBeFound("touppercase.in=" + DEFAULT_TOUPPERCASE + "," + UPDATED_TOUPPERCASE);

        // Get all the stdCodesGroupList where touppercase equals to UPDATED_TOUPPERCASE
        defaultStdCodesGroupShouldNotBeFound("touppercase.in=" + UPDATED_TOUPPERCASE);
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByTouppercaseIsNullOrNotNull() throws Exception {
        // Initialize the database
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);

        // Get all the stdCodesGroupList where touppercase is not null
        defaultStdCodesGroupShouldBeFound("touppercase.specified=true");

        // Get all the stdCodesGroupList where touppercase is null
        defaultStdCodesGroupShouldNotBeFound("touppercase.specified=false");
    }

    @Test
    @Transactional
    public void getAllStdCodesGroupsByStdCodesIsEqualToSomething() throws Exception {
        // Initialize the database
        StdCodes stdCodes = StdCodesResourceIT.createEntity(em);
        em.persist(stdCodes);
        em.flush();
        stdCodesGroup.addStdCodes(stdCodes);
        stdCodesGroupRepository.saveAndFlush(stdCodesGroup);
        Long stdCodesId = stdCodes.getId();

        // Get all the stdCodesGroupList where stdCodes equals to stdCodesId
        defaultStdCodesGroupShouldBeFound("stdCodesId.equals=" + stdCodesId);

        // Get all the stdCodesGroupList where stdCodes equals to stdCodesId + 1
        defaultStdCodesGroupShouldNotBeFound("stdCodesId.equals=" + (stdCodesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStdCodesGroupShouldBeFound(String filter) throws Exception {
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(stdCodesGroup.getId().intValue())))
            .andExpect(jsonPath("$.[*].groupCode").value(hasItem(DEFAULT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].groupDesc").value(hasItem(DEFAULT_GROUP_DESC)))
            .andExpect(jsonPath("$.[*].systemInd").value(hasItem(DEFAULT_SYSTEM_IND.toString())))
            .andExpect(jsonPath("$.[*].secLevelRequired").value(hasItem(DEFAULT_SEC_LEVEL_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].valueRequired").value(hasItem(DEFAULT_VALUE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].valueType").value(hasItem(DEFAULT_VALUE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].descriptionRequired").value(hasItem(DEFAULT_DESCRIPTION_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].externalCodeRequired").value(hasItem(DEFAULT_EXTERNAL_CODE_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].externalCodeLength").value(hasItem(DEFAULT_EXTERNAL_CODE_LENGTH)))
            .andExpect(jsonPath("$.[*].parentGroupRequired").value(hasItem(DEFAULT_PARENT_GROUP_REQUIRED.toString())))
            .andExpect(jsonPath("$.[*].parentGroupCode").value(hasItem(DEFAULT_PARENT_GROUP_CODE)))
            .andExpect(jsonPath("$.[*].touppercase").value(hasItem(DEFAULT_TOUPPERCASE.booleanValue())));

        // Check, that the count call also returns 1
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStdCodesGroupShouldNotBeFound(String filter) throws Exception {
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStdCodesGroupMockMvc.perform(get("/api/std-codes-groups/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        stdCodesGroupService.save(stdCodesGroup);

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
        stdCodesGroupService.save(stdCodesGroup);

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
