package mz.ac.luis.seia.finacieme.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ConfigFirebase {
    private static FirebaseAuth auth;
    private static DatabaseReference refFirebase;
    public static FirebaseAuth getAuth() {

        if(auth == null){
            auth = FirebaseAuth.getInstance();
        }

        return auth;
    }

    public  static DatabaseReference getFirebaseDataBase(){
        if(refFirebase == null){
            refFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return refFirebase;
    }




}
