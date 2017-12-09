package com.hengyi.japp.cargo.interfaces.websocket.internal;

import com.hengyi.japp.cargo.application.GpsDataService;
import com.hengyi.japp.cargo.domain.gps.CarGpsData;
import com.hengyi.japp.cargo.interfaces.websocket.WebSocketGpsBean;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 描述：
 *
 * @author jzb 2017-12-09
 */
@ApplicationScoped
public class WebSocketGpsBeanImpl extends AbstractWebSocketBean implements WebSocketGpsBean {
    @Inject
    private GpsDataService gpsDataService;

    @Override
    public void onOpen(Session session, EndpointConfig conf, String carNo) {
        setSession(session, SessionKey.CAR_NO, carNo);
        Disposable subscription = Observable.interval(0, 10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        l -> sendGpsData(carNo, session),
                        t -> onError(session, t)
                );
        setSession(session, SessionKey.SUBSCRIPTION, subscription);
    }

    private void sendGpsData(String carNo, Session session) {
        Optional<CarGpsData> optional = gpsDataService.getGpsData(carNo);
        optional.ifPresent(session.getAsyncRemote()::sendObject);
    }

    @Override
    public void onClose(Session session) {
        Disposable subscription = getSession(session, SessionKey.SUBSCRIPTION);
        if (subscription != null) {
            subscription.dispose();
        }
        super.onClose(session);
    }

    private static final class SessionKey {
        private static final String CAR_NO = "CAR_NO";
        private static final String SUBSCRIPTION = "SUBSCRIPTION";
    }

}
