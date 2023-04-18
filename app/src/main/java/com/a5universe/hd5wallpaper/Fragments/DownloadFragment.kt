package com.a5universe.hd5wallpaper.Fragments


import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.a5universe.hd5wallpaper.Adapter.CatImagesAdapter
import com.a5universe.hd5wallpaper.Adapter.CollectionAdapter
import com.a5universe.hd5wallpaper.databinding.FragmentDownloadBinding
import java.io.File

class DownloadFragment : Fragment() {

    lateinit var binding: FragmentDownloadBinding
    private val STORAGE_PERMISSION_CODE = 101


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        binding = FragmentDownloadBinding.inflate(layoutInflater,container,false)

        val allFiles: Array<File>
        val  imageList = arrayListOf<String>()

        //for allow storage permission then code is executable
        requestPermission()

            val targetPath =
                Environment.getExternalStorageDirectory().absolutePath + "/Pictures/HD5 Wallpaper"
            val targetFile = File(targetPath)
            allFiles = targetFile.listFiles()!!
            for (data in allFiles) {
                imageList.add(data.absolutePath)
            }
            binding.rcvCollection.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            binding.rcvCollection.adapter = CollectionAdapter(requireContext(), imageList)


        return binding.root
    }

    //method of permission...
    private fun requestPermission(): Boolean{
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
            return  false
        }

        return true
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == STORAGE_PERMISSION_CODE){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                Toast.makeText(requireContext(), "Permission Granted", Toast.LENGTH_SHORT).show()

            else
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),STORAGE_PERMISSION_CODE)
        }

    }



}