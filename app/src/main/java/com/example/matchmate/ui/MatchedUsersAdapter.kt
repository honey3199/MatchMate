package com.example.matchmate.ui

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.matchmate.R
import com.example.matchmate.data.local.Status
import com.example.matchmate.data.local.User
import com.example.matchmate.data.local.getDetails
import com.example.matchmate.databinding.MatchedUserItemBinding

class UserDataAdapter(private val onStatusChanged: (user: User) -> Unit) :
    RecyclerView.Adapter<UserDataAdapter.UserViewHolder>() {
    private var userList = listOf<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        UserViewHolder(
            MatchedUserItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.apply {
            userList.getOrNull(position)?.let {
                setData(it) { status ->
                    userList[position].status = status
                    onStatusChanged(userList[position])
                    notifyItemChanged(position)
                }
            }
        }
    }

    override fun getItemCount(): Int = userList.size

    fun setUserData(users: List<User>) {
        userList = users
        notifyDataSetChanged()
    }

    class UserViewHolder(private val binding: MatchedUserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun setData(user: User, onStatusChanged: (status: Status) -> Unit) {
            binding.apply {
                userName.text = user.name
                userDetails.text = user.getDetails()
                tvMatchScore.text = itemView.context.getString(R.string.match_score, user.matchScore)
                with(user.status) {
                    when (this) {
                        Status.SELECTED -> {
                            tvStatus.visibility = VISIBLE
                            tvStatus.text = this.value
                            btnAccept.isEnabled = false
                            btnAccept.alpha = 0.5f
                            btnDeclined.isEnabled = false
                            btnDeclined.alpha = 0.5f
                            tvStatus.setBackgroundResource(R.drawable.bg_selected)
                            tvStatus.setTextColor(
                                ContextCompat.getColor(
                                    itemView.context, android.R.color.holo_green_dark
                                )
                            )
                        }

                        Status.REJECTED -> {
                            tvStatus.visibility = VISIBLE
                            tvStatus.text = this.value
                            btnAccept.isEnabled = false
                            btnAccept.alpha = 0.5f
                            btnDeclined.isEnabled = false
                            btnDeclined.alpha = 0.5f
                            tvStatus.setBackgroundResource(R.drawable.bg_rejected)
                            tvStatus.setTextColor(
                                ContextCompat.getColor(
                                    itemView.context, android.R.color.holo_red_dark
                                )
                            )
                        }

                        Status.NOT_VIEWED -> {
                            tvStatus.visibility = GONE
                            btnAccept.isEnabled = true
                            btnAccept.alpha = 1f
                            btnDeclined.isEnabled = true
                            btnDeclined.alpha = 1f
                        }
                    }
                }

                btnAccept.setOnClickListener {
                    onStatusChanged(Status.SELECTED)
                }

                btnDeclined.setOnClickListener {
                    onStatusChanged(Status.REJECTED)
                }
            }
        }
    }
}
