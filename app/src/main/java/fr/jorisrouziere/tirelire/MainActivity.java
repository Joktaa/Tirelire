package fr.jorisrouziere.tirelire;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.widget.NumberPicker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import fr.jorisrouziere.tirelire.adapters.HistoriqueListAdapter;
import fr.jorisrouziere.tirelire.models.Historique;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {

    RecyclerView historiqueList;
    ArrayList<Historique> data = new ArrayList<>(Arrays.asList(
            new Historique(LocalDateTime.now(), "DEPOT", 20.0),
            new Historique(LocalDateTime.now(), "RETRAIT", 10.0),
            new Historique(LocalDateTime.now(), "DEPOT", 30.0)
    ));

    private NumberPicker npEuros;
    private NumberPicker npCentimes;

    final HistoriqueListAdapter adapter = new HistoriqueListAdapter(new HistoriqueListAdapter.HistoriqueDiff());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        historiqueList = findViewById(R.id.historique_list);
        if (!adapter.hasObservers()) {
            adapter.setHasStableIds(true);
        }
        historiqueList.setAdapter(adapter);
        historiqueList.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        adapter.submitList(data);


        npEuros = findViewById(R.id.np_euros);
        npCentimes = findViewById(R.id.np_centimes);

        npEuros.setMinValue(0);
        npCentimes.setMinValue(0);
        npEuros.setMaxValue(100);
        npCentimes.setMaxValue(100);


    }
}