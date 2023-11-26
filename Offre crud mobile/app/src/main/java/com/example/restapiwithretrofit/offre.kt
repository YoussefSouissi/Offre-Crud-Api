package com.example.restapiwithretrofit

data class offre (
        val code : Int? = null,
        val intitule :String? = null,
        val specialite :String? = null,
        val societe:String? = null,
        val nbPostes: Int? = null,
        val pays:String? = null,
        var isSelected:Boolean=false
        )