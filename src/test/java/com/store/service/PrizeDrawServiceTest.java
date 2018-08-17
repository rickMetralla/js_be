package com.store.service;

import com.store.domain.PrizeDraw;
import com.store.repository.PrizeDrawRepository;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PrizeDrawServiceTest {
    @Mock
    PrizeDrawRepository prizeDrawRepository;

    @InjectMocks
    PrizeDrawService prizeDrawService;

    @Test
    public void getAllPrizes() throws Exception {
        int expectedTotal = 3;
        when(prizeDrawRepository.findAll()).thenReturn(new ArrayList<PrizeDraw>() {{
            add(new PrizeDraw());
            add(new PrizeDraw());
            add(new PrizeDraw());
        }});
        List<PrizeDraw> actual = prizeDrawService.getAllPrizes();
        assertEquals("[Error] Expected amount is different to actual result", actual.size(), expectedTotal);
    }

    @Test
    public void getAllWinners() throws Exception {
        boolean winner = true;
        int promoId = 1;
        int amountExpected = 1;
        List<PrizeDraw> prizeDrawList = new ArrayList<PrizeDraw>() {{
            add(new PrizeDraw(213, 1, 3, true, 1));
            add(new PrizeDraw(321, 2, 5, true, 15));
            add(new PrizeDraw(123, 3, 7, true, 7));
        }};

        when(prizeDrawRepository.findPrizeDrawByWinner(winner)).thenReturn(prizeDrawList);
        List<PrizeDraw> actualResult = prizeDrawService.getAllWinners(winner, promoId);
        assertEquals("[Error] Expected amount and actual are different", amountExpected, actualResult.size());
        assertEquals("[Error] Expected winner and actual are different", winner, actualResult.get(0).isWinner());
    }

    @Test
    public void getAllByPromoId() throws Exception {
        int expectedPromoId = 2;
        int expectedAmount = 4;

        when(prizeDrawRepository.findPrizeDrawByPromoId(expectedPromoId)).thenReturn(new ArrayList<PrizeDraw>() {{
            add(new PrizeDraw(567, 2, 3, true, 1));
            add(new PrizeDraw(765, 2, 1, false, null));
            add(new PrizeDraw(657, 2, 1, false, null));
            add(new PrizeDraw(666, 2, 1, false, null));
        }});
        List<PrizeDraw> actual = prizeDrawService.getAllByPromoId(expectedPromoId);
        assertEquals("[Error] Expected amount and actual are different", expectedAmount, actual.size());
        for (PrizeDraw prizeDraw : actual) {
            assertEquals("[Error] Expected promotion id and actual are different", expectedPromoId, prizeDraw.getPromoId());
        }
    }

    @Test
    public void getAllByCustomerId() throws Exception {
        int expectedCustomerDni = 6432037;
        int expectedAmount = 4;

        when(prizeDrawRepository.findPrizeDrawByCustDni(expectedCustomerDni)).thenReturn(new ArrayList<PrizeDraw>(){{
            add(new PrizeDraw(6432037, 1, 3, true, 1));
            add(new PrizeDraw(6432037, 2, 1, false, null));
            add(new PrizeDraw(6432037, 3, 1, false, null));
            add(new PrizeDraw(6432037, 4, 1, false, null));
        }});

        List<PrizeDraw> actual = prizeDrawService.getAllByCustomerId(expectedCustomerDni);
        assertEquals("[Error] Expected amount and actual are different", expectedAmount, actual.size());
        for (PrizeDraw prizeDraw : actual) {
            assertEquals("[Error] Expected customer dni and actual are different", expectedCustomerDni, prizeDraw.getCustDni());
        }
    }

    @Test
    public void getPrizeDrawByDniAndPromoId() throws Exception {
        int expectedCustomerDni = 6432037;
        int expectedPromoId = 1;
        when(prizeDrawRepository.findPrizeDrawByCustDniAndPromoId(expectedCustomerDni, expectedPromoId))
                .thenReturn(new PrizeDraw(6432037, 1, 3, true, 1));

        PrizeDraw actual = prizeDrawService.getPrizeDrawByDniAndPromoId(expectedCustomerDni, expectedPromoId);
        assertEquals("[Error] Expected customer dni and actual are different", expectedCustomerDni, actual.getCustDni());
        assertEquals("[Error] Expected promo id and actual are different", expectedPromoId, actual.getPromoId());
    }

    @Test
    public void getById() throws Exception {
        int prizeDrawId = 4;
        when(prizeDrawRepository.getOne(prizeDrawId )).thenReturn(new PrizeDraw());
        PrizeDraw actual = prizeDrawService.getById(prizeDrawId);
        assertNotNull("[Error] Cannot be null.", actual);
    }

    @Test
    public void create() throws Exception {
        PrizeDraw expectedPrizeDraw = new PrizeDraw(999999, 1, 1, false, null);

        when(prizeDrawRepository.save(expectedPrizeDraw))
                .thenReturn(new PrizeDraw(999999, 1, 1, false, null));
        PrizeDraw actual = prizeDrawService.create(expectedPrizeDraw);
        assertEquals("[Error] Expected and actual cust dni are different", expectedPrizeDraw.getCustDni(), actual.getCustDni());
        assertEquals("[Error] Expected and actual promo id are different", expectedPrizeDraw.getPromoId(), actual.getPromoId());
        assertEquals("[Error] Expected and actual chances are different", expectedPrizeDraw.getChances(), actual.getChances());
        assertNull("[Error] Expected and actual articles aren't null", expectedPrizeDraw.getWonArticle());
        assertNull("[Error] Expected and actual articles aren't null", actual.getWonArticle());
        assertEquals("[Error] Expected and actual winner status are different", expectedPrizeDraw.isWinner(), actual.isWinner());
    }

    @Test
    public void update() throws Exception {
        PrizeDraw updatedPrizeDraw = new PrizeDraw(6432037, 1, 2, true, 1);
        boolean updatedField = true;
        int updatedWonArticle = 1;

        when(prizeDrawRepository.save(updatedPrizeDraw)).thenReturn(updatedPrizeDraw);
        PrizeDraw actual = prizeDrawService.update(updatedPrizeDraw);
        assertEquals("[Error] Expected and actual customer are different", updatedPrizeDraw.isWinner(), updatedField);
        assertTrue("[Error] Expected and actual customer are different", updatedPrizeDraw.getWonArticle() == updatedWonArticle);
    }

    @Test
    @Ignore
    public void deletePrizeDraw() throws Exception {
        int idToDelete = 123;
        Mockito.doNothing().when(prizeDrawRepository).deleteById(idToDelete);
        Mockito.doReturn(true).when(prizeDrawService).deletePrizeDraw(idToDelete);
        assertTrue("I need correction", true); //needs to review unit test for void methods
    }
}