package fr.jorisrouziere.tirelire.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.jorisrouziere.tirelire.room.models.Pieces;

@Dao
public interface PiecesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Pieces pieces);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Pieces pieces);

    @Query("SELECT * FROM pieces")
    LiveData<List<Pieces>> getPieces();
}
