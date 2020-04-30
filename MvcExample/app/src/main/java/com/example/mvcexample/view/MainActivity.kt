package com.example.mvcexample.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mvcexample.data.Model
import com.example.mvcexample.R
import com.example.mvcexample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {
//사용자에게 보이는 현상 또는 부분을 담당하기 위한 클래스
    val adapter: RecyclerViewAdapter by lazy {
        RecyclerViewAdapter(model)
    }

    val model = Model()

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayoutValues()
        setListener()
    }


    fun initLayoutValues() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerview.layoutManager = createLayoutManager()
        binding.recyclerview.adapter = adapter
        binding.lifecycleOwner = this

    }



    fun setListener(){
        binding.btnInput.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_input -> {// 클릭 이벤트를 부른  뷰의 아이디가 버튼 아이디와 동일하면
                onClickInputBtn()
            }
            else -> {

            }

        }
    }


    fun onClickInputBtn() {
        model.createTestDatas()
        adapter.notifyDataSetChanged()
    }


    fun createLayoutManager(): LinearLayoutManager {
        var layoutmanager = LinearLayoutManager(this)
        layoutmanager.orientation = RecyclerView.VERTICAL
        return layoutmanager
    }
}
