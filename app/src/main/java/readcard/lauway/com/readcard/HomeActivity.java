package readcard.lauway.com.readcard;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import me.ele.omniknight.common.tools.ToastUtil;
import me.ele.omniknight.guardian.activity.GuardianActivity;
import me.ele.omniknight.guardian.event.Observes;
import me.ele.omniknight.guardian.inject.annotation.InjectView;
import readcard.lauway.com.readcard.net.HttpRequestParams;
import readcard.lauway.com.readcard.net.ResponseParseHandler;
import readcard.lauway.com.readcard.nfc.NFCActivity;
import readcard.lauway.com.readcard.request.CreateCardRequest;


public class HomeActivity extends GuardianActivity {

    @InjectView(R.id.home_card)
    private TextView homeCard;
    @InjectView(R.id.home_submit)
    private TextView submit;
    private String cardNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setListener();
    }

    private void setListener() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCardRequest();
            }
        });
    }

    private void createCardRequest() {
        CreateCardRequest request = new CreateCardRequest(getApplicationContext());
        HttpRequestParams params = new HttpRequestParams();
        params.put("cardNo", cardNumber);
        params.put("hospitalPk", 4);
        params.put("statusCode", "NOT_USING");
        request.setParams(params);
        request.request(new ResponseParseHandler<Boolean>() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onFailure(String error, String message) {
                ToastUtil.showToastLong(getApplicationContext(), message==null?"提交失败":message);
            }

            @Override
            public void onSuccess(Boolean result) {
                if (result)
                    homeCard.setText("提交成功");
            }
        });
    }

    private void handleGetNFCEvent(@Observes NFCActivity.GetNFCCardEvent event) {
        cardNumber = event.getCard();
        homeCard.setText("卡号:" + cardNumber);
    }

}
