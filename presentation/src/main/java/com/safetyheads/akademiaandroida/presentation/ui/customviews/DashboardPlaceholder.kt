<<<<<<<< HEAD:app/src/main/java/com/safetyheads/akademiaandroida/dashboard/DashboardFragment.kt
package com.safetyheads.akademiaandroida.dashboard
========
package com.safetyheads.akademiaandroida.presentation.ui.customviews
>>>>>>>> 8aee5c5d39c4e8306cb02c7907570dffe70cd40c:presentation/src/main/java/com/safetyheads/akademiaandroida/presentation/ui/customviews/DashboardPlaceholder.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
<<<<<<<< HEAD:app/src/main/java/com/safetyheads/akademiaandroida/dashboard/DashboardFragment.kt
import com.safetyheads.akademiaandroida.databinding.FragmentDashboardBinding
========
import com.safetyheads.akademiaandroida.presentation.databinding.FragmentDashboardPlaceholderBinding
>>>>>>>> 8aee5c5d39c4e8306cb02c7907570dffe70cd40c:presentation/src/main/java/com/safetyheads/akademiaandroida/presentation/ui/customviews/DashboardPlaceholder.kt

class DashboardFragment : Fragment() {

    private lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

}
