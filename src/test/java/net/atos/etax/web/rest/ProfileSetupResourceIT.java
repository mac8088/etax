package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.ProfileSetup;
import net.atos.etax.repository.ProfileSetupRepository;
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

/**
 * Integration tests for the {@Link ProfileSetupResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class ProfileSetupResourceIT {

    private static final String DEFAULT_PROFILE_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PROFILE_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_OFFICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_OFFICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_USER_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_USER_TYPE = "BBBBBBBBBB";

    @Autowired
    private ProfileSetupRepository profileSetupRepository;

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

    private MockMvc restProfileSetupMockMvc;

    private ProfileSetup profileSetup;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProfileSetupResource profileSetupResource = new ProfileSetupResource(profileSetupRepository);
        this.restProfileSetupMockMvc = MockMvcBuilders.standaloneSetup(profileSetupResource)
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
    public static ProfileSetup createEntity(EntityManager em) {
        ProfileSetup profileSetup = new ProfileSetup()
            .profileCode(DEFAULT_PROFILE_CODE)
            .cstdOfficeType(DEFAULT_CSTD_OFFICE_TYPE)
            .cstdUserType(DEFAULT_CSTD_USER_TYPE);
        return profileSetup;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProfileSetup createUpdatedEntity(EntityManager em) {
        ProfileSetup profileSetup = new ProfileSetup()
            .profileCode(UPDATED_PROFILE_CODE)
            .cstdOfficeType(UPDATED_CSTD_OFFICE_TYPE)
            .cstdUserType(UPDATED_CSTD_USER_TYPE);
        return profileSetup;
    }

    @BeforeEach
    public void initTest() {
        profileSetup = createEntity(em);
    }

    @Test
    @Transactional
    public void createProfileSetup() throws Exception {
        int databaseSizeBeforeCreate = profileSetupRepository.findAll().size();

        // Create the ProfileSetup
        restProfileSetupMockMvc.perform(post("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSetup)))
            .andExpect(status().isCreated());

        // Validate the ProfileSetup in the database
        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeCreate + 1);
        ProfileSetup testProfileSetup = profileSetupList.get(profileSetupList.size() - 1);
        assertThat(testProfileSetup.getProfileCode()).isEqualTo(DEFAULT_PROFILE_CODE);
        assertThat(testProfileSetup.getCstdOfficeType()).isEqualTo(DEFAULT_CSTD_OFFICE_TYPE);
        assertThat(testProfileSetup.getCstdUserType()).isEqualTo(DEFAULT_CSTD_USER_TYPE);
    }

    @Test
    @Transactional
    public void createProfileSetupWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = profileSetupRepository.findAll().size();

        // Create the ProfileSetup with an existing ID
        profileSetup.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfileSetupMockMvc.perform(post("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSetup)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileSetup in the database
        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkProfileCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSetupRepository.findAll().size();
        // set the field null
        profileSetup.setProfileCode(null);

        // Create the ProfileSetup, which fails.

        restProfileSetupMockMvc.perform(post("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSetup)))
            .andExpect(status().isBadRequest());

        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdOfficeTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSetupRepository.findAll().size();
        // set the field null
        profileSetup.setCstdOfficeType(null);

        // Create the ProfileSetup, which fails.

        restProfileSetupMockMvc.perform(post("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSetup)))
            .andExpect(status().isBadRequest());

        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCstdUserTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = profileSetupRepository.findAll().size();
        // set the field null
        profileSetup.setCstdUserType(null);

        // Create the ProfileSetup, which fails.

        restProfileSetupMockMvc.perform(post("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSetup)))
            .andExpect(status().isBadRequest());

        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllProfileSetups() throws Exception {
        // Initialize the database
        profileSetupRepository.saveAndFlush(profileSetup);

        // Get all the profileSetupList
        restProfileSetupMockMvc.perform(get("/api/profile-setups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(profileSetup.getId().intValue())))
            .andExpect(jsonPath("$.[*].profileCode").value(hasItem(DEFAULT_PROFILE_CODE.toString())))
            .andExpect(jsonPath("$.[*].cstdOfficeType").value(hasItem(DEFAULT_CSTD_OFFICE_TYPE.toString())))
            .andExpect(jsonPath("$.[*].cstdUserType").value(hasItem(DEFAULT_CSTD_USER_TYPE.toString())));
    }
    
    @Test
    @Transactional
    public void getProfileSetup() throws Exception {
        // Initialize the database
        profileSetupRepository.saveAndFlush(profileSetup);

        // Get the profileSetup
        restProfileSetupMockMvc.perform(get("/api/profile-setups/{id}", profileSetup.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(profileSetup.getId().intValue()))
            .andExpect(jsonPath("$.profileCode").value(DEFAULT_PROFILE_CODE.toString()))
            .andExpect(jsonPath("$.cstdOfficeType").value(DEFAULT_CSTD_OFFICE_TYPE.toString()))
            .andExpect(jsonPath("$.cstdUserType").value(DEFAULT_CSTD_USER_TYPE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProfileSetup() throws Exception {
        // Get the profileSetup
        restProfileSetupMockMvc.perform(get("/api/profile-setups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProfileSetup() throws Exception {
        // Initialize the database
        profileSetupRepository.saveAndFlush(profileSetup);

        int databaseSizeBeforeUpdate = profileSetupRepository.findAll().size();

        // Update the profileSetup
        ProfileSetup updatedProfileSetup = profileSetupRepository.findById(profileSetup.getId()).get();
        // Disconnect from session so that the updates on updatedProfileSetup are not directly saved in db
        em.detach(updatedProfileSetup);
        updatedProfileSetup
            .profileCode(UPDATED_PROFILE_CODE)
            .cstdOfficeType(UPDATED_CSTD_OFFICE_TYPE)
            .cstdUserType(UPDATED_CSTD_USER_TYPE);

        restProfileSetupMockMvc.perform(put("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProfileSetup)))
            .andExpect(status().isOk());

        // Validate the ProfileSetup in the database
        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeUpdate);
        ProfileSetup testProfileSetup = profileSetupList.get(profileSetupList.size() - 1);
        assertThat(testProfileSetup.getProfileCode()).isEqualTo(UPDATED_PROFILE_CODE);
        assertThat(testProfileSetup.getCstdOfficeType()).isEqualTo(UPDATED_CSTD_OFFICE_TYPE);
        assertThat(testProfileSetup.getCstdUserType()).isEqualTo(UPDATED_CSTD_USER_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingProfileSetup() throws Exception {
        int databaseSizeBeforeUpdate = profileSetupRepository.findAll().size();

        // Create the ProfileSetup

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfileSetupMockMvc.perform(put("/api/profile-setups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(profileSetup)))
            .andExpect(status().isBadRequest());

        // Validate the ProfileSetup in the database
        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProfileSetup() throws Exception {
        // Initialize the database
        profileSetupRepository.saveAndFlush(profileSetup);

        int databaseSizeBeforeDelete = profileSetupRepository.findAll().size();

        // Delete the profileSetup
        restProfileSetupMockMvc.perform(delete("/api/profile-setups/{id}", profileSetup.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProfileSetup> profileSetupList = profileSetupRepository.findAll();
        assertThat(profileSetupList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProfileSetup.class);
        ProfileSetup profileSetup1 = new ProfileSetup();
        profileSetup1.setId(1L);
        ProfileSetup profileSetup2 = new ProfileSetup();
        profileSetup2.setId(profileSetup1.getId());
        assertThat(profileSetup1).isEqualTo(profileSetup2);
        profileSetup2.setId(2L);
        assertThat(profileSetup1).isNotEqualTo(profileSetup2);
        profileSetup1.setId(null);
        assertThat(profileSetup1).isNotEqualTo(profileSetup2);
    }
}
