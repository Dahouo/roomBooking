package com.talenteum.roombooking.web.rest;

import com.talenteum.roombooking.RoomBookingApp;

import com.talenteum.roombooking.domain.Asset;
import com.talenteum.roombooking.repository.AssetRepository;
import com.talenteum.roombooking.service.AssetService;
import com.talenteum.roombooking.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static com.talenteum.roombooking.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AssetResource REST controller.
 *
 * @see AssetResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RoomBookingApp.class)
public class AssetResourceIntTest {

    private static final Boolean DEFAULT_TELECONFERENCE = false;
    private static final Boolean UPDATED_TELECONFERENCE = true;

    private static final Boolean DEFAULT_PROJECTOR = false;
    private static final Boolean UPDATED_PROJECTOR = true;

    private static final Boolean DEFAULT_WHITEBOARD = false;
    private static final Boolean UPDATED_WHITEBOARD = true;

    private static final Boolean DEFAULT_A_C = false;
    private static final Boolean UPDATED_A_C = true;

    private static final Boolean DEFAULT_INTERNET = false;
    private static final Boolean UPDATED_INTERNET = true;

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private AssetService assetService;

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

    private MockMvc restAssetMockMvc;

    private Asset asset;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetResource assetResource = new AssetResource(assetService);
        this.restAssetMockMvc = MockMvcBuilders.standaloneSetup(assetResource)
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
    public static Asset createEntity(EntityManager em) {
        Asset asset = new Asset()
            .teleconference(DEFAULT_TELECONFERENCE)
            .projector(DEFAULT_PROJECTOR)
            .whiteboard(DEFAULT_WHITEBOARD)
            .aC(DEFAULT_A_C)
            .internet(DEFAULT_INTERNET);
        return asset;
    }

    @Before
    public void initTest() {
        asset = createEntity(em);
    }

    @Test
    @Transactional
    public void createAsset() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isCreated());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate + 1);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.isTeleconference()).isEqualTo(DEFAULT_TELECONFERENCE);
        assertThat(testAsset.isProjector()).isEqualTo(DEFAULT_PROJECTOR);
        assertThat(testAsset.isWhiteboard()).isEqualTo(DEFAULT_WHITEBOARD);
        assertThat(testAsset.isaC()).isEqualTo(DEFAULT_A_C);
        assertThat(testAsset.isInternet()).isEqualTo(DEFAULT_INTERNET);
    }

    @Test
    @Transactional
    public void createAssetWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetRepository.findAll().size();

        // Create the Asset with an existing ID
        asset.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetMockMvc.perform(post("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAssets() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get all the assetList
        restAssetMockMvc.perform(get("/api/assets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(asset.getId().intValue())))
            .andExpect(jsonPath("$.[*].teleconference").value(hasItem(DEFAULT_TELECONFERENCE.booleanValue())))
            .andExpect(jsonPath("$.[*].projector").value(hasItem(DEFAULT_PROJECTOR.booleanValue())))
            .andExpect(jsonPath("$.[*].whiteboard").value(hasItem(DEFAULT_WHITEBOARD.booleanValue())))
            .andExpect(jsonPath("$.[*].aC").value(hasItem(DEFAULT_A_C.booleanValue())))
            .andExpect(jsonPath("$.[*].internet").value(hasItem(DEFAULT_INTERNET.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getAsset() throws Exception {
        // Initialize the database
        assetRepository.saveAndFlush(asset);

        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", asset.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(asset.getId().intValue()))
            .andExpect(jsonPath("$.teleconference").value(DEFAULT_TELECONFERENCE.booleanValue()))
            .andExpect(jsonPath("$.projector").value(DEFAULT_PROJECTOR.booleanValue()))
            .andExpect(jsonPath("$.whiteboard").value(DEFAULT_WHITEBOARD.booleanValue()))
            .andExpect(jsonPath("$.aC").value(DEFAULT_A_C.booleanValue()))
            .andExpect(jsonPath("$.internet").value(DEFAULT_INTERNET.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAsset() throws Exception {
        // Get the asset
        restAssetMockMvc.perform(get("/api/assets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAsset() throws Exception {
        // Initialize the database
        assetService.save(asset);

        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Update the asset
        Asset updatedAsset = assetRepository.findById(asset.getId()).get();
        // Disconnect from session so that the updates on updatedAsset are not directly saved in db
        em.detach(updatedAsset);
        updatedAsset
            .teleconference(UPDATED_TELECONFERENCE)
            .projector(UPDATED_PROJECTOR)
            .whiteboard(UPDATED_WHITEBOARD)
            .aC(UPDATED_A_C)
            .internet(UPDATED_INTERNET);

        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAsset)))
            .andExpect(status().isOk());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
        Asset testAsset = assetList.get(assetList.size() - 1);
        assertThat(testAsset.isTeleconference()).isEqualTo(UPDATED_TELECONFERENCE);
        assertThat(testAsset.isProjector()).isEqualTo(UPDATED_PROJECTOR);
        assertThat(testAsset.isWhiteboard()).isEqualTo(UPDATED_WHITEBOARD);
        assertThat(testAsset.isaC()).isEqualTo(UPDATED_A_C);
        assertThat(testAsset.isInternet()).isEqualTo(UPDATED_INTERNET);
    }

    @Test
    @Transactional
    public void updateNonExistingAsset() throws Exception {
        int databaseSizeBeforeUpdate = assetRepository.findAll().size();

        // Create the Asset

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetMockMvc.perform(put("/api/assets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(asset)))
            .andExpect(status().isBadRequest());

        // Validate the Asset in the database
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAsset() throws Exception {
        // Initialize the database
        assetService.save(asset);

        int databaseSizeBeforeDelete = assetRepository.findAll().size();

        // Delete the asset
        restAssetMockMvc.perform(delete("/api/assets/{id}", asset.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Asset> assetList = assetRepository.findAll();
        assertThat(assetList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asset.class);
        Asset asset1 = new Asset();
        asset1.setId(1L);
        Asset asset2 = new Asset();
        asset2.setId(asset1.getId());
        assertThat(asset1).isEqualTo(asset2);
        asset2.setId(2L);
        assertThat(asset1).isNotEqualTo(asset2);
        asset1.setId(null);
        assertThat(asset1).isNotEqualTo(asset2);
    }
}
