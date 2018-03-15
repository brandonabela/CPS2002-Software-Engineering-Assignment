package calculator;

import com.sun.org.apache.xpath.internal.operations.Equals;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert.*;

public class CalculatorTest {
    private Calculator calculator;
    @Before
    public void setup(){
        calculator = new Calculator();
    }

    @After
    public void cleanup(){
        calculator = null;
    }


    @Test
    public void addFuncTest(){
        Assert.assertEquals(5,calculator.add(2,3));
    }

    @Test
    public void divideFuncTest(){
        Assert.assertEquals(3,calculator.divide(2,6));

    }

    @Test
    public void divideZeraFuncTest(){
        org.junit.Assert.assertEquals(0,calculator.divide(0,1));
    }
}
