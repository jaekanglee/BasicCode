package com.example.mvcexample.data

class Model {
//실제로 어떤 로직을 돌리기 위한 클래스  데이터 가공을 위한 클래스 임

    var itemList :ArrayList<DataEntity> = ArrayList()



    //데이터를 만드는 펑션
    fun createTestDatas(){
        for(i in 0 until  100){
            var dataEntity = DataEntity()
            dataEntity.contents ="${i}번째 아이템"
            itemList.add(dataEntity)
        }

    }

    //데이터를 전부 지우는 펑션
    fun deleteAllDatas(){
        itemList.clear()
    }

    //클릭된 아이템의 데이터를 바꾸는 펑션
    fun clickedRowItem(pos : Int){
        itemList[pos].contents="클릭된 놈"
    }


    // 해당 아이템의 내용을 가져오는 펑션
    fun getItemRow(pos: Int):String?{
        return itemList[pos].contents
    }
}