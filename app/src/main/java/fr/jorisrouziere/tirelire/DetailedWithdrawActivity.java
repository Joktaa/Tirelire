package fr.jorisrouziere.tirelire;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

import fr.jorisrouziere.tirelire.room.Repository;

public class DetailedWithdrawActivity extends AppCompatActivity {
    private Repository repository;
    private HashMap<Double, Integer> detailedWithdrawData;

    private NumberPicker numberPicker10Centimes;
    private NumberPicker numberPicker20Centimes;
    private NumberPicker numberPicker50Centimes;
    private NumberPicker numberPicker1Euro;
    private NumberPicker numberPicker2Euro;


    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("detailedWithdrawData", detailedWithdrawData);
        intent.putExtra("test","test");
        setResult(RESULT_OK, intent);
        finish();

    }

    @SuppressLint("MissingInflatedId")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_detailed_withdrawn);

        repository = Repository.getInstance(getApplicationContext());
        detailedWithdrawData = (HashMap<Double, Integer>) getIntent().getSerializableExtra("detailedWithdrawData");

        numberPicker10Centimes = findViewById(R.id.detailWithdrawn_money_quantity_0_1);
        numberPicker20Centimes = findViewById(R.id.detailWithdrawn_money_quantity_0_2);
        numberPicker50Centimes = findViewById(R.id.detailWithdrawn_money_quantity_0_5);
        numberPicker1Euro = findViewById(R.id.detailWithdrawn_money_quantity_1);
        numberPicker2Euro = findViewById(R.id.detailWithdrawn_money_quantity_2);


        initListener();
        initData();
    }

    private void initListener() {
        initNumberPicker(numberPicker10Centimes,0.1);
        initNumberPicker(numberPicker20Centimes,0.2);
        initNumberPicker(numberPicker50Centimes,0.5);
        initNumberPicker(numberPicker1Euro,1.0);
        initNumberPicker(numberPicker2Euro,2.0);

        findViewById(R.id.detailWithdrawn_button).setOnClickListener(view -> onBackPressed());

    }

    private void initData() {
        if(detailedWithdrawData != null && !detailedWithdrawData.isEmpty()){
            numberPicker10Centimes.setValue(detailedWithdrawData.getOrDefault(0.1,0));
            numberPicker20Centimes.setValue(detailedWithdrawData.getOrDefault(0.2,0));
            numberPicker50Centimes.setValue(detailedWithdrawData.getOrDefault(0.5,0));
            numberPicker1Euro.setValue(detailedWithdrawData.getOrDefault(1.0,0));
            numberPicker2Euro.setValue(detailedWithdrawData.getOrDefault(2.0,0));
        }
    }

    private void initNumberPicker(NumberPicker np, Double type)
    {
        np.setMinValue(0);
        np.setMaxValue(20);
        np.setWrapSelectorWheel(true);
        np.setOnValueChangedListener((numberPicker, oldValue, newValue) -> detailedWithdrawData.replace(type,oldValue,newValue));
    }
}
