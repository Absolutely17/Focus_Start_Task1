package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {
    private List<SymbolInExpression> result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void calculateExpression(View view) {
        EditText editText = (EditText) findViewById(R.id.editExpression);
        String expression = editText.getText().toString();
        transform(expression);
        TextView showText = findViewById(R.id.showText);
        showText.setText(Double.toString(calculate()));
    }


    public void transform(String expression)
    {
        Stack<SymbolInExpression> stack = new Stack<>();
        result = new ArrayList<>();
        boolean previousDigit = false;
        boolean IsUnaryOperation = false;
        boolean IsFractionalNumber = false;
        for (int i=0;i<expression.length();i++)
        {
            char symbol = expression.charAt(i);
            switch (symbol)
            {
                case '(':
                    {
                        previousDigit=false;
                    stack.push(new SymbolInExpression(symbol));
                    break;
                }
                case ')':
                {
                    previousDigit=false;
                    SymbolInExpression topStack;
                    topStack = stack.pop();
                    while (topStack.getOperand()!='(')
                    {
                        result.add(topStack);
                        topStack=stack.pop();
                    }
                    break;
                }
                case '+' :
                {
                    previousDigit=false;
                    while (!stack.empty() && (stack.peek().getOperand()=='*' || stack.peek().getOperand() == '/' || stack.peek().getOperand() == '-'))
                        result.add(stack.pop());
                    stack.push(new SymbolInExpression(symbol));
                    break;
                }
                case '-':
                {
                    previousDigit=false;
                    if (i > 0 && Character.isDigit(expression.charAt(i-1))) {
                        while (!stack.empty() && (stack.peek().getOperand() == '*' || stack.peek().getOperand() == '/' || stack.peek().getOperand() == '+'))
                            result.add(stack.pop());
                        stack.push(new SymbolInExpression(symbol));
                    }
                    else IsUnaryOperation = true;
                    break;
                }
                case '*':
                {
                    previousDigit=false;
                    while (!stack.empty() && (stack.peek().getOperand()=='*' || stack.peek().getOperand() == '/'))
                        result.add(stack.pop());
                    stack.push(new SymbolInExpression(symbol));
                    break;
                }
                case '/':
                {
                    previousDigit=false;
                    while (!stack.empty() && (stack.peek().getOperand()=='*' || stack.peek().getOperand() == '/'))
                        result.add(stack.pop());
                    stack.push(new SymbolInExpression(symbol));
                    break;
                }
                case '.':
                {
                    result.get(result.size()-1).enableFractional();
                    previousDigit=true;
                    break;
                }
                default:
                {
                    if (Character.isDigit(symbol))
                    {
                        if (IsUnaryOperation) {
                            result.add(new SymbolInExpression(-Character.getNumericValue(symbol)));
                            IsUnaryOperation=false;
                            previousDigit = true;
                            break;
                        }
                        else if (previousDigit) {
                            result.get(result.size() - 1).addValue(Character.getNumericValue(symbol));
                            break;
                        }
                        previousDigit = true;
                        result.add(new SymbolInExpression(Character.getNumericValue(symbol)));

                    }
                }

            }
        }
        while (!stack.empty())
            result.add(stack.pop());
    }

    public double calculate()
    {
        Stack<Double> answerOnExpression = new Stack<>();
        for (int i=0;i<result.size();i++)
        {
            if (result.get(i).getOperand()==null)
                answerOnExpression.push(result.get(i).getNumber());
            else {
                double resultOperand = 0;
                switch (result.get(i).getOperand()){
                    case '+':
                    {
                        resultOperand=answerOnExpression.pop() + answerOnExpression.pop();

                        break;
                    }
                    case '-':
                    {
                        resultOperand=-answerOnExpression.pop() - -answerOnExpression.pop();

                        break;
                    }
                    case '*':
                    {
                        resultOperand=answerOnExpression.pop() * answerOnExpression.pop();

                        break;
                    }
                    case '/':
                    {
                        resultOperand=1/answerOnExpression.pop() * answerOnExpression.pop();

                        break;
                    }
                    case '^':
                    {
                        resultOperand=Math.pow(answerOnExpression.pop(),answerOnExpression.pop());

                    }
                }
                answerOnExpression.push(resultOperand);
            }
        }
        return answerOnExpression.pop();
    }
}
