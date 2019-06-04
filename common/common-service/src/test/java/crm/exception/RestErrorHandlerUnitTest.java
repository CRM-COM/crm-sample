package crm.exception;

import crm.security.JwtService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RestErrorHandlerUnitTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testExceptionMsg() {
        expectedException.expect(MicroserviceException.class);
        expectedException.expectMessage("Not found");

        throw new MicroserviceException("Not found");
    }

    @Test
    public void testExceptionKeyNameMsg() {
        String email = "osc@email.com";

        expectedException.expect(MicroserviceException.class);
        expectedException.expectMessage("Not found by email: " + email);

        throw new MicroserviceException("Not found by email: " + email);
    }

    @Test
    public void a() {
        new JwtService().decode("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJScGduMlJ1c0FYY2hTRGdaZXBtOGtmbENLSTVNR015STZwUnJuMEZLLUwwIn0.eyJqdGkiOiI3MGU5NjgyNi03NjdmLTQyZTYtOGNhZS0zYWQ5NjYwN2I0M2IiLCJleHAiOjE1NTkzMDU2MDIsIm5iZiI6MCwiaWF0IjoxNTU5MzA1MzAyLCJpc3MiOiJodHRwczovL2tleWNsb2FrLmNybWNsb3VkYXBpLmNvbS9hdXRoL3JlYWxtcy9jcm0tZGV2IiwiYXVkIjoiYWNjb3VudCIsInN1YiI6IjczZDUwOWFkLWY2NTMtNDlkNy1hNDY2LWRkNjMzZmRhZGJiMSIsInR5cCI6IkJlYXJlciIsImF6cCI6ImNybS1kZXYiLCJhdXRoX3RpbWUiOjAsInNlc3Npb25fc3RhdGUiOiJkNzg4YTAxMy03MjdlLTRiN2ItOGQ5NC1mNTk0MjQ5MGJjMWYiLCJhY3IiOiIxIiwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiYWRtaW4iLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7ImFjY291bnQiOnsicm9sZXMiOlsibWFuYWdlLWFjY291bnQiLCJtYW5hZ2UtYWNjb3VudC1saW5rcyIsInZpZXctcHJvZmlsZSJdfX0sInNjb3BlIjoicHJvZmlsZSBlbWFpbCIsImVtYWlsX3ZlcmlmaWVkIjpmYWxzZSwiZ2VuZGVyIjoibWFsZSIsInByZWZlcnJlZF91c2VybmFtZSI6ImFkbWluIn0.dWnfzjtwntNZwJ3_KyTzkbAjQKhd_kcAYouQFiiw0FknKOD4hbMjlAl4_0PepHG7mMA6jFnFiPydc15lTplZX1VncuPZ-XMBqn4JKBGwpKQVxtRqy9aazbydEa-J86D9-tyBt24eb1rKrELFLIz7ed793rDuI7k4C58MEk1ZFXF6UCNMhVpoFtuoOQbUVWJXqvY4WgH8-3wbFtIgbPHgXMfpe_e4DCUvvZPj8bDIdcgWIqKJcvVERXuJZcgaEFmyKqhwJL3d5ZuN0mkpA7m5o0_zzCbMEc7KAlWKzbEJDsrtV7m1RouqZhJo037UuRH27pa3jM3cgpNI3eVBy2iKQQ");
    }
}
