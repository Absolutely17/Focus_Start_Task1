package com.example.calculator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateInputExpression {

    public boolean InitialFormatCheck(String expression)
    {
        Pattern pattern = Pattern.compile("^[\\d\\+\\/\\*\\.\\- \\(\\)]*$");
        Matcher matcher = pattern.matcher(expression);
        if (!matcher.matches() || expression.isEmpty())
            return false;
        return true;
    }

    public boolean CheckRPNOnErrors(List<SymbolInExpression> result, int countNumbers, int countOperations)
    {
        for(int i=0;i<result.size()-1;i++)
            if (result.get(i).getOperand()!=null && result.get(i+1).getOperand()!=null)
            {
                return false;
            }
        if (countNumbers!=countOperations+1)
            return false;
        return true;
    }
}
