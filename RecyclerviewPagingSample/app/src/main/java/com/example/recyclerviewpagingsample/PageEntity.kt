package com.example.recyclerviewpagingsample

data class PageEntity(
    var page: Int=1,
    var size: Int=10
){
    fun plusPage(){
        page++
    }
}