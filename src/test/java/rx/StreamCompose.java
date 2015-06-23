package rx;


import org.junit.Test;
import rx.functions.*;
import rx.schedulers.Schedulers;
import rx.util.async.StoppableObservable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import static rx.util.async.Async.runAsync;


public class StreamCompose {

    public static class Instrument {
        public String id;
        public IdSet altId;

        public Instrument(String id) {
            this.id = id;
        }
    }

    public static class IdSet {
        private String ownerId;
        public final List<String> ids;

        public IdSet(String ownerId, String ... id) {
            this.ownerId = ownerId;
            this.ids = Arrays.asList(id);
        }
    }

    abstract class AsyncAction<T> implements Action2<Observer<? super T>, Subscription> {
        @Override
        public void call(Observer<? super T> obs, Subscription subscription) {
            Iterator<T> dat = getIterator();
            while (dat.hasNext()) {
                obs.onNext(dat.next());
                System.out.println("stream-> " + Thread.currentThread().getName());
                try {
                    Thread.currentThread().sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

            }
            obs.onCompleted();
        }

        protected abstract Iterator<T> getIterator();
    }

    class IdFetchAction implements Action2<Observer<? super IdSet>, Subscription> {

        @Override
        public void call(Observer<? super IdSet> obs, Subscription subscription) {
            List<IdSet> data = new ArrayList<>();
            data.add(new IdSet("1", "lcp1", "org1"));
            data.add(new IdSet("3", "lcp3", "org3"));
            data.add(new IdSet("4", "lcp4", "org4"));
            for(IdSet s : data) {
                obs.onNext(s);
                System.out.println("stream-> " + Thread.currentThread().getName());
                try {
                    Thread.currentThread().sleep(7000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }

            }
            obs.onCompleted();
        }
    }

    public StoppableObservable<Instrument> getInstrumentStreamer() {
        return runAsync(Schedulers.io(), new AsyncAction<Instrument>() {
            @Override
            protected Iterator<Instrument> getIterator() {
                return Arrays.asList(new Instrument("1"),
                        new Instrument("2"),
                        new Instrument("3"),
                        new Instrument("4")).iterator();
            }
        });
    }

    @Test
    public void testJoin() {
      //  JoinObservable.
        Observable.from(Arrays.asList("1", "2", "3", "4", "5"))
                .join(Observable.from(Arrays.asList("A", "B", "C")), new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                System.out.println("Left " + s);
                return Observable.never();
            }
        }, new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                System.out.println("Right " + s);
                return Observable.just(s);
            }
        }, new Func2<String, String, String>() {
            @Override
            public String call(String s, String s2) {
                return s + s2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                System.out.println(s);
            }
        });
    }


    @Test
    public void testThreading() throws InterruptedException {

        StoppableObservable<Instrument> so  = getInstrumentStreamer();
        final CountDownLatch l1 = new CountDownLatch(1);


        so.subscribe(new Action1<Instrument>() {
            @Override
            public void call(Instrument s) {
                System.out.println(s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                System.err.println(throwable);
            }
        }, new Action0() {
            @Override
            public void call() {
                System.out.println("Complete");
                l1.countDown();
            }
        });

        l1.await();
    }
}
