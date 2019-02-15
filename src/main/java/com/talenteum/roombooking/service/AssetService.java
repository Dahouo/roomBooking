package com.talenteum.roombooking.service;

import com.talenteum.roombooking.domain.Asset;
import com.talenteum.roombooking.repository.AssetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing Asset.
 */
@Service
@Transactional
public class AssetService {

    private final Logger log = LoggerFactory.getLogger(AssetService.class);

    private final AssetRepository assetRepository;

    public AssetService(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    /**
     * Save a asset.
     *
     * @param asset the entity to save
     * @return the persisted entity
     */
    public Asset save(Asset asset) {
        log.debug("Request to save Asset : {}", asset);
        return assetRepository.save(asset);
    }

    /**
     * Get all the assets.
     *
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public List<Asset> findAll() {
        log.debug("Request to get all Assets");
        return assetRepository.findAll();
    }


    /**
     * Get one asset by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<Asset> findOne(Long id) {
        log.debug("Request to get Asset : {}", id);
        return assetRepository.findById(id);
    }

    /**
     * Delete the asset by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Asset : {}", id);        assetRepository.deleteById(id);
    }
}
