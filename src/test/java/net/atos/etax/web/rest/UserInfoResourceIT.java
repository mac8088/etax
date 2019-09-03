package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.UserInfo;
import net.atos.etax.repository.UserInfoRepository;
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
 * Integration tests for the {@Link UserInfoResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class UserInfoResourceIT {

    private static final String DEFAULT_CSTD_ADM_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_ADM_SECTION = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_SECURITY_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_SECURITY_LEVEL = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_USER_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_MIDDLE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MIDDLE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_GENDER = "AAAAAAAAAA";
    private static final String UPDATED_GENDER = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUM = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUM = "BBBBBBBBBB";

    private static final String DEFAULT_FAX_NUM = "AAAAAAAAAA";
    private static final String UPDATED_FAX_NUM = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_EFFIECTIVE_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EFFIECTIVE_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_EXPIRY_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_EXPIRY_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_BLOCKED = false;
    private static final Boolean UPDATED_BLOCKED = true;

    private static final String DEFAULT_BLOCKED_REASON = "AAAAAAAAAA";
    private static final String UPDATED_BLOCKED_REASON = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FORCED_PWD_CHANGE = false;
    private static final Boolean UPDATED_FORCED_PWD_CHANGE = true;

    private static final String DEFAULT_CSTD_TITLES = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_TITLES = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_ADM_DIVSISON = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_ADM_DIVSISON = "BBBBBBBBBB";

    private static final String DEFAULT_LOGIN_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_LOGIN_STATUS = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_LOGIN_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LOGIN_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Integer DEFAULT_ATTEMPT = 3;
    private static final Integer UPDATED_ATTEMPT = 2;

    private static final Boolean DEFAULT_NEED_APPROVE = false;
    private static final Boolean UPDATED_NEED_APPROVE = true;

    private static final ZonedDateTime DEFAULT_LOGOUT_TIME = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_LOGOUT_TIME = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final String DEFAULT_NATIONAL_ID = "AAAAAAAAAA";
    private static final String UPDATED_NATIONAL_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_ORGANIZATION_GRADE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_ORGANIZATION_GRADE = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_EMPLOYMENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_EMPLOYMENT_TYPE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_MANU_SCRIPT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MANU_SCRIPT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MANU_SCRIPT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MANU_SCRIPT_CONTENT_TYPE = "image/png";

    private static final Integer DEFAULT_CC_VERSION = 1;
    private static final Integer UPDATED_CC_VERSION = 2;

    @Autowired
    private UserInfoRepository userInfoRepository;

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

    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserInfoResource userInfoResource = new UserInfoResource(userInfoRepository);
        this.restUserInfoMockMvc = MockMvcBuilders.standaloneSetup(userInfoResource)
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
    public static UserInfo createEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .cstdAdmSection(DEFAULT_CSTD_ADM_SECTION)
            .cstdSecurityLevel(DEFAULT_CSTD_SECURITY_LEVEL)
            .cstdUserType(DEFAULT_CSTD_USER_TYPE)
            .description(DEFAULT_DESCRIPTION)
            .middleName(DEFAULT_MIDDLE_NAME)
            .gender(DEFAULT_GENDER)
            .phoneNum(DEFAULT_PHONE_NUM)
            .faxNum(DEFAULT_FAX_NUM)
            .effiectiveDate(DEFAULT_EFFIECTIVE_DATE)
            .expiryDate(DEFAULT_EXPIRY_DATE)
            .blocked(DEFAULT_BLOCKED)
            .blockedReason(DEFAULT_BLOCKED_REASON)
            .forcedPwdChange(DEFAULT_FORCED_PWD_CHANGE)
            .cstdTitles(DEFAULT_CSTD_TITLES)
            .cstdStatus(DEFAULT_CSTD_STATUS)
            .cstdAdmDivsison(DEFAULT_CSTD_ADM_DIVSISON)
            .loginStatus(DEFAULT_LOGIN_STATUS)
            .loginTime(DEFAULT_LOGIN_TIME)
            .attempt(DEFAULT_ATTEMPT)
            .needApprove(DEFAULT_NEED_APPROVE)
            .logoutTime(DEFAULT_LOGOUT_TIME)
            .nationalId(DEFAULT_NATIONAL_ID)
            .cstdOrganizationGrade(DEFAULT_CSTD_ORGANIZATION_GRADE)
            .cstdEmploymentType(DEFAULT_CSTD_EMPLOYMENT_TYPE)
            .manuScript(DEFAULT_MANU_SCRIPT)
            .manuScriptContentType(DEFAULT_MANU_SCRIPT_CONTENT_TYPE)
            .ccVersion(DEFAULT_CC_VERSION);
        return userInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserInfo createUpdatedEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .cstdAdmSection(UPDATED_CSTD_ADM_SECTION)
            .cstdSecurityLevel(UPDATED_CSTD_SECURITY_LEVEL)
            .cstdUserType(UPDATED_CSTD_USER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .middleName(UPDATED_MIDDLE_NAME)
            .gender(UPDATED_GENDER)
            .phoneNum(UPDATED_PHONE_NUM)
            .faxNum(UPDATED_FAX_NUM)
            .effiectiveDate(UPDATED_EFFIECTIVE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .blocked(UPDATED_BLOCKED)
            .blockedReason(UPDATED_BLOCKED_REASON)
            .forcedPwdChange(UPDATED_FORCED_PWD_CHANGE)
            .cstdTitles(UPDATED_CSTD_TITLES)
            .cstdStatus(UPDATED_CSTD_STATUS)
            .cstdAdmDivsison(UPDATED_CSTD_ADM_DIVSISON)
            .loginStatus(UPDATED_LOGIN_STATUS)
            .loginTime(UPDATED_LOGIN_TIME)
            .attempt(UPDATED_ATTEMPT)
            .needApprove(UPDATED_NEED_APPROVE)
            .logoutTime(UPDATED_LOGOUT_TIME)
            .nationalId(UPDATED_NATIONAL_ID)
            .cstdOrganizationGrade(UPDATED_CSTD_ORGANIZATION_GRADE)
            .cstdEmploymentType(UPDATED_CSTD_EMPLOYMENT_TYPE)
            .manuScript(UPDATED_MANU_SCRIPT)
            .manuScriptContentType(UPDATED_MANU_SCRIPT_CONTENT_TYPE)
            .ccVersion(UPDATED_CC_VERSION);
        return userInfo;
    }

    @BeforeEach
    public void initTest() {
        userInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getCstdAdmSection()).isEqualTo(DEFAULT_CSTD_ADM_SECTION);
        assertThat(testUserInfo.getCstdSecurityLevel()).isEqualTo(DEFAULT_CSTD_SECURITY_LEVEL);
        assertThat(testUserInfo.getCstdUserType()).isEqualTo(DEFAULT_CSTD_USER_TYPE);
        assertThat(testUserInfo.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testUserInfo.getMiddleName()).isEqualTo(DEFAULT_MIDDLE_NAME);
        assertThat(testUserInfo.getGender()).isEqualTo(DEFAULT_GENDER);
        assertThat(testUserInfo.getPhoneNum()).isEqualTo(DEFAULT_PHONE_NUM);
        assertThat(testUserInfo.getFaxNum()).isEqualTo(DEFAULT_FAX_NUM);
        assertThat(testUserInfo.getEffiectiveDate()).isEqualTo(DEFAULT_EFFIECTIVE_DATE);
        assertThat(testUserInfo.getExpiryDate()).isEqualTo(DEFAULT_EXPIRY_DATE);
        assertThat(testUserInfo.isBlocked()).isEqualTo(DEFAULT_BLOCKED);
        assertThat(testUserInfo.getBlockedReason()).isEqualTo(DEFAULT_BLOCKED_REASON);
        assertThat(testUserInfo.isForcedPwdChange()).isEqualTo(DEFAULT_FORCED_PWD_CHANGE);
        assertThat(testUserInfo.getCstdTitles()).isEqualTo(DEFAULT_CSTD_TITLES);
        assertThat(testUserInfo.getCstdStatus()).isEqualTo(DEFAULT_CSTD_STATUS);
        assertThat(testUserInfo.getCstdAdmDivsison()).isEqualTo(DEFAULT_CSTD_ADM_DIVSISON);
        assertThat(testUserInfo.getLoginStatus()).isEqualTo(DEFAULT_LOGIN_STATUS);
        assertThat(testUserInfo.getLoginTime()).isEqualTo(DEFAULT_LOGIN_TIME);
        assertThat(testUserInfo.getAttempt()).isEqualTo(DEFAULT_ATTEMPT);
        assertThat(testUserInfo.isNeedApprove()).isEqualTo(DEFAULT_NEED_APPROVE);
        assertThat(testUserInfo.getLogoutTime()).isEqualTo(DEFAULT_LOGOUT_TIME);
        assertThat(testUserInfo.getNationalId()).isEqualTo(DEFAULT_NATIONAL_ID);
        assertThat(testUserInfo.getCstdOrganizationGrade()).isEqualTo(DEFAULT_CSTD_ORGANIZATION_GRADE);
        assertThat(testUserInfo.getCstdEmploymentType()).isEqualTo(DEFAULT_CSTD_EMPLOYMENT_TYPE);
        assertThat(testUserInfo.getManuScript()).isEqualTo(DEFAULT_MANU_SCRIPT);
        assertThat(testUserInfo.getManuScriptContentType()).isEqualTo(DEFAULT_MANU_SCRIPT_CONTENT_TYPE);
        assertThat(testUserInfo.getCcVersion()).isEqualTo(DEFAULT_CC_VERSION);
    }

    @Test
    @Transactional
    public void createUserInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo with an existing ID
        userInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkCstdAdmSectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setCstdAdmSection(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdSecurityLevelIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setCstdSecurityLevel(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setCstdUserType(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkGenderIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setGender(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBlockedIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setBlocked(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBlockedReasonIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setBlockedReason(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkForcedPwdChangeIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setForcedPwdChange(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdTitlesIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setCstdTitles(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdStatusIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setCstdStatus(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdAdmDivsisonIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setCstdAdmDivsison(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAttemptIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setAttempt(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNeedApproveIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setNeedApprove(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNationalIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = userInfoRepository.findAll().size();
        // set the field null
        userInfo.setNationalId(null);

        // Create the UserInfo, which fails.

        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdAdmSection").value(hasItem(DEFAULT_CSTD_ADM_SECTION.toString())))
            .andExpect(jsonPath("$.[*].cstdSecurityLevel").value(hasItem(DEFAULT_CSTD_SECURITY_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].cstdUserType").value(hasItem(DEFAULT_CSTD_USER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME.toString())))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER.toString())))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM.toString())))
            .andExpect(jsonPath("$.[*].faxNum").value(hasItem(DEFAULT_FAX_NUM.toString())))
            .andExpect(jsonPath("$.[*].effiectiveDate").value(hasItem(sameInstant(DEFAULT_EFFIECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].blocked").value(hasItem(DEFAULT_BLOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].blockedReason").value(hasItem(DEFAULT_BLOCKED_REASON.toString())))
            .andExpect(jsonPath("$.[*].forcedPwdChange").value(hasItem(DEFAULT_FORCED_PWD_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].cstdTitles").value(hasItem(DEFAULT_CSTD_TITLES.toString())))
            .andExpect(jsonPath("$.[*].cstdStatus").value(hasItem(DEFAULT_CSTD_STATUS.toString())))
            .andExpect(jsonPath("$.[*].cstdAdmDivsison").value(hasItem(DEFAULT_CSTD_ADM_DIVSISON.toString())))
            .andExpect(jsonPath("$.[*].loginStatus").value(hasItem(DEFAULT_LOGIN_STATUS.toString())))
            .andExpect(jsonPath("$.[*].loginTime").value(hasItem(sameInstant(DEFAULT_LOGIN_TIME))))
            .andExpect(jsonPath("$.[*].attempt").value(hasItem(DEFAULT_ATTEMPT)))
            .andExpect(jsonPath("$.[*].needApprove").value(hasItem(DEFAULT_NEED_APPROVE.booleanValue())))
            .andExpect(jsonPath("$.[*].logoutTime").value(hasItem(sameInstant(DEFAULT_LOGOUT_TIME))))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID.toString())))
            .andExpect(jsonPath("$.[*].cstdOrganizationGrade").value(hasItem(DEFAULT_CSTD_ORGANIZATION_GRADE.toString())))
            .andExpect(jsonPath("$.[*].cstdEmploymentType").value(hasItem(DEFAULT_CSTD_EMPLOYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].manuScriptContentType").value(hasItem(DEFAULT_MANU_SCRIPT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].manuScript").value(hasItem(Base64Utils.encodeToString(DEFAULT_MANU_SCRIPT))))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));
    }
    
    @Test
    @Transactional
    public void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().intValue()))
            .andExpect(jsonPath("$.cstdAdmSection").value(DEFAULT_CSTD_ADM_SECTION.toString()))
            .andExpect(jsonPath("$.cstdSecurityLevel").value(DEFAULT_CSTD_SECURITY_LEVEL.toString()))
            .andExpect(jsonPath("$.cstdUserType").value(DEFAULT_CSTD_USER_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.middleName").value(DEFAULT_MIDDLE_NAME.toString()))
            .andExpect(jsonPath("$.gender").value(DEFAULT_GENDER.toString()))
            .andExpect(jsonPath("$.phoneNum").value(DEFAULT_PHONE_NUM.toString()))
            .andExpect(jsonPath("$.faxNum").value(DEFAULT_FAX_NUM.toString()))
            .andExpect(jsonPath("$.effiectiveDate").value(sameInstant(DEFAULT_EFFIECTIVE_DATE)))
            .andExpect(jsonPath("$.expiryDate").value(sameInstant(DEFAULT_EXPIRY_DATE)))
            .andExpect(jsonPath("$.blocked").value(DEFAULT_BLOCKED.booleanValue()))
            .andExpect(jsonPath("$.blockedReason").value(DEFAULT_BLOCKED_REASON.toString()))
            .andExpect(jsonPath("$.forcedPwdChange").value(DEFAULT_FORCED_PWD_CHANGE.booleanValue()))
            .andExpect(jsonPath("$.cstdTitles").value(DEFAULT_CSTD_TITLES.toString()))
            .andExpect(jsonPath("$.cstdStatus").value(DEFAULT_CSTD_STATUS.toString()))
            .andExpect(jsonPath("$.cstdAdmDivsison").value(DEFAULT_CSTD_ADM_DIVSISON.toString()))
            .andExpect(jsonPath("$.loginStatus").value(DEFAULT_LOGIN_STATUS.toString()))
            .andExpect(jsonPath("$.loginTime").value(sameInstant(DEFAULT_LOGIN_TIME)))
            .andExpect(jsonPath("$.attempt").value(DEFAULT_ATTEMPT))
            .andExpect(jsonPath("$.needApprove").value(DEFAULT_NEED_APPROVE.booleanValue()))
            .andExpect(jsonPath("$.logoutTime").value(sameInstant(DEFAULT_LOGOUT_TIME)))
            .andExpect(jsonPath("$.nationalId").value(DEFAULT_NATIONAL_ID.toString()))
            .andExpect(jsonPath("$.cstdOrganizationGrade").value(DEFAULT_CSTD_ORGANIZATION_GRADE.toString()))
            .andExpect(jsonPath("$.cstdEmploymentType").value(DEFAULT_CSTD_EMPLOYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.manuScriptContentType").value(DEFAULT_MANU_SCRIPT_CONTENT_TYPE))
            .andExpect(jsonPath("$.manuScript").value(Base64Utils.encodeToString(DEFAULT_MANU_SCRIPT)))
            .andExpect(jsonPath("$.ccVersion").value(DEFAULT_CC_VERSION));
    }

    @Test
    @Transactional
    public void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findById(userInfo.getId()).get();
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo
            .cstdAdmSection(UPDATED_CSTD_ADM_SECTION)
            .cstdSecurityLevel(UPDATED_CSTD_SECURITY_LEVEL)
            .cstdUserType(UPDATED_CSTD_USER_TYPE)
            .description(UPDATED_DESCRIPTION)
            .middleName(UPDATED_MIDDLE_NAME)
            .gender(UPDATED_GENDER)
            .phoneNum(UPDATED_PHONE_NUM)
            .faxNum(UPDATED_FAX_NUM)
            .effiectiveDate(UPDATED_EFFIECTIVE_DATE)
            .expiryDate(UPDATED_EXPIRY_DATE)
            .blocked(UPDATED_BLOCKED)
            .blockedReason(UPDATED_BLOCKED_REASON)
            .forcedPwdChange(UPDATED_FORCED_PWD_CHANGE)
            .cstdTitles(UPDATED_CSTD_TITLES)
            .cstdStatus(UPDATED_CSTD_STATUS)
            .cstdAdmDivsison(UPDATED_CSTD_ADM_DIVSISON)
            .loginStatus(UPDATED_LOGIN_STATUS)
            .loginTime(UPDATED_LOGIN_TIME)
            .attempt(UPDATED_ATTEMPT)
            .needApprove(UPDATED_NEED_APPROVE)
            .logoutTime(UPDATED_LOGOUT_TIME)
            .nationalId(UPDATED_NATIONAL_ID)
            .cstdOrganizationGrade(UPDATED_CSTD_ORGANIZATION_GRADE)
            .cstdEmploymentType(UPDATED_CSTD_EMPLOYMENT_TYPE)
            .manuScript(UPDATED_MANU_SCRIPT)
            .manuScriptContentType(UPDATED_MANU_SCRIPT_CONTENT_TYPE)
            .ccVersion(UPDATED_CC_VERSION);

        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserInfo)))
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getCstdAdmSection()).isEqualTo(UPDATED_CSTD_ADM_SECTION);
        assertThat(testUserInfo.getCstdSecurityLevel()).isEqualTo(UPDATED_CSTD_SECURITY_LEVEL);
        assertThat(testUserInfo.getCstdUserType()).isEqualTo(UPDATED_CSTD_USER_TYPE);
        assertThat(testUserInfo.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testUserInfo.getMiddleName()).isEqualTo(UPDATED_MIDDLE_NAME);
        assertThat(testUserInfo.getGender()).isEqualTo(UPDATED_GENDER);
        assertThat(testUserInfo.getPhoneNum()).isEqualTo(UPDATED_PHONE_NUM);
        assertThat(testUserInfo.getFaxNum()).isEqualTo(UPDATED_FAX_NUM);
        assertThat(testUserInfo.getEffiectiveDate()).isEqualTo(UPDATED_EFFIECTIVE_DATE);
        assertThat(testUserInfo.getExpiryDate()).isEqualTo(UPDATED_EXPIRY_DATE);
        assertThat(testUserInfo.isBlocked()).isEqualTo(UPDATED_BLOCKED);
        assertThat(testUserInfo.getBlockedReason()).isEqualTo(UPDATED_BLOCKED_REASON);
        assertThat(testUserInfo.isForcedPwdChange()).isEqualTo(UPDATED_FORCED_PWD_CHANGE);
        assertThat(testUserInfo.getCstdTitles()).isEqualTo(UPDATED_CSTD_TITLES);
        assertThat(testUserInfo.getCstdStatus()).isEqualTo(UPDATED_CSTD_STATUS);
        assertThat(testUserInfo.getCstdAdmDivsison()).isEqualTo(UPDATED_CSTD_ADM_DIVSISON);
        assertThat(testUserInfo.getLoginStatus()).isEqualTo(UPDATED_LOGIN_STATUS);
        assertThat(testUserInfo.getLoginTime()).isEqualTo(UPDATED_LOGIN_TIME);
        assertThat(testUserInfo.getAttempt()).isEqualTo(UPDATED_ATTEMPT);
        assertThat(testUserInfo.isNeedApprove()).isEqualTo(UPDATED_NEED_APPROVE);
        assertThat(testUserInfo.getLogoutTime()).isEqualTo(UPDATED_LOGOUT_TIME);
        assertThat(testUserInfo.getNationalId()).isEqualTo(UPDATED_NATIONAL_ID);
        assertThat(testUserInfo.getCstdOrganizationGrade()).isEqualTo(UPDATED_CSTD_ORGANIZATION_GRADE);
        assertThat(testUserInfo.getCstdEmploymentType()).isEqualTo(UPDATED_CSTD_EMPLOYMENT_TYPE);
        assertThat(testUserInfo.getManuScript()).isEqualTo(UPDATED_MANU_SCRIPT);
        assertThat(testUserInfo.getManuScriptContentType()).isEqualTo(UPDATED_MANU_SCRIPT_CONTENT_TYPE);
        assertThat(testUserInfo.getCcVersion()).isEqualTo(UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Create the UserInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfo)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Delete the userInfo
        restUserInfoMockMvc.perform(delete("/api/user-infos/{id}", userInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfo.class);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(1L);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setId(userInfo1.getId());
        assertThat(userInfo1).isEqualTo(userInfo2);
        userInfo2.setId(2L);
        assertThat(userInfo1).isNotEqualTo(userInfo2);
        userInfo1.setId(null);
        assertThat(userInfo1).isNotEqualTo(userInfo2);
    }
}
