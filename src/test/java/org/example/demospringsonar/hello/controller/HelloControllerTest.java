package org.example.demospringsonar.hello.controller;

import org.example.demospringsonar.controller.hello;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(hello.class)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSayHello_withNameParameter() throws Exception {
        // Arrange: Define the name parameter
        String name = "John";

        // Act and Assert: Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                        .param("myName", name)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk()) // Ensure HTTP 200 status
                .andExpect(MockMvcResultMatchers.content().string("Hello John")) // Ensure response content
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)); // Ensure JSON content type
    }

    @Test
    public void testSayHello_withoutNameParameter() throws Exception {
        // Act and Assert: Perform the GET request without the name parameter and expect a 400 error
        mockMvc.perform(MockMvcRequestBuilders.get("/hello")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest()); // Ensure HTTP 400 status for missing param
    }
}
