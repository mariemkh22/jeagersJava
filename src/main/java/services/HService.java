package services;

import java.sql.SQLException;
import java.util.List;

public interface HService <T> {
    public void ajouter (T t) throws SQLException;
    public void modifier (T t) throws SQLException;
    public void supprimer (int id) throws SQLException;
    public List<T> afficher () throws SQLException;
    public void supprimerToutesLivraisons() throws SQLException;


}
