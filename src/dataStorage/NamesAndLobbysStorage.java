/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dataStorage;

import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author Swashy
 */
public class NamesAndLobbysStorage {

    static ArrayList names = new ArrayList();
    static ArrayList lobbys = new ArrayList();
    static ArrayList masterPORT = new ArrayList();
    static ArrayList masterIP = new ArrayList();
    static ArrayList lobbyNames = new ArrayList();

    public static ArrayList getLobbyNames() {
        return lobbyNames;
    }

    public static void setLobbyNames(ArrayList lobbyNames) {
        NamesAndLobbysStorage.lobbyNames = lobbyNames;
    }

    public static ArrayList getMasterPORT() {
        return masterPORT;
    }

    public static void setMasterPORT(ArrayList masterPORT) {
        NamesAndLobbysStorage.masterPORT = masterPORT;
    }

    public static ArrayList getMasterIP() {
        return masterIP;
    }

    public static void setMasterIP(ArrayList masterIP) {
        NamesAndLobbysStorage.masterIP = masterIP;
    }

    public static ArrayList getLobbys() {
        return lobbys;
    }

    public static void setLobbys(ArrayList lobbys) {
        NamesAndLobbysStorage.lobbys = lobbys;
    }

    public static ArrayList getNames() {
        return names;
    }

    public static void setNames(ArrayList names) {
        NamesAndLobbysStorage.names = names;
    }

}
