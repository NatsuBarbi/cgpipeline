package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.obj.Face;

public class PerspDivision implements IFilter<Face, Face> {

    private Pipe<Face> pipeSuccessor;

    @Override
    public void setPipeSuccessor(Pipe<Face> pipe) {
        this.pipeSuccessor = pipe;
    }

    @Override
    public void write(Face input) {
        Face face = new Face(input.getV1().multiply(1/input.getV1().getW()), input.getV2().multiply(1/input.getV2().getW()), input.getV3().multiply(1/input.getV3().getW()), input);
        this.pipeSuccessor.write(face);
    }
}
