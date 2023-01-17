package com.atahan.whatioweyoumate.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.atahan.whatioweyoumate.R
import com.atahan.whatioweyoumate.databinding.FragmentCalculationResultBinding
import com.atahan.whatioweyoumate.repository.FriendRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CalculationResultFragment : Fragment() {

    private lateinit var binding: FragmentCalculationResultBinding

    @Inject
    lateinit var repository: FriendRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalculationResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bill = ""
        repository.getFriends().forEach { friend ->
            val debtPerEach = friend.payment / repository.getFriends().size
            bill += "->Everyone should pay $debtPerEach to ${friend.name}\n"
        }
        binding.tvResult.text = bill
    }

}