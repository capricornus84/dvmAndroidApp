package com.dvm.dvmproject8.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvm.dvmproject8.databinding.FragmentSelectionsBinding
import com.dvm.dvmproject8.utils.AnimationHelper
import kotlinx.android.synthetic.main.fragment_selections.*

class SelectionsFragment : Fragment() {

    private lateinit var selFragBinding: FragmentSelectionsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        selFragBinding = FragmentSelectionsBinding.inflate(layoutInflater, container, false)
        return selFragBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(selections_fragment_root, requireActivity(), 4)
    }
}