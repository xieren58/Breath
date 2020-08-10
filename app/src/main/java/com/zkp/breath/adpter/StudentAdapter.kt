package com.zkp.breath.adpter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.zkp.breath.R
import com.zkp.breath.jetpack.paging.Student

class StudentAdapter : PagedListAdapter<Student, StudentAdapter.StudentViewHolder>(diffCallback) {

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder =
            StudentViewHolder(parent)

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Student>() {
            override fun areItemsTheSame(oldItem: Student, newItem: Student): Boolean =
                    oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Student, newItem: Student): Boolean =
                    oldItem == newItem
        }
    }

    class StudentViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.adapter_student, parent, false)) {

        private val nameView = itemView.findViewById<TextView>(R.id.name)
        var student: Student? = null

        fun bindTo(student: Student?) {
            this.student = student
            nameView.text = student?.name
        }
    }
}


