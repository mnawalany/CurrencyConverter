package com.zooplus.challenge.currencyConverter.service;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class IdGeneratorTest {

    private IdGenerator idGenerator = new IdGenerator();

    @Test
    public void shouldGenerateDifferentIds() {
        // Given
        Set<String> ids = new HashSet<>();
        int iterations = 10000;

        // When
        for (int i=0; i<iterations; i++) {
            ids.add(idGenerator.generateId());
        }

        // Then
        assertThat(ids).hasSize(iterations);
    }

}