package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;
import com.hackoeur.jglm.Mat4;

public class ModelFilter implements IFilter<Face, Face> {

    private Pipe<Face> pipeSuccessor;

    public ModelFilter(Mat4 mat) {
        Mat = mat;
    }


    public void setPipeSuccessor(Pipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    private Mat4 Mat;

    public void write(Face face) {
        Face newFace = new Face(Mat.multiply(face.getV1()), Mat.multiply(face.getV2()), Mat.multiply(face.getV3()), Mat.multiply(face.getN1()), Mat.multiply(face.getN2()), Mat.multiply(face.getN3()));
        this.pipeSuccessor.write(newFace);
    }

}
