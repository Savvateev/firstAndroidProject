package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityEditPostBinding
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val viewModel: PostViewModel by viewModels()
        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }
        val adapter = PostsAdapter(object : OnInteractionListener {

            override fun onEdit(post: Post) {
                //val content = post.content
                //intent.putExtra(Intent.EXTRA_TEXT, content)
                editPostLauncher.launch(post.content)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onShare(post: Post) {
//                viewModel.shareById(post.id)
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type = "text/plain"
                }
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        val newPostLauncher = registerForActivityResult(NewPostResultContract()) { result ->
            result ?: return@registerForActivityResult
            viewModel.changeContent(result)
            viewModel.save()
        }

        binding.fab.setOnClickListener {
            newPostLauncher.launch(Unit)
        }

//        val editPostLauncher = registerForActivityResult(EditPostResultContract()) { result ->
//            result ?: return@registerForActivityResult
//            viewModel.changeContent(result)
//            viewModel.save()
//        }

//        binding.fab.setOnClickListener {
//            editPostLauncher.launch(Unit)
//        }

//        binding.content.setOnFocusChangeListener { currentView, hasFocus ->
//            if (!hasFocus) {
//                binding.group.visibility = View.INVISIBLE
//            } else {
//                binding.group.visibility = View.VISIBLE
//            }
//        }
//
//        viewModel.edited.observe(this) { post ->
//            if (post.id == 0L) {
//                return@observe
//            }
//            with(binding.content) {
//                requestFocus()
//                setText(post.content)
//            }
//        }
//
//        binding.cancelButton.setOnClickListener {
//            viewModel.cancelEdit()
//            with(binding.content) {
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }
//
//        binding.save.setOnClickListener {
//            with(binding.content) {
//                if (text.isNullOrBlank()) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        context.getString(R.string.error_empty_content),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    return@setOnClickListener
//                }
//                viewModel.changeContent(text.toString())
//                viewModel.save()
//                setText("")
//                clearFocus()
//                AndroidUtils.hideKeyboard(this)
//            }
//        }
    }
}