package nonogram;



import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class AssignTest.
 */
public class AssignTest
{
    /**
     * Default constructor for test class AssignTest
     */
    public AssignTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    @Test
    public void GetColTest()
    {
        nonogram.Assign assign1 = new nonogram.Assign(3, 2, 1);
        assertEquals(2, assign1.getCol());
    }
}

