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

    public int CheckRPNOnErrors(List<SymbolInExpression> result, int countNumbers, int countOperations)
    {
        int openingBracket=0;
        int closingBracket=0;
        if (countNumbers!=countOperations+1)
            return 2;
        for (int i=0;i<result.size();i++) {
                if (result.get(i).getOperand() == '(')
                    openingBracket++;
                else if (result.get(i).getOperand() == ')')
                    closingBracket++;
        }
        if (openingBracket!=closingBracket)
            return 1;
        return 0;
    }
}
