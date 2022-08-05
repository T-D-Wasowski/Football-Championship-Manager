/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package football_championship_manager;

/**
 *
 * @author Tomasz Dawid Wasowski
 */
public interface LeagueManager {
    
    //This interface defines all specified Task 2 methods 
    
    void menu();
    
    void createClub();
    
    void deleteClub();
    
    void displayClubStatistics();
    
    void displayLeagueTable();
    
    void addPlayedMatch();
    
    void saveToFile();
    
    void loadFromFile();

}
