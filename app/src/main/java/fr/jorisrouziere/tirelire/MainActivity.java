package fr.jorisrouziere.tirelire;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import fr.jorisrouziere.tirelire.adapters.HistoriqueListAdapter;
import fr.jorisrouziere.tirelire.room.Repository;
import fr.jorisrouziere.tirelire.room.models.Historique;
import fr.jorisrouziere.tirelire.room.models.Piece;
import fr.jorisrouziere.tirelire.room.models.PieceType;


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
        repository = Repository.getInstance(getApplicationContext());
        Piece newPiece = new Piece(PieceType.EURO_2.getValue(),10);

        ArrayList<Historique> newHistorique = new ArrayList<>(Arrays.asList(
                new Historique(LocalDateTime.now(), Historique.ActionType.DEPOSIT.getValue(), 20.0),
                new Historique(LocalDateTime.now(), Historique.ActionType.WITHDRAW.getValue(), 10.0),
                new Historique(LocalDateTime.now(),  Historique.ActionType.DEPOSIT.getValue(),  30.0)
        ));
        repository.insertOnePieces(newPiece);
        repository.insertAllHistoriques(newHistorique);

        stockEuros = findViewById(R.id.stock_text);
        repository.getPieces().observe(this, (pieces) -> {
            LiveData<List<Piece>> piecesLive = repository.getPieces();
            if(piecesLive!= null && piecesLive.getValue() != null)
            stockEuros.setText(piecesLive.getValue().stream().mapToDouble(Piece::getTotalValue).sum() + "€");
        });

        historiqueList = findViewById(R.id.historique_list);
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true);
        }
        historiqueList.setAdapter(adapter);
        historiqueList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        repository.getHistoriques().observe(this, adapter::submitList);


        npEuros = findViewById(R.id.withdrawn_np_euros);
        npCentimes = findViewById(R.id.withdrawn_np_centimes);

        npEuros.setMinValue(0);
        npCentimes.setMinValue(0);
        npEuros.setMaxValue(100);
        npCentimes.setMaxValue(100);
    }
}