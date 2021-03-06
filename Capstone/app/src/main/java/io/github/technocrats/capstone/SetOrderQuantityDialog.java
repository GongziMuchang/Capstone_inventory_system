package io.github.technocrats.capstone;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class SetOrderQuantityDialog extends AppCompatDialogFragment implements View.OnClickListener, View.OnFocusChangeListener {

    private Spinner QuantitySpinner;
    private EditText editTextOrderQuantity;
    private Button btnAddQuantity;
    private Button btnSubtractQuantity;
    private Button btnDone;
    NumberFormat formatter;
    private SetOrderQuantityDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater layoutInflater = getActivity().getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.dialog_set_order_quantity, null);

        btnAddQuantity = view.findViewById(R.id.btnAddQuantity);
        btnSubtractQuantity = view.findViewById(R.id.btnSubtractQuantity);
        btnDone = view.findViewById(R.id.btnDone);

        btnAddQuantity.setEnabled(false);
        btnSubtractQuantity.setEnabled(false);
        btnDone.setEnabled(false);

        btnAddQuantity.setOnClickListener(this);
        btnSubtractQuantity.setOnClickListener(this);
        btnDone.setOnClickListener(this);

        QuantitySpinner = view.findViewById(R.id.QuantitiesSpinner);
        editTextOrderQuantity = view.findViewById(R.id.etProductQuantity);
        formatter = new DecimalFormat("#,###.##");

        builder.setView(view).setTitle(CreateOrderActivity.product + "    $" + CreateOrderActivity.price);

        editTextOrderQuantity.setText("$" + CreateOrderActivity.price + " × " + CreateOrderActivity.quantities[CreateOrderActivity.position] + " = $" + formatter.format(CreateOrderActivity.price * CreateOrderActivity.quantities[CreateOrderActivity.position]));

        btnAddQuantity.setEnabled(true);
        btnSubtractQuantity.setEnabled(true);
        btnDone.setEnabled(true);

        return builder.create();
    }

    @Override
    public void onAttach(Context _context) {
        super.onAttach(_context);

        try {
            listener = (SetOrderQuantityDialogListener) _context;
        } catch (ClassCastException e) {
            throw new ClassCastException(_context.toString() + "Dialog Listener not implemented");
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btnDone) {
            dismiss();
        }
        else if(view.getId() == R.id.btnAddQuantity) {
            CreateOrderActivity.quantities[CreateOrderActivity.position] += Integer.parseInt(QuantitySpinner.getSelectedItem().toString());
            editTextOrderQuantity.setText("$" + CreateOrderActivity.price + " × " + CreateOrderActivity.quantities[CreateOrderActivity.position] + " = $" + formatter.format(CreateOrderActivity.price * CreateOrderActivity.quantities[CreateOrderActivity.position]));
            CreateOrderActivity.total += CreateOrderActivity.price * Integer.parseInt(QuantitySpinner.getSelectedItem().toString());
            listener.updateTotalTextView();
        }
        else if(view.getId() == R.id.btnSubtractQuantity) {
            if(CreateOrderActivity.quantities[CreateOrderActivity.position] >= Integer.parseInt(QuantitySpinner.getSelectedItem().toString())) {
                CreateOrderActivity.quantities[CreateOrderActivity.position] -= Integer.parseInt(QuantitySpinner.getSelectedItem().toString());
                editTextOrderQuantity.setText("$" + CreateOrderActivity.price + " × " + CreateOrderActivity.quantities[CreateOrderActivity.position] + " = $" + formatter.format(CreateOrderActivity.price * CreateOrderActivity.quantities[CreateOrderActivity.position]));
                CreateOrderActivity.total -= CreateOrderActivity.price * Integer.parseInt(QuantitySpinner.getSelectedItem().toString());
                listener.updateTotalTextView();
            }
        }
    }

    @Override
    public void onFocusChange(View view, boolean b) {
    }

    public interface SetOrderQuantityDialogListener
    {
        void updateTotalTextView();
    }
}
