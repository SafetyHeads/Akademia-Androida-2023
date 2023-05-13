package com.safetyheads.akademiaandroida.presentation.ui.fragments.profile

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentChangeAvatarBottomSheetBinding
import com.safetyheads.akademiaandroida.presentation.ui.viewmodels.ProfileViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel

class ChangeAvatarBottomSheetDialogFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentChangeAvatarBottomSheetBinding
    private val viewModel: ProfileViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentChangeAvatarBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        binding.apply {
            chooseImageCardView.setOnClickListener {
                pickPhotoLauncher.launch(
                    Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                )
            }

            makePhotoCardView.setOnClickListener {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (takePictureIntent.resolveActivity(requireContext().packageManager) != null) {
                    takePictureLauncher.launch(takePictureIntent)
                }
            }

            removeImageCardView.setOnClickListener {
                viewModel.removeImageFromUserProfile()
                this@ChangeAvatarBottomSheetDialogFragment.dismiss()
            }
        }
    }

    private val pickPhotoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data
                if (uri != null) {
                    viewModel.addImageToFirebaseUriStorage(uri)
                    this@ChangeAvatarBottomSheetDialogFragment.dismiss()
                }
            }
        }

    private val takePictureLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data = result.data
                val imageBitmap = data?.extras?.get("data") as Bitmap
                viewModel.addImageToFirebaseBitmapStorage(imageBitmap)
                this@ChangeAvatarBottomSheetDialogFragment.dismiss()
            }
        }
}