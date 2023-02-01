package fr.jorisrouziere.tirelire.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import fr.jorisrouziere.tirelire.room.models.Piece;

@Dao
public interface PiecesDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertOne(Piece piece);

    @Query("UPDATE piece SET number = number +:newNumber WHERE value=:value")
    void updateNumber(double value, long newNumber);

    @Query("SELECT * FROM piece;")
    LiveData<List<Piece>> getPieces();

}
