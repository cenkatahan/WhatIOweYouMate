package com.atahan.whatioweyoumate.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.databinding.FragmentCalculationResultBinding


class CalculationResultFragment : Fragment() {

    private lateinit var binding: FragmentCalculationResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentCalculationResultBinding.inflate(inflater, container, false)
        return binding.root
    }

}