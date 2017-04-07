package tlor.iotweek.activity;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import tlor.iotweek.R;
import tlor.iotweek.network.request.CardCheckRequest;

public class CardCheckActivity extends AppCompatActivity implements CardCheckRequest.CardCheckRequestInterface {

    private final String[][] techList = new String[][] {
            new String[] {
                    NfcA.class.getName(),
                    NfcB.class.getName(),
                    NfcF.class.getName(),
                    NfcV.class.getName(),
                    IsoDep.class.getName(),
                    MifareClassic.class.getName(),
                    MifareUltralight.class.getName(), Ndef.class.getName()
            }
    };

    private Toolbar toolbar;
    private ProgressBar progress;
    private TextView mainTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(getString(R.string.nfc_activity_title));
        setContentView(R.layout.activity_check_card);
        progress = (ProgressBar) findViewById(R.id.progress);
        mainTextView = (TextView) findViewById(R.id.mainTextView);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        enabledBackgroundDispatch();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableBackgroundDispatch();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (intent.getAction().equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            Toast.makeText(this, getString(R.string.message_checking_card), Toast.LENGTH_SHORT).show();
            checkCard(ByteArrayToHexString(intent.getByteArrayExtra(NfcAdapter.EXTRA_ID)));
        }
    }

    private void checkCard(String cardId) {
        progress.setVisibility(View.VISIBLE);
        CardCheckRequest request = new CardCheckRequest(this, this, cardId);
        request.execute();
    }

    private void enabledBackgroundDispatch() {
        // creating pending intent
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        // creating intent receiver for NFC events
        IntentFilter filter = new IntentFilter();
        filter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
        filter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
        // enabling foreground dispatch for getting intent from NFC event
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent, new IntentFilter[]{filter}, this.techList);
    }

    private void disableBackgroundDispatch() {
        // disabling foreground dispatch
        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcAdapter.disableForegroundDispatch(this);
    }

    private String ByteArrayToHexString(byte [] inarray) {
        int i, j, in;
        String [] hex = {"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
        String out= "";

        for(j = 0 ; j < inarray.length ; ++j)
        {
            in = (int) inarray[j] & 0xff;
            i = (in >> 4) & 0x0f;
            out += hex[i];
            i = in & 0x0f;
            out += hex[i];
            if (j!= inarray.length-1)
                out += ":";
        }
        return out;
    }

    private void showDialog(String cardId, String cardName) {
        String title = getString(cardName == null ? R.string.nfc_dialog_title_missing : R.string.nfc_dialog_title_found);
        String message = cardName == null ? getString(R.string.nfc_dialog_message_missing, cardId) : getString(R.string.nfc_dialog_message_found, cardId, cardName);
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(cardName == null ? android.R.drawable.ic_dialog_alert : android.R.drawable.ic_dialog_info)
                .show();
    }

    @Override
    public void onCardChecked(String cardId, String cardName) {
        Log.d(getClass().getName(), "Card name: " + cardName);
        showDialog(cardId, cardName);
        progress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onCardCheckFailure() {
        Log.d(getClass().getName(), "Request has failed");
        Toast.makeText(this, R.string.error_checking_card, Toast.LENGTH_SHORT).show();
        progress.setVisibility(View.INVISIBLE);
    }
}
