package com.nexon.maple.connectionTest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class ControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_url()  throws Exception{
        //when
        ResultActions indexUrl = mockMvc.perform(get("/"));
        ResultActions failUrl = mockMvc.perform(get("/2"));

        //then
        indexUrl.andExpect(status().isOk());
        failUrl.andExpect(status().isNotFound());
    }
}
