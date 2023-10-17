package at.acolic.gk7acalculator;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.graphics.Color;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private Button berechnenButton; // Der Button "Berechnen"
    private TextView resultTextView;
    private RadioGroup operationRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber1 = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        berechnenButton = findViewById(R.id.button); // Aktualisierte ID des Buttons
        resultTextView = findViewById(R.id.textView);
        operationRadioGroup = findViewById(R.id.radioGroup);

        resultTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Clear the result when the user touches the output field
                resultTextView.setText("");
                return true;
            }
        });

        berechnenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected operation
                int selectedOperationId = operationRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedOperationRadioButton = findViewById(selectedOperationId);

                // Get the input values
                double num1 = Double.parseDouble(editTextNumber1.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());

                // Perform the calculation based on the selected operation
                String operation = selectedOperationRadioButton.getText().toString();
                double result = 0;

                switch (operation) {
                    case "+":
                        result = num1 + num2;
                        break;
                    case "-":
                        result = num1 - num2;
                        break;
                    case "*":
                        result = num1 * num2;
                        break;
                    case "/":
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            resultTextView.setText("Division by zero is not allowed!");
                            return;
                        }
                        break;
                }

                // Set text color based on the sign of the result
                if (result < 0) {
                    resultTextView.setTextColor(Color.RED); // Negative numbers are shown in red
                } else {
                    resultTextView.setTextColor(Color.BLACK); // Non-negative numbers are shown in black
                }

                // Display the result in the resultTextView
                resultTextView.setText(String.valueOf(result));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Set the background color of the "Berechnen" button to green
        berechnenButton.setBackgroundColor(Color.GREEN);
    }
}
