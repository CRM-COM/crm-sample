package crm.model.crm;

import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CRMCreateMemberApiTest {

  @Test
  public void testMarshallRoundTrip() throws Exception {
    String expectedJson = "{\n"
        + "   \"token\" : \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c3IiOiJhX3N0ZXdhcmQiLCJvcmciOiJzdGFnaW5nIiwib3VuIjoiQjQ1MjlENUQwNkU3NDlCMDk3NkEzNTEyRUM0QUVERTYiLCJleHAiOiIxNTU5NTc3NzQ4IiwiaWF0IjoiMTU1OTU3MDU0OCIsImp0aSI6IkE5NkRCREI0NUI2MDRCMkZBQTA0RUMwRjAyMjdBMDhCIn0.O_TX-OfFOtuYzlII8tirO2UHpVJXALjBAmkJlTFrLkY\",\n"
        + "   \"type\" : \"PERSON\",\n"
        + "   \"title\" : \"Mr\",\n"
        + "   \"first_name\" : \"Tony\",\n"
        + "   \"last_name\" : \"Wilson\",\n"
        + "   \"demographics\" :\n"
        + "      {\n"
        + "         \"gender\" : \"MALE\",\n"
        + "         \"id_number\" : \"EXTERNAL_ID_FROM_DBMS\",\n"
        + "         \"date_of_birth\" :\n"
        + "            {\n"
        + "               \"day\" : 12,\n"
        + "               \"month\" : 5,\n"
        + "               \"year\" : 1981\n"
        + "            },\n"
        + "         \"phone_set\" :\n"
        + "            [\n"
        + "               {\n"
        + "                  \"type\" : \"LANDLINE\",\n"
        + "                  \"number\" : \"22114455\"\n"
        + "               }\n"
        + "            ],\n"
        + "         \"email_set\" :\n"
        + "            [\n"
        + "               {\n"
        + "                  \"type\" : \"PERSONAL\",\n"
        + "                  \"email_address\" : \"dhenz@gmail.com\"\n"
        + "               }\n"
        + "            ]\n"
        + "      }\n"
        + "}";

    ObjectMapper objectMapper = new ObjectMapper();
    String actualJson =
        objectMapper
            .writeValueAsString(objectMapper.readValue(expectedJson, CRMCreateMemberRequest.class));

    JSONAssert.assertEquals(expectedJson, actualJson, false);
  }

}
