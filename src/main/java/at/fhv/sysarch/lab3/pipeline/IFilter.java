package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;

// TODO: Think about how generics can be applied in this context
// TODO: The current solution is JUST an illustration and not sufficient for the example. It only shows how generics may be used.
// TODO: Can you use one interface for both implementations (push and pull)? Or do they require specific interfaces?
public interface IFilter<I, O> extends ISink<I> {

    void setPipeSuccessor(PushPipe<O> pipe);

    void write(I input);

}
