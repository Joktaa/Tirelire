package fr.jorisrouziere.tirelire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import fr.jorisrouziere.tirelire.adapters.HistoriqueListAdapter;
import fr.jorisrouziere.tirelire.room.Repository;
import fr.jorisrouziere.tirelire.room.models.Historique;
import fr.jorisrouziere.tirelire.room.models.Pieces;

public class MainActivity extends AppCompatActivity {

    Repository repository;

    RecyclerView historiqueList;

    private TextView stockEuros;

    private NumberPicker npEuros;
    private NumberPicker npCentimes;

    final HistoriqueListAdapter adapter = new HistoriqueListAdapter(new HistoriqueListAdapter.HistoriqueDiff());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Remplissage de la BDD
        repository = new Repository(getApplicationContext());
        Pieces newPieces = new Pieces();
        newPieces.setUn(2);
        newPieces.setDeux(3);
        newPieces.setDix(4);
        ArrayList<Historique> newHistorique = new ArrayList<>(Arrays.asList(
                new Historique(LocalDateTime.now(), "DEPOT", 20.0),
                new Historique(LocalDateTime.now(), "RETRAIT", 10.0),
                new Historique(LocalDateTime.now(), "DEPOT", 30.0)
        ));
        repository.insertOnePieces(newPieces);
        repository.insertAllHistoriques(newHistorique);

        stockEuros = findViewById(R.id.stock_text);
        repository.getPieces().observe(this, (pieces) -> {
            stockEuros.setText(pieces.get(0).getSomme().toString() + "€");
        });

        historiqueList = findViewById(R.id.historique_list);
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true);
        }
        historiqueList.setAdapter(adapter);
        historiqueList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        repository.getHistoriques().observe(this, adapter::submitList);


        npEuros = findViewById(R.id.withdrawn_np_euros);
        npCentimes = findViewById(R.id.withdraw_np_centimes);

        npEuros.setMinValue(0);
        npCentimes.setMinValue(0);
        npEuros.setMaxValue(100);
        npCentimes.setMaxValue(100);
    }
}