package fr.jorisrouziere.tirelire;

import static android.content.Intent.FLAG_ACTIVITY_PREVIOUS_IS_TOP;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDateTime;

import fr.jorisrouziere.tirelire.mqtt.MqttClient;
import fr.jorisrouziere.tirelire.room.Repository;
import fr.jorisrouziere.tirelire.room.models.Historique;
import fr.jorisrouziere.tirelire.room.models.Piece;
import fr.jorisrouziere.tirelire.room.models.PieceType;

public class HomeActivity extends AppCompatActivity {

    private Repository repository;
    private MqttClient mqttClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        repository = Repository.getInstance(getApplicationContext());
        mqttClient = MqttClient.getInstance(getApplicationContext());

        initData();
        initListener();
    }

    private void initData() {
        loadTestData();
        TextView currentMoney = findViewById(R.id.home_currentMoney);
        TextView textTimeSinceLastWithdraw = findViewById(R.id.home_textTimeSinceLastWithdraw);
        String text_neverWithdraw = getApplicationContext().getResources().getString(R.string.neverWithdraw);
        String text_lastWithdraw = getApplicationContext().getResources().getString(R.string.lastWithdrawDoneThe);

        repository.getPieces().observe(this, (pieces -> {
            double currentMoneyValue = pieces.stream().mapToDouble(Piece::getTotalValue).sum();
            currentMoney.setText(currentMoneyValue + "€");

        }));

        repository.getHistoriques().observe(this, (historiques -> {
            if (historiques != null && !historiques.isEmpty()) {
                String lastWithdrawn = historiques.stream().filter(historique -> historique.getType().equals(Historique.ActionType.WITHDRAW.getValue())).reduce((first, second) -> second).get().getDateString();
                textTimeSinceLastWithdraw.setText(lastWithdrawn != null ? String.format(text_lastWithdraw, lastWithdrawn) : text_neverWithdraw);
            }
        }));


    }

    private void initListener() {
        ImageButton refreshButton = findViewById(R.id.home_refreshButton);
        Button withdrawnButton = findViewById(R.id.home_buttonWithdrawn);
        Button historyButton = findViewById(R.id.home_buttonHistory);

        refreshButton.setOnClickListener(view -> {
            //TODO call refresh mqtt
        });

        withdrawnButton.setOnClickListener(view -> {
            startActivity(new Intent(this, WithdrawActivity.class));
        });

        historyButton.setOnClickListener(view -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });
    }

    private void loadTestData() {
        //TODO to remove, only test
        repository.insertOnePieces(new Piece(PieceType.EURO_1.getValue(), 0));
        repository.insertOnePieces(new Piece(PieceType.EURO_2.getValue(), 0));
        repository.insertOnePieces(new Piece(PieceType.CENTIME_10.getValue(), 0));
        repository.insertOnePieces(new Piece(PieceType.CENTIME_20.getValue(), 0));
        repository.insertOnePieces(new Piece(PieceType.CENTIME_50.getValue(), 0));
        repository.insertOneHistorique(new Historique(LocalDateTime.of(2022, 12, 14, 04, 43), Historique.ActionType.DEPOSIT.getValue(), 10.0));
        repository.insertOneHistorique(new Historique(LocalDateTime.of(2022, 12, 14, 11, 43), Historique.ActionType.WITHDRAW.getValue(), 10.0));

    }
}
