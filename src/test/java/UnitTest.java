import org.hamcrest.core.Is;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;

public class UnitTest {


    @org.junit.Test
    public void should_1_equals_1() {

        assertThat(1, Is.is(1));

        boolean a = true;
        boolean b = false;
        a &= b;
        assertFalse(a);
    }

}
