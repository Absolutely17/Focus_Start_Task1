package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = findViewById(R.id.editExpression);
        editText.setOnKeyListener(this);
    }
    @Override
    public boolean onKey(View view, int keyCode, KeyEvent event){
        if(event.getAction() == KeyEvent.ACTION_DOWN &&
                (keyCode == KeyEvent.KEYCODE_ENTER))
        {
            calculateExpression(view);
            return true;
        }
        return false;
    }
    public void calculateExpression(View view) {
        EditText editText = findViewById(R.id.editExpression);
        TextView showText = findViewById(R.id.showText);
        String expression = editText.getText().toString();
        ReversePolishNotation reversePN = new ReversePolishNotation();
        ValidateInputExpression validator = new ValidateInputExpression();
        //Производим первоначальную проверку выражения на соответствие формату
        if (!validator.InitialFormatCheck(expression)) {
            editText.setError("Строка имеет неверный формат.");
            return;
        }
        //Преобразовываем из инфиксной в ОПЗ
        reversePN.TransformToRPN(expression);
        //Проверка полученного выражения в ОПЗ на ошибки
        switch (validator.CheckRPNOnErrors(reversePN.getResult(), reversePN.getCountNumbers(), reversePN.getCountOperations())) {
            case 1: {
                editText.setError("Скобки расставлены неверно.");
                return;
            }
            case 2: {
                editText.setError("Количество чисел не соответствует количеству операций.");
                return;
            }
        }
        //Подсчет выражения
        String responseOnExpression;
        double resultExpression = 0;
        try {
            resultExpression = reversePN.CalculateRPN();
        }
        catch(Exception e){
            editText.setError("В введенной строке ошибка. Исправьте её и повторите.");
        }
        responseOnExpression = new DecimalFormat("#.##").format(resultExpression);
        showText.setText(responseOnExpression);
    }
}
