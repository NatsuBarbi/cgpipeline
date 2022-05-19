package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;

public class ModelSource implements IFilter<Model, Face> {

    private Pipe<Face> pipeSuccessor;

    public void setPipeSuccessor(Pipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    public void write(Model model) {
        for(Face face : model.getFaces()){
            // TODO: write face to next filter
            pipeSuccessor.write(face);
        }
    }
}
