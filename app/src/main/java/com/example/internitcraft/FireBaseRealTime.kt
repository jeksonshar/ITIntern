package com.example.internitcraft

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FireBaseRealTime {

    fun insertElement(name: String, value: String) {
        val fireBase = Firebase.database
        val myRef = fireBase.getReference(name)
        myRef.setValue(value)
    }

    fun insertChildElement(name: String, child: String, value: String) {
        val fireBase = Firebase.database
        val myRef = fireBase.getReference(name)
        myRef.child(child).setValue(value)
    }

    fun insertChildInChildElement(name: String, child1: String, child2: String, value: String) {
        val fireBase = Firebase.database
        val myRef = fireBase.getReference(name)
        myRef.child(child1).child(child2).setValue(value)
    }

    fun getChildInChildElement(name: String, child1: String, child2: String): String {
        val fireBase = Firebase.database
        val myRef = fireBase.getReference(name)
        return myRef.child(child1).child(child2).key ?: "---"
    }

}