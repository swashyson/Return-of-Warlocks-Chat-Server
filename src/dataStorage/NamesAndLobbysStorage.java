/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStorage;

import java.util.ArrayList;

/**
 *
 * @author Swashy
 */
public class NamesAndLobbysStorage {
    
    static ArrayList names = new ArrayList();

    public static ArrayList getNames() {
        return names;
    }

    public static void setNames(ArrayList names) {
        NamesAndLobbysStorage.names = names;
    }
    
}
