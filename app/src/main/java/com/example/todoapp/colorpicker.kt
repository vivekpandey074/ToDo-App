package com.example.todoapp

object  colorpicker {
    val colors= arrayOf( "#884EA0",
        "#6CB6E8",
        "#BB8FCE",
        "#707B7C",
         "#50F32F",
        "#2E4053",
        "#A2D9CE",
        "#797D7F",
        "#A93226",

        "#1F618D",







    )
    var currentcolorindex=0

    fun getcolor():String{
        currentcolorindex=(currentcolorindex+1)% colors.size
        return  colors[currentcolorindex]
    }
}