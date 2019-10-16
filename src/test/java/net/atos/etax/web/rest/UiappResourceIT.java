package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.Uiapp;
import net.atos.etax.domain.Resource;
import net.atos.etax.repository.UiappRepository;
import net.atos.etax.service.UiappService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.UiappCriteria;
import net.atos.etax.service.UiappQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static net.atos.etax.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link UiappResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class UiappResourceIT {

    private static final String DEFAULT_APP_CODE = "AAAAAAAAAA";
    private static final String UPDATED_APP_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_APP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_APP_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_APP_DESC = "AAAAAAAAAA";
    private static final String UPDATED_APP_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_APP_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_APP_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private UiappRepository uiappRepository;

    @Mock
    private UiappRepository uiappRepositoryMock;

    @Mock
    private UiappService uiappServiceMock;

    @Autowired
    private UiappService uiappService;

    @Autowired
    private UiappQueryService uiappQueryService;

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

    private MockMvc restUiappMockMvc;

    private Uiapp uiapp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UiappResource uiappResource = new UiappResource(uiappService, uiappQueryService);
        this.restUiappMockMvc = MockMvcBuilders.standaloneSetup(uiappResource)
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
    public static Uiapp createEntity(EntityManager em) {
        Uiapp uiapp = new Uiapp()
            .appCode(DEFAULT_APP_CODE)
            .appName(DEFAULT_APP_NAME)
            .appDesc(DEFAULT_APP_DESC)
            .cstdModule(DEFAULT_CSTD_MODULE)
            .appMessage(DEFAULT_APP_MESSAGE);
        return uiapp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uiapp createUpdatedEntity(EntityManager em) {
        Uiapp uiapp = new Uiapp()
            .appCode(UPDATED_APP_CODE)
            .appName(UPDATED_APP_NAME)
            .appDesc(UPDATED_APP_DESC)
            .cstdModule(UPDATED_CSTD_MODULE)
            .appMessage(UPDATED_APP_MESSAGE);
        return uiapp;
    }

    @BeforeEach
    public void initTest() {
        uiapp = createEntity(em);
    }

    @Test
    @Transactional
    public void createUiapp() throws Exception {
        int databaseSizeBeforeCreate = uiappRepository.findAll().size();

        // Create the Uiapp
        restUiappMockMvc.perform(post("/api/uiapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiapp)))
            .andExpect(status().isCreated());

        // Validate the Uiapp in the database
        List<Uiapp> uiappList = uiappRepository.findAll();
        assertThat(uiappList).hasSize(databaseSizeBeforeCreate + 1);
        Uiapp testUiapp = uiappList.get(uiappList.size() - 1);
        assertThat(testUiapp.getAppCode()).isEqualTo(DEFAULT_APP_CODE);
        assertThat(testUiapp.getAppName()).isEqualTo(DEFAULT_APP_NAME);
        assertThat(testUiapp.getAppDesc()).isEqualTo(DEFAULT_APP_DESC);
        assertThat(testUiapp.getCstdModule()).isEqualTo(DEFAULT_CSTD_MODULE);
        assertThat(testUiapp.getAppMessage()).isEqualTo(DEFAULT_APP_MESSAGE);
    }

    @Test
    @Transactional
    public void createUiappWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uiappRepository.findAll().size();

        // Create the Uiapp with an existing ID
        uiapp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUiappMockMvc.perform(post("/api/uiapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiapp)))
            .andExpect(status().isBadRequest());

        // Validate the Uiapp in the database
        List<Uiapp> uiappList = uiappRepository.findAll();
        assertThat(uiappList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkAppCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = uiappRepository.findAll().size();
        // set the field null
        uiapp.setAppCode(null);

        // Create the Uiapp, which fails.

        restUiappMockMvc.perform(post("/api/uiapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiapp)))
            .andExpect(status().isBadRequest());

        List<Uiapp> uiappList = uiappRepository.findAll();
        assertThat(uiappList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUiapps() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList
        restUiappMockMvc.perform(get("/api/uiapps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uiapp.getId().intValue())))
            .andExpect(jsonPath("$.[*].appCode").value(hasItem(DEFAULT_APP_CODE.toString())))
            .andExpect(jsonPath("$.[*].appName").value(hasItem(DEFAULT_APP_NAME.toString())))
            .andExpect(jsonPath("$.[*].appDesc").value(hasItem(DEFAULT_APP_DESC.toString())))
            .andExpect(jsonPath("$.[*].cstdModule").value(hasItem(DEFAULT_CSTD_MODULE.toString())))
            .andExpect(jsonPath("$.[*].appMessage").value(hasItem(DEFAULT_APP_MESSAGE.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllUiappsWithEagerRelationshipsIsEnabled() throws Exception {
        UiappResource uiappResource = new UiappResource(uiappServiceMock, uiappQueryService);
        when(uiappServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restUiappMockMvc = MockMvcBuilders.standaloneSetup(uiappResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUiappMockMvc.perform(get("/api/uiapps?eagerload=true"))
        .andExpect(status().isOk());

        verify(uiappServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllUiappsWithEagerRelationshipsIsNotEnabled() throws Exception {
        UiappResource uiappResource = new UiappResource(uiappServiceMock, uiappQueryService);
            when(uiappServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restUiappMockMvc = MockMvcBuilders.standaloneSetup(uiappResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restUiappMockMvc.perform(get("/api/uiapps?eagerload=true"))
        .andExpect(status().isOk());

            verify(uiappServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getUiapp() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get the uiapp
        restUiappMockMvc.perform(get("/api/uiapps/{id}", uiapp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uiapp.getId().intValue()))
            .andExpect(jsonPath("$.appCode").value(DEFAULT_APP_CODE.toString()))
            .andExpect(jsonPath("$.appName").value(DEFAULT_APP_NAME.toString()))
            .andExpect(jsonPath("$.appDesc").value(DEFAULT_APP_DESC.toString()))
            .andExpect(jsonPath("$.cstdModule").value(DEFAULT_CSTD_MODULE.toString()))
            .andExpect(jsonPath("$.appMessage").value(DEFAULT_APP_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getAllUiappsByAppCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appCode equals to DEFAULT_APP_CODE
        defaultUiappShouldBeFound("appCode.equals=" + DEFAULT_APP_CODE);

        // Get all the uiappList where appCode equals to UPDATED_APP_CODE
        defaultUiappShouldNotBeFound("appCode.equals=" + UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppCodeIsInShouldWork() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appCode in DEFAULT_APP_CODE or UPDATED_APP_CODE
        defaultUiappShouldBeFound("appCode.in=" + DEFAULT_APP_CODE + "," + UPDATED_APP_CODE);

        // Get all the uiappList where appCode equals to UPDATED_APP_CODE
        defaultUiappShouldNotBeFound("appCode.in=" + UPDATED_APP_CODE);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appCode is not null
        defaultUiappShouldBeFound("appCode.specified=true");

        // Get all the uiappList where appCode is null
        defaultUiappShouldNotBeFound("appCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllUiappsByAppNameIsEqualToSomething() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appName equals to DEFAULT_APP_NAME
        defaultUiappShouldBeFound("appName.equals=" + DEFAULT_APP_NAME);

        // Get all the uiappList where appName equals to UPDATED_APP_NAME
        defaultUiappShouldNotBeFound("appName.equals=" + UPDATED_APP_NAME);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppNameIsInShouldWork() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appName in DEFAULT_APP_NAME or UPDATED_APP_NAME
        defaultUiappShouldBeFound("appName.in=" + DEFAULT_APP_NAME + "," + UPDATED_APP_NAME);

        // Get all the uiappList where appName equals to UPDATED_APP_NAME
        defaultUiappShouldNotBeFound("appName.in=" + UPDATED_APP_NAME);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appName is not null
        defaultUiappShouldBeFound("appName.specified=true");

        // Get all the uiappList where appName is null
        defaultUiappShouldNotBeFound("appName.specified=false");
    }

    @Test
    @Transactional
    public void getAllUiappsByAppDescIsEqualToSomething() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appDesc equals to DEFAULT_APP_DESC
        defaultUiappShouldBeFound("appDesc.equals=" + DEFAULT_APP_DESC);

        // Get all the uiappList where appDesc equals to UPDATED_APP_DESC
        defaultUiappShouldNotBeFound("appDesc.equals=" + UPDATED_APP_DESC);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppDescIsInShouldWork() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appDesc in DEFAULT_APP_DESC or UPDATED_APP_DESC
        defaultUiappShouldBeFound("appDesc.in=" + DEFAULT_APP_DESC + "," + UPDATED_APP_DESC);

        // Get all the uiappList where appDesc equals to UPDATED_APP_DESC
        defaultUiappShouldNotBeFound("appDesc.in=" + UPDATED_APP_DESC);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appDesc is not null
        defaultUiappShouldBeFound("appDesc.specified=true");

        // Get all the uiappList where appDesc is null
        defaultUiappShouldNotBeFound("appDesc.specified=false");
    }

    @Test
    @Transactional
    public void getAllUiappsByCstdModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where cstdModule equals to DEFAULT_CSTD_MODULE
        defaultUiappShouldBeFound("cstdModule.equals=" + DEFAULT_CSTD_MODULE);

        // Get all the uiappList where cstdModule equals to UPDATED_CSTD_MODULE
        defaultUiappShouldNotBeFound("cstdModule.equals=" + UPDATED_CSTD_MODULE);
    }

    @Test
    @Transactional
    public void getAllUiappsByCstdModuleIsInShouldWork() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where cstdModule in DEFAULT_CSTD_MODULE or UPDATED_CSTD_MODULE
        defaultUiappShouldBeFound("cstdModule.in=" + DEFAULT_CSTD_MODULE + "," + UPDATED_CSTD_MODULE);

        // Get all the uiappList where cstdModule equals to UPDATED_CSTD_MODULE
        defaultUiappShouldNotBeFound("cstdModule.in=" + UPDATED_CSTD_MODULE);
    }

    @Test
    @Transactional
    public void getAllUiappsByCstdModuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where cstdModule is not null
        defaultUiappShouldBeFound("cstdModule.specified=true");

        // Get all the uiappList where cstdModule is null
        defaultUiappShouldNotBeFound("cstdModule.specified=false");
    }

    @Test
    @Transactional
    public void getAllUiappsByAppMessageIsEqualToSomething() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appMessage equals to DEFAULT_APP_MESSAGE
        defaultUiappShouldBeFound("appMessage.equals=" + DEFAULT_APP_MESSAGE);

        // Get all the uiappList where appMessage equals to UPDATED_APP_MESSAGE
        defaultUiappShouldNotBeFound("appMessage.equals=" + UPDATED_APP_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppMessageIsInShouldWork() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appMessage in DEFAULT_APP_MESSAGE or UPDATED_APP_MESSAGE
        defaultUiappShouldBeFound("appMessage.in=" + DEFAULT_APP_MESSAGE + "," + UPDATED_APP_MESSAGE);

        // Get all the uiappList where appMessage equals to UPDATED_APP_MESSAGE
        defaultUiappShouldNotBeFound("appMessage.in=" + UPDATED_APP_MESSAGE);
    }

    @Test
    @Transactional
    public void getAllUiappsByAppMessageIsNullOrNotNull() throws Exception {
        // Initialize the database
        uiappRepository.saveAndFlush(uiapp);

        // Get all the uiappList where appMessage is not null
        defaultUiappShouldBeFound("appMessage.specified=true");

        // Get all the uiappList where appMessage is null
        defaultUiappShouldNotBeFound("appMessage.specified=false");
    }

    @Test
    @Transactional
    public void getAllUiappsByResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        Resource resource = ResourceResourceIT.createEntity(em);
        em.persist(resource);
        em.flush();
        uiapp.addResource(resource);
        uiappRepository.saveAndFlush(uiapp);
        Long resourceId = resource.getId();

        // Get all the uiappList where resource equals to resourceId
        defaultUiappShouldBeFound("resourceId.equals=" + resourceId);

        // Get all the uiappList where resource equals to resourceId + 1
        defaultUiappShouldNotBeFound("resourceId.equals=" + (resourceId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUiappShouldBeFound(String filter) throws Exception {
        restUiappMockMvc.perform(get("/api/uiapps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uiapp.getId().intValue())))
            .andExpect(jsonPath("$.[*].appCode").value(hasItem(DEFAULT_APP_CODE)))
            .andExpect(jsonPath("$.[*].appName").value(hasItem(DEFAULT_APP_NAME)))
            .andExpect(jsonPath("$.[*].appDesc").value(hasItem(DEFAULT_APP_DESC)))
            .andExpect(jsonPath("$.[*].cstdModule").value(hasItem(DEFAULT_CSTD_MODULE)))
            .andExpect(jsonPath("$.[*].appMessage").value(hasItem(DEFAULT_APP_MESSAGE)));

        // Check, that the count call also returns 1
        restUiappMockMvc.perform(get("/api/uiapps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUiappShouldNotBeFound(String filter) throws Exception {
        restUiappMockMvc.perform(get("/api/uiapps?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUiappMockMvc.perform(get("/api/uiapps/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUiapp() throws Exception {
        // Get the uiapp
        restUiappMockMvc.perform(get("/api/uiapps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUiapp() throws Exception {
        // Initialize the database
        uiappService.save(uiapp);

        int databaseSizeBeforeUpdate = uiappRepository.findAll().size();

        // Update the uiapp
        Uiapp updatedUiapp = uiappRepository.findById(uiapp.getId()).get();
        // Disconnect from session so that the updates on updatedUiapp are not directly saved in db
        em.detach(updatedUiapp);
        updatedUiapp
            .appCode(UPDATED_APP_CODE)
            .appName(UPDATED_APP_NAME)
            .appDesc(UPDATED_APP_DESC)
            .cstdModule(UPDATED_CSTD_MODULE)
            .appMessage(UPDATED_APP_MESSAGE);

        restUiappMockMvc.perform(put("/api/uiapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUiapp)))
            .andExpect(status().isOk());

        // Validate the Uiapp in the database
        List<Uiapp> uiappList = uiappRepository.findAll();
        assertThat(uiappList).hasSize(databaseSizeBeforeUpdate);
        Uiapp testUiapp = uiappList.get(uiappList.size() - 1);
        assertThat(testUiapp.getAppCode()).isEqualTo(UPDATED_APP_CODE);
        assertThat(testUiapp.getAppName()).isEqualTo(UPDATED_APP_NAME);
        assertThat(testUiapp.getAppDesc()).isEqualTo(UPDATED_APP_DESC);
        assertThat(testUiapp.getCstdModule()).isEqualTo(UPDATED_CSTD_MODULE);
        assertThat(testUiapp.getAppMessage()).isEqualTo(UPDATED_APP_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingUiapp() throws Exception {
        int databaseSizeBeforeUpdate = uiappRepository.findAll().size();

        // Create the Uiapp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUiappMockMvc.perform(put("/api/uiapps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uiapp)))
            .andExpect(status().isBadRequest());

        // Validate the Uiapp in the database
        List<Uiapp> uiappList = uiappRepository.findAll();
        assertThat(uiappList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUiapp() throws Exception {
        // Initialize the database
        uiappService.save(uiapp);

        int databaseSizeBeforeDelete = uiappRepository.findAll().size();

        // Delete the uiapp
        restUiappMockMvc.perform(delete("/api/uiapps/{id}", uiapp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uiapp> uiappList = uiappRepository.findAll();
        assertThat(uiappList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uiapp.class);
        Uiapp uiapp1 = new Uiapp();
        uiapp1.setId(1L);
        Uiapp uiapp2 = new Uiapp();
        uiapp2.setId(uiapp1.getId());
        assertThat(uiapp1).isEqualTo(uiapp2);
        uiapp2.setId(2L);
        assertThat(uiapp1).isNotEqualTo(uiapp2);
        uiapp1.setId(null);
        assertThat(uiapp1).isNotEqualTo(uiapp2);
    }
}
