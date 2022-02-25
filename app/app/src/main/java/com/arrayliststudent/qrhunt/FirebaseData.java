package com.arrayliststudent.qrhunt;

import com.google.firebase.firestore.FirebaseFirestore;

//Firebase Docs: https://firebase.google.com/docs/guides?authuser=0&hl=en
//Firebase Samples: https://firebase.google.com/docs/samples?authuser=0&hl=en

public class FirebaseData {
    final String TAG = "you're it";
    FirebaseFirestore database;

}

/*
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if false;
    }
  }
}*/