package net.atos.etax.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import org.hibernate.cache.jcache.ConfigSettings;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(javax.cache.CacheManager cacheManager) {
        return hibernateProperties -> hibernateProperties.put(ConfigSettings.CACHE_MANAGER, cacheManager);
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            createCache(cm, net.atos.etax.repository.UserRepository.USERS_BY_LOGIN_CACHE);
            createCache(cm, net.atos.etax.repository.UserRepository.USERS_BY_EMAIL_CACHE);
            createCache(cm, net.atos.etax.domain.User.class.getName());
            createCache(cm, net.atos.etax.domain.Authority.class.getName());
            createCache(cm, net.atos.etax.domain.User.class.getName() + ".authorities");
            createCache(cm, net.atos.etax.domain.BankAccount.class.getName());
            createCache(cm, net.atos.etax.domain.BankAccount.class.getName() + ".operations");
            createCache(cm, net.atos.etax.domain.Label.class.getName());
            createCache(cm, net.atos.etax.domain.Label.class.getName() + ".operations");
            createCache(cm, net.atos.etax.domain.Operation.class.getName());
            createCache(cm, net.atos.etax.domain.Operation.class.getName() + ".labels");
            createCache(cm, net.atos.etax.domain.StdCodes.class.getName());
            createCache(cm, net.atos.etax.domain.StdCodes.class.getName() + ".stdCodesDescs");
            createCache(cm, net.atos.etax.domain.StdCodesDesc.class.getName());
            createCache(cm, net.atos.etax.domain.StdCodesGroup.class.getName());
            createCache(cm, net.atos.etax.domain.StdCodesGroup.class.getName() + ".stdCodes");
            createCache(cm, net.atos.etax.domain.StdCodesProp.class.getName());
            createCache(cm, net.atos.etax.domain.ExchangeRate.class.getName());
            createCache(cm, net.atos.etax.domain.PublicHoliday.class.getName());
            createCache(cm, net.atos.etax.domain.Office.class.getName());
            createCache(cm, net.atos.etax.domain.OfficeRelationship.class.getName());
            createCache(cm, net.atos.etax.domain.OfficeAreaCode.class.getName());
            createCache(cm, net.atos.etax.domain.OfficeTaxFunc.class.getName());
            createCache(cm, net.atos.etax.domain.OfficeWeekday.class.getName());
            createCache(cm, net.atos.etax.domain.UserInfo.class.getName());
            // jhipster-needle-ehcache-add-entry
        };
    }

    private void createCache(javax.cache.CacheManager cm, String cacheName) {
        javax.cache.Cache<Object, Object> cache = cm.getCache(cacheName);
        if (cache != null) {
            cm.destroyCache(cacheName);
        }
        cm.createCache(cacheName, jcacheConfiguration);
    }
}
