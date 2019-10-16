package net.atos.etax.web.rest;

import net.atos.etax.EtaxApp;
import net.atos.etax.domain.Resource;
import net.atos.etax.domain.Uiapp;
import net.atos.etax.repository.ResourceRepository;
import net.atos.etax.service.ResourceService;
import net.atos.etax.web.rest.errors.ExceptionTranslator;
import net.atos.etax.service.dto.ResourceCriteria;
import net.atos.etax.service.ResourceQueryService;

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

import net.atos.etax.domain.enumeration.ResourceType;
/**
 * Integration tests for the {@Link ResourceResource} REST controller.
 */
@SpringBootTest(classes = EtaxApp.class)
public class ResourceResourceIT {

    private static final String DEFAULT_RES_CODE = "AAAAAAAAAA";
    private static final String UPDATED_RES_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_RES_NAME = "AAAAAAAAAA";
    private static final String UPDATED_RES_NAME = "BBBBBBBBBB";

    private static final ResourceType DEFAULT_RES_TYPE = ResourceType.WORKFLOW;
    private static final ResourceType UPDATED_RES_TYPE = ResourceType.CONTROLLER;

    private static final String DEFAULT_APP_DESC = "AAAAAAAAAA";
    private static final String UPDATED_APP_DESC = "BBBBBBBBBB";

    private static final String DEFAULT_CSTD_MODULE = "AAAAAAAAAA";
    private static final String UPDATED_CSTD_MODULE = "BBBBBBBBBB";

    private static final String DEFAULT_RES_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_RES_CONTENT = "BBBBBBBBBB";

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceQueryService resourceQueryService;

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

    private MockMvc restResourceMockMvc;

    private Resource resource;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ResourceResource resourceResource = new ResourceResource(resourceService, resourceQueryService);
        this.restResourceMockMvc = MockMvcBuilders.standaloneSetup(resourceResource)
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
    public static Resource createEntity(EntityManager em) {
        Resource resource = new Resource()
            .resCode(DEFAULT_RES_CODE)
            .resName(DEFAULT_RES_NAME)
            .resType(DEFAULT_RES_TYPE)
            .appDesc(DEFAULT_APP_DESC)
            .cstdModule(DEFAULT_CSTD_MODULE)
            .resContent(DEFAULT_RES_CONTENT);
        return resource;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Resource createUpdatedEntity(EntityManager em) {
        Resource resource = new Resource()
            .resCode(UPDATED_RES_CODE)
            .resName(UPDATED_RES_NAME)
            .resType(UPDATED_RES_TYPE)
            .appDesc(UPDATED_APP_DESC)
            .cstdModule(UPDATED_CSTD_MODULE)
            .resContent(UPDATED_RES_CONTENT);
        return resource;
    }

    @BeforeEach
    public void initTest() {
        resource = createEntity(em);
    }

    @Test
    @Transactional
    public void createResource() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isCreated());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate + 1);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getResCode()).isEqualTo(DEFAULT_RES_CODE);
        assertThat(testResource.getResName()).isEqualTo(DEFAULT_RES_NAME);
        assertThat(testResource.getResType()).isEqualTo(DEFAULT_RES_TYPE);
        assertThat(testResource.getAppDesc()).isEqualTo(DEFAULT_APP_DESC);
        assertThat(testResource.getCstdModule()).isEqualTo(DEFAULT_CSTD_MODULE);
        assertThat(testResource.getResContent()).isEqualTo(DEFAULT_RES_CONTENT);
    }

    @Test
    @Transactional
    public void createResourceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = resourceRepository.findAll().size();

        // Create the Resource with an existing ID
        resource.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkResCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = resourceRepository.findAll().size();
        // set the field null
        resource.setResCode(null);

        // Create the Resource, which fails.

        restResourceMockMvc.perform(post("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isBadRequest());

        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllResources() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].resCode").value(hasItem(DEFAULT_RES_CODE.toString())))
            .andExpect(jsonPath("$.[*].resName").value(hasItem(DEFAULT_RES_NAME.toString())))
            .andExpect(jsonPath("$.[*].resType").value(hasItem(DEFAULT_RES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].appDesc").value(hasItem(DEFAULT_APP_DESC.toString())))
            .andExpect(jsonPath("$.[*].cstdModule").value(hasItem(DEFAULT_CSTD_MODULE.toString())))
            .andExpect(jsonPath("$.[*].resContent").value(hasItem(DEFAULT_RES_CONTENT.toString())));
    }
    
    @Test
    @Transactional
    public void getResource() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", resource.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(resource.getId().intValue()))
            .andExpect(jsonPath("$.resCode").value(DEFAULT_RES_CODE.toString()))
            .andExpect(jsonPath("$.resName").value(DEFAULT_RES_NAME.toString()))
            .andExpect(jsonPath("$.resType").value(DEFAULT_RES_TYPE.toString()))
            .andExpect(jsonPath("$.appDesc").value(DEFAULT_APP_DESC.toString()))
            .andExpect(jsonPath("$.cstdModule").value(DEFAULT_CSTD_MODULE.toString()))
            .andExpect(jsonPath("$.resContent").value(DEFAULT_RES_CONTENT.toString()));
    }

    @Test
    @Transactional
    public void getAllResourcesByResCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resCode equals to DEFAULT_RES_CODE
        defaultResourceShouldBeFound("resCode.equals=" + DEFAULT_RES_CODE);

        // Get all the resourceList where resCode equals to UPDATED_RES_CODE
        defaultResourceShouldNotBeFound("resCode.equals=" + UPDATED_RES_CODE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResCodeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resCode in DEFAULT_RES_CODE or UPDATED_RES_CODE
        defaultResourceShouldBeFound("resCode.in=" + DEFAULT_RES_CODE + "," + UPDATED_RES_CODE);

        // Get all the resourceList where resCode equals to UPDATED_RES_CODE
        defaultResourceShouldNotBeFound("resCode.in=" + UPDATED_RES_CODE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resCode is not null
        defaultResourceShouldBeFound("resCode.specified=true");

        // Get all the resourceList where resCode is null
        defaultResourceShouldNotBeFound("resCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResNameIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resName equals to DEFAULT_RES_NAME
        defaultResourceShouldBeFound("resName.equals=" + DEFAULT_RES_NAME);

        // Get all the resourceList where resName equals to UPDATED_RES_NAME
        defaultResourceShouldNotBeFound("resName.equals=" + UPDATED_RES_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByResNameIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resName in DEFAULT_RES_NAME or UPDATED_RES_NAME
        defaultResourceShouldBeFound("resName.in=" + DEFAULT_RES_NAME + "," + UPDATED_RES_NAME);

        // Get all the resourceList where resName equals to UPDATED_RES_NAME
        defaultResourceShouldNotBeFound("resName.in=" + UPDATED_RES_NAME);
    }

    @Test
    @Transactional
    public void getAllResourcesByResNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resName is not null
        defaultResourceShouldBeFound("resName.specified=true");

        // Get all the resourceList where resName is null
        defaultResourceShouldNotBeFound("resName.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resType equals to DEFAULT_RES_TYPE
        defaultResourceShouldBeFound("resType.equals=" + DEFAULT_RES_TYPE);

        // Get all the resourceList where resType equals to UPDATED_RES_TYPE
        defaultResourceShouldNotBeFound("resType.equals=" + UPDATED_RES_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResTypeIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resType in DEFAULT_RES_TYPE or UPDATED_RES_TYPE
        defaultResourceShouldBeFound("resType.in=" + DEFAULT_RES_TYPE + "," + UPDATED_RES_TYPE);

        // Get all the resourceList where resType equals to UPDATED_RES_TYPE
        defaultResourceShouldNotBeFound("resType.in=" + UPDATED_RES_TYPE);
    }

    @Test
    @Transactional
    public void getAllResourcesByResTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resType is not null
        defaultResourceShouldBeFound("resType.specified=true");

        // Get all the resourceList where resType is null
        defaultResourceShouldNotBeFound("resType.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByAppDescIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where appDesc equals to DEFAULT_APP_DESC
        defaultResourceShouldBeFound("appDesc.equals=" + DEFAULT_APP_DESC);

        // Get all the resourceList where appDesc equals to UPDATED_APP_DESC
        defaultResourceShouldNotBeFound("appDesc.equals=" + UPDATED_APP_DESC);
    }

    @Test
    @Transactional
    public void getAllResourcesByAppDescIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where appDesc in DEFAULT_APP_DESC or UPDATED_APP_DESC
        defaultResourceShouldBeFound("appDesc.in=" + DEFAULT_APP_DESC + "," + UPDATED_APP_DESC);

        // Get all the resourceList where appDesc equals to UPDATED_APP_DESC
        defaultResourceShouldNotBeFound("appDesc.in=" + UPDATED_APP_DESC);
    }

    @Test
    @Transactional
    public void getAllResourcesByAppDescIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where appDesc is not null
        defaultResourceShouldBeFound("appDesc.specified=true");

        // Get all the resourceList where appDesc is null
        defaultResourceShouldNotBeFound("appDesc.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByCstdModuleIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where cstdModule equals to DEFAULT_CSTD_MODULE
        defaultResourceShouldBeFound("cstdModule.equals=" + DEFAULT_CSTD_MODULE);

        // Get all the resourceList where cstdModule equals to UPDATED_CSTD_MODULE
        defaultResourceShouldNotBeFound("cstdModule.equals=" + UPDATED_CSTD_MODULE);
    }

    @Test
    @Transactional
    public void getAllResourcesByCstdModuleIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where cstdModule in DEFAULT_CSTD_MODULE or UPDATED_CSTD_MODULE
        defaultResourceShouldBeFound("cstdModule.in=" + DEFAULT_CSTD_MODULE + "," + UPDATED_CSTD_MODULE);

        // Get all the resourceList where cstdModule equals to UPDATED_CSTD_MODULE
        defaultResourceShouldNotBeFound("cstdModule.in=" + UPDATED_CSTD_MODULE);
    }

    @Test
    @Transactional
    public void getAllResourcesByCstdModuleIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where cstdModule is not null
        defaultResourceShouldBeFound("cstdModule.specified=true");

        // Get all the resourceList where cstdModule is null
        defaultResourceShouldNotBeFound("cstdModule.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByResContentIsEqualToSomething() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resContent equals to DEFAULT_RES_CONTENT
        defaultResourceShouldBeFound("resContent.equals=" + DEFAULT_RES_CONTENT);

        // Get all the resourceList where resContent equals to UPDATED_RES_CONTENT
        defaultResourceShouldNotBeFound("resContent.equals=" + UPDATED_RES_CONTENT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResContentIsInShouldWork() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resContent in DEFAULT_RES_CONTENT or UPDATED_RES_CONTENT
        defaultResourceShouldBeFound("resContent.in=" + DEFAULT_RES_CONTENT + "," + UPDATED_RES_CONTENT);

        // Get all the resourceList where resContent equals to UPDATED_RES_CONTENT
        defaultResourceShouldNotBeFound("resContent.in=" + UPDATED_RES_CONTENT);
    }

    @Test
    @Transactional
    public void getAllResourcesByResContentIsNullOrNotNull() throws Exception {
        // Initialize the database
        resourceRepository.saveAndFlush(resource);

        // Get all the resourceList where resContent is not null
        defaultResourceShouldBeFound("resContent.specified=true");

        // Get all the resourceList where resContent is null
        defaultResourceShouldNotBeFound("resContent.specified=false");
    }

    @Test
    @Transactional
    public void getAllResourcesByUiappIsEqualToSomething() throws Exception {
        // Initialize the database
        Uiapp uiapp = UiappResourceIT.createEntity(em);
        em.persist(uiapp);
        em.flush();
        resource.addUiapp(uiapp);
        resourceRepository.saveAndFlush(resource);
        Long uiappId = uiapp.getId();

        // Get all the resourceList where uiapp equals to uiappId
        defaultResourceShouldBeFound("uiappId.equals=" + uiappId);

        // Get all the resourceList where uiapp equals to uiappId + 1
        defaultResourceShouldNotBeFound("uiappId.equals=" + (uiappId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultResourceShouldBeFound(String filter) throws Exception {
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(resource.getId().intValue())))
            .andExpect(jsonPath("$.[*].resCode").value(hasItem(DEFAULT_RES_CODE)))
            .andExpect(jsonPath("$.[*].resName").value(hasItem(DEFAULT_RES_NAME)))
            .andExpect(jsonPath("$.[*].resType").value(hasItem(DEFAULT_RES_TYPE.toString())))
            .andExpect(jsonPath("$.[*].appDesc").value(hasItem(DEFAULT_APP_DESC)))
            .andExpect(jsonPath("$.[*].cstdModule").value(hasItem(DEFAULT_CSTD_MODULE)))
            .andExpect(jsonPath("$.[*].resContent").value(hasItem(DEFAULT_RES_CONTENT)));

        // Check, that the count call also returns 1
        restResourceMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultResourceShouldNotBeFound(String filter) throws Exception {
        restResourceMockMvc.perform(get("/api/resources?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restResourceMockMvc.perform(get("/api/resources/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingResource() throws Exception {
        // Get the resource
        restResourceMockMvc.perform(get("/api/resources/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateResource() throws Exception {
        // Initialize the database
        resourceService.save(resource);

        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Update the resource
        Resource updatedResource = resourceRepository.findById(resource.getId()).get();
        // Disconnect from session so that the updates on updatedResource are not directly saved in db
        em.detach(updatedResource);
        updatedResource
            .resCode(UPDATED_RES_CODE)
            .resName(UPDATED_RES_NAME)
            .resType(UPDATED_RES_TYPE)
            .appDesc(UPDATED_APP_DESC)
            .cstdModule(UPDATED_CSTD_MODULE)
            .resContent(UPDATED_RES_CONTENT);

        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedResource)))
            .andExpect(status().isOk());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
        Resource testResource = resourceList.get(resourceList.size() - 1);
        assertThat(testResource.getResCode()).isEqualTo(UPDATED_RES_CODE);
        assertThat(testResource.getResName()).isEqualTo(UPDATED_RES_NAME);
        assertThat(testResource.getResType()).isEqualTo(UPDATED_RES_TYPE);
        assertThat(testResource.getAppDesc()).isEqualTo(UPDATED_APP_DESC);
        assertThat(testResource.getCstdModule()).isEqualTo(UPDATED_CSTD_MODULE);
        assertThat(testResource.getResContent()).isEqualTo(UPDATED_RES_CONTENT);
    }

    @Test
    @Transactional
    public void updateNonExistingResource() throws Exception {
        int databaseSizeBeforeUpdate = resourceRepository.findAll().size();

        // Create the Resource

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restResourceMockMvc.perform(put("/api/resources")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(resource)))
            .andExpect(status().isBadRequest());

        // Validate the Resource in the database
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteResource() throws Exception {
        // Initialize the database
        resourceService.save(resource);

        int databaseSizeBeforeDelete = resourceRepository.findAll().size();

        // Delete the resource
        restResourceMockMvc.perform(delete("/api/resources/{id}", resource.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Resource> resourceList = resourceRepository.findAll();
        assertThat(resourceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resource.class);
        Resource resource1 = new Resource();
        resource1.setId(1L);
        Resource resource2 = new Resource();
        resource2.setId(resource1.getId());
        assertThat(resource1).isEqualTo(resource2);
        resource2.setId(2L);
        assertThat(resource1).isNotEqualTo(resource2);
        resource1.setId(null);
        assertThat(resource1).isNotEqualTo(resource2);
    }
}
