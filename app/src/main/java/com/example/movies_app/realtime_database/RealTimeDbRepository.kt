package com.example.movies_app.realtime_database
import android.content.Context
import android.provider.Settings
import com.example.movies_app.firebase.ResultState
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class RealTimeDbRepository(private val db: DatabaseReference, private val context: Context) :
    RealTimeRepository {

    private fun getDeviceId(): String {
        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
    }

    override fun insert(item: RealTimeUser.RealTimeItems): Flow<ResultState<String>> =
        callbackFlow {
            trySend(ResultState.Loading)
            val deviceId = getDeviceId()
            val userNode = db.child("users").child(deviceId).child("items")

            userNode.push().setValue(item)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        trySend(ResultState.Success("Data Inserted Successfully"))
                    }
                }
                .addOnFailureListener {
                    trySend(ResultState.Error(it))
                }

            awaitClose { close() }
        }

    override fun getItems(): Flow<ResultState<List<RealTimeUser>>> = callbackFlow {
        trySend(ResultState.Loading)

        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        val valueEvent = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val itemList = mutableListOf<RealTimeUser>()
                snapshot.children.forEach { dataSnapshot ->
                    val item = dataSnapshot.getValue(RealTimeUser.RealTimeItems::class.java)
                    val key = dataSnapshot.key
                    if (item != null && key != null) {
                        itemList.add(RealTimeUser(item, key))
                    }
                }
                trySend(ResultState.Success(itemList))
            }

            override fun onCancelled(error: DatabaseError) {
                trySend(ResultState.Error(Exception(error.message)))
            }
        }

        userNode.addValueEventListener(valueEvent)

        awaitClose {
            userNode.removeEventListener(valueEvent)
            close()
        }
    }

    override fun delete(key: String): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        userNode.child(key).removeValue()
            .addOnCompleteListener {
                trySend(ResultState.Success("Item deleted"))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it))
            }

        awaitClose { close() }
    }

    override fun update(res: RealTimeUser): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

        val deviceId = getDeviceId()
        val userNode = db.child("users").child(deviceId).child("items")

        val map = HashMap<String, Any>()
        map["username"] = res.items.userName
        map["email"] = res.items.email
        map["password"] = res.items.password


        userNode.child(res.key!!).updateChildren(map)
            .addOnCompleteListener {
                trySend(ResultState.Success("Updated successfully"))
            }
            .addOnFailureListener {
                trySend(ResultState.Error(it))
            }

        awaitClose { close() }
    }
}