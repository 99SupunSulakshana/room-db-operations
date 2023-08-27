package com.example.roomdatabaseapplication.ui.fragments.addDataFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabaseapplication.data.model.User
import com.example.roomdatabaseapplication.databinding.FragmentAddDataBinding

class AddDataFragment : Fragment() {

    val TAG = "AddDataFragment"
    private var _binding : FragmentAddDataBinding? = null
    private val binding get() = _binding!!
    lateinit var mainView: View
    private lateinit var viewModel : UserViewModel

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddDataBinding.inflate(inflater,container,false)
        viewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mainView = binding.root
        binding.addBtn.setOnClickListener {
            insertDataToDataBase()
        }
        binding.toolbar.mainTitle.text = "Add User Details"
        binding.toolbar.helpBtn.visibility = View.INVISIBLE
        return mainView
    }

    private fun insertDataToDataBase(){
        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val age = binding.editTextAge.text

        Log.e(TAG, "FirstName : $firstName")
        Log.e(TAG, "LastName : $lastName")
        Log.e(TAG, "Age : $age")

        if(inputValidations(firstName, lastName, age)){
            val user = User(0, firstName, lastName, Integer.parseInt(age.toString()))
            viewModel.addUser(user)
            Toast.makeText(requireContext(), "Successfully Added!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputValidations(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && (TextUtils.isEmpty(lastName)) && age.isEmpty())
    }

}