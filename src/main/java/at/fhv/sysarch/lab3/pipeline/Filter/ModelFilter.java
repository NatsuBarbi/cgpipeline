package at.fhv.sysarch.lab3.pipeline.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import com.hackoeur.jglm.Mat4;

public class ModelFilter implements IFilter<Face, Face> {

    private PushPipe<Face> pipeSuccessor;
    //private PullPipe<Face> pipeSuccessor;

    public ModelFilter(Mat4 mat) {
        Mat = mat;
    }

    private Mat4 rot;


    public void setPipeSuccessor(PushPipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    private final Mat4 Mat;

    public void setRot(Mat4 rot) {
        this.rot = rot;
    }
    public void write(Face face) {
        Mat4 updateMat = Mat.multiply(this.rot);
        Face newFace = new Face(updateMat.multiply(face.getV1()), updateMat.multiply(face.getV2()),
                                updateMat.multiply(face.getV3()), updateMat.multiply(face.getN1()),
                                updateMat.multiply(face.getN2()), updateMat.multiply(face.getN3()));
        this.pipeSuccessor.write(newFace);
    }



}
