package com.atahan.whatioweyoumate.interfaces

interface CalculationContractor {

    interface ICalculationView {
        fun calculateDebts()
    }

    interface ICalculationPresenter {
        fun calculate()
    }
}