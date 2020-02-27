package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        if (!validator.CheckRPNOnErrors(reversePN.getResult(), reversePN.getCountNumbers(), reversePN.getCountOperations())) {
            editText.setError("Строка имеет ошибки. Исправьте их и повторите.");
            return;
        }
        //Подсчет выражения
        String responseOnExpression = new DecimalFormat("#.##").format(reversePN.CalculateRPN());
        showText.setText(responseOnExpression);
    }
}
