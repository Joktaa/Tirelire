package fr.jorisrouziere.tirelire.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import fr.jorisrouziere.tirelire.room.dao.HistoriqueDAO;
import fr.jorisrouziere.tirelire.room.dao.PiecesDAO;
import fr.jorisrouziere.tirelire.room.models.Historique;
import fr.jorisrouziere.tirelire.room.models.Pieces;

@Database(
        entities = {
                Pieces.class,
                Historique.class
        },
        version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class TirelireDatabase extends RoomDatabase {

    private static volatile TirelireDatabase INSTANCE;

    public abstract PiecesDAO piecesDAO();
    public abstract HistoriqueDAO historiqueDAO();

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService DATABASE_WRITE_EXECUTOR = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static TirelireDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TirelireDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), TirelireDatabase.class, "tirelire-db")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}