<androidx.constraintlayout.widget.ConstraintLayout
android:id="@+id/days100ProgressCheckLayout"
android:layout_width="wrap_content"
android:layout_height="wrap_content"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintTop_toBottomOf="@id/days100InfoBG"
>

<com.mikhaellopez.circularprogressbar.CircularProgressBar
android:id="@+id/days100CircularProgress"
android:layout_width="60dp"
android:layout_height="60dp"
app:cpb_background_progressbar_color="@color/colorAccent"
app:cpb_background_progressbar_width="3dp"
app:cpb_progress_direction="to_right"
app:cpb_progressbar_color="@color/colorAccent"
app:cpb_progressbar_width="5dp"
app:cpb_round_border="true"
app:layout_constraintStart_toStartOf="parent"
app:layout_constraintEnd_toEndOf="parent"
app:layout_constraintBottom_toBottomOf="parent"
app:layout_constraintTop_toTopOf="parent"
/>

<ImageView
android:id="@+id/days100Check"
android:layout_width="40dp"
android:layout_height="40dp"
android:src="@drawable/ic_check"
android:tint="@color/colorAccent"
app:layout_constraintBottom_toBottomOf="@+id/days100CircularProgress"
app:layout_constraintEnd_toEndOf="@+id/days100CircularProgress"
app:layout_constraintStart_toStartOf="@+id/days100CircularProgress"
app:layout_constraintTop_toTopOf="@+id/days100CircularProgress" />
</androidx.constraintlayout.widget.ConstraintLayout>