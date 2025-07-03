package com.example.matchmate.ui

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.matchmate.R
import com.example.matchmate.data.local.Status
import com.example.matchmate.data.local.User
import com.example.matchmate.data.local.getDetails
import com.example.matchmate.databinding.MatchedUserItemBinding
import com.bumptech.glide.request.target.Target

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
                tvMatchScore.text =
                    itemView.context.getString(R.string.match_score, user.matchScore)

                Glide.with(itemView.context).load(user.picture.large)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable?>,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Hide image, show initials
                            profileImage.visibility = GONE
                            profileInitials.visibility = VISIBLE
                            profileInitials.text = getInitials(user.name)
                            return true
                        }

                        override fun onResourceReady(
                            resource: Drawable,
                            model: Any,
                            target: Target<Drawable?>?,
                            dataSource: DataSource,
                            isFirstResource: Boolean
                        ): Boolean {
                            // Show image, hide initials
                            profileImage.visibility = VISIBLE
                            profileInitials.visibility = GONE
                            return false
                        }
                    }).into(profileImage)

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

        fun getInitials(fullName: String): String {
            val parts = fullName.trim().split("\\s+".toRegex())
            return when {
                parts.size >= 2 -> {
                    // Take the first character of the first and last names
                    "${parts.first().first()}${parts.last().first()}".uppercase()
                }
                parts.size == 1 && parts[0].isNotEmpty() -> {
                    // Single name, return first two characters
                    parts[0].take(2).uppercase()
                }
                else -> "--" // Fallback if name is empty or invalid
            }
        }
    }
}
