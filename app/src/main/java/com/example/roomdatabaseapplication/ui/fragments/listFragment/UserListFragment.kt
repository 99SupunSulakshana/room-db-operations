package com.example.roomdatabaseapplication.ui.fragments.listFragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Query
import com.example.roomdatabaseapplication.R
import com.example.roomdatabaseapplication.databinding.FragmentUserListBinding
import com.example.roomdatabaseapplication.ui.fragments.addDataFragment.UserViewModel

class UserListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding : FragmentUserListBinding? = null
    private val binding get() = _binding!!
    private lateinit var mainView: View
    private lateinit var viewModel: UserListViewModel
    lateinit var listAdapter: ListAdapter

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserListBinding.inflate(inflater,container,false)
        mainView = binding.root
        binding.addUserBtn.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_addDataFragment)
        }
        binding.toolbar.mainTitle.text = "User List"
        binding.toolbar.helpBtn.setOnClickListener {
            userDelete()
        }
        binding.toolbar.search.setOnQueryTextListener(this)
        listAdapter = ListAdapter()
        binding.list.apply {
            layoutManager = LinearLayoutManager(
                requireActivity(),
            )
            adapter = listAdapter
        }
        viewModel = ViewModelProvider(this)[UserListViewModel::class.java]
        observer()
        return mainView
    }

    private fun observer(){
        viewModel.readAllData.observe(this, Observer { user ->
            listAdapter.setData(user)
        })
    }

    private fun userDelete(){
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("YES"){_,_ ->
            viewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Deleted Everything!", Toast.LENGTH_SHORT).show()
            findNavController().popBackStack()
        }
        builder.setNegativeButton("NO"){_,_ ->

        }
        builder.setTitle("Delete All?")
        builder.setMessage("Are you sure you want to delete all users?")
        builder.create().show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        Log.e("UserListFragment", "Query : $query")
        if(query != null){
            searchDataBase(query)
        }
        return true
    }

    override fun onQueryTextChange(query: String?): Boolean {
        Log.e("UserListFragment", "Query : $query")
        if(query != null){
            searchDataBase(query)
        }
        return true
    }

    private fun searchDataBase(searchQuery: String){
        Log.e("UserListFragment", "Query : $searchQuery")
        val searchQueryType = "%$searchQuery%"
        Log.e("UserListFragment", "Query : $searchQueryType")
        viewModel.searchUser(searchQueryType).observe(this, Observer{ list ->
            listAdapter.setData(list)
        })
    }
}