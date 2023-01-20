package fr.jorisrouziere.tirelire.room;

import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

import fr.jorisrouziere.tirelire.room.dao.HistoriqueDAO;
import fr.jorisrouziere.tirelire.room.dao.PiecesDAO;
import fr.jorisrouziere.tirelire.room.models.Historique;
import fr.jorisrouziere.tirelire.room.models.Piece;

public final class Repository {

    private static Repository repository;

    private final PiecesDAO piecesDAO;
    private final HistoriqueDAO historiqueDAO;

    static public Repository getInstance(Context context)
    {
        if(repository == null )
            repository = new Repository(context);

        return repository;
    }

    private Repository(Context context) {
        TirelireDatabase db = TirelireDatabase.getDatabase(context);
        piecesDAO = db.piecesDAO();
        historiqueDAO = db.historiqueDAO();
    }

    public void insertOnePieces(Piece piece) {
        TirelireDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> piecesDAO.insertOne(piece));
    }

    public void updateOnePiece(Piece piece) {
        TirelireDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> piecesDAO.update(piece));
    }

    public LiveData<List<Piece>> getPieces() {
        return piecesDAO.getPieces();
    }




    public void insertAllHistoriques(List<Historique> historiqueList) {
        TirelireDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> historiqueDAO.insertAll(historiqueList));
    }

    public void insertOneHistorique(Historique historique) {
        TirelireDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> historiqueDAO.insertOne(historique));
    }

    public void updateOneHistorique(Historique historique) {
        TirelireDatabase.DATABASE_WRITE_EXECUTOR.execute(() -> historiqueDAO.update(historique));
    }

    public LiveData<List<Historique>> getHistoriques() {
        return historiqueDAO.getHistorique();
    }
}