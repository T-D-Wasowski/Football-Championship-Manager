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
public class Main {

    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        
        //Creates a premier league manager
        PremierLeagueManager leagueManager = new PremierLeagueManager();
        
        //Runs the menu method on the created league manager object
        leagueManager.menu();
    }
    
}
