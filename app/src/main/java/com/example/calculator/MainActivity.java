package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    TextView btn_1,btn_2,btn_3,btn_4,btn_5,btn_6,btn_7,btn_8,btn_9,btn_0,btn_dot,btn_plus,btn_minus,btn_multi,btn_division,btn_module,btn_equal,btn_AllClear,btn_Clear;
    TextView InputText;
    TextView OutputText;
    String Expression;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        InputText = findViewById(R.id.inputTxt);
        OutputText = findViewById(R.id.OutTxt);

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_0 = findViewById(R.id.btn_0);

        btn_dot = findViewById(R.id.btn_Dot);
        btn_plus = findViewById(R.id.btn_Add);
        btn_minus = findViewById(R.id.btn_Minus);
        btn_multi = findViewById(R.id.btn_Multi);
        btn_division = findViewById(R.id.btn_div);
        btn_module = findViewById(R.id.btn_module);
        btn_equal = findViewById(R.id.btn_Equal);
        btn_AllClear = findViewById(R.id.btn_AC);
        btn_Clear = findViewById(R.id.btn_C);

        btn_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"1");
            }
        });

        btn_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"2");
            }
        });

        btn_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"3");
            }
        });

        btn_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"4");
            }
        });

        btn_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"5");
            }
        });

        btn_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"6");
            }
        });

        btn_7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"7");
            }
        });

        btn_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"8");
            }
        });

        btn_9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"9");
            }
        });

        btn_0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();
                InputText.setText(Expression +"0");
            }
        });

        btn_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOperator(".");
            }
        });

        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOperator("+");
            }
        });

        btn_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOperator("-");
            }
        });

        btn_multi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOperator("x");
            }
        });

        btn_division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOperator("÷");
            }
        });

        btn_module.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetOperator("%");
            }
        });

        btn_AllClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputText.setText("");
                OutputText.setText("");
                Expression="";
            }
        });
        btn_Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Expression != null && Expression.length() > 0) {
                    // Remove the last character from the expression
                    Expression = Expression.substring(0, Expression.length() - 1);
                    InputText.setText(Expression);
                } else {
                    InputText.setText("");
                }
            }
        });

        btn_equal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Expression = InputText.getText().toString();

                if (Expression.isEmpty()) {
                    OutputText.setText("");
                } else {
                    Expression = Expression.replaceAll("x", "*");
                    Expression = Expression.replaceAll("÷", "/");

                    // Check if the expression contains only an operand followed by an operator
                    if (Expression.matches("^[+*/%.].*|.*[+\\-*/%.]$")) {
                        OutputText.setText("Invalid expression");
                    } else {
                        Context rhino = Context.enter();
                        rhino.setOptimizationLevel(-1);

                        String Result;

                        Scriptable scriptable = rhino.initStandardObjects();
                        Result = rhino.evaluateString(scriptable, Expression, "javascript", 1, null).toString();
                        if (Result.equals("Infinity")) {
                            OutputText.setText("Undefined");
                        } else {
                            OutputText.setText(Result);
                        }
                    }
                }
            }
        });


    }
    private void SetOperator(String operator) {
        Expression = InputText.getText().toString();

        if (Expression.isEmpty()) {
            // If the expression is empty, allow typing a negative number
            Expression += operator;
        } else {
            // Check if the last character of the expression is an operator or not
            if (isOperator(Expression.charAt(Expression.length() - 1))) {
                // if yes then replace the last operator with the new operator
                Expression = Expression.substring(0, Expression.length() - 1) + operator;
            } else {
                // if no then append the operator to the expression
                Expression += operator;
            }
        }

        InputText.setText(Expression);
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == 'x' || c == '÷' || c == '%' || c=='.';
    }

}