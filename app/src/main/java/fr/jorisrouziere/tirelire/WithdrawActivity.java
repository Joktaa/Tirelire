package fr.jorisrouziere.tirelire;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicReference;

import fr.jorisrouziere.tirelire.room.Repository;
import fr.jorisrouziere.tirelire.room.models.Piece;

public class WithdrawActivity extends AppCompatActivity {

    private Repository repository;

    private final HashMap<Double, Integer> detailedWithdrawData = new HashMap<>();

    private Double currentWantedValue = 0.0;

    private NumberPicker npEuro;
    private NumberPicker npCentime;
    private Button detailedWithdrawButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawn);

        repository = Repository.getInstance(getApplicationContext());

        initData();
        initListener();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                HashMap<Double, Integer> new_detailedWithdrawData = (HashMap<Double, Integer>) data.getSerializableExtra("detailedWithdrawData");
                updateNumberPickerWithDetail(detailedWithdrawData, new_detailedWithdrawData);
                detailedWithdrawData.clear();
                detailedWithdrawData.putAll(new_detailedWithdrawData);

            }
        }
    }

    private void initListener() {
        ImageButton refreshButton = findViewById(R.id.withdrawn_refreshButton);
        ImageButton goBackButton = findViewById(R.id.withdrawn_backToHomeButton);
        detailedWithdrawButton = findViewById(R.id.withdraw_detailedWithdrawnButton);
        Button withdrawButton = findViewById(R.id.withdrawn_withdrawnButton);

        initNumberPicker();

        //TODO add MQTT call
        //refreshButton.setOnClickListener(view -> );

        detailedWithdrawButton.setOnClickListener(view -> {
            Intent detailedWithdrawn = new Intent(this, DetailedWithdrawActivity.class);
            detailedWithdrawn.putExtra("detailedWithdrawData", detailedWithdrawData);
            startActivityForResult(detailedWithdrawn, 1);

        });
        //TODO add MQTT call
        //withdrawButton.setOnClickListener(view -> );
        goBackButton.setOnClickListener(view -> super.onBackPressed());
    }

    private void initNumberPicker() {
        npCentime.setMinValue(0);
        npCentime.setMaxValue(9);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            npCentime.setSelectionDividerHeight(10);
        }
        npCentime.setWrapSelectorWheel(true);
        npCentime.setDisplayedValues(new String[]{"0", "10", "20", "30", "40", "50", "60", "70", "80", "90"});
        npCentime.setOnValueChangedListener((numberPicker, old, newValue) -> {
            currentWantedValue -= new BigDecimal(old).divide(new BigDecimal("10.0")).doubleValue();
            currentWantedValue += new BigDecimal(newValue).divide(new BigDecimal("10.0")).doubleValue();
        });

        npEuro.setMinValue(0);
        npEuro.setMaxValue(76);
        npEuro.setWrapSelectorWheel(true);
        npEuro.setOnValueChangedListener((numberPicker, old, newValue) -> {
            currentWantedValue -= (float) old ;
            currentWantedValue += (float) newValue ;
        });

    }

    private void initData() {
        TextView currentMoney = findViewById(R.id.withdrawn_currentMoney);

        repository.getPieces().observe(this, (pieces -> {
            double currentMoneyValue = pieces.stream().mapToDouble(Piece::getTotalValue).sum();
            currentMoney.setText(currentMoneyValue + "€");
            pieces.forEach(piece -> detailedWithdrawData.put(piece.getValue(), 0));
        }));

        npCentime = findViewById(R.id.withdrawn_np_centimes);
        npEuro = findViewById(R.id.withdrawn_np_euros);
    }

    private void updateNumberPickerWithDetail(HashMap<Double, Integer> oldValue, HashMap<Double, Integer> newValue) {
        currentWantedValue -= getSumFromDetail(oldValue);
        currentWantedValue += getSumFromDetail(newValue);
        updateNumberPickers(currentWantedValue);
    }

    private double getSumFromDetail(HashMap<Double, Integer> map) {
        AtomicReference<Double> result = new AtomicReference<>(0.0);

        map.forEach((key, value) -> {
            result.updateAndGet(v -> (v + map.getOrDefault(key, 0) * key));
            double tmp = map.getOrDefault(key, 0) * key;
        });

        return result.get();

    }

    private void updateNumberPickers(double newValue) {
        BigDecimal bigDecimal = new BigDecimal(String.valueOf(newValue));
        int euro = bigDecimal.intValue();
        int centime = bigDecimal.subtract(new BigDecimal(euro)).multiply(BigDecimal.valueOf(10)).intValue();

        npEuro.setValue(euro);
        npCentime.setValue(centime);
    }

}
