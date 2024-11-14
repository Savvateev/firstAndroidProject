package ru.netology.nmedia

import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.netology.nmedia.databinding.ActivityMainBinding
import kotlin.math.truncate

data class Post
    (val id: Int,
     val author: String, val content: String,
     val published: String, var likedByMe: Boolean)

fun toView(count : Int) : String {
    return when (count) {
        in 1..999 -> count.toString()
        in 1000..999999 -> ((count/1000).toDouble()).toString() + "K "
        in 1000000..100000000 -> ((count/1000000).toDouble()).toString() + "M "
        else -> "0"
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val post = Post (
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенс ... ",
            published = "21 мая в 18:36",
            likedByMe = false
        )
        with (binding) {
            var countOfLikes = 11
            var countOfShare = 11
            var countOfView = 1100
            author.text = post.author
            published.text = post.published
            PostTextView.text = post.content
            countOfLikesView.text = toView(countOfLikes)
            countOfSharesView.text = toView(countOfShare)
            countOfVView.text = toView(countOfView)
            if (post.likedByMe) {
                likes?.setImageResource(R.drawable.baseline_favorite_24)
            }
            likes?.setOnClickListener {
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) countOfLikes += 1
                else countOfLikes -= 1
                countOfLikesView.text = toView(countOfLikes)
                likes.setImageResource(
                    if (post.likedByMe) R.drawable.baseline_favorite_24
                    else  R.drawable.baseline_favorite_border_24
                )
            }
            share?.setOnClickListener {
                countOfShare += 1
                countOfSharesView.text = toView(countOfShare)
            }
        }
    }
}
