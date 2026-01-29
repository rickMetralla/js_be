package com.store.service;

import com.store.domain.Prize;
import com.store.repository.PrizeRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrizeServiceTest {

    @Mock
    PrizeRepository prizeRepository;

    @InjectMocks
    PrizeService prizeService;

    @Test
    public void getAllPrizesTest() throws Exception {
        int expectedPrizes = 3;
        when(prizeRepository.findAll()).thenReturn(new ArrayList<Prize>(){{
            add(new Prize());
            add(new Prize());
            add(new Prize());
        }});
        List<Prize> actual = prizeService.getAllPrizes();
        assertTrue("[Error] Expected amount of prizes are different", expectedPrizes == actual.size());
    }

    @Test
    public void getAllPrizesByPromoIdTest() throws Exception {
        int promoId = 23;
        int expectedPrizeAmount = 3;
        when(prizeRepository.getPrizesByPromoId(promoId)).thenReturn(new ArrayList<Prize>(){{
            add(new Prize(1, 23, "oven", 1));
            add(new Prize(2, 23, "iron", 1));
            add(new Prize(3, 23, "micro", 1));
        }});
        List<Prize> actual = prizeService.getAllPrizesByPromoId(promoId);
        assertEquals("", expectedPrizeAmount, actual.size());
        for (Prize prize: actual) {
            assertEquals("[Error] Expected promotion id is different", promoId, prize.getPromoId());
        }
    }

    @Test
    @Ignore
    public void getOnePrizeByIdTest() throws Exception {
    }

    @Test
    @Ignore
    public void createTest() throws Exception {
    }

    @Test
    @Ignore
    public void updateTest() throws Exception {
    }

    @Test
    @Ignore
    public void deleteByIdTest() throws Exception {
    }

}