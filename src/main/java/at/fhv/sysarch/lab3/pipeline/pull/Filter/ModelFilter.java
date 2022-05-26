package at.fhv.sysarch.lab3.pipeline.pull.Filter;

import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.pipeline.Push.IFilter;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.pull.IFilterPull;
import at.fhv.sysarch.lab3.pipeline.pull.PullPipe;
import com.hackoeur.jglm.Mat4;

public class ModelFilter implements IFilterPull<Face, Face> {

    private PullPipe<Face> pipePrecessor;

    public ModelFilter(Mat4 mat) {
        Mat = mat;
    }

    public boolean hasFaces() { return pipePrecessor.hasFaces(); }
    private Mat4 rot;



    private final Mat4 Mat;

    public void setRot(Mat4 rot) {
        this.rot = rot;
    }
    public Face read() {
        Face face = pipePrecessor.read();
        Mat4 updateMat = Mat; //.multiply(this.rot);
        Face newFace = new Face(updateMat.multiply(face.getV1()), updateMat.multiply(face.getV2()),
                                updateMat.multiply(face.getV3()), updateMat.multiply(face.getN1()),
                                updateMat.multiply(face.getN2()), updateMat.multiply(face.getN3()));
        return newFace;
    }


    @Override
    public void getFromPrecessor(PullPipe<Face> pipe) {
        this.pipePrecessor = pipe;
    }


}
