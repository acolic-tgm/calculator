package at.acolic.gk7acalculator;

import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editTextNumber;
    private EditText editTextNumber2;
    private RadioGroup radioGroup;
    private Button calculateButton;
    private TextView outputTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextNumber = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        radioGroup = findViewById(R.id.radioGroup);
        calculateButton = findViewById(R.id.button);
        outputTextView = findViewById(R.id.outputTextView);

        // Set up onTouchListener for clearing the outputTextView
        outputTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Clear the outputTextView when touched
                outputTextView.setText("");
                return true;
            }
        });

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the values entered by the user
                double num1 = Double.parseDouble(editTextNumber.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());
                double result = 0;

                // Get the selected radio button ID from the RadioGroup
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton selectedRadioButton = findViewById(selectedId);

                // Perform the selected calculation
                if (selectedRadioButton != null) {
                    String operation = selectedRadioButton.getText().toString();
                    if (operation.equals("+")) {
                        result = num1 + num2;
                    } else if (operation.equals("-")) {
                        result = num1 - num2;
                    } else if (operation.equals("*")) {
                        result = num1 * num2;
                    } else if (operation.equals("/")) {
                        if (num2 != 0) {
                            result = num1 / num2;
                        } else {
                            outputTextView.setText("Division by zero is not allowed.");
                            return;
                        }
                    }
                }

                // Set the text color based on the result
                if (result < 0) {
                    outputTextView.setTextColor(Color.RED);
                } else {
                    outputTextView.setTextColor(Color.BLACK);
                }

                // Display the result in the outputTextView
                outputTextView.setText(String.valueOf(result));
            }
        });
    }
}
