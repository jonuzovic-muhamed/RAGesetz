package com.jonuzovic.ragesetz.infrastructure.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component
@PropertySource(value = "classpath:lawsources.yml", factory = YamlPropertySourceFactory.class)
@ConfigurationProperties(prefix = "law-sources")
public class LawSourcesProperties {
    private List<LawSource> laws = new ArrayList<>();;

    public List<LawSource> getLaws() {
        return laws;
    }

    public void setLaws(List<LawSource> laws) {
        this.laws = laws;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LawSource {
        private String code;
        private String url;
    }
}
