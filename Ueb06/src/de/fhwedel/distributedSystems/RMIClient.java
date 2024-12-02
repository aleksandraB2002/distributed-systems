package de.fhwedel.distributedSystems;

import java.io.IOException;

public class RMIClient {
    public static void main(String[] args) {
        try {
            DataBase db = readStubFromFile("database.stub");

            db.addRecord(4101, "Appen");
            db.addRecord(4102, "Ahrensburg");
            db.addRecord(4103, "Wedel");
            db.addRecord(4104, "Aumühle");
            db.addRecord(4105, "Seevetal");
            db.addRecord(4106, "Quickborn");

            System.out.println(db.getRecord(4103));
            System.out.println(db.getRecord(4107));
            System.out.println("Database size: " + db.getSize());



            DBResult result = db.getRecordObj(4103);
            if (result != null) {
                System.out.println("Record key: " + result.getKey());
                System.out.println("Record value: " + result.getValue());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static DataBase readStubFromFile(String fileName)
            throws java.io.FileNotFoundException, java.io.IOException, java.lang.ClassNotFoundException {
        java.io.FileInputStream fis = new java.io.FileInputStream(fileName);
        java.io.ObjectInputStream in = new java.io.ObjectInputStream(fis);
        DataBase remoteObj = (DataBase) in.readObject();
        in.close();
        return remoteObj;
    }
}