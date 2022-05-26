package at.fhv.sysarch.lab3.pipeline.Push;

public interface ISink<I> {

    void write(I input);

}
