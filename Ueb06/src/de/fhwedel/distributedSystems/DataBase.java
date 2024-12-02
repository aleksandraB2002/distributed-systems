package de.fhwedel.distributedSystems;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface DataBase extends Remote {
    String getRecord(int index) throws RemoteException;
    void addRecord(int index, String record) throws RemoteException;
    int getSize() throws RemoteException;
    DBResult getRecordObj(int index) throws RemoteException;

}
