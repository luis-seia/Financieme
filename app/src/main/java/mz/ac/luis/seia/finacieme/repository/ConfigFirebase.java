package mz.ac.luis.seia.finacieme.repository;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import mz.ac.luis.seia.finacieme.helper.Base64Custom;

public class ConfigFirebase {
    private static FirebaseAuth auth;
    private static DatabaseReference refFirebase;
    private static DatabaseReference userRef;
    private static final String NODE_USERS = "usuarios";
    private static final String NODE_CARTEIRAS = "carteira";
    private static final String NODE_DIVIDA = "divida";
    private static final String NODE_MOVIMENTACAO = "movimentacao";
    private static final String NODE_SALDOTOTAL = "saldoTotal";
    private static final String NODE_DEBITOTOTAL = "debitoTotal";
    private static final String NODE_RECEITATOTAL = "receitaTotal";
    private static final String NODE_DESPESATOTAL = "despesaTotal";

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
    public  static DatabaseReference getUserRef(){
        String userId = Base64Custom.codificarBase64(auth.getCurrentUser().getEmail());
            userRef = refFirebase.child(NODE_USERS).child(userId);
        return userRef;
    }

    public static String usuariosNo(){
        return NODE_USERS;
    }

    public static String movimentacaoNo(){
        return NODE_MOVIMENTACAO;
    }
    public static String receitaTotalNo(){
        return NODE_RECEITATOTAL;
    }
    public static String despesaTotalNo(){
        return NODE_DESPESATOTAL;
    }
    public static String debitoTotalNo(){
        return NODE_DEBITOTOTAL;
    }
    public static String saldoTotalNo(){
        return NODE_SALDOTOTAL;
    }

    public static String carteriasNo(){
        return NODE_CARTEIRAS;
    }

    public static String dividasNo(){
        return NODE_DIVIDA;
    }





}
