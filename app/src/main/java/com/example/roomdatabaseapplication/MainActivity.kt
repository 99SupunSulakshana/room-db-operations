package com.example.roomdatabaseapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.roomdatabaseapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(intent.hasExtra("list_add")){
            val navHostFragment =
                (supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment)
            val inflater = navHostFragment.navController.navInflater
            val graph = inflater.inflate(R.navigation.user_nav)
            graph.setStartDestination(R.id.userListFragment)
            navHostFragment.navController.graph = graph
        }
    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navContaller = findNavController(R.id.)
//        return navContaller.navigateUp() || super.onSupportNavigateUp()
//    }
}