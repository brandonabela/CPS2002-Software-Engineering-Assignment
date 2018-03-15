package calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CalculatorTest
{
    private Calculator calculator;

    @Before
    public void Setup()
    {
        calculator = new Calculator();
    }

    @Test
    public void testSubtractPositiveValues()
    {
        Assert.assertEquals(5, calculator.subtract(5, 10));
    }

    @Test
    public void testSubtractNegativeValues()
    {
        Assert.assertEquals(-15, calculator.subtract(5, -10));
    }

    @Test
    public void testMultiplyPositiveValues()
    {
        Assert.assertEquals(24, calculator.multiply(3, 8));
    }

    @Test
    public void testMultiplyNegativeValues()
    {
        Assert.assertEquals(-8, calculator.multiply(2, -4));
    }
}
