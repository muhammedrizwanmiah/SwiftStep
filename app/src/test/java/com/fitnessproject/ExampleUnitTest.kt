
val allTimeDist = snapshot.child("user_data/total_distance_by_day/Mon").value.toString().toDouble() +
        snapshot.child("user_data/total_distance_by_day/Tue").value.toString().toDouble() +
        snapshot.child("user_data/total_distance_by_day/Wed").value.toString().toDouble() +
        snapshot.child("user_data/total_distance_by_day/Thu").value.toString().toDouble() +
        snapshot.child("user_data/total_distance_by_day/Fri").value.toString().toDouble() +
        snapshot.child("user_data/total_distance_by_day/Sat").value.toString().toDouble() +
        snapshot.child("user_data/total_distance_by_day/Sun").value.toString().toDouble()

val percentage5Distance = (allTimeDist / 5.00 * 100).roundToInt()
days3ProgressText.text = "You are $percentage5Distance% of the way there!"
days3Progress.progress = allTimeDist.toString().toInt()

if(percentage5Distance >= 100){
    days3.setImageResource(R.drawable.days3)
    days3Layout.setBackgroundResource(R.drawable.card_achievement_checked)
    days3InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    days3ProgressLayout.visibility = (View.INVISIBLE)
    days3ProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    days3.setImageResource(R.drawable.days3u)
    days3Layout.setBackgroundResource(R.drawable.card_body)
    days3InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    days3ProgressLayout.visibility = (View.VISIBLE)
    days3ProgressCheckLayout.visibility = (View.INVISIBLE)
}

/////////

val percentage10Distance = (allTimeDist / 10.00 * 100).roundToInt()
days7ProgressText.text = "You are $percentage10Distance% of the way there!"
days7Progress.progress = allTimeDist.toString().toInt()

if(percentage10Distance >= 100){
    days7.setImageResource(R.drawable.days7)
    days7Layout.setBackgroundResource(R.drawable.card_achievement_checked)
    days7InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    days7ProgressLayout.visibility = (View.INVISIBLE)
    days7ProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    days7.setImageResource(R.drawable.days7u)
    days7Layout.setBackgroundResource(R.drawable.card_body)
    days7InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    days7ProgressLayout.visibility = (View.VISIBLE)
    days7ProgressCheckLayout.visibility = (View.INVISIBLE)
}

///////////

val percentage20Distance = (allTimeDist / 20.00 * 100).roundToInt()
days14ProgressText.text = "You are $percentage20Distance% of the way there!"
days14Progress.progress = allTimeDist.toString().toInt()

if(percentage20Distance >= 100){
    days14.setImageResource(R.drawable.days14)
    days14Layout.setBackgroundResource(R.drawable.card_achievement_checked)
    days14InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    days14ProgressLayout.visibility = (View.INVISIBLE)
    days14ProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    days14.setImageResource(R.drawable.days14u)
    days14Layout.setBackgroundResource(R.drawable.card_body)
    days14InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    days14ProgressLayout.visibility = (View.VISIBLE)
    days14ProgressCheckLayout.visibility = (View.INVISIBLE)
}

///////////

val percentage30Distance = (allTimeDist / 30.00 * 100).roundToInt()
days30ProgressText.text = "You are $percentage30Distance% of the way there!"
days30Progress.progress = allTimeDist.toString().toInt()

if(percentage30Distance >= 100){
    days30.setImageResource(R.drawable.days30)
    days30Layout.setBackgroundResource(R.drawable.card_achievement_checked)
    days30InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    days30ProgressLayout.visibility = (View.INVISIBLE)
    days30ProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    days30.setImageResource(R.drawable.days30u)
    days30Layout.setBackgroundResource(R.drawable.card_body)
    days30InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    days30ProgressLayout.visibility = (View.VISIBLE)
    days30ProgressCheckLayout.visibility = (View.INVISIBLE)
}
/////////////

val percentage42Distance = (allTimeDist / 42.00 * 100).roundToInt()
days60ProgressText.text = "You are $percentage42Distance% of the way there!"
days60Progress.progress = allTimeDist.toString().toInt()

if(percentage42Distance >= 100){
    days60.setImageResource(R.drawable.days60)
    days60Layout.setBackgroundResource(R.drawable.card_achievement_checked)
    days60InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    days60ProgressLayout.visibility = (View.INVISIBLE)
    days60ProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    days60.setImageResource(R.drawable.days60u)
    days60Layout.setBackgroundResource(R.drawable.card_body)
    days60InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    days60ProgressLayout.visibility = (View.VISIBLE)
    days60ProgressCheckLayout.visibility = (View.INVISIBLE)
}

////////////

val percentage60Distance = (allTimeDist / 60.00 * 100).roundToInt()
days100ProgressText.text = "You are $percentage60Distance% of the way there!"
days100Progress.progress = allTimeDist.toString().toInt()

if(percentage60Distance >= 100){
    days100.setImageResource(R.drawable.days100)
    days100Layout.setBackgroundResource(R.drawable.card_achievement_checked)
    days100InfoBG.setBackgroundColor(resources.getColor(R.color.colorAccent))
    days100ProgressLayout.visibility = (View.INVISIBLE)
    days100ProgressCheckLayout.visibility = (View.VISIBLE)
} else {
    days100.setImageResource(R.drawable.days100u)
    days100Layout.setBackgroundResource(R.drawable.card_body)
    days100InfoBG.setBackgroundColor(resources.getColor(R.color.cardHeadingColor))
    days100ProgressLayout.visibility = (View.VISIBLE)
    days100ProgressCheckLayout.visibility = (View.INVISIBLE)
}
