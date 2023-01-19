package fr.jorisrouziere.tirelire.adapters;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import fr.jorisrouziere.tirelire.room.models.Historique;

public class HistoriqueListAdapter extends ListAdapter<Historique, HistoriqueViewHolder> {

    public HistoriqueListAdapter(@NonNull DiffUtil.ItemCallback<Historique> diffCallback) {
        super(diffCallback);
    }

    @Override
    public HistoriqueViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return HistoriqueViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoriqueViewHolder holder, int position) {
        Historique current = getItem(position);
        holder.bind(current);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    public static class HistoriqueDiff extends DiffUtil.ItemCallback<Historique> {
        @Override
        public boolean areItemsTheSame(@NonNull Historique oldItem, @NonNull Historique newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Historique oldItem, @NonNull Historique newItem) {
            return oldItem.getId() == (newItem.getId());
        }
    }
}

