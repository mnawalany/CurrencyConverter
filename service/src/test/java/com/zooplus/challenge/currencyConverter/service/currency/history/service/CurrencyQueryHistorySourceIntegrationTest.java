package com.zooplus.challenge.currencyConverter.service.currency.history.service;

import com.zooplus.challenge.currencyConverter.service.SpringProfile;
import com.zooplus.challenge.currencyConverter.service.config.ServiceConfiguration;
import com.zooplus.challenge.currencyConverter.service.currency.Currency;
import com.zooplus.challenge.currencyConverter.service.currency.CurrencyQuery;
import com.zooplus.challenge.currencyConverter.service.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfiguration.class)
@ActiveProfiles(SpringProfile.TEST)
public class CurrencyQueryHistorySourceIntegrationTest {

    @Autowired
    private CurrencyQueryHistorySource currencyQueryHistorySource;

    @Test
    public void shouldReturnEmptyListWhenNoCurrencyQuery() {
        // Given
        User user = new User("id", "username");

        // When
        List<CurrencyQuery> recentQueries = currencyQueryHistorySource.getRecentCurrencyQueries(user);

        // Then
        assertThat(recentQueries).isEmpty();
    }

    @Test
    public void shouldAddAndReturnCurrencyQuery() {
        // Given
        CurrencyQuery currencyQuery = createCurrencyQuery(1);
        User user = new User("id", "username");

        // When
        currencyQueryHistorySource.addCurrencyQuery(user, currencyQuery);
        List<CurrencyQuery> recentQueries = currencyQueryHistorySource.getRecentCurrencyQueries(user);

        // Then
        assertThat(recentQueries).hasSize(1).contains(currencyQuery);
    }

    @Test
    public void shouldReturnDifferentQueriesForDifferentUsers() {
        // Given
        User user1 = new User("id1", "username1");
        User user2 = new User("id2", "username2");

        CurrencyQuery currencyQuery1 = createCurrencyQuery(1);
        CurrencyQuery currencyQuery2 = createCurrencyQuery(2);
        CurrencyQuery currencyQuery3 = createCurrencyQuery(3);

        // When
        currencyQueryHistorySource.addCurrencyQuery(user1, currencyQuery1);
        currencyQueryHistorySource.addCurrencyQuery(user2, currencyQuery2);
        currencyQueryHistorySource.addCurrencyQuery(user1, currencyQuery3);

        List<CurrencyQuery> recentQueries1 = currencyQueryHistorySource.getRecentCurrencyQueries(user1);
        List<CurrencyQuery> recentQueries2 = currencyQueryHistorySource.getRecentCurrencyQueries(user2);

        // Then
        assertThat(recentQueries1).hasSize(2).contains(currencyQuery1, currencyQuery3);
        assertThat(recentQueries2).hasSize(1).contains(currencyQuery2);
    }

    @Test
    public void shouldReturnOnlyLatestTenResults() {
        // Given
        User user = new User("id", "username");

        List<CurrencyQuery> queries = new LinkedList<>();
        for (int i=0; i<15; i++) {
            CurrencyQuery query = createCurrencyQuery(1);
            queries.add(query);
            currencyQueryHistorySource.addCurrencyQuery(user, query);
        }

        // When
        List<CurrencyQuery> recentQueries = currencyQueryHistorySource.getRecentCurrencyQueries(user);

        // Then
        assertThat(recentQueries).hasSize(10);
        for (int i=0; i<10; i++) {
            assertThat(recentQueries.get(i)).isEqualTo(queries.get(queries.size()-i-1));
        }
    }

    private CurrencyQuery createCurrencyQuery(int id) {
        return new CurrencyQuery(
                LocalDateTime.now(),
                new Currency("c1-"+id, "c1"),
                new BigDecimal(id*10),
                new Currency("c2-"+id, "c2"),
                new BigDecimal(id*20));
    }

}