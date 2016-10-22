package fiuba.tallerdeproyectos2.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import fiuba.tallerdeproyectos2.R;

public class ExamInfoDialogFragment extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pautas");
        builder.setMessage(R.string.exam_info)
                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        listener.onDialogPositiveClick(ExamInfoDialogFragment.this);
                    }
                });
        return builder.create();
    }

    public interface ExamInfoDialogListener {
        void onDialogPositiveClick(DialogFragment dialog);
    }

    ExamInfoDialogFragment.ExamInfoDialogListener listener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (ExamInfoDialogFragment.ExamInfoDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement ExamInfoDialogListener");
        }
    }
}
