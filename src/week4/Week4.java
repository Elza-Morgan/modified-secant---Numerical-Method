/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package week4;

import java.util.ArrayList;
import java.util.Scanner;
import net.objecthunter.exp4j.ExpressionBuilder;

/**
 *
 * @author Elza Morgan
 */
public class Week4 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<Double> X= new ArrayList<>();
        ArrayList<Double> f_of_x= new ArrayList<>();
        ArrayList<Double> f_dash_of_x= new ArrayList<>();
        ArrayList<Boolean>con1=new ArrayList<>(); //f(x)<=0
        ArrayList<Boolean>con2=new ArrayList<>();//max number of itreation
        
        
        Scanner input=new Scanner(System.in);
        System.out.print("Enter the Equation for f(x): ");
        String equation1=input.next();
        System.out.print("Enter your value for 'x': ");
        double x=input.nextDouble();
        System.out.print("Enter your value for Tol: ");
        double tol=input.nextDouble();
        System.out.print("Enter your value for 'N': ");
        double n=input.nextDouble();
        
        String equation2=dev(equation1);
        
        //cal for first index
        double x_i=x;
        x_i=Math.round(x_i*10000.0)/10000.0;
        X.add(x_i);
        
        double external_f_of_x=equation(equation1,x);
        external_f_of_x=Math.round(external_f_of_x*10000.0)/10000.0;
        f_of_x.add(external_f_of_x);
        
         double external_f_dash_of_x=equation(equation2,x);
         external_f_dash_of_x=Math.round(external_f_dash_of_x*10000.0)/10000.0;
        f_dash_of_x.add(external_f_dash_of_x);
        
        boolean cond1;
        boolean cond2;
        int i;
        for(i=0 ; i<n ;i++)
            {  
              cond1=false;//f(x)<=0
              cond2=false;  //max number of itration
                
            x_i=x_i-(f_of_x.get(i)/f_dash_of_x.get(i));
            x_i=Math.round(x_i*10000.0)/10000.0;
            X.add(x_i);
            
            if(Math.abs(f_of_x.get(i))<= tol)
            {
                cond1=true;
                con1.add(cond1);
                break;
            
            }else{ 
                 con2.add(cond2);   
                 con1.add(cond1);
                    double y=equation(equation1,x_i);//f(x)
                    y=Math.round(y*10000.0)/10000.0;
                    f_of_x.add(y);

                    double j=equation(equation2,x_i);//f'(x)
                    j=Math.round(j*10000.0)/10000.0;
                    f_dash_of_x.add(j);
            }
            
        }    
        if(i == n){
                        cond2=true;
                        con2.add(cond2);          
                  }   
                    
                    else{
                        cond2=false;
                        con2.add(cond2);  
                    }
        printtable(X,f_of_x,f_dash_of_x,con1,con2);
        
    }
    
    public static double equation(String equation, double z) //f(x)        
    {
               net.objecthunter.exp4j.Expression exp = new ExpressionBuilder(equation).variables("x").build().setVariable("x", z);
                double result = exp.evaluate();
                return result;
            
   }
//      public static double equation22(String equation, double z)  //f'(x)       
//    {
//               net.objecthunter.exp4j.Expression exp = new ExpressionBuilder(equation).variables("x").build().setVariable("x", z);
//                double result = exp.evaluate();
//                return result;           
//   }
     
    public static void printtable( ArrayList<Double> X,ArrayList<Double> f_of_x,ArrayList<Double> f_dash_of_x, ArrayList<Boolean>con1, ArrayList<Boolean>con2)
    {
         System.out.printf("%-20s%-15s%-15s%-20s%s%n", "X", "F(x)", "F'(x)","Con1","Con2");
           for (int i = 0; i < f_of_x.size(); i++) {
            System.out.printf("%-20s%-15s%-15s%-20s%s%n",
                        X.get(i), f_of_x.get(i),
                        f_dash_of_x.get(i),con1.get(i),con2.get(i));
            }
           System.out.println("The Root is: " + f_of_x.get(f_of_x.size()-1) );
        
    }
    
    public static String dev(String equation){
        int length=equation.length();
        for(int i=0; i< length ; i++){
        if(equation.charAt(i)=='^')//checks any with this symbol 
        {
            char pow=equation.charAt(i+1);//takes the number after the power signe
            char new_pow=(char)((int)pow-1);// used to sub the power
//        x^2-10
//2x^1-10
//2x^2-10
//4x^1-10
//sin(x^1)-10
//sin(1x^0)
        if(i==1) // this used if x is has no cofficient
        {//used to convert from char to string and then concate the rest of the equation
          equation=Character.toString(pow)+equation;
           //this is used to start from 0 and i+2 will be replaced by x and then 
           //concate with the rest of equation
          equation = equation.substring(0, i+2)+ new_pow + equation.substring(i + 3);   
        }
            if(i>1)//when x has a coff
            {  
                int num1= Character.getNumericValue(pow);//takes the power
                int num2= Character.getNumericValue(equation.charAt(i-2)); //takes the coff of x
                int num3=num1*num2;
                char num4=Character.forDigit(num3,10); //this function is used convert it to decimal 
                equation = equation.substring(0, i+1)+ new_pow + equation.substring(i + 2);
                if(equation.charAt(0)!='s')//not sin
                {
                  equation = equation.substring(0, i-2)+ num4+ equation.substring(i-1);
                }
                 else{
                       equation = equation.substring(0, i-1)+ pow + equation.substring(i-1);
                      }
               
            
            }
             i+=2;
             length=equation.length();   
        }
        //2x^2-10
        else if((equation.charAt(i)=='x')) //searching for x only
        {
              if(i!=equation.length()-1)//thats if there something else before the x
              {
                  if(equation.charAt(i+1)!='^')
                  {     //this is used to remove the x^0
                        equation = equation.substring(0, i) + equation.substring(i + 1);
                        length= equation.length();
                    }
              }
              else if(i==equation.length()-1) //thats when x is found at the end of the equ
              { //x^2-x-->
                  equation = equation.substring(0, i) + equation.substring(i+1);//removes the x
                  equation = equation.substring(0, i-1) + equation.substring(i);//removes the sign
                  equation = equation.substring(0, i-2) + equation.substring(i-1);
               length= equation.length();
              }
              
          }
            //thats when digit is found at the end of the string
        //x^2-10
             else if(Character.isDigit(equation.charAt(i))&&(i==equation.length()-1))
             {
               equation = equation.substring(0, i) + equation.substring(i + 1);//removes the current value
               if(Character.isDigit(equation.charAt(i-1)))//2 digits found at the end of the string
               { 
                equation = equation.substring(0, i-1) + equation.substring(i);//removes the second digit
                equation = equation.substring(0, i-2) + equation.substring(i-1);//removes signe
               }else
               equation = equation.substring(0, i-1) + equation.substring(i);//remove the sign with one digit   
             length= equation.length();
          } 
        
        }
        if(equation.charAt(0)=='s'){
              equation = equation.replace("sin(1x^0)", "cos(x)");
          }
      System.out.println("F'(x) = "+equation);
      return equation;
   
        
}
}

