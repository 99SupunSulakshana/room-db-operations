package com.example.roomdatabaseapplication.ui.fragments.update

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Update
import com.example.roomdatabaseapplication.R
import com.example.roomdatabaseapplication.data.model.User
import com.example.roomdatabaseapplication.databinding.FragmentAddDataBinding
import com.example.roomdatabaseapplication.databinding.FragmentUpdateBinding
import com.example.roomdatabaseapplication.ui.fragments.addDataFragment.UserViewModel

class UpdateFragment : Fragment() {

    val TAG = "UpdateFragment"
    private var _binding : FragmentUpdateBinding? = null
    private val binding get() = _binding!!
    lateinit var mainView: View
    private lateinit var viewModel : UpdateViewModel
    private val args: UpdateFragmentArgs by navArgs()

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this)[UpdateViewModel::class.java]
        mainView = binding.root

        binding.editTextFirstName.setText(args.user?.firstName)
        binding.editTextLastName.setText(args.user?.lastName)
        binding.editTextAge.setText(args.user?.age.toString())
        binding.toolbar.mainTitle.text = "Update User Details"
        binding.toolbar.helpBtn.setOnClickListener {
            userDelete()
        }
        binding.updateBtn.setOnClickListener {
            userDataUpdate()
        }

        return mainView
    }

    private fun userDataUpdate(){
        val firstName = binding.editTextFirstName.text.toString()
        val lastName = binding.editTextLastName.text.toString()
        val age = binding.editTextAge.text

        Log.e(TAG, "FirstName : $firstName")
        Log.e(TAG, "LastName : $lastName")
        Log.e(TAG, "Age : $age")

        if(inputValidations(firstName,lastName,age)){
            val updatedUser = args.user?.id?.let { User(it, firstName, lastName, Integer.parseInt(age.toString())) }
            if (updatedUser != null) {
                viewModel.updateUser(updatedUser)
                Toast.makeText(requireContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }else{
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun inputValidations(firstName: String, lastName: String, age: Editable): Boolean{
        return !(TextUtils.isEmpty(firstName) && (TextUtils.isEmpty(lastName)) && age.isEmpty())
    }

    private fun userDelete(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES"){_,_ ->
            args.user?.let { viewModel.deleteUser(it) }
            Toast.makeText(requireContext(), "Successfully Deleted ${args.user?.firstName}!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("NO"){_,_ ->

        }
        builder.setTitle("Delete ${args.user?.firstName}?")
        builder.setMessage("Are you sure you want to delete ${args.user?.firstName}?")
        builder.create().show()
    }

}