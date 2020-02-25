package com.example.calculator;

public class SymbolInExpression {
    private double number;
    private Character operand;
    private String isFractional = "";

    public SymbolInExpression(int value)
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

    public String getIsFractional() {
        return isFractional;
    }

    public void setIsFractional(String isFractional) {
        this.isFractional = isFractional;
    }

    public void addValue(int number){
        String tempForCreateNumber;
        if (isFractional.equals(".")) {

            if (this.number % 1 == 0)
                tempForCreateNumber = "" + (int)this.number + isFractional + number;
            else
                tempForCreateNumber= "" + this.number + number;
            this.number=Double.parseDouble(tempForCreateNumber);
        }
        else {
            tempForCreateNumber="" + (int)this.number + number;
            this.number=Double.parseDouble(tempForCreateNumber);
        }
    }

    public void enableFractional()
    {
        isFractional=".";
    }
}
