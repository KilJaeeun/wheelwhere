package com.example.kotlin_tmap.ui.home.model

import com.example.kotlin_tmap.ui.home.adapter.model.PointRecyclerModel
import com.example.kotlin_tmap.ui.home.presenter.HomeContract
import com.example.kotlin_tmap.ui.home.presenter.HomePresenter
import com.skt.Tmap.TMapPoint

class DataSourceObject (private val view : HomeContract.View,
                        private val homePresenter: HomePresenter,
                        private val pointRecyclerModel: PointRecyclerModel)  : DataSourceInterface{

    private val MAP_POI_DATA : DataSource by lazy {
        DataSource(view, homePresenter, pointRecyclerModel)
    }

    override fun FindAroundNamePOI(point: TMapPoint)
            = MAP_POI_DATA.FindAroundNamePOI(point)
}