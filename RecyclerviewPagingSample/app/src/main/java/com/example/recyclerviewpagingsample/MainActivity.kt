package com.example.recyclerviewpagingsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewpagingsample.databinding.ActivityMainBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    private val scrollObserver = BehaviorSubject.createDefault(false).toSerialized()

    private val generaRecyclerviewAdapter: GeneraRecyclerviewAdapter by lazy {
        GeneraRecyclerviewAdapter()
    }

    private val pageEntity :PageEntity by lazy {
        PageEntity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        initRecyclerview()
    }

    fun initRecyclerview() {
        addObserver()

        binding.recyclerview.run {

            generaRecyclerviewAdapter.addItems(createTestDatas())

            LinearLayoutManager(context).apply {
                orientation = RecyclerView.VERTICAL
                layoutManager = this
            }
            setRecyclerViewScrolListener()
            adapter = generaRecyclerviewAdapter
        }
    }

    fun setRecyclerViewScrolListener(){
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(recyclerView.canScrollVertically(-1)){
                    Log.d("ScrollListener","Vertical End")
                    scrollObserver.onNext(true)
                }
            }
        })
    }


    fun addObserver(){
        compositeDisposable.add(
            scrollObserver.throttleFirst(1,TimeUnit.SECONDS,Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                    onNext = {
                        Log.d("Next Page Request!","Call Api")
                        pageEntity.plusPage()
                        // 다음페이지 요청API(pageEntity)
                        //결과가 나오면
                        generaRecyclerviewAdapter.addItems(createTestDatas())
                        generaRecyclerviewAdapter.notifyDataSetChanged()
                    },
                    onError = {
                        it.printStackTrace()
                    }
                )
        )
    }


    fun createTestDatas(): ArrayList<TestModelEntity> {
        return ArrayList<TestModelEntity>().apply {
            for (i in 0..10) {
                TestModelEntity("$i 번째 아이템").apply {
                    add(this)
                }
            }
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}