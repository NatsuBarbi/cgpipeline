package at.fhv.sysarch.lab3.pipeline;

import at.fhv.sysarch.lab3.animation.AnimationRenderer;
import at.fhv.sysarch.lab3.obj.Face;
import at.fhv.sysarch.lab3.obj.Model;
import at.fhv.sysarch.lab3.pipeline.Push.Filter.*;
import at.fhv.sysarch.lab3.pipeline.Push.IFilterPush;
import at.fhv.sysarch.lab3.pipeline.Push.IPushPipe;
import at.fhv.sysarch.lab3.pipeline.Push.PushPipe;
import at.fhv.sysarch.lab3.pipeline.Push.PushSource;
import at.fhv.sysarch.lab3.pipeline.data.Pair;
import at.fhv.sysarch.lab3.pipeline.data.PipelineData;
import com.hackoeur.jglm.Mat4;
import com.hackoeur.jglm.Matrices;
import javafx.animation.AnimationTimer;
import javafx.scene.paint.Color;

public class PushPipelineFactory {
    public static AnimationTimer createPipeline(PipelineData pd) {

        // TODO: push from the source (model)
        IFilterPush<Model, Face> source = new PushSource();

        // TODO 1. perform model-view transformation from model to VIEW SPACE coordinates
        ModelFilterPush modelFilter = new ModelFilterPush(pd.getModelTranslation().multiply(pd.getViewTransform()));
        PushPipe<Face> sourceToFilter = new PushPipe<Face>();
        sourceToFilter.setSuccessor(modelFilter);
        source.setPipeSuccessor(sourceToFilter);

        // TODO 2. perform backface culling in VIEW SPACE
        BackfaceFilterPush backfaceFilter = new BackfaceFilterPush();
        PushPipe<Face> mvToBf = new PushPipe<Face>();
        mvToBf.setSuccessor(backfaceFilter);
        modelFilter.setPipeSuccessor(mvToBf);

        // TODO 3. perform depth sorting in VIEW SPACE
        // not able in PushPipeline

        // TODO 4. add coloring (space unimportant)
        ColorFilterPush colorFilter = new ColorFilterPush(pd);
        PushPipe<Face> bfToCf = new PushPipe<Face>();
        bfToCf.setSuccessor(colorFilter);
        backfaceFilter.setPipeSuccessor(bfToCf);

        // lighting can be switched on/off
        ProjTransformFilterPush pt = new ProjTransformFilterPush(pd.getProjTransform());

        if (pd.isPerformLighting()) {
            // 4a. TODO perform lighting in VIEW SPACE
            LightFilterPush lf = new LightFilterPush(pd);
            PushPipe<Pair<Face, Color>> cfToLf = new PushPipe<Pair<Face, Color>>();
            cfToLf.setSuccessor(lf);
            colorFilter.setPipeSuccessor(cfToLf);

            PushPipe<Pair<Face, Color>> lfToPt = new PushPipe<>();
            lfToPt.setSuccessor(pt);
            lf.setPipeSuccessor(lfToPt);

            // 5. TODO perform projection transformation on VIEW SPACE coordinates
        } else {
            PushPipe<Pair<Face, Color>> cfToPt = new PushPipe<Pair<Face, Color>>();
            cfToPt.setSuccessor(pt);
            colorFilter.setPipeSuccessor(cfToPt);
        }

        // TODO 6. perform perspective division to screen coordinates
        PerspDivision pdiv = new PerspDivision();
        PushPipe<Pair<Face, Color>> ptToPdiv = new PushPipe<Pair<Face, Color>>();
        ptToPdiv.setSuccessor(pdiv);
        pt.setPipeSuccessor(ptToPdiv);

        // TODO Viewing Transformation
        ProjTransformFilterPush mv = new ProjTransformFilterPush(pd.getViewportTransform());
        PushPipe<Pair<Face, Color>> pdivToMv = new PushPipe<Pair<Face, Color>>();
        pdivToMv.setSuccessor(mv);
        pdiv.setPipeSuccessor(pdivToMv);

        // TODO 7. feed into the sink (renderer)
        IPushPipe<Pair<Face, Color>> modelSink = new PushRenderer(pd.getGraphicsContext(), pd.getRenderingMode());
        PushPipe<Pair<Face, Color>> toSink = new PushPipe<Pair<Face, Color>>();
        toSink.setSuccessor(modelSink);
        mv.setPipeSuccessor(toSink);

        // returning an animation renderer which handles clearing of the
        // viewport and computation of the praction
        return new AnimationRenderer(pd) {
            // TODO rotation variable goes in here
            float pos = 0f;

            /** This method is called for every frame from the JavaFX Animation
             * system (using an AnimationTimer, see AnimationRenderer).
             * @param fraction the time which has passed since the last render call in a fraction of a second
             * @param model    the model to render
             */
            @Override
            protected void render(float fraction, Model model) {
                // TODO compute rotation in radians
                pos += fraction;
                double radians = pos % (2 * Math.PI);
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

                source.write(model);
            }
        };
    }
}
