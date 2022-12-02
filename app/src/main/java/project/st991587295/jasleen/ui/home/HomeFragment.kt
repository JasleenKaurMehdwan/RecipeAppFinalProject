package project.st991587295.jasleen.ui.home

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import project.st991587295.jasleen.databinding.FragmentHomeBinding
import project.st991587295.jasleen.model.Recipe
import project.st991587295.jasleen.ui.Create.CreateViewModel
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
   private lateinit var recyclerView : RecyclerView
   private lateinit var arraylist : ArrayList<Recipe>
   private lateinit var myAdapter : RecipeAdapter
   private lateinit var db: FirebaseFirestore
   private lateinit var dbstorage: FirebaseStorage
    private  var selectedImage: Uri? = null

    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater,container , false)
        val root: View = binding.root
      recyclerView =  binding.recyclerid
        recyclerView.layoutManager=LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        arraylist = arrayListOf()
        myAdapter = context?.let { RecipeAdapter(it,arraylist) }!!
        recyclerView.adapter = myAdapter
        EventChangeListener()

        return root
    }

    fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        dbstorage = FirebaseStorage.getInstance()
        db.collection("recipe").addSnapshotListener(object : EventListener<QuerySnapshot> {
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {
                if (error != null) {
                    Log.e("Firebase Error", error.message.toString())
                    return
                }
                for (dc: DocumentChange in value?.documentChanges!!) {
                    if (dc.type == DocumentChange.Type.ADDED) {
                        arraylist.add(dc.document.toObject((Recipe::class.java)))
                    }
                }
                myAdapter.notifyDataSetChanged()
            }

        })

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}