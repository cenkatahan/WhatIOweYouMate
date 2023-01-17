package com.atahan.whatioweyoumate.presenter

import com.atahan.whatioweyoumate.interfaces.CalculationContractor
import javax.inject.Inject

class CalculationPresenter @Inject constructor(): CalculationContractor.ICalculationPresenter {

    private lateinit var view: CalculationContractor.ICalculationView

    override fun calculate() {
        view.calculateDebts()
    }

    fun setView(iCalculationView: CalculationContractor.ICalculationView) {
        view = iCalculationView
    }
}