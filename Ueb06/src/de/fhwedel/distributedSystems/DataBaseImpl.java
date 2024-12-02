package de.fhwedel.distributedSystems;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class DataBaseImpl implements DataBase {
    private List<String> records;

    public DataBaseImpl() throws RemoteException {
        records = new ArrayList<>();
    }

    public String getRecord(int index) throws RemoteException {
        if (index >= 0 && index < records.size()) {
            return records.get(index);
        }
        return null;
    }

    public DBResult getRecordObj(int index) throws RemoteException {
        if (index >= 0 && index < records.size()) {
            DBResult result = new DBResult();
            result.setKey(index);
            result.setValue(records.get(index));
            return result;
        }
        return null;
    }

    public void addRecord(int index, String record) throws RemoteException {
        while (index >= records.size()) {
            records.add(null);
        }
        records.set(index, record);
    }

    public int getSize() throws RemoteException {
        int count = 0;
        for (String record : records) {
            if (record != null) {
                count++;
            }
        }
        return count;
    }

    public int getIndex(String record) throws RemoteException {
        return records.indexOf(record);
    }

    public static void main(String[] args) {
        try {
            DataBaseImpl db = new DataBaseImpl();
            DataBase stub = (DataBase) UnicastRemoteObject.exportObject(db, 0);
            writeStubToFile("database.stub", stub);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void writeStubToFile(String fileName, Remote stub)
            throws java.io.FileNotFoundException, java.io.IOException {
        java.io.FileOutputStream fos = new java.io.FileOutputStream(fileName);
        java.io.ObjectOutputStream out = new java.io.ObjectOutputStream(fos);
        out.writeObject(stub);
        out.close();
    }
}
