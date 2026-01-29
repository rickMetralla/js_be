package com.store.controller;

import com.store.domain.Prize;
import com.store.domain.Rank;
import com.store.service.PrizeService;
import com.store.service.RankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PrizeRankController.class)
@WebAppConfiguration
public class PrizeRankControllerTest {

    @Autowired
    private MockMvc controllerMvc;

    @MockBean
    private PrizeService prizeService;

    @MockBean
    private RankService rankService;

    @Test
    public void getAllRanks() throws Exception {
        given(rankService.getAllRank()).willReturn(new ArrayList<Rank>(){{
            add(new Rank());
            add(new Rank());
            add(new Rank());
        }});
        controllerMvc.perform(get("/prizes/ranks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)));
        verify(rankService, times(1)).getAllRank();
        verifyNoMoreInteractions(rankService);
    }

    @Test
    public void saveRankPost() throws Exception {
    }

    @Test
    public void getAllPrizes() throws Exception {
        given(prizeService.getAllPrizes()).willReturn(new ArrayList<Prize>(){{
            add(new Prize());
            add(new Prize());
            add(new Prize());
        }});
        controllerMvc.perform(get("/prizes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)));
        verify(prizeService, times(1)).getAllPrizes();
        verifyNoMoreInteractions(prizeService);
    }

    @Test
    public void getAllPrizesByPromoId() throws Exception {
        int promoId = 4;
        given(prizeService.getAllPrizesByPromoId(promoId)).willReturn(new ArrayList<Prize>(){{
            add(new Prize(1,4,"oven", 1));
            add(new Prize(2,4,"iron", 1));
            add(new Prize(3,4,"microwave", 2));
        }});
        controllerMvc.perform(get("/prizes/4"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].promoId", is(4)))
                .andExpect(jsonPath("$[1].promoId", is(4)))
                .andExpect(jsonPath("$[2].promoId", is(4)));

        verify(prizeService, times(1)).getAllPrizesByPromoId(promoId);
        verifyNoMoreInteractions(prizeService);
    }

    @Test
    public void savePrize() throws Exception {
        Prize prize = new Prize(1,4,"oven", 1);
        given(prizeService.create(prize)).willReturn(prize);

        controllerMvc.perform(post("/prizes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(prize)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.id", is(1)))
//                .andExpect(jsonPath("$.promoId", is(4)))
//                .andExpect(jsonPath("$.name", is("oven")))
//                .andExpect(jsonPath("$.rank", is(1)));

//        verify(prizeService, times(1)).create(prize);
//        verifyNoMoreInteractions(prizeService);
    }

    static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}