package project.st991587295.jasleen.ui.Create

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import project.st991587295.jasleen.R
import project.st991587295.jasleen.databinding.FragmentCreateBinding
import project.st991587295.jasleen.model.Recipe
import java.util.*


class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null

    // Write a message to the database
    private lateinit var database : FirebaseFirestore
    private  var auth: FirebaseAuth ?  =null
    private lateinit var storage: FirebaseStorage
    private  var selectedImage: Uri ? = null
    var geturl: Uri? = null
    private lateinit var dialog: AlertDialog.Builder
    var categorytext= "Non-Vegetarian"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View {

        ViewModelProvider(this).get(CreateViewModel::class.java)
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root
        database = FirebaseFirestore.getInstance()
        storage = FirebaseStorage.getInstance()
            // radiobuttonselected()
        binding.createbtn.setOnClickListener {
            UploadImage()




        }

        binding.image.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }
        return root

    }
/**
    fun UploadData()
    {
        val recipe = Recipe(
            binding.nametxt.text.toString(),
            binding.ingredientstxt.text.toString(),
            binding.desctxt.text.toString(),
              task.toString(),
            binding.displaycategory.toString()
        )



        database.collection("recipe")
            .document(binding.nametxt.text.toString())
            .set(recipe)
            .addOnSuccessListener {
                Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show()
                UploadImage()
                startActivity(Intent(context, MainActivity::class.java))
            }
    }
*/
    fun radiobuttonselected()
    {

        binding.rb.setOnCheckedChangeListener{group,checkedId->
            if(checkedId== R.id.vegrb)
            {
                binding.displaycategory.setText("Vegetarian")
            }
            if(checkedId==R.id.Nvrb)
            {
                binding.displaycategory.setText("Non - Vegetarian")
            }

        }
    }
    fun UploadImage() {
        val storage = storage.reference.child("RecipeImages").child(Date().time.toString())

        selectedImage?.let {
            storage.putFile(it).addOnCompleteListener {
                if (it.isSuccessful) {
                    storage.downloadUrl.addOnSuccessListener { task ->
                   //     Toast.makeText(context, "testing", Toast.LENGTH_SHORT).show()
                        val map = HashMap<String , Any>()
                        map["image"] = task.toString()
                        map["name"] = binding.nametxt!!.text.toString()
                        map["ingredients"] = binding.ingredientstxt!!.text.toString()
                        map["description"] to binding.desctxt!!.text.toString()
                        "category" to radiobuttonselected()
                        val recipe = Recipe(
                            binding.nametxt.text.toString(),
                            binding.ingredientstxt.text.toString(),
                            binding.desctxt.text.toString(),
                            task.toString(),
                            binding.displaycategory.toString()
                        )



                        database.collection("recipe")
                            .document(binding.nametxt.text.toString())
                            .set(recipe)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Data inserted", Toast.LENGTH_SHORT).show()
                                UploadImage()
                                //startActivity(Intent(context, MainActivity::class.java))
                            }

                    }
                }
            }
        }

    }




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImage = data.data!!
                binding.image.setImageURI(selectedImage)

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

