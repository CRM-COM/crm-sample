package crm.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@RunWith(SpringRunner.class)
@ContextConfiguration
@SpringBootTest(classes = RestErrorHandlerIntegrationTest.TestApplication.class)
@EnableWebMvc
@ActiveProfiles("test")
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@AutoConfigureWebClient
@WebAppConfiguration
public class RestErrorHandlerIntegrationTest {

    @SpringBootApplication
    @ComponentScan(basePackages = {"crm"})
    public static class TestApplication {
    }

    private static final String ENUM_NOT_FOUND_EXCEPTION_JSON = "{\"errorCode\":\"NOT_FOUND\",\"errorDescription\":\"Enum not found by externalId id\"}";

    private static final String ILLEGAL_ARGUMENT_EXCEPTION_JSON = "{\"errorCode\":\"BAD_REQUEST\",\"errorDescription\":\"illegal\"}";

    private static final String RUNTIME_EXCEPTION_JSON = "{\"errorCode\":\"BAD_REQUEST\",\"errorDescription\":\"Something wrong happened.\"}";

    private static final String FILE_NOT_FOUND_EXCEPTION_JSON = "{\"errorCode\":\"NOT_FOUND\",\"errorDescription\":\"File not found exception\"}";

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testHandleMicroserviceException() throws Exception {
        mockMvc.perform(get("/microservice"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(ENUM_NOT_FOUND_EXCEPTION_JSON));
    }

    @Test
    public void testHandleIllegalArgumentException() throws Exception {
        mockMvc.perform(get("/illegal"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(ILLEGAL_ARGUMENT_EXCEPTION_JSON));
    }

    @Test
    public void testHandleFileNotFoundException() throws Exception {
        mockMvc.perform(get("/fileNotFound"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(FILE_NOT_FOUND_EXCEPTION_JSON));
    }

    @Test
    public void testHandleInValidDto() throws Exception {
        mockMvc.perform(get("/invalid"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void testCustomExceptionWithResponseStatus() throws Exception {
        mockMvc.perform(get("/custom508"))
                .andExpect(status().isLoopDetected())
                .andExpect(content().string(""));
    }

    @Test
    public void testException() throws Exception {
        mockMvc.perform(get("/exception"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(""));
    }
}