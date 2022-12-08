package project.st991587295.jasleen.ui.home

import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import project.st991587295.jasleen.databinding.RecipeDetailsBinding
import project.st991587295.jasleen.model.Recipe


class RecipeDetailActivity: AppCompatActivity() {


    lateinit var binding: RecipeDetailsBinding
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        binding = RecipeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var imageurl : Uri
      //  imageurl = intent.getStringExtra("iimage")
        binding.namedetail.text = intent.getStringExtra("iname")
       // binding.categorydetail.text= intent.getStringExtra("icategory")
        binding.ingredientsdetail.text = intent.getStringExtra("iingredients")
        binding.descriptiondetail.text = intent.getStringExtra("idescription")

         imageurl = Uri.parse(intent.getStringExtra("iimage"))

        Glide.with(this).load(imageurl).into(binding.imageView2)





}





}
