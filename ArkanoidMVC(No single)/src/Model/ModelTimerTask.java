package Model;

import java.util.TimerTask;

public class ModelTimerTask extends TimerTask {
    private Model model;

    public ModelTimerTask(Model _model){
        model = _model;
    }

    @Override
    public void run() {
        if(model.getBall().getSpeed() == 0){
            model.pause();
        }
        else{
            model.moveBall();
            if(model.getDesk().getDeskDirection().equals("left")){
                model.moveDeskLeft();
            }

            if(model.getDesk().getDeskDirection().equals("right")){
                model.moveDeskRight();
            }
        }
    }
}
