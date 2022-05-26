package at.fhv.sysarch.lab3.pipeline.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;

public class BackfaceFilter implements IFilter<Face, Face> {

    private PushPipe<Face> pipeSuccessor;

    public BackfaceFilter() {

    }


    public void setPipeSuccessor(PushPipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }


    @Override
    public void write(Face face) {
       if(face.getV1().dot(face.getN1()) > 0) {
           return;
       } else {
           this.pipeSuccessor.write(face);
       }
    }

    // Todo Edits Barbi





}
