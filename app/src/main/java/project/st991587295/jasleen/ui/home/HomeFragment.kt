package project.st991587295.jasleen.ui.home


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.firestore.EventListener
import com.google.firebase.storage.FirebaseStorage
import project.st991587295.jasleen.R
import project.st991587295.jasleen.databinding.FragmentHomeBinding
import project.st991587295.jasleen.model.Recipe
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var arraylist: ArrayList<Recipe>
    private lateinit var templist: ArrayList<Recipe>
    private lateinit var myAdapter: RecipeAdapter
    private lateinit var db: FirebaseFirestore
    private var dbstorage: FirebaseFirestore? = null

    //  private var storageReference: StorageReference? = null
    private var selectedImage: Uri? = null

    private val binding get() = _binding!!

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.menu_item, menu)
        val item = menu.findItem(R.id.search_action)
        val searchView = item?.actionView as SearchView



        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                templist.clear()
                val searchText = newText!!.lowercase(Locale.getDefault())
                if(searchText.isNotEmpty())
                {
                    arraylist.forEach{
                        if(it.name?.lowercase(Locale.getDefault())!!.contains(searchText))
                        {
                            templist.add(it)
                        }
                    }
                  //  recyclerView.adapter = myAdapter
                    myAdapter.notifyDataSetChanged()
                }
                else{
                    templist.clear()
                    templist.addAll(arraylist)
                   // recyclerView.adapter = myAdapter
                    myAdapter.notifyDataSetChanged()
                }
                return false
            }

        })
        super.onCreateOptionsMenu(menu, inflater)


    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        recyclerView = binding.recyclerid
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)

        arraylist = arrayListOf()
        templist = arrayListOf()
        templist.addAll(arraylist)
        myAdapter = context?.let { RecipeAdapter(it, arraylist) }!!
        recyclerView.adapter = myAdapter



        myAdapter.setOnItemClickListener(object: RecipeAdapter.onItemClickListener{
            override fun onItemClick(position: Int) {
                val intent = Intent(context,RecipeDetailActivity::class.java)
              //  Glide.with(this@HomeFragment).load(Recipe().image).into(.imageView)
                //put extras
                intent.putExtra("iname", arraylist[position].name)
                //intent.putExtra("icategory", arraylist[position].category)
                intent.putExtra("iimage", arraylist[position].image).toString()
               // intent.putExtra("iimage", arraylist[position].image)
                intent.putExtra("iingredients", arraylist[position].ingredients)
                intent.putExtra("idescription", arraylist[position].description)
                startActivity(intent)
            }

        } )
        EventChangeListener()

        return root
    }

    fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        val storage = FirebaseStorage.getInstance()
        storage!!.reference.child("RecipeImages").child(Date().time.toString())
            .downloadUrl.addOnSuccessListener { uri->
                print("success")
             Recipe(image = uri.toString())
            }

       // recyclerView.adapter = context?.let { RecipeAdapter(it, arraylist) }



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