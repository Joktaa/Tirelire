package fr.jorisrouziere.tirelire.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.jorisrouziere.tirelire.room.models.Historique;

@Dao
public interface HistoriqueDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Historique> historiqueList);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Historique historique);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Historique historique);

    @Query("SELECT * FROM historique")
    LiveData<List<Historique>> getHistorique();
}
