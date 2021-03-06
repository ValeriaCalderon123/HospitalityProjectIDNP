package com.example.hospitalityproject.model

import android.content.Context
import android.graphics.*
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.example.hospitalityproject.R

class BarChart (context: Context, arrayIds: ArrayList<String>, arrayCategories: ArrayList<Especialidad>, arrayNames: ArrayList<String>): View(context){


    private lateinit var paintBackgroundBar: Paint
    private lateinit var paintLineBottom: Paint
    private lateinit var paintLines: Paint
    private lateinit var paintLabelBar: Paint
    private lateinit var paintTitleBar: Paint
    private var spaceLeft = 0f
    private var spaceRight = 0f
    private var heightAboveBottom = 0f
    private var pixelsPerValue = 0f
    private var numberOfCities = 0
    private var widthBar = 0f
    private var titleBarGraph = ""

    private var arrayIdsBC: ArrayList<String> = arrayIds
    private var arrayCategoriesBC: ArrayList<Especialidad> = arrayCategories
    private var arrayNamesBC: ArrayList<String> = arrayNames

    private lateinit var arrayHC: ArrayList<HospitalCategories>

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.v("infoDraw","Height: $height (Altura)")    // Alto
        Log.v("infoDraw", "Width: $width (Anchura)")    // Ancho

        arrayHC = arrayListOf()

        Log.d("TestReadBC", arrayIdsBC.toString())
        Log.d("TestReadBC", arrayCategoriesBC.toString())
        Log.d("TestReadBC", arrayNamesBC.toString())

        configVariables()
        setColors()
        drawLines(canvas) //This method also draws the labels of 'y' Axis
        drawValues(canvas) //This method also draws the labels of 'x' Axis
        drawBottomLineAndTittle(canvas)
    }



    private fun configVariables(){
        spaceLeft = 100f
        spaceRight = 50f
        heightAboveBottom = height / 6F
        pixelsPerValue = (height - heightAboveBottom) / 120f
        titleBarGraph = "Especialidades por hospital"
        Log.v("infoDraw", "heightAboveBottom: $heightAboveBottom")
        Log.v("infoDraw", "height - heightAboveBottom: ${height - heightAboveBottom}")
        Log.v("infoDraw", "valuePerPixel: $pixelsPerValue")
    }

    private fun setColors(){
        // Set background color
        paintBackgroundBar = Paint()
        val blueColor01 = ContextCompat.getColor(context, R.color.blue01)
        Log.v("infoDraw", "Color blue $blueColor01.toString()")
        val blueColor02 = ContextCompat.getColor(context, R.color.blue02)
        val PurpleColor01 = ContextCompat.getColor(context, R.color.purple_700)
        paintBackgroundBar.setColor(blueColor01)
        // Set lines bottom
        paintLineBottom = Paint()
        paintLineBottom.setColor(Color.BLACK)
        paintLineBottom.strokeWidth = 5f
        // Set lines
        paintLines = Paint()
        paintLines.setColor(Color.GRAY)
//        paintLines.strokeWidth = 0.5f

        paintLabelBar = Paint()
        paintLabelBar.setColor(blueColor02)
        paintLabelBar.textSize = 35f

        paintTitleBar = Paint()
        paintTitleBar.setColor(PurpleColor01)
        paintTitleBar.textSize = 80f
        paintTitleBar.typeface = Typeface.create(Typeface.SANS_SERIF,Typeface.NORMAL);

    }

    private fun drawValues(canvas: Canvas?) {

        for (i in 0 until arrayIdsBC.size){
            var tmpHospitalCategories = HospitalCategories(arrayIdsBC.get(i), 0, ContextCompat.getColor(context, R.color.color01))
            arrayHC.add(tmpHospitalCategories)
        }
        Log.d("TestReadBC arrayHC", arrayHC.toString())

        for (i in 0 until arrayCategoriesBC.size){
            for (j in 0 until arrayIdsBC.size){
                if(arrayCategoriesBC.get(i).codHospital.toString().equals(arrayIdsBC.get(j))){
                    arrayHC.get(j).addValue()
                }
            }
        }
        Log.d("TestReadBC arrayHC 2", arrayHC.toString())
        /*
        val arrayAllCitiesNames = ArrayList<String>()
        var arrayCitiesNames = ArrayList<String>()
        arrayAllCitiesNames.add("Arequipa")
        arrayAllCitiesNames.add("Lima")
        arrayAllCitiesNames.add("Callao")
        arrayAllCitiesNames.add("Amazonas")
        arrayAllCitiesNames.add("Ayacucho")
        arrayAllCitiesNames.add("Tacna")
        arrayAllCitiesNames.add("Puno")
        val arrayAllColors = ArrayList<Int>()
        var arrayColors = ArrayList<Int>();
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color01));
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color02));
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color03));
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color04));
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color05));
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color06));
        arrayAllColors.add(ContextCompat.getColor(context, R.color.color07));
        val rndNumberOfCities = (4..6).random()
        Log.v("infoDraw", "Valor random n??mero de ciudades: $rndNumberOfCities")

        for(i in 0 until rndNumberOfCities){

            val rndNumber = (0 until arrayAllCitiesNames.size).random()
            arrayCitiesNames.add(arrayAllCitiesNames.get(rndNumber))
            arrayColors.add(arrayAllColors.get(rndNumber))
        }

        for(i in 0 until arrayCitiesNames.size)
            Log.v("infoDraw", arrayCitiesNames.get(i))

        val arrayTmp = arrayCitiesNames
        arrayCitiesNames = arrayTmp.distinct() as ArrayList<String>
        val arrayTmp2 = arrayColors
        arrayColors = arrayTmp2.distinct() as ArrayList<Int>
        Log.v("infoDraw", "Actualizaci??n para eliminar repetidos")

        for(i in 0 until arrayCitiesNames.size)
            Log.v("infoDraw", arrayCitiesNames.get(i))

        val arrayCities = ArrayList<HospitalCategories>()
        for(i in 0 until arrayCitiesNames.size)
            arrayCities.add(HospitalCategories(arrayCitiesNames.get(i), (10..100).random(), arrayColors.get(i)))

        for(i in 0 until arrayCities.size) {
            Log.v("infoDraw", "Pos $i ${arrayCities.get(i)}")
        }
        */

        numberOfCities = arrayHC.size

        widthBar = (width - spaceRight - spaceLeft) / (numberOfCities * 3f)

        Log.v("infoDraw", "widthBar: $widthBar")

        for(i in 1..numberOfCities){
            drawRectangle(canvas, arrayHC.get(i - 1).getValue() * 20, (3 * (i - 1) + 1),arrayHC.get(i - 1).getName(), arrayHC.get(i - 1).getColor())
//                      drawRectangle(canvas, arrayCities.get(i - 1).getValue(), (3 * (i - 1) + 1),arrayCitiesNames.get(i-1))
        }

    }

    private fun drawBottomLineAndTittle(canvas: Canvas?){
        val list = mutableListOf<Float>()
        var spaceTittle = width.toFloat()/2.8f - 330f
        list.add(spaceLeft)                                             // x0
        list.add(height - heightAboveBottom)                            // y0
        list.add(width - spaceRight)                                    // xf
        list.add(height - heightAboveBottom)                            // yf
        canvas!!.drawLines(list.toFloatArray(), paintLineBottom)
        canvas!!.drawText(titleBarGraph,spaceTittle, 150f, paintTitleBar)
    }

    private fun drawLines(canvas: Canvas?){
        val listLines = mutableListOf<Float>()
        val space = 20
        for(i in 1..5){
            val tmp = (height - heightAboveBottom) - (pixelsPerValue * space * i)
            listLines.add(spaceLeft)
            listLines.add(tmp)
            listLines.add(width - spaceRight)
            listLines.add(tmp)
            Log.v("infoDraw", "x0 = $spaceLeft \t y0 = $tmp \t xf = ${width - spaceRight} \t yf = $tmp")

            canvas!!.drawText((1f * i).toInt().toString(), 35f,tmp + 10f , paintLabelBar) //Labels Axis Y
        }
        canvas!!.drawLines(listLines.toFloatArray(), paintLines)

    }

    private fun drawRectangle(canvas: Canvas?, _height: Int, _pos: Int, _nameCity: String, _color: Int){
        val left = spaceLeft + (_pos * widthBar)
        val top = ((height - heightAboveBottom) - (_height * pixelsPerValue))
        val right = left + widthBar
        val bottom = height - heightAboveBottom
        Log.v("infoDraw", "$_pos left = $left \t top = $top \t right = $right \t bottom = $bottom")
        val rect = Rect(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        val backGround = Paint()
        backGround.setColor(_color)
        canvas!!.drawRect(rect, backGround)

        canvas!!.drawText(_nameCity,left, bottom + 40f, paintLabelBar) //Labels Axis X
    }
}