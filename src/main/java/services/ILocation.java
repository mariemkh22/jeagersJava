package services;

import java.sql.SQLException;
import java.util.List;

public interface ILocation <S>{
    public void ajouterLocation (S s) throws SQLException;
    public void modifierLocation (S s) throws SQLException;
    public void supprimerLocation (int id) throws SQLException;
    public List<S> afficherLocation () throws SQLException;
    public void supprimerToutesLocations() throws SQLException;
}
