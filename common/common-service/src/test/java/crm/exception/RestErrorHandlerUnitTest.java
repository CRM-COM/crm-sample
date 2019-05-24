package crm.exception;

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
}
