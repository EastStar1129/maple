package com.nexon.maple.connectionTest;

import com.nexon.maple.view.ViewController;
import com.nexon.maple.view.service.ViewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewController.class)
public class ControllerTest{

    @MockBean
    private ViewService viewService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void test_url()  throws Exception{
        //when
        ResultActions indexUrl = mockMvc.perform(get("/"));
        ResultActions failUrl = mockMvc.perform(get("/index2"));

        //then
        indexUrl.andExpect(status().isOk());
        failUrl.andExpect(status().isNotFound());
    }
}
