package com.example.movieappui.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.movieappui.R
import com.example.movieappui.adapter.CategoriesAdapter
import com.example.movieappui.adapter.FilmListAdapter
import com.example.movieappui.adapter.SliderAdapter
import com.example.movieappui.adapter.UpcomingListAdapter
import com.example.movieappui.databinding.FragmentBoardBinding
import com.example.movieappui.domain.SliderItem
import com.example.movieappui.viewModel.CategoryViewModel
import com.example.movieappui.viewModel.FilmViewModel
import kotlin.math.abs

class BoardFragment : Fragment() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var binding: FragmentBoardBinding
    private lateinit var sliderItems : ArrayList<SliderItem>
    private lateinit var handler : Handler
    private lateinit var sliderAdapter : SliderAdapter
    private lateinit var filmsAdapter: FilmListAdapter
    private lateinit var upcomingAdapter: UpcomingListAdapter
    private lateinit var categoriesAdapter : CategoriesAdapter

    private val filmViewModel : FilmViewModel by viewModels()
    private val categoryViewModel : CategoryViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBoardBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setUpTransformer()
        viewPager2.currentItem = 1
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                handler.removeCallbacks(runnable)
                handler.postDelayed(runnable, 2000)
            }
        })

        setUpPopularMoviesRv()
        binding.popularProgressBar.visibility = View.VISIBLE
        filmViewModel.setLoadingPopular(binding.popularProgressBar)
        filmViewModel.getMovies(1)
        sendRequest()

        setUpUpcomingMoviesRv()
        binding.upcomingProgressBar.visibility = View.VISIBLE
        filmViewModel.setLoadingUpcoming(binding.upcomingProgressBar)
        filmViewModel.getUpcomingMovies(3)
        sendUpComingRequest()

        setUpCategoriesAdapter()
        binding.categoryProgressBar.visibility = View.VISIBLE
        categoryViewModel.setLoadingProgressBar(binding.categoryProgressBar)
        categoryViewModel.getCategory()
        sendCategoriesRequest()
    }

    private fun sendCategoriesRequest() {
        categoryViewModel.categoryLiveData.observe(viewLifecycleOwner, Observer {
            categoriesAdapter.setListCategory(it)
        })
    }

    private fun setUpCategoriesAdapter() {
        categoriesAdapter = CategoriesAdapter()
        binding.rvCategory.apply{
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoriesAdapter
        }
    }

    private fun sendUpComingRequest() {
        filmViewModel.upcomingLiveData.observe(viewLifecycleOwner, Observer {
            upcomingAdapter.setListFilm(it)
        })
    }

    private fun setUpUpcomingMoviesRv() {
        upcomingAdapter = UpcomingListAdapter()
        binding.rvUpcoming.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = upcomingAdapter
        }
    }

    private fun setUpPopularMoviesRv() {
        filmsAdapter = FilmListAdapter()
        binding.rvPopular.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = filmsAdapter
        }
    }

    private fun sendRequest() {
        filmViewModel.moviesLiveData.observe(viewLifecycleOwner, Observer {
            filmsAdapter.setListFilm(it)
        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()
        handler.postDelayed(runnable, 2000)
    }

    private val runnable = Runnable{
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        viewPager2.setPageTransformer(transformer)
    }

    private fun init(){
        viewPager2 = binding.viewPagerMovies
        handler = Handler(Looper.myLooper()!!)
        sliderItems = ArrayList()
        sliderItems.add(SliderItem(R.drawable.wide))
        sliderItems.add(SliderItem(R.drawable.wide1))
        sliderItems.add(SliderItem(R.drawable.wide3))

        sliderAdapter = SliderAdapter(sliderItems, viewPager2)

        viewPager2.adapter = sliderAdapter
        viewPager2.offscreenPageLimit = 3
        viewPager2.clipToPadding = false
        viewPager2.clipChildren = false
        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
}