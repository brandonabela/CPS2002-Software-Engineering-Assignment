package calculator;

public class Calculator
{
    public int add(int value1,int value2){
        return value1+value2;
    }

    public int subtract (int value1, int value2)
    {
        return value2 - value1;
    }

    public int multiply (int value1, int value2)
    {
        return value2 * value1;
    }

    public int divide(int value1, int value2)
    {
        if(value1 == 0)
        {
            return 0;
        }
        else
        {
            return value2/value1;
        }
    }
}
