package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Filter.*;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.data.ModelSource;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;


public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: push from the source (model)

        // TODO: the connection of filters and pipes requires a lot of boilerplate code. Think about options how this can be minimized

        IFilter<Model, Face> source = new ModelSource();

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        ModelFilter modelFilter = new ModelFilter(pd.getModelTranslation().multiply(pd.getViewTransform()));
        PushPipe<Face> sourceToFilter = new PushPipe<Face>();
        sourceToFilter.setSuccessor(modelFilter);
        source.setPipeSuccessor(sourceToFilter);

        // TODO 2. perform backface culling in VIEW SPACE
        BackfaceFilter backfaceFilter = new BackfaceFilter();
        PushPipe<Face> mvToBf = new PushPipe<Face>();
        mvToBf.setSuccessor(backfaceFilter);
        modelFilter.setPipeSuccessor(mvToBf);

        // TODO 3. perform depth sorting in VIEW SPACE
        // not able in PushPipeline

        // TODO 4. add coloring (space unimportant)
        ColorFilter colorFilter = new ColorFilter(pd);
        PushPipe<Face> bfToCf = new PushPipe<Face>();
        bfToCf.setSuccessor(colorFilter);
        backfaceFilter.setPipeSuccessor(bfToCf);


        // lighting can be switched on/off

        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            /* Punkte Produkt von 2 normalisierten Vektoren < 0 = Schwarz

             */


            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {

        }

        ProjTransformFilter pt = new ProjTransformFilter(pd.getProjTransform());
        PushPipe<Pair<Face, Color>> cfToPt = new PushPipe<Pair<Face, Color>>();
        cfToPt.setSuccessor(pt);
        colorFilter.setPipeSuccessor(cfToPt);


        // TODO 6. perform perspective division to screen coordinates
        PerspDivision pdiv = new PerspDivision();
        PushPipe<Pair<Face, Color>> ptToPdiv = new PushPipe<Pair<Face, Color>>();
        ptToPdiv.setSuccessor(pdiv);
        pt.setPipeSuccessor(ptToPdiv);


        // TODO Viewing Transformation - Extra Klasse
        ProjTransformFilter mv = new ProjTransformFilter(pd.getViewportTransform());
        PushPipe<Pair<Face, Color>> pdivToMv = new PushPipe<Pair<Face, Color>>();
        pdivToMv.setSuccessor(mv);
        pdiv.setPipeSuccessor(pdivToMv);

        // TODO 7. feed into the sink (renderer)
        ISink<Pair<Face, Color>> modelSink = new Renderer(pd.getGraphicsContext(), pd.getRenderingMode());
        PushPipe<Pair<Face, Color>> toSink = new PushPipe<Pair<Face, Color>>();
        toSink.setSuccessor(modelSink);
        mv.setPipeSuccessor(toSink);

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
                pos += fraction;
                double radians = pos % (2*Math.PI);
                // TODO create new model rotation matrix using pd.modelRotAxis
                Mat4 rot = Matrices.rotate(
                        (float) radians,
                        pd.getModelRotAxis()
                );
                // TODO compute updated model-view tranformation

                modelFilter.setRot(rot);

                // TODO update model-view modelFilter
                PushPipe<Face> updatePipe = new PushPipe<>();
                updatePipe.setSuccessor(modelFilter);
                model.getFaces().forEach(updatePipe::write);
                // TODO trigger rendering of the pipeline

                // line
                // pd.getGraphicsContext().setStroke(Color.PINK);
                // pd.getGraphicsContext().strokeLine(0 + pos, 0 + pos, 100 + pos, 100 + pos);
                // pos++;

                source.write(model);

         }
        };
    }
}
