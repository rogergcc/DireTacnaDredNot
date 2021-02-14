package com.educaciontacna.drednot.ui.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseManager {
    public final FirebaseAuth auth = FirebaseAuth.getInstance();
//    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public final FirebaseFirestore db = FirebaseFirestore.getInstance();
//    final FirebaseStorage storage = FirebaseStorage.getInstance();
}
