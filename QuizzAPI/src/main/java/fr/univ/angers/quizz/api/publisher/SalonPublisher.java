

package fr.univ.angers.quizz.api.publisher;

import fr.univ.angers.quizz.api.model.Salon;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.observables.ConnectableObservable;
import org.springframework.stereotype.Component;

@Component
public class SalonPublisher {

    private final Flowable<Salon> publisher;

    private ObservableEmitter<Salon> emitter;

    public SalonPublisher() {
        Observable<Salon> commentUpdateObservable = Observable.create(emitter -> {
            this.emitter = emitter;
        });

        ConnectableObservable<Salon> connectableObservable = commentUpdateObservable.share().publish();
        connectableObservable.connect();

        publisher = connectableObservable.toFlowable(BackpressureStrategy.BUFFER);
    }

    public void publish(final Salon salon) {
        Salon newSalon = new Salon();
        newSalon.setCodeAcces(salon.getCodeAcces());
        newSalon.setEnseignant(salon.getEnseignant());
        newSalon.setEtudiants(salon.getEtudiants());
        newSalon.setQuestionCourante(salon.getQuestionCourante());
        newSalon.setQuestionsEnAttente(salon.getQuestionEnAttente());
        newSalon.setQuestionsPosees(salon.getQuestionPosees());

        // データ変更を通知
        emitter.onNext(newSalon);
    }

    public Flowable<Salon> getPublisher() {
        // データ変更を配信
        return publisher;
    }
}