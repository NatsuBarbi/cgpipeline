package at.fhv.sysarch.lab3.pipeline.data;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;

public class ModelSource implements IFilter<Model, Face> {

    private PushPipe<Face> pipeSuccessor;

    public void setPipeSuccessor(PushPipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    public void write(Model model) {
        for(Face face : model.getFaces()){
            // TODO: write face to next filter
            pipeSuccessor.write(face);
        }
    }
}
