package Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Context {
    private Stack<Double> stack = new Stack<>();
    private Map<String, Double> map = new HashMap<>();

     public Double pop(){
         return stack.pop();
     }

     public void push(Double num){
         stack.push(num);
     }

     public void put_variable(String variable, Double value){
         if(map.containsKey(variable)){
             map.replace(variable, value);
         }else{
             map.put(variable, value);
         }
     }

     public Double get_variable_val(String variable){
         return map.get(variable);
     }

     public boolean isVariable_in_stack(String variable){
         return map.containsKey(variable);
     }

     public int stack_size(){
         return stack.size();
     }

     public Double peek_stack(){
         return stack.peek();
     }
}
