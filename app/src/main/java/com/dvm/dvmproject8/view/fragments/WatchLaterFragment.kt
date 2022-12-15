package com.dvm.dvmproject8.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dvm.dvmproject8.databinding.FragmentWatchLaterBinding
import com.dvm.dvmproject8.utils.AnimationHelper
//import kotlinx.android.synthetic.main.fragment_watch_later.*

class WatchLaterFragment : Fragment() {

    private lateinit var watchLaterBinding: FragmentWatchLaterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        watchLaterBinding = FragmentWatchLaterBinding.inflate(layoutInflater, container, false)
        return watchLaterBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AnimationHelper.performFragmentCircularRevealAnimation(watchLaterBinding.watchLaterFragmentRoot, requireActivity(), 3)
    }
}