package shivamdh.com.fitness60;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Locale;
import java.util.Timer;

import static shivamdh.com.fitness60.Options.distanceC;
import static shivamdh.com.fitness60.Options.timerC;
import static shivamdh.com.fitness60.Options.weightC;


public class DistanceActivity extends Activities {
    private TextView repHeader;

    DistanceActivity(ViewGroup mainContainer, View theLayout, LayoutInflater theInflate, Context aContext, int activityNum, Activity currActivity) {
        super(); //just call on a constructor from above
        defaultSetup(mainContainer, theLayout, theInflate, aContext, activityNum, currActivity);

        //replace reps from default activity with distance
        repHeader = (TextView) n.findViewById(R.id.reps_text);
        if (this.getClass() == DistanceActivity.class) { //check in fact that this is a distance activty
            if (distanceC) {
                repHeader.setText(R.string.kmC);
            } else {
                repHeader.setText(R.string.milesC);
            }
        } else { //meaning this is a bodyweight exercise activity
            if (weightC) {
                repHeader.setText(R.string.kgchoice);
            } else {
                repHeader.setText(R.string.lbsC);
            }
        }

        TableRow header = (TableRow) n.findViewById(R.id.new_workout_header);
        header.removeViewAt(1); //remove weight column

        //set the columns to correct widths
        table.setColumnStretchable(0, true);
        table.setColumnStretchable(1, true);
        table.setColumnStretchable(2, true);

        start = System.currentTimeMillis();
        setTimer = new Timer();
        setTimer.schedule(new runTimer(ourActivity), 1000, 1000);

        createDistanceRow();
    }

    void createDistanceRow() {
        newRow = new TableRow(theContext);
        sets = new EditText(theContext);
        reps = new EditText(theContext);
        setNumber++;
        sets.setText(String.format(Locale.getDefault(), "%d", setNumber));
        if (setNumber == 1) {
            createFirstDistanceRowOnly(); //special syntax for first row only
        }

        sets.setGravity(Gravity.CENTER);
        reps.setGravity(Gravity.CENTER);
        newRow.addView(sets);
        newRow.addView(reps);

        if (timerC) {
            time = new TextView(theContext);
            time.setText(R.string.defaultTime);
            start = System.currentTimeMillis();
            time.setGravity(Gravity.CENTER);
            newRow.addView(time);
        }
        table.addView(newRow,setNumber);

    }

    private void createFirstDistanceRowOnly() {
        if (this.getClass() == BodyweightExercise.class) {
            reps.setHint(R.string.addtl_weight);
        } else {
            reps.setHint(R.string.distance_text);
        }
        reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               try {
                   firstReps = Integer.parseInt(s.toString());
               } catch (Exception ignored) {

               }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
