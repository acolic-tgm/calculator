package at.acolic.gk7acalculator;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText editTextNumber1;
    private EditText editTextNumber2;
    private Button berechnenButton;
    private Button msButton; // Memory Store Button
    private Button mrButton; // Memory Recall Button
    private TextView resultTextView;
    private RadioGroup operationRadioGroup;
    private SharedPreferences sharedPreferences;
    private double result = 0; // Klassevariable für das Ergebnis der Berechnung

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        editTextNumber1 = findViewById(R.id.editTextNumber);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        berechnenButton = findViewById(R.id.berechnenButton);
        msButton = findViewById(R.id.msButton);
        mrButton = findViewById(R.id.mrButton);
        resultTextView = findViewById(R.id.textView);
        operationRadioGroup = findViewById(R.id.radioGroup);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        resultTextView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                resultTextView.setText("");
                return true;
            }
        });

        berechnenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num1Str = editTextNumber1.getText().toString();
                String num2Str = editTextNumber2.getText().toString();

                if (num1Str.isEmpty() || num2Str.isEmpty()) {
                    return;
                }

                double num1 = Double.parseDouble(num1Str);
                double num2 = Double.parseDouble(num2Str);

                int selectedOperationId = operationRadioGroup.getCheckedRadioButtonId();
                RadioButton selectedOperationRadioButton = findViewById(selectedOperationId);

                String operation = selectedOperationRadioButton.getText().toString();

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
                            return;
                        }
                        break;
                    default:
                        return;
                }

                resultTextView.setText(String.valueOf(result));

                // Ändert die Textfarbe auf Rot, wenn das Ergebnis negativ ist
                if (result < 0) {
                    resultTextView.setTextColor(Color.RED);
                } else {
                    resultTextView.setTextColor(Color.WHITE);
                }
            }
        });

        msButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("result", (float) result);
                editor.apply();

                // Zeigt einen Toast mit der Meldung "Gespeichert" ("Saved")
                Toast.makeText(MainActivity.this, "Gespeichert", Toast.LENGTH_SHORT).show();
            }
        });

        mrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float storedResult = sharedPreferences.getFloat("result", 0);

                resultTextView.setText(String.valueOf(storedResult));

                // Ändert die Textfarbe auf Rot, wenn das geladene Ergebnis negativ ist
                if (storedResult < 0) {
                    resultTextView.setTextColor(Color.RED);
                } else {
                    resultTextView.setTextColor(Color.WHITE);
                }

                // Zeigt einen Toast mit der Meldung "Geladen" ("Loaded")
                Toast.makeText(MainActivity.this, "Geladen", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        berechnenButton.setBackgroundColor(getResources().getColor(R.color.green)); // Setzt die Hintergrundfarbe von "Berechnen" auf Grün
    }
}
