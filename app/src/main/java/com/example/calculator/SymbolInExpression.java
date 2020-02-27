package com.example.calculator;

import java.math.BigDecimal;

public class SymbolInExpression {
    private double number;
    private Character operand;

    public SymbolInExpression(double value)
    {
        this.number = value;
    }
    public SymbolInExpression(char operand)
    {
        this.operand = operand;
    }

    public double getNumber() {
        return number;
    }

    public void setNumber(double number) {
        this.number = number;
    }

    public Character getOperand() {
        return operand;
    }

    public void setOperand(Character operand) {
        this.operand = operand;
    }



}
