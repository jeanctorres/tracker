package com.example.tracker.home.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tracker.R
import com.example.tracker.databinding.WorkoutsFragmentBinding

class WorkoutsFragment : Fragment() {

    private lateinit var exercisesViewModel: ExercisesViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        exercisesViewModel = ViewModelProviders.of(this).get(ExercisesViewModel::class.java)

        var binding = DataBindingUtil.inflate<WorkoutsFragmentBinding>(
            inflater,
            R.layout.home_workouts_fragment,
            container,
            false
        )
            .apply {

                viewModel = exercisesViewModel
                lifecycleOwner = viewLifecycleOwner
                callback = object : Callback {
                    override fun action() {
                        println("ACTION!")

                    }
                }

                val adapter = WorkoutsListAdapter(requireContext())
                recyclerView.adapter = adapter


                recyclerView.layoutManager =
                    LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

                this.lifecycleOwner?.let {
                    exercisesViewModel.workouts.observe(it, Observer { workouts ->
                        workouts?.let {
                            println("************************ Fragment XUserAndWorkoutsAndExercises:  ${it.size} UserAndWorkouts")
                            if (it.size > 0) {
                                println("${it[0].user} user")
                                println("${it[0].workoutsAndExercises.size} workoutsAndExercises")
                                adapter.setWorkoutsAndExercises(it[0].workoutsAndExercises)
                            }
                        }
                    })
                }

            }

        return binding.root
    }

    interface Callback {
        fun action()
    }


    fun onCreateViewOld(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.home_workouts_fragment, container, false)
        val recyclerView = root.findViewById<RecyclerView>(R.id.recycler_view)


        val adapter = WorkoutsListAdapter(requireContext())
        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)

        exercisesViewModel = ViewModelProviders.of(this).get(ExercisesViewModel::class.java)

        exercisesViewModel.workouts.observe(this, Observer { workouts ->
            workouts?.let {
                println("************************ Fragment XUserAndWorkoutsAndExercises:  ${it.size} UserAndWorkouts")
                if (it.size > 0) {
                    println("${it[0].user} user")
                    println("${it[0].workoutsAndExercises.size} workoutsAndExercises")
                    adapter.setWorkoutsAndExercises(it[0].workoutsAndExercises)
                }
            }
        })
        return root;

    }
}