package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.UserInfo;
import net.atos.etax.domain.User;
import net.atos.etax.repository.UserInfoRepository;
import net.atos.etax.service.UserInfoService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.UserInfoCriteria;
import net.atos.etax.service.UserInfoQueryService;

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
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoQueryService userInfoQueryService;

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
        final UserInfoResource userInfoResource = new UserInfoResource(userInfoService, userInfoQueryService);
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
    public void getAllUserInfosByCstdAdmSectionIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdAdmSection equals to DEFAULT_CSTD_ADM_SECTION
        defaultUserInfoShouldBeFound("cstdAdmSection.equals=" + DEFAULT_CSTD_ADM_SECTION);

        // Get all the userInfoList where cstdAdmSection equals to UPDATED_CSTD_ADM_SECTION
        defaultUserInfoShouldNotBeFound("cstdAdmSection.equals=" + UPDATED_CSTD_ADM_SECTION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdAdmSectionIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdAdmSection in DEFAULT_CSTD_ADM_SECTION or UPDATED_CSTD_ADM_SECTION
        defaultUserInfoShouldBeFound("cstdAdmSection.in=" + DEFAULT_CSTD_ADM_SECTION + "," + UPDATED_CSTD_ADM_SECTION);

        // Get all the userInfoList where cstdAdmSection equals to UPDATED_CSTD_ADM_SECTION
        defaultUserInfoShouldNotBeFound("cstdAdmSection.in=" + UPDATED_CSTD_ADM_SECTION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdAdmSectionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdAdmSection is not null
        defaultUserInfoShouldBeFound("cstdAdmSection.specified=true");

        // Get all the userInfoList where cstdAdmSection is null
        defaultUserInfoShouldNotBeFound("cstdAdmSection.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdSecurityLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdSecurityLevel equals to DEFAULT_CSTD_SECURITY_LEVEL
        defaultUserInfoShouldBeFound("cstdSecurityLevel.equals=" + DEFAULT_CSTD_SECURITY_LEVEL);

        // Get all the userInfoList where cstdSecurityLevel equals to UPDATED_CSTD_SECURITY_LEVEL
        defaultUserInfoShouldNotBeFound("cstdSecurityLevel.equals=" + UPDATED_CSTD_SECURITY_LEVEL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdSecurityLevelIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdSecurityLevel in DEFAULT_CSTD_SECURITY_LEVEL or UPDATED_CSTD_SECURITY_LEVEL
        defaultUserInfoShouldBeFound("cstdSecurityLevel.in=" + DEFAULT_CSTD_SECURITY_LEVEL + "," + UPDATED_CSTD_SECURITY_LEVEL);

        // Get all the userInfoList where cstdSecurityLevel equals to UPDATED_CSTD_SECURITY_LEVEL
        defaultUserInfoShouldNotBeFound("cstdSecurityLevel.in=" + UPDATED_CSTD_SECURITY_LEVEL);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdSecurityLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdSecurityLevel is not null
        defaultUserInfoShouldBeFound("cstdSecurityLevel.specified=true");

        // Get all the userInfoList where cstdSecurityLevel is null
        defaultUserInfoShouldNotBeFound("cstdSecurityLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdUserTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdUserType equals to DEFAULT_CSTD_USER_TYPE
        defaultUserInfoShouldBeFound("cstdUserType.equals=" + DEFAULT_CSTD_USER_TYPE);

        // Get all the userInfoList where cstdUserType equals to UPDATED_CSTD_USER_TYPE
        defaultUserInfoShouldNotBeFound("cstdUserType.equals=" + UPDATED_CSTD_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdUserTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdUserType in DEFAULT_CSTD_USER_TYPE or UPDATED_CSTD_USER_TYPE
        defaultUserInfoShouldBeFound("cstdUserType.in=" + DEFAULT_CSTD_USER_TYPE + "," + UPDATED_CSTD_USER_TYPE);

        // Get all the userInfoList where cstdUserType equals to UPDATED_CSTD_USER_TYPE
        defaultUserInfoShouldNotBeFound("cstdUserType.in=" + UPDATED_CSTD_USER_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdUserTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdUserType is not null
        defaultUserInfoShouldBeFound("cstdUserType.specified=true");

        // Get all the userInfoList where cstdUserType is null
        defaultUserInfoShouldNotBeFound("cstdUserType.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where description equals to DEFAULT_DESCRIPTION
        defaultUserInfoShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the userInfoList where description equals to UPDATED_DESCRIPTION
        defaultUserInfoShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultUserInfoShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the userInfoList where description equals to UPDATED_DESCRIPTION
        defaultUserInfoShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where description is not null
        defaultUserInfoShouldBeFound("description.specified=true");

        // Get all the userInfoList where description is null
        defaultUserInfoShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByMiddleNameIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where middleName equals to DEFAULT_MIDDLE_NAME
        defaultUserInfoShouldBeFound("middleName.equals=" + DEFAULT_MIDDLE_NAME);

        // Get all the userInfoList where middleName equals to UPDATED_MIDDLE_NAME
        defaultUserInfoShouldNotBeFound("middleName.equals=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMiddleNameIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where middleName in DEFAULT_MIDDLE_NAME or UPDATED_MIDDLE_NAME
        defaultUserInfoShouldBeFound("middleName.in=" + DEFAULT_MIDDLE_NAME + "," + UPDATED_MIDDLE_NAME);

        // Get all the userInfoList where middleName equals to UPDATED_MIDDLE_NAME
        defaultUserInfoShouldNotBeFound("middleName.in=" + UPDATED_MIDDLE_NAME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByMiddleNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where middleName is not null
        defaultUserInfoShouldBeFound("middleName.specified=true");

        // Get all the userInfoList where middleName is null
        defaultUserInfoShouldNotBeFound("middleName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender equals to DEFAULT_GENDER
        defaultUserInfoShouldBeFound("gender.equals=" + DEFAULT_GENDER);

        // Get all the userInfoList where gender equals to UPDATED_GENDER
        defaultUserInfoShouldNotBeFound("gender.equals=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender in DEFAULT_GENDER or UPDATED_GENDER
        defaultUserInfoShouldBeFound("gender.in=" + DEFAULT_GENDER + "," + UPDATED_GENDER);

        // Get all the userInfoList where gender equals to UPDATED_GENDER
        defaultUserInfoShouldNotBeFound("gender.in=" + UPDATED_GENDER);
    }

    @Test
    @Transactional
    public void getAllUserInfosByGenderIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where gender is not null
        defaultUserInfoShouldBeFound("gender.specified=true");

        // Get all the userInfoList where gender is null
        defaultUserInfoShouldNotBeFound("gender.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByPhoneNumIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNum equals to DEFAULT_PHONE_NUM
        defaultUserInfoShouldBeFound("phoneNum.equals=" + DEFAULT_PHONE_NUM);

        // Get all the userInfoList where phoneNum equals to UPDATED_PHONE_NUM
        defaultUserInfoShouldNotBeFound("phoneNum.equals=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllUserInfosByPhoneNumIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNum in DEFAULT_PHONE_NUM or UPDATED_PHONE_NUM
        defaultUserInfoShouldBeFound("phoneNum.in=" + DEFAULT_PHONE_NUM + "," + UPDATED_PHONE_NUM);

        // Get all the userInfoList where phoneNum equals to UPDATED_PHONE_NUM
        defaultUserInfoShouldNotBeFound("phoneNum.in=" + UPDATED_PHONE_NUM);
    }

    @Test
    @Transactional
    public void getAllUserInfosByPhoneNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where phoneNum is not null
        defaultUserInfoShouldBeFound("phoneNum.specified=true");

        // Get all the userInfoList where phoneNum is null
        defaultUserInfoShouldNotBeFound("phoneNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByFaxNumIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where faxNum equals to DEFAULT_FAX_NUM
        defaultUserInfoShouldBeFound("faxNum.equals=" + DEFAULT_FAX_NUM);

        // Get all the userInfoList where faxNum equals to UPDATED_FAX_NUM
        defaultUserInfoShouldNotBeFound("faxNum.equals=" + UPDATED_FAX_NUM);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFaxNumIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where faxNum in DEFAULT_FAX_NUM or UPDATED_FAX_NUM
        defaultUserInfoShouldBeFound("faxNum.in=" + DEFAULT_FAX_NUM + "," + UPDATED_FAX_NUM);

        // Get all the userInfoList where faxNum equals to UPDATED_FAX_NUM
        defaultUserInfoShouldNotBeFound("faxNum.in=" + UPDATED_FAX_NUM);
    }

    @Test
    @Transactional
    public void getAllUserInfosByFaxNumIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where faxNum is not null
        defaultUserInfoShouldBeFound("faxNum.specified=true");

        // Get all the userInfoList where faxNum is null
        defaultUserInfoShouldNotBeFound("faxNum.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByEffiectiveDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where effiectiveDate equals to DEFAULT_EFFIECTIVE_DATE
        defaultUserInfoShouldBeFound("effiectiveDate.equals=" + DEFAULT_EFFIECTIVE_DATE);

        // Get all the userInfoList where effiectiveDate equals to UPDATED_EFFIECTIVE_DATE
        defaultUserInfoShouldNotBeFound("effiectiveDate.equals=" + UPDATED_EFFIECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEffiectiveDateIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where effiectiveDate in DEFAULT_EFFIECTIVE_DATE or UPDATED_EFFIECTIVE_DATE
        defaultUserInfoShouldBeFound("effiectiveDate.in=" + DEFAULT_EFFIECTIVE_DATE + "," + UPDATED_EFFIECTIVE_DATE);

        // Get all the userInfoList where effiectiveDate equals to UPDATED_EFFIECTIVE_DATE
        defaultUserInfoShouldNotBeFound("effiectiveDate.in=" + UPDATED_EFFIECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEffiectiveDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where effiectiveDate is not null
        defaultUserInfoShouldBeFound("effiectiveDate.specified=true");

        // Get all the userInfoList where effiectiveDate is null
        defaultUserInfoShouldNotBeFound("effiectiveDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByEffiectiveDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where effiectiveDate greater than or equals to DEFAULT_EFFIECTIVE_DATE
        defaultUserInfoShouldBeFound("effiectiveDate.greaterOrEqualThan=" + DEFAULT_EFFIECTIVE_DATE);

        // Get all the userInfoList where effiectiveDate greater than or equals to UPDATED_EFFIECTIVE_DATE
        defaultUserInfoShouldNotBeFound("effiectiveDate.greaterOrEqualThan=" + UPDATED_EFFIECTIVE_DATE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByEffiectiveDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where effiectiveDate less than or equals to DEFAULT_EFFIECTIVE_DATE
        defaultUserInfoShouldNotBeFound("effiectiveDate.lessThan=" + DEFAULT_EFFIECTIVE_DATE);

        // Get all the userInfoList where effiectiveDate less than or equals to UPDATED_EFFIECTIVE_DATE
        defaultUserInfoShouldBeFound("effiectiveDate.lessThan=" + UPDATED_EFFIECTIVE_DATE);
    }


    @Test
    @Transactional
    public void getAllUserInfosByExpiryDateIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where expiryDate equals to DEFAULT_EXPIRY_DATE
        defaultUserInfoShouldBeFound("expiryDate.equals=" + DEFAULT_EXPIRY_DATE);

        // Get all the userInfoList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultUserInfoShouldNotBeFound("expiryDate.equals=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByExpiryDateIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where expiryDate in DEFAULT_EXPIRY_DATE or UPDATED_EXPIRY_DATE
        defaultUserInfoShouldBeFound("expiryDate.in=" + DEFAULT_EXPIRY_DATE + "," + UPDATED_EXPIRY_DATE);

        // Get all the userInfoList where expiryDate equals to UPDATED_EXPIRY_DATE
        defaultUserInfoShouldNotBeFound("expiryDate.in=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByExpiryDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where expiryDate is not null
        defaultUserInfoShouldBeFound("expiryDate.specified=true");

        // Get all the userInfoList where expiryDate is null
        defaultUserInfoShouldNotBeFound("expiryDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByExpiryDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where expiryDate greater than or equals to DEFAULT_EXPIRY_DATE
        defaultUserInfoShouldBeFound("expiryDate.greaterOrEqualThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the userInfoList where expiryDate greater than or equals to UPDATED_EXPIRY_DATE
        defaultUserInfoShouldNotBeFound("expiryDate.greaterOrEqualThan=" + UPDATED_EXPIRY_DATE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByExpiryDateIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where expiryDate less than or equals to DEFAULT_EXPIRY_DATE
        defaultUserInfoShouldNotBeFound("expiryDate.lessThan=" + DEFAULT_EXPIRY_DATE);

        // Get all the userInfoList where expiryDate less than or equals to UPDATED_EXPIRY_DATE
        defaultUserInfoShouldBeFound("expiryDate.lessThan=" + UPDATED_EXPIRY_DATE);
    }


    @Test
    @Transactional
    public void getAllUserInfosByBlockedIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where blocked equals to DEFAULT_BLOCKED
        defaultUserInfoShouldBeFound("blocked.equals=" + DEFAULT_BLOCKED);

        // Get all the userInfoList where blocked equals to UPDATED_BLOCKED
        defaultUserInfoShouldNotBeFound("blocked.equals=" + UPDATED_BLOCKED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBlockedIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where blocked in DEFAULT_BLOCKED or UPDATED_BLOCKED
        defaultUserInfoShouldBeFound("blocked.in=" + DEFAULT_BLOCKED + "," + UPDATED_BLOCKED);

        // Get all the userInfoList where blocked equals to UPDATED_BLOCKED
        defaultUserInfoShouldNotBeFound("blocked.in=" + UPDATED_BLOCKED);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBlockedIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where blocked is not null
        defaultUserInfoShouldBeFound("blocked.specified=true");

        // Get all the userInfoList where blocked is null
        defaultUserInfoShouldNotBeFound("blocked.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByBlockedReasonIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where blockedReason equals to DEFAULT_BLOCKED_REASON
        defaultUserInfoShouldBeFound("blockedReason.equals=" + DEFAULT_BLOCKED_REASON);

        // Get all the userInfoList where blockedReason equals to UPDATED_BLOCKED_REASON
        defaultUserInfoShouldNotBeFound("blockedReason.equals=" + UPDATED_BLOCKED_REASON);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBlockedReasonIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where blockedReason in DEFAULT_BLOCKED_REASON or UPDATED_BLOCKED_REASON
        defaultUserInfoShouldBeFound("blockedReason.in=" + DEFAULT_BLOCKED_REASON + "," + UPDATED_BLOCKED_REASON);

        // Get all the userInfoList where blockedReason equals to UPDATED_BLOCKED_REASON
        defaultUserInfoShouldNotBeFound("blockedReason.in=" + UPDATED_BLOCKED_REASON);
    }

    @Test
    @Transactional
    public void getAllUserInfosByBlockedReasonIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where blockedReason is not null
        defaultUserInfoShouldBeFound("blockedReason.specified=true");

        // Get all the userInfoList where blockedReason is null
        defaultUserInfoShouldNotBeFound("blockedReason.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByForcedPwdChangeIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where forcedPwdChange equals to DEFAULT_FORCED_PWD_CHANGE
        defaultUserInfoShouldBeFound("forcedPwdChange.equals=" + DEFAULT_FORCED_PWD_CHANGE);

        // Get all the userInfoList where forcedPwdChange equals to UPDATED_FORCED_PWD_CHANGE
        defaultUserInfoShouldNotBeFound("forcedPwdChange.equals=" + UPDATED_FORCED_PWD_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByForcedPwdChangeIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where forcedPwdChange in DEFAULT_FORCED_PWD_CHANGE or UPDATED_FORCED_PWD_CHANGE
        defaultUserInfoShouldBeFound("forcedPwdChange.in=" + DEFAULT_FORCED_PWD_CHANGE + "," + UPDATED_FORCED_PWD_CHANGE);

        // Get all the userInfoList where forcedPwdChange equals to UPDATED_FORCED_PWD_CHANGE
        defaultUserInfoShouldNotBeFound("forcedPwdChange.in=" + UPDATED_FORCED_PWD_CHANGE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByForcedPwdChangeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where forcedPwdChange is not null
        defaultUserInfoShouldBeFound("forcedPwdChange.specified=true");

        // Get all the userInfoList where forcedPwdChange is null
        defaultUserInfoShouldNotBeFound("forcedPwdChange.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdTitlesIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdTitles equals to DEFAULT_CSTD_TITLES
        defaultUserInfoShouldBeFound("cstdTitles.equals=" + DEFAULT_CSTD_TITLES);

        // Get all the userInfoList where cstdTitles equals to UPDATED_CSTD_TITLES
        defaultUserInfoShouldNotBeFound("cstdTitles.equals=" + UPDATED_CSTD_TITLES);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdTitlesIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdTitles in DEFAULT_CSTD_TITLES or UPDATED_CSTD_TITLES
        defaultUserInfoShouldBeFound("cstdTitles.in=" + DEFAULT_CSTD_TITLES + "," + UPDATED_CSTD_TITLES);

        // Get all the userInfoList where cstdTitles equals to UPDATED_CSTD_TITLES
        defaultUserInfoShouldNotBeFound("cstdTitles.in=" + UPDATED_CSTD_TITLES);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdTitlesIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdTitles is not null
        defaultUserInfoShouldBeFound("cstdTitles.specified=true");

        // Get all the userInfoList where cstdTitles is null
        defaultUserInfoShouldNotBeFound("cstdTitles.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdStatus equals to DEFAULT_CSTD_STATUS
        defaultUserInfoShouldBeFound("cstdStatus.equals=" + DEFAULT_CSTD_STATUS);

        // Get all the userInfoList where cstdStatus equals to UPDATED_CSTD_STATUS
        defaultUserInfoShouldNotBeFound("cstdStatus.equals=" + UPDATED_CSTD_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdStatus in DEFAULT_CSTD_STATUS or UPDATED_CSTD_STATUS
        defaultUserInfoShouldBeFound("cstdStatus.in=" + DEFAULT_CSTD_STATUS + "," + UPDATED_CSTD_STATUS);

        // Get all the userInfoList where cstdStatus equals to UPDATED_CSTD_STATUS
        defaultUserInfoShouldNotBeFound("cstdStatus.in=" + UPDATED_CSTD_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdStatus is not null
        defaultUserInfoShouldBeFound("cstdStatus.specified=true");

        // Get all the userInfoList where cstdStatus is null
        defaultUserInfoShouldNotBeFound("cstdStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdAdmDivsisonIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdAdmDivsison equals to DEFAULT_CSTD_ADM_DIVSISON
        defaultUserInfoShouldBeFound("cstdAdmDivsison.equals=" + DEFAULT_CSTD_ADM_DIVSISON);

        // Get all the userInfoList where cstdAdmDivsison equals to UPDATED_CSTD_ADM_DIVSISON
        defaultUserInfoShouldNotBeFound("cstdAdmDivsison.equals=" + UPDATED_CSTD_ADM_DIVSISON);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdAdmDivsisonIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdAdmDivsison in DEFAULT_CSTD_ADM_DIVSISON or UPDATED_CSTD_ADM_DIVSISON
        defaultUserInfoShouldBeFound("cstdAdmDivsison.in=" + DEFAULT_CSTD_ADM_DIVSISON + "," + UPDATED_CSTD_ADM_DIVSISON);

        // Get all the userInfoList where cstdAdmDivsison equals to UPDATED_CSTD_ADM_DIVSISON
        defaultUserInfoShouldNotBeFound("cstdAdmDivsison.in=" + UPDATED_CSTD_ADM_DIVSISON);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdAdmDivsisonIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdAdmDivsison is not null
        defaultUserInfoShouldBeFound("cstdAdmDivsison.specified=true");

        // Get all the userInfoList where cstdAdmDivsison is null
        defaultUserInfoShouldNotBeFound("cstdAdmDivsison.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginStatus equals to DEFAULT_LOGIN_STATUS
        defaultUserInfoShouldBeFound("loginStatus.equals=" + DEFAULT_LOGIN_STATUS);

        // Get all the userInfoList where loginStatus equals to UPDATED_LOGIN_STATUS
        defaultUserInfoShouldNotBeFound("loginStatus.equals=" + UPDATED_LOGIN_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginStatusIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginStatus in DEFAULT_LOGIN_STATUS or UPDATED_LOGIN_STATUS
        defaultUserInfoShouldBeFound("loginStatus.in=" + DEFAULT_LOGIN_STATUS + "," + UPDATED_LOGIN_STATUS);

        // Get all the userInfoList where loginStatus equals to UPDATED_LOGIN_STATUS
        defaultUserInfoShouldNotBeFound("loginStatus.in=" + UPDATED_LOGIN_STATUS);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginStatus is not null
        defaultUserInfoShouldBeFound("loginStatus.specified=true");

        // Get all the userInfoList where loginStatus is null
        defaultUserInfoShouldNotBeFound("loginStatus.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginTime equals to DEFAULT_LOGIN_TIME
        defaultUserInfoShouldBeFound("loginTime.equals=" + DEFAULT_LOGIN_TIME);

        // Get all the userInfoList where loginTime equals to UPDATED_LOGIN_TIME
        defaultUserInfoShouldNotBeFound("loginTime.equals=" + UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginTime in DEFAULT_LOGIN_TIME or UPDATED_LOGIN_TIME
        defaultUserInfoShouldBeFound("loginTime.in=" + DEFAULT_LOGIN_TIME + "," + UPDATED_LOGIN_TIME);

        // Get all the userInfoList where loginTime equals to UPDATED_LOGIN_TIME
        defaultUserInfoShouldNotBeFound("loginTime.in=" + UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginTime is not null
        defaultUserInfoShouldBeFound("loginTime.specified=true");

        // Get all the userInfoList where loginTime is null
        defaultUserInfoShouldNotBeFound("loginTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginTime greater than or equals to DEFAULT_LOGIN_TIME
        defaultUserInfoShouldBeFound("loginTime.greaterOrEqualThan=" + DEFAULT_LOGIN_TIME);

        // Get all the userInfoList where loginTime greater than or equals to UPDATED_LOGIN_TIME
        defaultUserInfoShouldNotBeFound("loginTime.greaterOrEqualThan=" + UPDATED_LOGIN_TIME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLoginTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where loginTime less than or equals to DEFAULT_LOGIN_TIME
        defaultUserInfoShouldNotBeFound("loginTime.lessThan=" + DEFAULT_LOGIN_TIME);

        // Get all the userInfoList where loginTime less than or equals to UPDATED_LOGIN_TIME
        defaultUserInfoShouldBeFound("loginTime.lessThan=" + UPDATED_LOGIN_TIME);
    }


    @Test
    @Transactional
    public void getAllUserInfosByAttemptIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where attempt equals to DEFAULT_ATTEMPT
        defaultUserInfoShouldBeFound("attempt.equals=" + DEFAULT_ATTEMPT);

        // Get all the userInfoList where attempt equals to UPDATED_ATTEMPT
        defaultUserInfoShouldNotBeFound("attempt.equals=" + UPDATED_ATTEMPT);
    }

    @Test
    @Transactional
    public void getAllUserInfosByAttemptIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where attempt in DEFAULT_ATTEMPT or UPDATED_ATTEMPT
        defaultUserInfoShouldBeFound("attempt.in=" + DEFAULT_ATTEMPT + "," + UPDATED_ATTEMPT);

        // Get all the userInfoList where attempt equals to UPDATED_ATTEMPT
        defaultUserInfoShouldNotBeFound("attempt.in=" + UPDATED_ATTEMPT);
    }

    @Test
    @Transactional
    public void getAllUserInfosByAttemptIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where attempt is not null
        defaultUserInfoShouldBeFound("attempt.specified=true");

        // Get all the userInfoList where attempt is null
        defaultUserInfoShouldNotBeFound("attempt.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByAttemptIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where attempt greater than or equals to DEFAULT_ATTEMPT
        defaultUserInfoShouldBeFound("attempt.greaterOrEqualThan=" + DEFAULT_ATTEMPT);

        // Get all the userInfoList where attempt greater than or equals to (DEFAULT_ATTEMPT + 1)
        defaultUserInfoShouldNotBeFound("attempt.greaterOrEqualThan=" + (DEFAULT_ATTEMPT + 1));
    }

    @Test
    @Transactional
    public void getAllUserInfosByAttemptIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where attempt less than or equals to DEFAULT_ATTEMPT
        defaultUserInfoShouldNotBeFound("attempt.lessThan=" + DEFAULT_ATTEMPT);

        // Get all the userInfoList where attempt less than or equals to (DEFAULT_ATTEMPT + 1)
        defaultUserInfoShouldBeFound("attempt.lessThan=" + (DEFAULT_ATTEMPT + 1));
    }


    @Test
    @Transactional
    public void getAllUserInfosByNeedApproveIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where needApprove equals to DEFAULT_NEED_APPROVE
        defaultUserInfoShouldBeFound("needApprove.equals=" + DEFAULT_NEED_APPROVE);

        // Get all the userInfoList where needApprove equals to UPDATED_NEED_APPROVE
        defaultUserInfoShouldNotBeFound("needApprove.equals=" + UPDATED_NEED_APPROVE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNeedApproveIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where needApprove in DEFAULT_NEED_APPROVE or UPDATED_NEED_APPROVE
        defaultUserInfoShouldBeFound("needApprove.in=" + DEFAULT_NEED_APPROVE + "," + UPDATED_NEED_APPROVE);

        // Get all the userInfoList where needApprove equals to UPDATED_NEED_APPROVE
        defaultUserInfoShouldNotBeFound("needApprove.in=" + UPDATED_NEED_APPROVE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNeedApproveIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where needApprove is not null
        defaultUserInfoShouldBeFound("needApprove.specified=true");

        // Get all the userInfoList where needApprove is null
        defaultUserInfoShouldNotBeFound("needApprove.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByLogoutTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where logoutTime equals to DEFAULT_LOGOUT_TIME
        defaultUserInfoShouldBeFound("logoutTime.equals=" + DEFAULT_LOGOUT_TIME);

        // Get all the userInfoList where logoutTime equals to UPDATED_LOGOUT_TIME
        defaultUserInfoShouldNotBeFound("logoutTime.equals=" + UPDATED_LOGOUT_TIME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLogoutTimeIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where logoutTime in DEFAULT_LOGOUT_TIME or UPDATED_LOGOUT_TIME
        defaultUserInfoShouldBeFound("logoutTime.in=" + DEFAULT_LOGOUT_TIME + "," + UPDATED_LOGOUT_TIME);

        // Get all the userInfoList where logoutTime equals to UPDATED_LOGOUT_TIME
        defaultUserInfoShouldNotBeFound("logoutTime.in=" + UPDATED_LOGOUT_TIME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLogoutTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where logoutTime is not null
        defaultUserInfoShouldBeFound("logoutTime.specified=true");

        // Get all the userInfoList where logoutTime is null
        defaultUserInfoShouldNotBeFound("logoutTime.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByLogoutTimeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where logoutTime greater than or equals to DEFAULT_LOGOUT_TIME
        defaultUserInfoShouldBeFound("logoutTime.greaterOrEqualThan=" + DEFAULT_LOGOUT_TIME);

        // Get all the userInfoList where logoutTime greater than or equals to UPDATED_LOGOUT_TIME
        defaultUserInfoShouldNotBeFound("logoutTime.greaterOrEqualThan=" + UPDATED_LOGOUT_TIME);
    }

    @Test
    @Transactional
    public void getAllUserInfosByLogoutTimeIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where logoutTime less than or equals to DEFAULT_LOGOUT_TIME
        defaultUserInfoShouldNotBeFound("logoutTime.lessThan=" + DEFAULT_LOGOUT_TIME);

        // Get all the userInfoList where logoutTime less than or equals to UPDATED_LOGOUT_TIME
        defaultUserInfoShouldBeFound("logoutTime.lessThan=" + UPDATED_LOGOUT_TIME);
    }


    @Test
    @Transactional
    public void getAllUserInfosByNationalIdIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nationalId equals to DEFAULT_NATIONAL_ID
        defaultUserInfoShouldBeFound("nationalId.equals=" + DEFAULT_NATIONAL_ID);

        // Get all the userInfoList where nationalId equals to UPDATED_NATIONAL_ID
        defaultUserInfoShouldNotBeFound("nationalId.equals=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNationalIdIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nationalId in DEFAULT_NATIONAL_ID or UPDATED_NATIONAL_ID
        defaultUserInfoShouldBeFound("nationalId.in=" + DEFAULT_NATIONAL_ID + "," + UPDATED_NATIONAL_ID);

        // Get all the userInfoList where nationalId equals to UPDATED_NATIONAL_ID
        defaultUserInfoShouldNotBeFound("nationalId.in=" + UPDATED_NATIONAL_ID);
    }

    @Test
    @Transactional
    public void getAllUserInfosByNationalIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where nationalId is not null
        defaultUserInfoShouldBeFound("nationalId.specified=true");

        // Get all the userInfoList where nationalId is null
        defaultUserInfoShouldNotBeFound("nationalId.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdOrganizationGradeIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdOrganizationGrade equals to DEFAULT_CSTD_ORGANIZATION_GRADE
        defaultUserInfoShouldBeFound("cstdOrganizationGrade.equals=" + DEFAULT_CSTD_ORGANIZATION_GRADE);

        // Get all the userInfoList where cstdOrganizationGrade equals to UPDATED_CSTD_ORGANIZATION_GRADE
        defaultUserInfoShouldNotBeFound("cstdOrganizationGrade.equals=" + UPDATED_CSTD_ORGANIZATION_GRADE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdOrganizationGradeIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdOrganizationGrade in DEFAULT_CSTD_ORGANIZATION_GRADE or UPDATED_CSTD_ORGANIZATION_GRADE
        defaultUserInfoShouldBeFound("cstdOrganizationGrade.in=" + DEFAULT_CSTD_ORGANIZATION_GRADE + "," + UPDATED_CSTD_ORGANIZATION_GRADE);

        // Get all the userInfoList where cstdOrganizationGrade equals to UPDATED_CSTD_ORGANIZATION_GRADE
        defaultUserInfoShouldNotBeFound("cstdOrganizationGrade.in=" + UPDATED_CSTD_ORGANIZATION_GRADE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdOrganizationGradeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdOrganizationGrade is not null
        defaultUserInfoShouldBeFound("cstdOrganizationGrade.specified=true");

        // Get all the userInfoList where cstdOrganizationGrade is null
        defaultUserInfoShouldNotBeFound("cstdOrganizationGrade.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdEmploymentTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdEmploymentType equals to DEFAULT_CSTD_EMPLOYMENT_TYPE
        defaultUserInfoShouldBeFound("cstdEmploymentType.equals=" + DEFAULT_CSTD_EMPLOYMENT_TYPE);

        // Get all the userInfoList where cstdEmploymentType equals to UPDATED_CSTD_EMPLOYMENT_TYPE
        defaultUserInfoShouldNotBeFound("cstdEmploymentType.equals=" + UPDATED_CSTD_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdEmploymentTypeIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdEmploymentType in DEFAULT_CSTD_EMPLOYMENT_TYPE or UPDATED_CSTD_EMPLOYMENT_TYPE
        defaultUserInfoShouldBeFound("cstdEmploymentType.in=" + DEFAULT_CSTD_EMPLOYMENT_TYPE + "," + UPDATED_CSTD_EMPLOYMENT_TYPE);

        // Get all the userInfoList where cstdEmploymentType equals to UPDATED_CSTD_EMPLOYMENT_TYPE
        defaultUserInfoShouldNotBeFound("cstdEmploymentType.in=" + UPDATED_CSTD_EMPLOYMENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCstdEmploymentTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where cstdEmploymentType is not null
        defaultUserInfoShouldBeFound("cstdEmploymentType.specified=true");

        // Get all the userInfoList where cstdEmploymentType is null
        defaultUserInfoShouldNotBeFound("cstdEmploymentType.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCcVersionIsEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ccVersion equals to DEFAULT_CC_VERSION
        defaultUserInfoShouldBeFound("ccVersion.equals=" + DEFAULT_CC_VERSION);

        // Get all the userInfoList where ccVersion equals to UPDATED_CC_VERSION
        defaultUserInfoShouldNotBeFound("ccVersion.equals=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCcVersionIsInShouldWork() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ccVersion in DEFAULT_CC_VERSION or UPDATED_CC_VERSION
        defaultUserInfoShouldBeFound("ccVersion.in=" + DEFAULT_CC_VERSION + "," + UPDATED_CC_VERSION);

        // Get all the userInfoList where ccVersion equals to UPDATED_CC_VERSION
        defaultUserInfoShouldNotBeFound("ccVersion.in=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCcVersionIsNullOrNotNull() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ccVersion is not null
        defaultUserInfoShouldBeFound("ccVersion.specified=true");

        // Get all the userInfoList where ccVersion is null
        defaultUserInfoShouldNotBeFound("ccVersion.specified=false");
    }

    @Test
    @Transactional
    public void getAllUserInfosByCcVersionIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ccVersion greater than or equals to DEFAULT_CC_VERSION
        defaultUserInfoShouldBeFound("ccVersion.greaterOrEqualThan=" + DEFAULT_CC_VERSION);

        // Get all the userInfoList where ccVersion greater than or equals to UPDATED_CC_VERSION
        defaultUserInfoShouldNotBeFound("ccVersion.greaterOrEqualThan=" + UPDATED_CC_VERSION);
    }

    @Test
    @Transactional
    public void getAllUserInfosByCcVersionIsLessThanSomething() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList where ccVersion less than or equals to DEFAULT_CC_VERSION
        defaultUserInfoShouldNotBeFound("ccVersion.lessThan=" + DEFAULT_CC_VERSION);

        // Get all the userInfoList where ccVersion less than or equals to UPDATED_CC_VERSION
        defaultUserInfoShouldBeFound("ccVersion.lessThan=" + UPDATED_CC_VERSION);
    }


    @Test
    @Transactional
    public void getAllUserInfosByUserIsEqualToSomething() throws Exception {
        // Initialize the database
        User user = UserResourceIT.createEntity(em);
        em.persist(user);
        em.flush();
        userInfo.setUser(user);
        userInfoRepository.saveAndFlush(userInfo);
        Long userId = user.getId();

        // Get all the userInfoList where user equals to userId
        defaultUserInfoShouldBeFound("userId.equals=" + userId);

        // Get all the userInfoList where user equals to userId + 1
        defaultUserInfoShouldNotBeFound("userId.equals=" + (userId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUserInfoShouldBeFound(String filter) throws Exception {
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].cstdAdmSection").value(hasItem(DEFAULT_CSTD_ADM_SECTION)))
            .andExpect(jsonPath("$.[*].cstdSecurityLevel").value(hasItem(DEFAULT_CSTD_SECURITY_LEVEL)))
            .andExpect(jsonPath("$.[*].cstdUserType").value(hasItem(DEFAULT_CSTD_USER_TYPE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].middleName").value(hasItem(DEFAULT_MIDDLE_NAME)))
            .andExpect(jsonPath("$.[*].gender").value(hasItem(DEFAULT_GENDER)))
            .andExpect(jsonPath("$.[*].phoneNum").value(hasItem(DEFAULT_PHONE_NUM)))
            .andExpect(jsonPath("$.[*].faxNum").value(hasItem(DEFAULT_FAX_NUM)))
            .andExpect(jsonPath("$.[*].effiectiveDate").value(hasItem(sameInstant(DEFAULT_EFFIECTIVE_DATE))))
            .andExpect(jsonPath("$.[*].expiryDate").value(hasItem(sameInstant(DEFAULT_EXPIRY_DATE))))
            .andExpect(jsonPath("$.[*].blocked").value(hasItem(DEFAULT_BLOCKED.booleanValue())))
            .andExpect(jsonPath("$.[*].blockedReason").value(hasItem(DEFAULT_BLOCKED_REASON)))
            .andExpect(jsonPath("$.[*].forcedPwdChange").value(hasItem(DEFAULT_FORCED_PWD_CHANGE.booleanValue())))
            .andExpect(jsonPath("$.[*].cstdTitles").value(hasItem(DEFAULT_CSTD_TITLES)))
            .andExpect(jsonPath("$.[*].cstdStatus").value(hasItem(DEFAULT_CSTD_STATUS)))
            .andExpect(jsonPath("$.[*].cstdAdmDivsison").value(hasItem(DEFAULT_CSTD_ADM_DIVSISON)))
            .andExpect(jsonPath("$.[*].loginStatus").value(hasItem(DEFAULT_LOGIN_STATUS)))
            .andExpect(jsonPath("$.[*].loginTime").value(hasItem(sameInstant(DEFAULT_LOGIN_TIME))))
            .andExpect(jsonPath("$.[*].attempt").value(hasItem(DEFAULT_ATTEMPT)))
            .andExpect(jsonPath("$.[*].needApprove").value(hasItem(DEFAULT_NEED_APPROVE.booleanValue())))
            .andExpect(jsonPath("$.[*].logoutTime").value(hasItem(sameInstant(DEFAULT_LOGOUT_TIME))))
            .andExpect(jsonPath("$.[*].nationalId").value(hasItem(DEFAULT_NATIONAL_ID)))
            .andExpect(jsonPath("$.[*].cstdOrganizationGrade").value(hasItem(DEFAULT_CSTD_ORGANIZATION_GRADE)))
            .andExpect(jsonPath("$.[*].cstdEmploymentType").value(hasItem(DEFAULT_CSTD_EMPLOYMENT_TYPE)))
            .andExpect(jsonPath("$.[*].manuScriptContentType").value(hasItem(DEFAULT_MANU_SCRIPT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].manuScript").value(hasItem(Base64Utils.encodeToString(DEFAULT_MANU_SCRIPT))))
            .andExpect(jsonPath("$.[*].ccVersion").value(hasItem(DEFAULT_CC_VERSION)));

        // Check, that the count call also returns 1
        restUserInfoMockMvc.perform(get("/api/user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUserInfoShouldNotBeFound(String filter) throws Exception {
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUserInfoMockMvc.perform(get("/api/user-infos/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
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
        userInfoService.save(userInfo);

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
        userInfoService.save(userInfo);

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
