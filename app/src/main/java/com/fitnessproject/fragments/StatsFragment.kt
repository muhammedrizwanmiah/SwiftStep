package com.fitnessproject.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.fitnessproject.R
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_stats.*
import java.util.ArrayList

class StatsFragment : Fragment() {

    lateinit var auth: FirebaseAuth
    var databaseReference :  DatabaseReference? = null
    var database: FirebaseDatabase? = null

    var wedCal = 0.0

    var currentTotalOrAvg = "Total"
    var currentTotalStepsCalOrDist = "Steps"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,

        ): View? {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database?.reference!!.child("profile")

        setBarChart()
        loadStats()
        graphButtonClicked()
    }

    private fun loadStats() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        if(currentTotalOrAvg == "Total" && currentTotalStepsCalOrDist == "Steps"){
            barChartTotalSteps.visibility = (View.VISIBLE)
            barChartTotalSteps.animateY(3000)
            barChartTotalCal.visibility = (View.INVISIBLE)
            barChartTotalDist.visibility = (View.INVISIBLE)

            barChartAvgSteps.visibility = (View.INVISIBLE)
            barChartAvgCal.visibility = (View.INVISIBLE)
            barChartAvgDist.visibility = (View.INVISIBLE)

        }
        if(currentTotalOrAvg == "Total" && currentTotalStepsCalOrDist == "Cal"){
            barChartTotalSteps.visibility = (View.INVISIBLE)
            barChartTotalCal.visibility = (View.VISIBLE)
            barChartTotalCal.animateY(3000)
            barChartTotalDist.visibility = (View.INVISIBLE)

            barChartAvgSteps.visibility = (View.INVISIBLE)
            barChartAvgCal.visibility = (View.INVISIBLE)
            barChartAvgDist.visibility = (View.INVISIBLE)
        }
        if(currentTotalOrAvg == "Total" && currentTotalStepsCalOrDist == "Dist"){
            barChartTotalSteps.visibility = (View.INVISIBLE)
            barChartTotalCal.visibility = (View.INVISIBLE)
            barChartTotalDist.visibility = (View.VISIBLE)
            barChartTotalDist.animateY(3000)

            barChartAvgSteps.visibility = (View.INVISIBLE)
            barChartAvgCal.visibility = (View.INVISIBLE)
            barChartAvgDist.visibility = (View.INVISIBLE)
        }

        if(currentTotalOrAvg == "Avg" && currentTotalStepsCalOrDist == "Steps"){
            barChartTotalSteps.visibility = (View.INVISIBLE)
            barChartTotalCal.visibility = (View.INVISIBLE)
            barChartTotalDist.visibility = (View.INVISIBLE)

            barChartAvgSteps.visibility = (View.VISIBLE)
            barChartAvgSteps.animateY(3000)
            barChartAvgCal.visibility = (View.INVISIBLE)
            barChartAvgDist.visibility = (View.INVISIBLE)

        }
        if(currentTotalOrAvg == "Avg" && currentTotalStepsCalOrDist == "Cal"){
            barChartTotalSteps.visibility = (View.INVISIBLE)
            barChartTotalCal.visibility = (View.INVISIBLE)
            barChartTotalDist.visibility = (View.INVISIBLE)

            barChartAvgSteps.visibility = (View.INVISIBLE)
            barChartAvgCal.visibility = (View.VISIBLE)
            barChartAvgCal.animateY(3000)
            barChartAvgDist.visibility = (View.INVISIBLE)
        }
        if(currentTotalOrAvg == "Avg" && currentTotalStepsCalOrDist == "Dist"){
            barChartTotalSteps.visibility = (View.INVISIBLE)
            barChartTotalCal.visibility = (View.INVISIBLE)
            barChartTotalDist.visibility = (View.INVISIBLE)

            barChartAvgSteps.visibility = (View.INVISIBLE)
            barChartAvgCal.visibility = (View.INVISIBLE)
            barChartAvgDist.visibility = (View.VISIBLE)
            barChartAvgDist.animateY(3000)
        }

        userreference?.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    private fun graphButtonClicked(){

        if(currentTotalOrAvg == "Total") {
            totalsButton.setBackgroundResource(R.drawable.graph_button) //green
            avgsButton.setBackgroundResource(R.drawable.graph_button_grey)
        } else if (currentTotalOrAvg == "Avg"){
        totalsButton.setBackgroundResource(R.drawable.graph_button_grey)
        avgsButton.setBackgroundResource(R.drawable.graph_button)}

        if(currentTotalStepsCalOrDist == "Steps") {
            stepsButton.setBackgroundResource(R.drawable.graph_button)
            calButton.setBackgroundResource(R.drawable.graph_button_grey) //green
            distButton.setBackgroundResource(R.drawable.graph_button_grey)
        } else if (currentTotalStepsCalOrDist == "Cal") {
            stepsButton.setBackgroundResource(R.drawable.graph_button_grey)
            calButton.setBackgroundResource(R.drawable.graph_button) //green
            distButton.setBackgroundResource(R.drawable.graph_button_grey)
        } else if(currentTotalStepsCalOrDist == "Dist"){
            stepsButton.setBackgroundResource(R.drawable.graph_button_grey)
            calButton.setBackgroundResource(R.drawable.graph_button_grey)
            distButton.setBackgroundResource(R.drawable.graph_button) //green
        }

        totalsButton.setOnClickListener {
            currentTotalOrAvg = "Total"
            totalsButton.setBackgroundResource(R.drawable.graph_button) //green
            avgsButton.setBackgroundResource(R.drawable.graph_button_grey)
            loadStats()
        }
        avgsButton.setOnClickListener {
            currentTotalOrAvg = "Avg"
            totalsButton.setBackgroundResource(R.drawable.graph_button_grey)
            avgsButton.setBackgroundResource(R.drawable.graph_button)
            loadStats()
        }
        stepsButton.setOnClickListener {
            currentTotalStepsCalOrDist = "Steps"
            stepsButton.setBackgroundResource(R.drawable.graph_button)
            calButton.setBackgroundResource(R.drawable.graph_button_grey) //green
            distButton.setBackgroundResource(R.drawable.graph_button_grey)
            loadStats()
        }
        calButton.setOnClickListener {
            currentTotalStepsCalOrDist = "Cal"
            stepsButton.setBackgroundResource(R.drawable.graph_button_grey)
            calButton.setBackgroundResource(R.drawable.graph_button) //green
            distButton.setBackgroundResource(R.drawable.graph_button_grey)
            loadStats()
        }
        distButton.setOnClickListener {
            currentTotalStepsCalOrDist = "Dist"
            stepsButton.setBackgroundResource(R.drawable.graph_button_grey)
            calButton.setBackgroundResource(R.drawable.graph_button_grey)
            distButton.setBackgroundResource(R.drawable.graph_button) //green
            loadStats()
        }
    }

    private fun setBarChart() {

        val user = auth.currentUser
        val userreference = databaseReference?.child(user?.uid!!)

        userreference?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                //totals graph for calorie
                val entriesTotalCal = ArrayList<BarEntry>()
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Mon").value.toString()
                            .toFloat(), 0
                    )
                )
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Tue").value.toString()
                            .toFloat(), 1
                    )
                )
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Wed").value.toString()
                            .toFloat(), 2
                    )
                )
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Thu").value.toString()
                            .toFloat(), 3
                    )
                )
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Fri").value.toString()
                            .toFloat(), 4
                    )
                )
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Sat").value.toString()
                            .toFloat(), 5
                    )
                )
                entriesTotalCal.add(
                    BarEntry(
                        snapshot.child("user_data/total_calories_by_day/Sun").value.toString()
                            .toFloat(), 6
                    )
                )

                val barDataSetTotalCal = BarDataSet(entriesTotalCal, "Cells")

                val labels = ArrayList<String>()
                labels.add("MON")
                labels.add("TUE")
                labels.add("WED")
                labels.add("THU")
                labels.add("FRI")
                labels.add("SAT")
                labels.add("SUN")
                val dataTotalCal = BarData(labels, barDataSetTotalCal)
                barChartTotalCal.data = dataTotalCal // set the data and list of labels into chart

                barChartTotalCal.setDescription("")  // set the description

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                barDataSetTotalCal.color = resources.getColor(R.color.colorAccent)
                barChartTotalCal.axisRight.setDrawLabels(false)
                barChartTotalCal.axisLeft.setDrawGridLines(false);
                barChartTotalCal.axisLeft.setDrawAxisLine(false);
                barChartTotalCal.axisRight.setDrawGridLines(false);
                barChartTotalCal.axisRight.setDrawAxisLine(false)
                barChartTotalCal.axisLeft.setDrawLabels(false);
                barChartTotalCal.xAxis.setDrawGridLines(false);
                barChartTotalCal.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                barChartTotalCal.xAxis.setDrawAxisLine(false);
                barChartTotalCal.animateY(3000)

                dataTotalCal.setValueTextSize(15f)
                dataTotalCal.setValueTextColor(Color.parseColor("#FFAAAAAA"))

                barChartTotalCal.xAxis.textSize = 15f
                barChartTotalCal.xAxis.textColor = Color.parseColor("#FFFFFFFF")

                //totals graph for steps

                val entriesTotalSteps = ArrayList<BarEntry>()
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Mon").value.toString()
                            .toFloat(), 0
                    )
                )
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Tue").value.toString()
                            .toFloat(), 1
                    )
                )
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Wed").value.toString()
                            .toFloat(), 2
                    )
                )
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Thu").value.toString()
                            .toFloat(), 3
                    )
                )
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Fri").value.toString()
                            .toFloat(), 4
                    )
                )
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Sat").value.toString()
                            .toFloat(), 5
                    )
                )
                entriesTotalSteps.add(
                    BarEntry(
                        snapshot.child("user_data/total_steps_by_day/Sun").value.toString()
                            .toFloat(), 6
                    )
                )

                val barDataSetTotalSteps = BarDataSet(entriesTotalSteps, "Cells")

                val dataTotalSteps = BarData(labels, barDataSetTotalSteps)
                barChartTotalSteps.data =
                    dataTotalSteps // set the data and list of labels into chart

                barChartTotalSteps.setDescription("")  // set the description

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                barDataSetTotalSteps.color = resources.getColor(R.color.colorAccent)
                barChartTotalSteps.axisRight.setDrawLabels(false)
                barChartTotalSteps.axisLeft.setDrawGridLines(false);
                barChartTotalSteps.axisLeft.setDrawAxisLine(false);
                barChartTotalSteps.axisRight.setDrawGridLines(false);
                barChartTotalSteps.axisRight.setDrawAxisLine(false)
                barChartTotalSteps.axisLeft.setDrawLabels(false);
                barChartTotalSteps.xAxis.setDrawGridLines(false);
                barChartTotalSteps.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                barChartTotalSteps.xAxis.setDrawAxisLine(false);
                barChartTotalSteps.animateY(3000)

                dataTotalSteps.setValueTextSize(15f)
                dataTotalSteps.setValueTextColor(Color.parseColor("#FFAAAAAA"))

                barChartTotalSteps.xAxis.textSize = 15f
                barChartTotalSteps.xAxis.textColor = Color.parseColor("#FFFFFFFF")


                //bar chart for totalDist

                val entriesTotalDist = ArrayList<BarEntry>()
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Mon").value.toString()
                            .toFloat(), 0
                    )
                )
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Tue").value.toString()
                            .toFloat(), 1
                    )
                )
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Wed").value.toString()
                            .toFloat(), 2
                    )
                )
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Thu").value.toString()
                            .toFloat(), 3
                    )
                )
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Fri").value.toString()
                            .toFloat(), 4
                    )
                )
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Sat").value.toString()
                            .toFloat(), 5
                    )
                )
                entriesTotalDist.add(
                    BarEntry(
                        snapshot.child("user_data/total_distance_by_day/Sun").value.toString()
                            .toFloat(), 6
                    )
                )

                val barDataSetTotalDist = BarDataSet(entriesTotalDist, "Cells")
                val dataTotalDist = BarData(labels, barDataSetTotalDist)
                barChartTotalDist.data = dataTotalDist // set the data and list of labels into chart

                barChartTotalDist.setDescription("")  // set the description

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                barDataSetTotalDist.color = resources.getColor(R.color.colorAccent)
                barChartTotalDist.axisRight.setDrawLabels(false)
                barChartTotalDist.axisLeft.setDrawGridLines(false);
                barChartTotalDist.axisLeft.setDrawAxisLine(false);
                barChartTotalDist.axisRight.setDrawGridLines(false);
                barChartTotalDist.axisRight.setDrawAxisLine(false)
                barChartTotalDist.axisLeft.setDrawLabels(false);
                barChartTotalDist.xAxis.setDrawGridLines(false);
                barChartTotalDist.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                barChartTotalDist.xAxis.setDrawAxisLine(false);

                barChartTotalDist.legend.isEnabled = false;
                barChartTotalCal.legend.isEnabled = false;
                barChartTotalSteps.legend.isEnabled = false;

                barChartAvgDist.legend.isEnabled = false;
                barChartAvgCal.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;

                barChartTotalDist.animateY(3000)

                dataTotalDist.setValueTextSize(15f)
                dataTotalDist.setValueTextColor(Color.parseColor("#FFAAAAAA"))

                barChartTotalDist.xAxis.textSize = 15f
                barChartTotalDist.xAxis.textColor = Color.parseColor("#FFFFFFFF")


                //Average Cal

                var avgCalForMon = 0f
                var avgCalForTue = 0f
                var avgCalForWed = 0f
                var avgCalForThu = 0f
                var avgCalForFri = 0f
                var avgCalForSat = 0f
                var avgCalForSun = 0f

                var avgStepsForMon = 0f
                var avgStepsForTue = 0f
                var avgStepsForWed = 0f
                var avgStepsForThu = 0f
                var avgStepsForFri = 0f
                var avgStepsForSat = 0f
                var avgStepsForSun = 0f

                var avgDistForMon = 0f
                var avgDistForTue = 0f
                var avgDistForWed = 0f
                var avgDistForThu = 0f
                var avgDistForFri = 0f
                var avgDistForSat = 0f
                var avgDistForSun = 0f

                //

                avgCalForMon = if(snapshot.child("user_data/counter_for_day/Mon").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Mon").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Mon").value.toString()
                        .toInt()
                }

                avgCalForTue = if(snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Tue").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Tue").value.toString()
                        .toInt()
                }

                avgCalForWed = if(snapshot.child("user_data/counter_for_day/Wed").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Wed").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Wed").value.toString()
                        .toInt()
                }

                avgCalForThu = if(snapshot.child("user_data/counter_for_day/Thu").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Thu").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Thu").value.toString()
                        .toInt()
                }

                avgCalForFri = if(snapshot.child("user_data/counter_for_day/Fri").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Fri").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Fri").value.toString()
                        .toInt()
                }

                avgCalForSat = if(snapshot.child("user_data/counter_for_day/Sat").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Sat").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Sat").value.toString()
                        .toInt()
                }

                avgCalForSun = if(snapshot.child("user_data/counter_for_day/Sun").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Sun").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Sun").value.toString()
                        .toInt()
                }

                //
                avgDistForMon = if(snapshot.child("user_data/counter_for_day/Mon").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Mon").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Mon").value.toString()
                        .toInt()
                }

                avgDistForTue = if(snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Tue").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Tue").value.toString()
                        .toInt()
                }

                avgDistForWed = if(snapshot.child("user_data/counter_for_day/Wed").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Wed").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Wed").value.toString()
                        .toInt()
                }

                avgDistForThu = if(snapshot.child("user_data/counter_for_day/Thu").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Thu").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Thu").value.toString()
                        .toInt()
                }

                avgDistForFri = if(snapshot.child("user_data/counter_for_day/Fri").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Fri").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Fri").value.toString()
                        .toInt()
                }

                avgDistForSat = if(snapshot.child("user_data/counter_for_day/Sat").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Sat").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Sat").value.toString()
                        .toInt()
                }

                avgDistForSun = if(snapshot.child("user_data/counter_for_day/Sun").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_distance_by_day/Sun").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Sun").value.toString()
                        .toInt()
                }

                // 0 check for steps
                avgStepsForMon = if(snapshot.child("user_data/counter_for_day/Mon").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Mon").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Mon").value.toString()
                        .toInt()
                }

                avgStepsForTue = if(snapshot.child("user_data/counter_for_day/Tue").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Tue").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Tue").value.toString()
                        .toInt()
                }

                avgStepsForWed = if(snapshot.child("user_data/counter_for_day/Wed").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Wed").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Wed").value.toString()
                        .toInt()
                }

                avgStepsForThu = if(snapshot.child("user_data/counter_for_day/Thu").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Thu").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Thu").value.toString()
                        .toInt()
                }

                avgStepsForFri = if(snapshot.child("user_data/counter_for_day/Fri").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Fri").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Fri").value.toString()
                        .toInt()
                }

                avgStepsForSat = if(snapshot.child("user_data/counter_for_day/Sat").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Sat").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Sat").value.toString()
                        .toInt()
                }

                avgStepsForSun = if(snapshot.child("user_data/counter_for_day/Sun").value.toString().toInt() == 0){
                    0f
                } else {
                    snapshot.child("user_data/total_steps_by_day/Sun").value.toString()
                        .toFloat() / snapshot.child("user_data/counter_for_day/Sun").value.toString()
                        .toInt()
                }


                val entriesAvgCal = ArrayList<BarEntry>()
                entriesAvgCal.add(BarEntry(avgCalForMon, 0))
                entriesAvgCal.add(BarEntry(avgCalForTue, 1))
                entriesAvgCal.add(BarEntry(avgCalForWed, 2))
                entriesAvgCal.add(BarEntry(avgCalForThu, 3))
                entriesAvgCal.add(BarEntry(avgCalForFri, 4))
                entriesAvgCal.add(BarEntry(avgCalForSat, 5))
                entriesAvgCal.add(BarEntry(avgCalForSun, 6))

                val barDataSetAvgCal = BarDataSet(entriesAvgCal, "Cells")
                val dataAvgCal = BarData(labels, barDataSetAvgCal)
                barChartAvgCal.data = dataAvgCal // set the data and list of labels into chart

                barChartAvgCal.setDescription("")  // set the description

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                barDataSetAvgCal.color = resources.getColor(R.color.colorAccent)
                barChartAvgCal.axisRight.setDrawLabels(false)
                barChartAvgCal.axisLeft.setDrawGridLines(false);
                barChartAvgCal.axisLeft.setDrawAxisLine(false);
                barChartAvgCal.axisRight.setDrawGridLines(false);
                barChartAvgCal.axisRight.setDrawAxisLine(false)
                barChartAvgCal.axisLeft.setDrawLabels(false);
                barChartAvgCal.xAxis.setDrawGridLines(false);
                barChartAvgCal.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                barChartAvgCal.xAxis.setDrawAxisLine(false);

                barChartAvgCal.legend.isEnabled = false;
                barChartAvgCal.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;

                barChartAvgCal.animateY(3000)

                dataAvgCal.setValueTextSize(15f)
                dataAvgCal.setValueTextColor(Color.parseColor("#FFAAAAAA"))

                barChartAvgCal.xAxis.textSize = 15f
                barChartAvgCal.xAxis.textColor = Color.parseColor("#FFFFFFFF")

                //AvgSteps

                val entriesAvgSteps = ArrayList<BarEntry>()
                entriesAvgSteps.add(BarEntry(avgStepsForMon, 0))
                entriesAvgSteps.add(BarEntry(avgStepsForTue, 1))
                entriesAvgSteps.add(BarEntry(avgStepsForWed, 2))
                entriesAvgSteps.add(BarEntry(avgStepsForThu, 3))
                entriesAvgSteps.add(BarEntry(avgStepsForFri, 4))
                entriesAvgSteps.add(BarEntry(avgStepsForSat, 5))
                entriesAvgSteps.add(BarEntry(avgStepsForSun, 6))

                val barDataSetAvgSteps = BarDataSet(entriesAvgSteps, "Cells")
                val dataAvgSteps = BarData(labels, barDataSetAvgSteps)
                barChartAvgSteps.data = dataAvgSteps // set the data and list of labels into chart

                barChartAvgSteps.setDescription("")  // set the description

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                barDataSetAvgSteps.color = resources.getColor(R.color.colorAccent)
                barChartAvgSteps.axisRight.setDrawLabels(false)
                barChartAvgSteps.axisLeft.setDrawGridLines(false);
                barChartAvgSteps.axisLeft.setDrawAxisLine(false);
                barChartAvgSteps.axisRight.setDrawGridLines(false);
                barChartAvgSteps.axisRight.setDrawAxisLine(false)
                barChartAvgSteps.axisLeft.setDrawLabels(false);
                barChartAvgSteps.xAxis.setDrawGridLines(false);
                barChartAvgSteps.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                barChartAvgSteps.xAxis.setDrawAxisLine(false);

                barChartAvgSteps.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;

                barChartAvgSteps.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;

                barChartAvgSteps.animateY(3000)

                dataAvgSteps.setValueTextSize(15f)
                dataAvgSteps.setValueTextColor(Color.parseColor("#FFAAAAAA"))

                barChartAvgSteps.xAxis.textSize = 15f
                barChartAvgSteps.xAxis.textColor = Color.parseColor("#FFFFFFFF")

                //Avg Dist
                val entriesAvgDist = ArrayList<BarEntry>()
                entriesAvgDist.add(BarEntry(avgDistForMon, 0))
                entriesAvgDist.add(BarEntry(avgDistForTue, 1))
                entriesAvgDist.add(BarEntry(avgDistForWed, 2))
                entriesAvgDist.add(BarEntry(avgDistForThu, 3))
                entriesAvgDist.add(BarEntry(avgDistForFri, 4))
                entriesAvgDist.add(BarEntry(avgDistForSat, 5))
                entriesAvgDist.add(BarEntry(avgDistForSun, 6))

                val barDataSetAvgDist = BarDataSet(entriesAvgDist, "Cells")
                val dataAvgDist = BarData(labels, barDataSetAvgDist)
                barChartAvgDist.data = dataAvgDist // set the data and list of labels into chart

                barChartAvgDist.setDescription("")  // set the description

                //barDataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                barDataSetAvgDist.color = resources.getColor(R.color.colorAccent)
                barChartAvgDist.axisRight.setDrawLabels(false)
                barChartAvgDist.axisLeft.setDrawGridLines(false);
                barChartAvgDist.axisLeft.setDrawAxisLine(false);
                barChartAvgDist.axisRight.setDrawGridLines(false);
                barChartAvgDist.axisRight.setDrawAxisLine(false)
                barChartAvgDist.axisLeft.setDrawLabels(false);
                barChartAvgDist.xAxis.setDrawGridLines(false);
                barChartAvgDist.xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE)
                barChartAvgDist.xAxis.setDrawAxisLine(false);

                barChartAvgDist.legend.isEnabled = false;
                barChartAvgCal.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;

                barChartAvgDist.legend.isEnabled = false;
                barChartAvgCal.legend.isEnabled = false;
                barChartAvgSteps.legend.isEnabled = false;

                barChartAvgDist.animateY(3000)

                dataAvgDist.setValueTextSize(15f)
                dataAvgDist.setValueTextColor(Color.parseColor("#FFAAAAAA"))

                barChartAvgDist.xAxis.textSize = 15f
                barChartAvgDist.xAxis.textColor = Color.parseColor("#FFFFFFFF")
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}
