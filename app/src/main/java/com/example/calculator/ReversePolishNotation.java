package com.example.calculator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ReversePolishNotation {

    private List<SymbolInExpression> result;
    private int countNumbers;
    private int countOperations;

    public List<SymbolInExpression> getResult(){
        return result;
    }
    public int getCountNumbers(){
        return countNumbers;
    }
    public int getCountOperations(){
        return countOperations;
    }

    //Определяем приоритет операции
    private int GetPriority(char symbol)
    {
        if (symbol=='+' || symbol=='-')
            return 1;
        else if (symbol=='*' || symbol=='/')
            return 2;
        else return 0;
    }
    //Преображаем инфиксное выражение в ОПЗ
    public void TransformToRPN(String expression)
    {
        Stack<SymbolInExpression> stack = new Stack<>();
        result = new ArrayList<>();
        countNumbers=0;
        countOperations=0;
        boolean isUnaryMinus = false;
        for (int i=0;i<expression.length();i++)
        {
            char symbol = expression.charAt(i);
            if (symbol == '(')
            {
                stack.push(new SymbolInExpression(symbol));
            }
            else if (symbol == ')')
            {
                SymbolInExpression topStack;
                topStack = stack.pop();
                while (topStack.getOperand()!= '(') {
                    result.add(topStack);
                    topStack = stack.pop();
                }
            }
            else if (Character.isDigit(symbol))
            {
                String tempForCreateNumber = "";
                if (isUnaryMinus) {
                    tempForCreateNumber += "-";
                    isUnaryMinus=false;
                }
                while (i<expression.length() && (Character.isDigit(expression.charAt(i)) || expression.charAt(i)=='.'))
                {
                    tempForCreateNumber+=expression.charAt(i);
                    i++;
                }
                i--;
                result.add(new SymbolInExpression(Double.parseDouble(tempForCreateNumber)));
                countNumbers++;
            }
            else {
                if (symbol=='-' && (i==0 || !Character.isDigit(expression.charAt(i-1))))
                    isUnaryMinus=true;
                else
                {
                    int priorityOperation = GetPriority(symbol);
                    int priorityPrevOperation = !stack.empty() ? GetPriority(stack.peek().getOperand()) : 0;
                    while (!stack.empty() && (priorityOperation == priorityPrevOperation || priorityOperation < priorityPrevOperation)) {
                        result.add(stack.pop());
                        priorityPrevOperation = !stack.empty() ? GetPriority(stack.peek().getOperand()) : 0;
                    }
                    stack.push(new SymbolInExpression(symbol));
                    countOperations++;
                }
            }
        }
        while (!stack.empty())
            result.add(stack.pop());
    }
    //Считаем выражение в ОПЗ
    public double CalculateRPN()
    {
        Stack<Double> answerOnExpression = new Stack<>();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getOperand() == ' ')
                answerOnExpression.push(result.get(i).getNumber());
            else {
                double resultOperand = 0;
                switch (result.get(i).getOperand()) {
                    case '+': {
                        resultOperand = answerOnExpression.pop() + answerOnExpression.pop();

                        break;
                    }
                    case '-': {
                        resultOperand = -answerOnExpression.pop() - -answerOnExpression.pop();

                        break;
                    }
                    case '*': {
                        resultOperand = answerOnExpression.pop() * answerOnExpression.pop();

                        break;
                    }
                    case '/': {
                        resultOperand = 1 / answerOnExpression.pop() * answerOnExpression.pop();

                        break;
                    }
                }
                answerOnExpression.push(resultOperand);
            }
        }
        return answerOnExpression.pop();
    }
}
