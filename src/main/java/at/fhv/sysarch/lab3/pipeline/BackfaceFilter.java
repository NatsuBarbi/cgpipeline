package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

public class BackfaceFilter implements IFilter<Face, Face> {

    private Pipe<Face> pipeSuccessor;

    public BackfaceFilter() {

    }


    public void setPipeSuccessor(Pipe<Face> pipe) {
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
