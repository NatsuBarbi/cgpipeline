package at.fhv.sysarch.lab3.pipeline.Push;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class PushSource implements IFilterPush<Model, Face> {

    private PushPipe<Face> pipeSuccessor;

    public void setPipeSuccessor(PushPipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    public void write(Model model) {
        for (Face face : model.getFaces()) {
            pipeSuccessor.write(face);
        }
    }
}
