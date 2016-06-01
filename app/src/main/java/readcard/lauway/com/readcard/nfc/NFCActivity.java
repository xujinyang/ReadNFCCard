package readcard.lauway.com.readcard.nfc;

import android.annotation.SuppressLint;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.Toast;

import me.ele.omniknight.aura.Inject;
import me.ele.omniknight.common.tools.AppLogger;
import me.ele.omniknight.common.tools.ToastUtil;
import me.ele.omniknight.guardian.activity.GuardianActivity;
import me.ele.omniknight.guardian.event.ApplicationEvent;
import me.ele.omniknight.guardian.event.EventManager;
import readcard.lauway.com.readcard.R;
import readcard.lauway.com.readcard.utils.StringUtils;

/**
 * NFC响应页面
 *
 * @author Jinyang
 */
@SuppressLint("NewApi")
public class NFCActivity extends GuardianActivity {

    public static final String TAG = "NFCActivity";
    private NfcAdapter nfcAdapter;

    private String mClientCardNfcId;
    @Inject
    private EventManager notificationCenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nfc);
        initNfcParse();
    }

    private void initNfcParse() {
        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter == null) {
            AppLogger.e("---->Nfc error ！！！");
            Toast.makeText(getApplicationContext(), "不支持NFC功能！", Toast.LENGTH_SHORT).show();
        } else if (!nfcAdapter.isEnabled()) {
            AppLogger.e("---->Nfc close ！！！");
            Toast.makeText(getApplicationContext(), "请打开NFC功能！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        AppLogger.e("---->onDestroy");
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            final Parcelable p = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Tag tagFromIntent = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            MifareClassic mfc = MifareClassic.get(tagFromIntent);

            try {
                mfc.connect();
                byte[] byteId = mfc.getTag().getId();
                String nfcId = StringUtils.hexToHexString(byteId);
                if (!nfcId.isEmpty()) {
                    mClientCardNfcId = nfcId;
                    Log.i(TAG, "卡的内容" + mClientCardNfcId);
                    notificationCenter.fire(new GetNFCCardEvent(mClientCardNfcId));
                    finish();
                } else {
                    ToastUtil.showToastLong(getApplicationContext(), "识别失败！请重新刷卡！");
                }
            } catch (Exception e) {
                AppLogger.e("error = " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public class GetNFCCardEvent extends ApplicationEvent {
        private String card;

        public String getCard() {
            return card;
        }

        public void setCard(String card) {
            this.card = card;
        }

        public GetNFCCardEvent(String card) {
            this.card = card;
        }

        public Class<?>[] handler() {
            return null;
        }
    }

}
