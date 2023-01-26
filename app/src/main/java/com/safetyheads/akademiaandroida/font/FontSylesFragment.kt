package com.safetyheads.akademiaandroida.font

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.R
import com.safetyheads.akademiaandroida.databinding.FragmentFontStylesBinding

class FontSylesFragment: Fragment() {

    private lateinit var binding: FragmentFontStylesBinding
    private lateinit var rvAdapter: FontStylesAdapter

    private var description: ArrayList<String> = arrayListOf()
    private var listFont: ArrayList<Int> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFontStylesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initLists()
        initUI()
    }

    private fun initUI() {
        rvAdapter = FontStylesAdapter(description, listFont)
        binding.recyclerFonts.layoutManager = LinearLayoutManager(context)
        binding.recyclerFonts.setHasFixedSize(true)
        binding.recyclerFonts.adapter = rvAdapter
    }

    private fun initLists() {
        description.add("H1")
        description.add("H2")
        description.add("H3")
        description.add("Subtitle")
        description.add("Text")
        description.add("Text semibold")
        description.add("Text 2")
        description.add("Text 2 semibold")
        description.add("Button")
        description.add("Footer")

        listFont.add(R.style.H1)
        listFont.add(R.style.H2)
        listFont.add(R.style.H3)
        listFont.add(R.style.Subtitle)
        listFont.add(R.style.Text)
        listFont.add(R.style.Text_semibold)
        listFont.add(R.style.Text2)
        listFont.add(R.style.Text2_semibold)
        listFont.add(R.style.Button)
        listFont.add(R.style.Footer)
    }
}