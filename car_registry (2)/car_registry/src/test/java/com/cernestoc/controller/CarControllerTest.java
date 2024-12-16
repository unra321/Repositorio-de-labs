package com.cernestoc.controller;

import com.cernestoc.controller.mapper.BrandMapper;
import com.cernestoc.controller.mapper.CarMapper;
import com.cernestoc.domain.Car;
import com.cernestoc.filter.JwtAuthenticationFilter;
import com.cernestoc.services.AuthenticationService;
import com.cernestoc.services.BrandService;
import com.cernestoc.services.CarService;
import com.cernestoc.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarController carController;

    @MockBean
    private CarService carService;

    @MockBean
    private BrandService brandService;

    @MockBean
    private BrandMapper brandMapper;

    @MockBean
    private UserService userService;

    @MockBean
    private CarMapper carMapper;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void setup(){
        //Init MockMvc Object and build
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
    }


    @Test
    @WithMockUser(username = "user", password = "password", roles = "CLIENT")
    void gerCar_Test() throws Exception{
        //Given
        Car car = new Car();
        car.setId(1);
        car.setModel("Ateca");

        //When
        when(carService.getCarById(1)).thenReturn(car);

        //Then
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/cars/1").with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Ateca"));

    }


}
