package com.safetyheads.akademiaandroida

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.safetyheads.akademiaandroida.databinding.FontStylesFragmentBinding

class FontSylesFragment: Fragment() {

    private lateinit var binding: FontStylesFragmentBinding
    private lateinit var rvAdapter: FontStylesAdapter

    private var description: ArrayList<String> = arrayListOf()
    private var listFont: ArrayList<Int> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FontStylesFragmentBinding.inflate(inflater, container, false)
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

        listFont.add(R.font.header_1)
        listFont.add(R.font.header_2)
        listFont.add(R.font.header_3)
        listFont.add(R.font.subtitle)
        listFont.add(R.font.text)
        listFont.add(R.font.text_semi_bold)
        listFont.add(R.font.text_2)
        listFont.add(R.font.text_2_semi_bold)
        listFont.add(R.font.button)
        listFont.add(R.font.footer)
    }
}