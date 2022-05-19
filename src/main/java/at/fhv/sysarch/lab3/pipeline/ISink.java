package at.fhv.sysarch.lab3.pipeline;

public interface ISink<I> {

    void write(I input);

}
