package fr.jorisrouziere.tirelire.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import fr.jorisrouziere.tirelire.R;
import fr.jorisrouziere.tirelire.models.Historique;

public class HistoriqueViewHolder extends RecyclerView.ViewHolder{

    private final TextView date;
    private final TextView type;
    private final TextView montant;

    public HistoriqueViewHolder(@NonNull View itemView) {
        super(itemView);
        date = itemView.findViewById(R.id.item_date);
        type = itemView.findViewById(R.id.item_type);
        montant = itemView.findViewById(R.id.item_montant);
    }

    public void bind(Historique historique) {
        date.setText(historique.getDateString());
        type.setText(historique.getType());
        montant.setText(historique.getMontantString());
    }

    static HistoriqueViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historique, parent, false);
        return new HistoriqueViewHolder(view);
    }
}
