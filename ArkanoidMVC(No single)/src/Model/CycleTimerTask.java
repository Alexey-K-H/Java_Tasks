package Model;

import java.util.TimerTask;

public class CycleTimerTask extends TimerTask {
    private Model model;

    public CycleTimerTask(Model _model){
        model = _model;
    }

    @Override
    public void run() {
        model.increaseTime();
    }
}
