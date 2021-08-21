package br.com.dextra.potter.web;

import br.com.dextra.potter.PotterApplication;
import br.com.dextra.potter.web.errors.RestExceptionMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.function.Supplier;

import static br.com.dextra.potter.web.TestUtil.createFormattingConversionService;
import static java.util.Arrays.asList;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.skyscreamer.jsonassert.JSONCompareMode.LENIENT;
import static org.skyscreamer.jsonassert.JSONCompareMode.NON_EXTENSIBLE;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@ActiveProfiles("test")
@SpringBootTest(classes = PotterApplication.class)
public abstract class ResourceBaseIT {

    @Autowired
    protected MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    protected PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    protected RestExceptionMapper restExceptionMapper;

    @Autowired
    protected EntityManager em;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected Validator validator;

    protected MockMvc restMockMvc;

    @Value("${app.service.domain-uri}")
    protected String appServiceDomainUri;

    @BeforeEach
    public void setup() {
        openMocks(this);
        this.restMockMvc = standaloneSetup(getResources())
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .setControllerAdvice(restExceptionMapper)
                .setConversionService(createFormattingConversionService())
                .addPlaceholderValue("app.service.domain-uri", appServiceDomainUri)
                .setMessageConverters(jacksonMessageConverter)
                .setValidator(validator)
                .build();
    }

    public Object getResource() {
        return null;
    }

    public Object[] getResources() {
        Object resource = getResource();
        return resource != null ? new Object[]{resource} : null;
    }

    public ResultMatcher jsonPath_for_badRequest(String message) {
        return jsonPath("$[?(@.message=='" + message + "')]").exists();
    }


    public void checkResult(MvcResult result, Object object) throws JsonProcessingException, UnsupportedEncodingException, JSONException {
        String responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(object), responseBody, NON_EXTENSIBLE);
    }

    public void checkResult(MvcResult result, List<?> list) throws JsonProcessingException, UnsupportedEncodingException, JSONException {
        String responseBody = result.getResponse().getContentAsString();
        JSONAssert.assertEquals(objectMapper.writeValueAsString(list), responseBody, LENIENT);
    }

    public ResultMatcher jsonPath_badRequestQueryParameters(String parameter) {
        return jsonPath("$.detail").value(Matchers.stringContainsInOrder(asList("Required", "parameter '" + parameter + "'")));
    }

    public ResultMatcher jsonPathExpressionForNotNull(String atr) {
        return jsonPath("$..fieldErrors[?(@.field=='" + atr + "' && @.code=='NotNull')]").exists();
    }

    public ResultMatcher jsonPathExpressionForSize(String atr, int max, int min) {
        return jsonPath("$..fieldErrors[?(@.field=='" + atr + "' && @.code=='Size' && @.message contains '" + max + "' && @.message contains '" + min + "')]").exists();
    }

    public ResultMatcher jsonPathExpressionForIncorrectIsChildOfTipoBasico(String atr) {
        return jsonPath("$..fieldErrors[?(@.field=='" + atr + "' && @.code=='IsChildOfTipoBasico')]").exists();
    }

    public ResultMatcher jsonPath_for_badRequest(String entityName, String errorKey) {
        return jsonPath("$[?(@.message=='" + entityName + "' && @.status=='400' && @.errorKey=='" + errorKey + "')]").exists();
    }

    public ResultActions verify_badRequest_for_requestParam(Supplier<ResultActions> resultActionsConsumer, String parameter) throws Exception {
        return resultActionsConsumer.get().andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.detail").value("Required String parameter '" + parameter + "' is not present"))
                .andExpect(jsonPath("$.message").value("error.http.400"))
                .andExpect(jsonPath("$.title").value("Bad Request"));
    }

    public ResultActions verify_badRequest_for(Supplier<ResultActions> resultActionsConsumer, String message) throws Exception {
        return resultActionsConsumer.get().andExpect(status().isBadRequest()).andExpect(jsonPath("$.message").value(message));
    }
}
