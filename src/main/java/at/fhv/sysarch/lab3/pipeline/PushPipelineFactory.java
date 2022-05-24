package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {
        // TODO: push from the source (model)

        // TODO: the connection of filters and pipes requires a lot of boilerplate code. Think about options how this can be minimized
        IFilter<Model, Face> source = new ModelSource();
        IFilter<Face, Face> filter = new ModelFilter(pd.getModelTranslation().multiply(pd.getViewTransform()));
        Pipe<Face> sourceToFilter = new Pipe<Face>();
        sourceToFilter.setSuccessor(filter);
        source.setPipeSuccessor(sourceToFilter);
        BackfaceFilter bc = new BackfaceFilter();
        Pipe<Face> filterToBc = new Pipe<Face>();
        filterToBc.setSuccessor(bc);
        filter.setPipeSuccessor(filterToBc);
        ColorFilter cf = new ColorFilter(pd);
        Pipe<Face> bcToCf = new Pipe<Face>();
        bcToCf.setSuccessor(cf);
        bc.setPipeSuccessor(bcToCf);
        ProjTransformFilter pt = new ProjTransformFilter(pd.getProjTransform());
        Pipe <Pair<Face, Color>>  cfToPt = new Pipe<Pair<Face,Color>>();
        cfToPt.setSuccessor(pt);
        cf.setPipeSuccessor(cfToPt);
        PerspDivision pdiv = new PerspDivision();
        Pipe<Pair<Face, Color>>ptToPdiv = new Pipe<Pair<Face, Color>>();
        ptToPdiv.setSuccessor(pdiv);
        pt.setPipeSuccessor(ptToPdiv);
        ProjTransformFilter mv = new ProjTransformFilter(pd.getViewportTransform());
        Pipe<Pair<Face, Color>> pdivToMv = new Pipe<Pair<Face, Color>>();
        pdivToMv.setSuccessor(mv);
        pdiv.setPipeSuccessor(pdivToMv);
        ISink<Pair<Face,Color>> sink = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());
        Pipe<Pair<Face, Color>> toSink = new Pipe<Pair<Face, Color>>();
        toSink.setSuccessor(sink);
        mv.setPipeSuccessor(toSink);



        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates

        // TODO 2. perform backface culling in VIEW SPACE



        // TODO 3. perform depth sorting in VIEW SPACE
        // not able in PushPipeline

        // TODO 4. add coloring (space unimportant)





        // lighting can be switched on/off
        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {

        }




        // TODO 6. perform perspective division to screen coordinates



        // TODO 7. feed into the sink (renderer)


        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            private int pos = 0;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians

                // TODO create new model rotation matrix using pd.modelRotAxis

                // TODO compute updated model-view tranformation

                // TODO update model-view filter

                // TODO trigger rendering of the pipeline

                // line
                pd.getGraphicsContext().setStroke(Color.PINK);
                pd.getGraphicsContext().strokeLine(0+pos,0+pos,100+pos,100+pos);
                pos++;

                source.write(model);
            }
        };
    }
}
