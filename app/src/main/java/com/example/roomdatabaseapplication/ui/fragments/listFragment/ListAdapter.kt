package com.example.roomdatabaseapplication.ui.fragments.listFragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabaseapplication.data.model.User
import com.example.roomdatabaseapplication.databinding.UserItemBinding

class ListAdapter: RecyclerView.Adapter<com.example.roomdatabaseapplication.ui.fragments.listFragment.ListAdapter.MyViewHolder>() {

    private var userList = emptyList<User>()

    class MyViewHolder(binding: UserItemBinding):RecyclerView.ViewHolder(binding.root){
        var id = binding.count
        val name = binding.name
        val age = binding.age
        val container = binding.container
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.id.text = currentItem.id.toString()
        val name = currentItem.firstName+ " " + currentItem.lastName
        holder.name.text = name
        holder.age.text = currentItem.age.toString()
        holder.container.setOnClickListener {
            val action = UserListFragmentDirections.actionUserListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(user:List<User>){
        this.userList = user
        notifyDataSetChanged()
    }

}