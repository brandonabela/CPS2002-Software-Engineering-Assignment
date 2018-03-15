package calculator;

public class Calculator {
  public int add(int value1,int value2){
      return value1+value2;
  }

  public int divide(int value1, int value2){
      if(value1 == 0){
          return 0;
      }else{
        return value2/value1;
      }
  }
}
