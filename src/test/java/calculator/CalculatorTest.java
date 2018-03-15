package calculator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
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
    public void testAddPositiveValues(){
        Assert.assertEquals(5,calculator.add(2, 3));
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

    @Test
    public void testDividePositiveValues()
    {
        Assert.assertEquals(30, calculator.divide(2, 6));
    }

    @Test
    public void divideZeroFuncTest()
    {
        org.junit.Assert.assertEquals(0, calculator.divide(0,1));
    }

    @After
    public void cleanup()
    {
        calculator = null;
    }
}
